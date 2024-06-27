package com.yarmovezzoli.gestioninv.Repositories;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.DemandaHistorica;
import com.yarmovezzoli.gestioninv.Entities.PrediccionDemanda;
import com.yarmovezzoli.gestioninv.Enums.TipoPrediccion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PrediccionDemandaRepository extends BaseRepository<PrediccionDemanda, Long> {

    @Query("SELECT p FROM PrediccionDemanda p WHERE p.articulo = :articulo")
    List<PrediccionDemanda> findByArticulo(@Param("articulo") Articulo articulo);

    @Query("SELECT p FROM PrediccionDemanda p WHERE p.articulo = :articulo AND YEAR(p.fechaPrediccion) = :year")
    List<PrediccionDemanda> findByYearAndArticulo(@Param("articulo") Articulo articulo, @Param("year") Integer year);

    @Query("SELECT p FROM PrediccionDemanda p WHERE p.fechaPrediccion >= :fechaDesde AND p.fechaPrediccion <= :fechaHasta AND p.articulo = :articulo AND p.tipoPrediccion = :tipoPrediccion")
    List<PrediccionDemanda> findeByPeriodoAndArticulo(@Param("articulo") Articulo articulo, @Param("fechaDesde") LocalDate fechaDesde, @Param("fechaHasta") LocalDate fechaHasta, @Param("tipoPrediccion") TipoPrediccion tipoPrediccion);


}
