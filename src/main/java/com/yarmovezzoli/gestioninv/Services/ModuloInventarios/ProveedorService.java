package com.yarmovezzoli.gestioninv.Services.ModuloInventarios;

import com.yarmovezzoli.gestioninv.DTOs.CrearProveedorArticuloRequest;
import com.yarmovezzoli.gestioninv.DTOs.EditarProveedorDTO;
import com.yarmovezzoli.gestioninv.DTOs.CrearProveedorRequest;
import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import com.yarmovezzoli.gestioninv.Entities.ProveedorArticulo;
import com.yarmovezzoli.gestioninv.Services.BaseService;

import java.util.List;
import java.util.Optional;

public interface ProveedorService extends BaseService<Proveedor,Long> {

    List<Proveedor> buscarPorNombre(String filtroNombre) throws Exception;
    public List<Proveedor> mostrarProveedores() throws Exception;
    public Optional<Proveedor> modificarDatosProveedor(Long id, EditarProveedorDTO editarProveedorDTO) throws Exception;
    Proveedor crearProveedor(CrearProveedorRequest crearProveedorRequest) throws Exception;
    ProveedorArticulo crearProveedorArticulo(CrearProveedorArticuloRequest crearProveedorArticuloRequest) throws Exception;

}