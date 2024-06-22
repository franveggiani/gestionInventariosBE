package com.yarmovezzoli.gestioninv.Repositories;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.DemandaHistorica;
import com.yarmovezzoli.gestioninv.Enums.TipoPeriodo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DemandaHistoricaRepository extends BaseRepository<DemandaHistorica, Long> {

    @Query(value = "SELECT dh FROM DemandaHistorica dh WHERE dh.fechaDesde = :fechaDesde")
    Optional<DemandaHistorica> findByFechaDesde(@Param("fechaDesde") LocalDate fechaDesde);

    @Query(value = "SELECT dh FROM DemandaHistorica dh WHERE tipoPeriodo = :tipoPeriodo AND articulo = :articulo")
    List<DemandaHistorica> findAllByArticuloYTipoPeriodo(@Param("articulo") Articulo articulo, @Param("tipoPeriodo") TipoPeriodo tipoPeriodo);
}
