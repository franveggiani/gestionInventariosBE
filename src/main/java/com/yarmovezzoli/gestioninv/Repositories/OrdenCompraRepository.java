package com.yarmovezzoli.gestioninv.Repositories;

import com.yarmovezzoli.gestioninv.Entities.OrdenCompra;
import com.yarmovezzoli.gestioninv.Enums.EstadoOrden;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenCompraRepository extends BaseRepository<OrdenCompra, Long>{
    @Query(value = "SELECT oc FROM OrdenCompra oc WHERE oc.id = :id")
    OrdenCompra buscarPorId(@Param("id") Long id);
    @Query("SELECT oc FROM OrdenCompra oc ORDER BY oc.nroOrdenCompra DESC")
    Optional<OrdenCompra> findTopByOrderByNroOrdenCompraDesc();

}