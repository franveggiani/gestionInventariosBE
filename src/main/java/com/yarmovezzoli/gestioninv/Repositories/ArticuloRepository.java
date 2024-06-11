package com.yarmovezzoli.gestioninv.Repositories;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.ModeloInventario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticuloRepository extends BaseRepository<Articulo, Long> {
    @Query(
            value = "SELECT * FROM Articulo a WHERE a.nombre LIKE %:filtro% ",
            nativeQuery = true
    )
    List<Articulo> buscarArticuloPorNombre(@Param("filtro") String filtro);

}
