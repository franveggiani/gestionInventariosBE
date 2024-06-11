package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Enums.EstadoOrden;
import com.yarmovezzoli.gestioninv.Enums.ModeloInventario;

import java.util.List;

public interface ArticuloService {
    List<Articulo> buscar(String filtro) throws Exception;
    List<ModeloInventario> getTiposInventario() throws Exception;
    public List<EstadoOrden> getEstadoOrdenDeCompra() throws Exception;


}
