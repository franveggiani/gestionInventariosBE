package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.DTOs.CrearProveedorArticuloDTO;
import com.yarmovezzoli.gestioninv.Entities.ProveedorArticulo;

public interface ProveedorArticuloService extends BaseService<ProveedorArticulo,Long>{
    public ProveedorArticulo nuevoProveedorArticulo(CrearProveedorArticuloDTO crearProveedorArticuloDTO) throws Exception;
}