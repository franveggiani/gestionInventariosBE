package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.DTOs.CrearProveedorArticuloDTO;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import com.yarmovezzoli.gestioninv.Entities.ProveedorArticulo;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.BaseRepository;
import com.yarmovezzoli.gestioninv.Repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProveedorArticuloServiceImpl extends BaseServiceImpl<ProveedorArticulo,Long> implements ProveedorArticuloService {
    public ProveedorArticuloServiceImpl(BaseRepository<ProveedorArticulo, Long> baseRepository) {
        super(baseRepository);
    }
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ArticuloRepository articuloRepository;

    @Override
    public ProveedorArticulo nuevoProveedorArticulo(CrearProveedorArticuloDTO crearProveedorArticuloDTO) throws Exception {
        ProveedorArticulo proveedorArticulo = new ProveedorArticulo();

        Optional<Articulo> articuloOptional = articuloRepository.findById(crearProveedorArticuloDTO.getArticuloId());
        if (!articuloOptional.isPresent()) {
            throw new Exception("Articulo no encontrado");
        }
        Articulo articulo = articuloOptional.get();

        Optional<Proveedor> proveedorOptional = proveedorRepository.findById(crearProveedorArticuloDTO.getProveedorId());
        if (!proveedorOptional.isPresent()) {
            throw new Exception("Proveedor no encontrado");
        }
        Proveedor proveedor = proveedorOptional.get();

        proveedorArticulo.setArticuloId(articulo);
        proveedorArticulo.setDemoraPromedio(crearProveedorArticuloDTO.getDemora());
        proveedorArticulo.setProveedor(proveedor);


        baseRepository.save(proveedorArticulo);
        return proveedorArticulo;
    }
}