package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.DTOs.CrearProveedorRequest;
import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import org.springframework.stereotype.Service;
import java.util.List;

public interface ProveedorService extends BaseService<Proveedor,Long>{

    List<Proveedor> buscarPorNombre(String filtroNombre) throws Exception;

    Proveedor crearProveedor(CrearProveedorRequest crearProveedorRequest) throws Exception;

}
