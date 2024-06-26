package com.yarmovezzoli.gestioninv.Repositories;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.DemandaHistorica;
import com.yarmovezzoli.gestioninv.Entities.PrediccionDemanda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PrediccionDemandaRepository extends BaseRepository<PrediccionDemanda, Long> {

    @Query("SELECT p FROM PrediccionDemanda p WHERE p.articulo = :articulo")
    List<PrediccionDemanda> findByArticulo(@Param("articulo") Articulo articulo);

    @Query("SELECT p FROM PrediccionDemanda p WHERE p.articulo = :articulo AND YEAR(p.fechaPrediccion) = :year")
    List<PrediccionDemanda> findByYearAndArticulo(@Param("articulo") Articulo articulo, @Param("year") Integer year);

}
