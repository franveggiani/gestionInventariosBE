package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.DTOs.CrearProveedorArticuloRequest;
import com.yarmovezzoli.gestioninv.DTOs.EditarProveedorArticuloDTO;
import com.yarmovezzoli.gestioninv.DTOs.ProveedorxArticuloDTO;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import com.yarmovezzoli.gestioninv.Entities.ProveedorArticulo;

import java.util.List;
import java.util.Optional;

public interface ProveedorArticuloService extends BaseService<ProveedorArticulo,Long>{
    public ProveedorArticulo nuevoProveedorArticulo(CrearProveedorArticuloRequest crearProveedorArticuloRequest) throws Exception;
    public Optional<ProveedorArticulo> modificarDatosProveedorArticulo(Long id, EditarProveedorArticuloDTO editarProveedorArticuloDTO) throws Exception;
    public List<ProveedorxArticuloDTO> obtenerArticulosPorProveedor(Long proveedorId) throws Exception;
    public Proveedor getPAPredeterminado(Long articuloId) throws Exception;
}