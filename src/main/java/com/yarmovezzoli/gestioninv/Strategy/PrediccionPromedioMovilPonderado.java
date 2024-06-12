package com.yarmovezzoli.gestioninv.Strategy;

import java.util.List;
import java.util.Map;

public class PrediccionPromedioMovilPonderado implements PrediccionDemandaStrategy{

    @Override
    public double predecirDemanda(Map<String, Object> parametros) {
        Double[] ponderaciones = (Double[]) parametros.get("ponderaciones");
        List<Double> arregloCantidades = (List<Double>) parametros.get("arregloCantidades");

        float[] denominador = {0};
        float[] numerador = {0};

        if (ponderaciones.length!= arregloCantidades.size()){
            throw new IllegalArgumentException("El tamaño de las ponderaciones y el arreglo de cantidades no coinciden");
        } else {
            for (int i = 0; i < ponderaciones.length; i++) {
                numerador[0] += ponderaciones[i] * arregloCantidades.get(i);
                denominador[0] += ponderaciones[i];
                System.out.println(ponderaciones[i]);
                System.out.println(arregloCantidades.get(i));
            }
        }

        return (numerador[0] / denominador[0]);
    }
}
