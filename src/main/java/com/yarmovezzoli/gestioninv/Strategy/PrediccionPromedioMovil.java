package com.yarmovezzoli.gestioninv.Strategy;

import com.yarmovezzoli.gestioninv.Entities.DemandaHistorica;

import java.util.List;
import java.util.Map;

public class PrediccionPromedioMovil implements PrediccionDemandaStrategy {

    @Override
    public double predecirDemanda(Map<String, Object> parametros) {

        Object demandaHistoricaObj = parametros.get("demandaHistoricaList");

        if (demandaHistoricaObj instanceof List<?>){
            List<DemandaHistorica> demandaHistoricasList = (List<DemandaHistorica>) demandaHistoricaObj;

            double[] sumatoria = {0};
            int[] n = {0};

            demandaHistoricasList.forEach(demandaHistorica -> {
                sumatoria[0] += demandaHistorica.getCantidadTotal();
                n[0] += 1;
                //System.out.println(sumatoria[0]);
            });

            double promedioMovil = sumatoria[0] / n[0];

            return promedioMovil;

        } else {
            throw new IllegalArgumentException("El parámetro solicitado no es correcto");
        }
    }
}
