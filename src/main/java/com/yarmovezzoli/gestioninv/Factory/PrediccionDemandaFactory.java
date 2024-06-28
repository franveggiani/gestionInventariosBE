package com.yarmovezzoli.gestioninv.Factory;

import com.yarmovezzoli.gestioninv.Enums.TipoPrediccion;
import com.yarmovezzoli.gestioninv.Strategy.PrediccionDemandaStrategy;
import com.yarmovezzoli.gestioninv.Strategy.PrediccionPromedioMovil;
import com.yarmovezzoli.gestioninv.Strategy.PrediccionPromedioMovilPonderado;
import com.yarmovezzoli.gestioninv.Strategy.PrediccionSuavizadoExponencial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class PrediccionDemandaFactory {

    @Autowired
    private ApplicationContext applicationContext;

    private static PrediccionDemandaFactory instance;

    public PrediccionDemandaFactory() {
    }

    public static synchronized PrediccionDemandaFactory getInstance(){
        if (instance == null){
            instance = new PrediccionDemandaFactory();
        }
        return instance;
    }

    public PrediccionDemandaStrategy getPrediccionDemandaStrategy(TipoPrediccion tipoPrediccion){
        if (tipoPrediccion.equals(TipoPrediccion.PROM_MOVIL)){
            return applicationContext.getBean(PrediccionPromedioMovil.class);
        } else if (tipoPrediccion.equals(TipoPrediccion.PROM_MOVIL_PONDERADO)){
            return applicationContext.getBean(PrediccionPromedioMovilPonderado.class);
        } else if (tipoPrediccion.equals(TipoPrediccion.EXPONENCIAL)){
            return applicationContext.getBean(PrediccionSuavizadoExponencial.class);
        } else {
            throw new Error("No existe este tipo de predicción");
        }
    }

}
