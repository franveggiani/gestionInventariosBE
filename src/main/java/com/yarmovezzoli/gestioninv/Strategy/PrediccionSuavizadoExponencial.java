package com.yarmovezzoli.gestioninv.Strategy;

import com.yarmovezzoli.gestioninv.DTOs.PrediccionDemandaRequest;
import com.yarmovezzoli.gestioninv.Entities.PrediccionDemanda;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PrediccionSuavizadoExponencial implements PrediccionDemandaStrategy{
    @Override
    public List<PrediccionDemanda> predecirDemanda(PrediccionDemandaRequest prediccionDemandaRequest) {
        return null;
    }
}
