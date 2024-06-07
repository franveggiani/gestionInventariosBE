package com.yarmovezzoli.gestioninv.Strategy;

import com.yarmovezzoli.gestioninv.Entities.DemandaHistorica;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PrediccionPromedioMovil implements PrediccionDemandaStrategy {

    @Override
    public double predecirDemanda(Map<String, Object> parametros) {

        Object arregloCantidadesObj = parametros.get("arregloCantidades");

        if (arregloCantidadesObj instanceof List<?>){
            List<Double> arregloCantidades = (List<Double>) arregloCantidadesObj;

            double[] sumatoria = {0};
            int[] n = {0};

            arregloCantidades.forEach(cantidad ->{
                sumatoria[0] += cantidad;
                n[0] += 1;
                System.out.println(cantidad);
            });

            return sumatoria[0] / n[0];

        } else {
            throw new IllegalArgumentException("El parámetro solicitado no es correcto");
        }
    }
}
