package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.Entities.Articulo;

import java.util.List;

public interface ArticuloService {
    List<Articulo> buscarProductoPorNombre (String filtro) throws Exception;
}
