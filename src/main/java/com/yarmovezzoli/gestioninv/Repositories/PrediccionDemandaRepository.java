package com.yarmovezzoli.gestioninv.Repositories;

import com.yarmovezzoli.gestioninv.Entities.DemandaHistorica;
import com.yarmovezzoli.gestioninv.Entities.PrediccionDemanda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PrediccionDemandaRepository extends BaseRepository<PrediccionDemanda, Long> {

}
