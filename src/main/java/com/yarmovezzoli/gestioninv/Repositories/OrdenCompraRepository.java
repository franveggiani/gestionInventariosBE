package com.yarmovezzoli.gestioninv.Repositories;

import com.yarmovezzoli.gestioninv.Entities.OrdenCompra;
import com.yarmovezzoli.gestioninv.Enums.EstadoOrden;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenCompraRepository extends BaseRepository<OrdenCompra, Long>{

    @Query(value = "SELECT oc FROM OrdenCompra oc WHERE oc.id = :id")
    OrdenCompra buscarPorId(@Param("id") Long id);

    @Query("SELECT oc FROM OrdenCompra oc " +
            "JOIN oc.estadoOrdenCompra eoc " +
            "JOIN oc.articuloId a " +
            "WHERE eoc.nombreEstado = :PENDIENTE " +
            "AND a.id = :articuloId")
    List<OrdenCompra> findOrdenesCompraPendientesByArticulo(@Param("articuloId") Long articuloId,
                                                            @Param("PENDIENTE") EstadoOrden estadoPendiente);
}