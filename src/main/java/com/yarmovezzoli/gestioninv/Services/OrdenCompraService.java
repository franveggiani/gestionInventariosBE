package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.DTOs.OrdenCompraDTO;
import com.yarmovezzoli.gestioninv.Entities.OrdenCompra;
import java.util.List;

public interface OrdenCompraService extends BaseService<OrdenCompra,Long>{
    public OrdenCompra newOrdenCompra (OrdenCompraDTO ordenCompraDTO) throws Exception;
    public List<OrdenCompra> mostrarOrdenesCompra () throws Exception;

    public OrdenCompra confirmarOrden (Long id) throws Exception;
}
