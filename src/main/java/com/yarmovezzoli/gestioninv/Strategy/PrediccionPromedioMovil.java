package com.yarmovezzoli.gestioninv.Strategy;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.DemandaHistorica;
import com.yarmovezzoli.gestioninv.Entities.PrediccionDemanda;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PrediccionPromedioMovil implements PrediccionDemandaStrategy {

    @Override
    public PrediccionDemanda predecirDemanda(Map<String, Object> parametros) {

        Object arregloCantidadesObj = parametros.get("arregloCantidades");

        if (arregloCantidadesObj instanceof List<?>){
            List<Double> arregloCantidades = (List<Double>) arregloCantidadesObj;

            Articulo articulo = (Articulo) parametros.get("articulo");
            LocalDate fechaDesdePrediccion = (LocalDate) parametros.get("fechaDesdePrediccion");
            LocalDate fechaHastaPrediccion = (LocalDate) parametros.get("fechaHastaPrediccion");

            double[] sumatoria = {0};
            int[] n = {0};

            arregloCantidades.forEach(cantidad ->{
                sumatoria[0] += cantidad;
                n[0] += 1;
                System.out.println(cantidad);
            });

            Double promedio = sumatoria[0] / n[0];

            PrediccionDemanda prediccion = new PrediccionDemanda();
            prediccion.setArticulo(articulo);
            prediccion.setFechaPrediccionRealizada(LocalDate.now());
            prediccion.setFechaDesde(fechaDesdePrediccion);
            prediccion.setFechaHasta(fechaHastaPrediccion);
            prediccion.setPrediccion(promedio);

            return prediccion;

        } else {
            throw new IllegalArgumentException("El parámetro solicitado no es correcto");
        }
    }
}
