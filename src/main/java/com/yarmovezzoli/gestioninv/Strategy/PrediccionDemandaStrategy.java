package com.yarmovezzoli.gestioninv.Strategy;

import java.util.Map;

public interface PrediccionDemandaStrategy {

    double predecirDemanda(Map<String, Object> parametros);

}
