package com.yarmovezzoli.gestioninv.Strategy;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.PrediccionDemanda;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PrediccionPromedioMovil implements PrediccionDemandaStrategy {

    @Override
    public PrediccionDemanda predecirDemanda(Map<String, Object> parametros) {

        Object arregloCantidadesObj = parametros.get("arregloCantidades");

        if (arregloCantidadesObj instanceof List<?>){
            List<Integer> arregloCantidades = (List<Integer>) arregloCantidadesObj;

            Articulo articulo = (Articulo) parametros.get("articulo");
            LocalDate fechaDesdePrediccion = (LocalDate) parametros.get("fechaDesdePrediccion");
            LocalDate fechaHastaPrediccion = (LocalDate) parametros.get("fechaHastaPrediccion");

            int sumatoria = 0;
            int n = 0;

            for (Integer cantidad : arregloCantidades) {
                sumatoria += cantidad;
                n += 1;
            }

            int promedio = Math.round(sumatoria / n);

            PrediccionDemanda prediccion = new PrediccionDemanda();
            prediccion.setArticulo(articulo);
            prediccion.setFechaPrediccion(LocalDate.now());
            System.out.println(prediccion.getFechaPrediccion());
            prediccion.setFechaDesde(fechaDesdePrediccion);
            prediccion.setFechaHasta(fechaHastaPrediccion);
            prediccion.setPrediccion(promedio);

            return prediccion;

        } else {
            throw new IllegalArgumentException("El parámetro solicitado no es correcto");
        }
    }
}
