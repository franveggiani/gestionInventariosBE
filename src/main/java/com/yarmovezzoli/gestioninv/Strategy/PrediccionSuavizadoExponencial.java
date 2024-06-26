package com.yarmovezzoli.gestioninv.Strategy;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.PrediccionDemanda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrediccionSuavizadoExponencial implements PrediccionDemandaStrategy{
    @Override
    public PrediccionDemanda predecirDemanda(Map<String, Object> parametros) {
        double alpha = (double) parametros.get("alpha");
        List<Double> arregloCantidades = (List<Double>) parametros.get("arregloCantidades");
        List<Double> promedioSuavizadoExp = new ArrayList<>();
        promedioSuavizadoExp.add(arregloCantidades.get(0));

        for (int i=1; i<promedioSuavizadoExp.size(); i++ ){
            double valorSuavizadoExponencial = promedioSuavizadoExp.get(i-1) + alpha *
                    (arregloCantidades.get(i-1)-promedioSuavizadoExp.get(i-1));
            promedioSuavizadoExp.add(valorSuavizadoExponencial);
        }

        //obtengo la última predicción calculada
        double ultimaPrediccionPSE = promedioSuavizadoExp.get(promedioSuavizadoExp.size());

        Articulo articulo = (Articulo) parametros.get("articulo");
        LocalDate fechaDesdePrediccion = (LocalDate) parametros.get("fechaDesdePrediccion");
        LocalDate fechaHastaPrediccion = (LocalDate) parametros.get("fechaHastaPrediccion");

        PrediccionDemanda prediccion = new PrediccionDemanda();

        prediccion.setArticulo(articulo);
        prediccion.setFechaDesde(fechaDesdePrediccion);
        prediccion.setFechaHasta(fechaHastaPrediccion);
        prediccion.setPrediccion(Double.valueOf(ultimaPrediccionPSE));


        return prediccion;
    }
}
