package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.Entities.OrdenCompra;
import java.util.List;

public interface OrdenCompraService extends BaseService<OrdenCompra,Long>{
    public List<OrdenCompra> obtenerOrdenesCompraPendientesPorArticulo(Long articuloId) throws Exception; //revisa para no pedir 2 veces lo mismo

}
