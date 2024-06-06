package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import java.util.List;

public interface ProveedorService extends BaseService<Proveedor,Long>{

    List<Proveedor> buscarPorNombre(String filtroNombre) throws Exception;
    public List<Proveedor> mostrarProveedores() throws Exception;

}
