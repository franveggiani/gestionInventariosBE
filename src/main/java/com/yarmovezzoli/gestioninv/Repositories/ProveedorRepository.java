package com.yarmovezzoli.gestioninv.Repositories;

import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends BaseRepository<Proveedor,Long>{

    @Query(value = "SELECT p FROM Proveedor p WHERE p.id = :id")
    Proveedor buscarPorId(@Param("id") Long id);

    @Query(value = "SELECT p FROM Proveedor p WHERE p.nombre LIKE '%?filtroNombre%' ")
    List<Proveedor> buscarPorNombre(@Param("filtroNombre") String filtroNombre);

}
