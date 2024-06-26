package com.yarmovezzoli.gestioninv.Factory;

import com.yarmovezzoli.gestioninv.Enums.ModeloInventario;
import com.yarmovezzoli.gestioninv.Strategy.CalculosInventario;
import com.yarmovezzoli.gestioninv.Strategy.CalculosInventarioRevisionContinua;
import com.yarmovezzoli.gestioninv.Strategy.CalculosInventarioRevisionPeriodica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CalculosInventarioFactory {

    @Autowired
    private ApplicationContext applicationContext;

    private static CalculosInventarioFactory instance;

    public CalculosInventarioFactory() {
    }

    public static CalculosInventarioFactory getInstance() {
        if (instance == null) {
            instance = new CalculosInventarioFactory();
        }
        return instance;
    }

    public CalculosInventario getCalculosInventario(ModeloInventario modelo) {
        if (modelo.equals(ModeloInventario.INTERVALO_FIJO)){
            return applicationContext.getBean(CalculosInventarioRevisionPeriodica.class);
        } else if (modelo.equals(ModeloInventario.LOTE_FIJO)){
            return applicationContext.getBean(CalculosInventarioRevisionContinua.class);
        } else {
            throw new IllegalArgumentException("El modelo no es válido");
        }
    }

}
