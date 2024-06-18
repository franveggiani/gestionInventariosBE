package com.yarmovezzoli.gestioninv.Repositories;

import com.yarmovezzoli.gestioninv.Entities.DemandaHistorica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface DemandaHistoricaRepository extends BaseRepository<DemandaHistorica, Long> {

    @Query(value = "SELECT dh FROM DemandaHistorica dh WHERE dh.fechaDesde = :fechaDesde")
    Optional<DemandaHistorica> findByFechaDesde(@Param("fechaDesde") LocalDate fechaDesde);

}
