package com.yarmovezzoli.gestioninv.Strategy;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.PrediccionDemanda;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PrediccionPromedioMovilPonderado implements PrediccionDemandaStrategy{

    @Override
    public PrediccionDemanda predecirDemanda(Map<String, Object> parametros) {

        Double[] ponderaciones = (Double[]) parametros.get("ponderaciones");
        List<Double> arregloCantidades = (List<Double>) parametros.get("arregloCantidades");

        float[] denominador = {0};
        float[] numerador = {0};
        float promedioPonderado;

        if (ponderaciones.length!= arregloCantidades.size()){
            throw new IllegalArgumentException("El tamaño de las ponderaciones y el arreglo de cantidades no coinciden");
        } else {
            for (int i = 0; i < ponderaciones.length; i++) {
                numerador[0] += ponderaciones[i] * arregloCantidades.get(i);
                denominador[0] += ponderaciones[i];
                System.out.println(ponderaciones[i]);
                System.out.println(arregloCantidades.get(i));
            }
            promedioPonderado = numerador[0] / denominador[0];
        }

        Articulo articulo = (Articulo) parametros.get("articulo");
        LocalDate fechaDesdePrediccion = (LocalDate) parametros.get("fechaDesdePrediccion");
        LocalDate fechaHastaPrediccion = (LocalDate) parametros.get("fechaHastaPrediccion");

        PrediccionDemanda prediccion = new PrediccionDemanda();

        prediccion.setArticulo(articulo);
        prediccion.setFechaDesde(fechaDesdePrediccion);
        prediccion.setFechaHasta(fechaHastaPrediccion);
        prediccion.setPrediccion(Double.valueOf(promedioPonderado));

        return prediccion;
    }
}