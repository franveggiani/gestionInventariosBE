package com.yarmovezzoli.gestioninv.Repositories;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.Venta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface VentaRepository extends BaseRepository<Venta, Long> {

    @Query(value = "SELECT v FROM Venta v WHERE v.fechaHoraAlta <= :fechaHasta AND v.fechaHoraAlta >= :fechaDesde AND articulo = :articulo")
    List<Venta> listaPorPeriodo(@Param("fechaDesde") LocalDate fechaDesde, @Param("fechaHasta") LocalDate fechaHasta, @Param("articulo") Articulo articulo);

}
