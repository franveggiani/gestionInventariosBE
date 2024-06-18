package com.yarmovezzoli.gestioninv.Strategy;

import com.yarmovezzoli.gestioninv.Entities.PrediccionDemanda;

import java.util.Map;

public class PrediccionSuavizadoExponencial implements PrediccionDemandaStrategy{
    @Override
    public PrediccionDemanda predecirDemanda(Map<String, Object> parametros) {
        return new PrediccionDemanda();
    }
}
