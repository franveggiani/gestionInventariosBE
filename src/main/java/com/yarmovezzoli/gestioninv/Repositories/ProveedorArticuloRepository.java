package com.yarmovezzoli.gestioninv.Repositories;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.ProveedorArticulo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProveedorArticuloRepository extends BaseRepository<ProveedorArticulo, Long> {

    @Query("SELECT pa FROM ProveedorArticulo pa WHERE pa.proveedor.id = :proveedorId")
    List<ProveedorArticulo> findArticulosByProveedorId(@Param("proveedorId") Long proveedorId);
}