package com.yarmovezzoli.gestioninv.Strategy;

import com.yarmovezzoli.gestioninv.DTOs.PrediccionDemandaRequest;
import com.yarmovezzoli.gestioninv.Entities.PrediccionDemanda;

import java.util.List;
import java.util.Map;

public interface PrediccionDemandaStrategy {

    List<PrediccionDemanda> predecirDemanda(PrediccionDemandaRequest prediccionDemandaRequest);

}
