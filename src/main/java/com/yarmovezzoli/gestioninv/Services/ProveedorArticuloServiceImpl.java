package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.DTOs.CrearProveedorArticuloRequest;
import com.yarmovezzoli.gestioninv.DTOs.EditarProveedorArticuloDTO;
import com.yarmovezzoli.gestioninv.DTOs.ProveedorxArticuloDTO;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import com.yarmovezzoli.gestioninv.Entities.ProveedorArticulo;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.BaseRepository;
import com.yarmovezzoli.gestioninv.Repositories.ProveedorArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;

    @Override
    public ProveedorArticulo nuevoProveedorArticulo(CrearProveedorArticuloRequest crearProveedorArticuloRequest) throws Exception {
        ProveedorArticulo proveedorArticulo = new ProveedorArticulo();

        Optional<Articulo> articuloOptional = articuloRepository.findById(crearProveedorArticuloRequest.getArticuloId());
        if (!articuloOptional.isPresent()) {
            throw new Exception("Articulo no encontrado");
        }
        Articulo articulo = articuloOptional.get();

        Optional<Proveedor> proveedorOptional = proveedorRepository.findById(crearProveedorArticuloRequest.getProveedorId());
        if (!proveedorOptional.isPresent()) {
            throw new Exception("Proveedor no encontrado");
        }
        Proveedor proveedor = proveedorOptional.get();

        proveedorArticulo.setArticulo(articulo);
        //proveedorArticulo.setDemoraPromedio(crearProveedorArticuloRequest.getDemora());
        proveedorArticulo.setProveedor(proveedor);


        baseRepository.save(proveedorArticulo);
        return proveedorArticulo;
    }

    @Override
    public Optional<ProveedorArticulo> modificarDatosProveedorArticulo(Long id, EditarProveedorArticuloDTO editarProveedorArticuloDTO) throws Exception {
            Optional<ProveedorArticulo> proveedorArticuloOptional = proveedorArticuloRepository.findById(id);
            if (proveedorArticuloOptional.isEmpty()) {
                ProveedorArticulo proveedorArticulo = proveedorArticuloOptional.get();
                //proveedorArticulo.setDemoraPromedio(editarProveedorArticuloDTO.getDemora());
                return Optional.ofNullable(proveedorArticuloRepository.save(proveedorArticulo));
            } else {
                throw new Exception("Proveedor-Articulo no encontrado");
            }

        }

    @Override
    public List<ProveedorxArticuloDTO> obtenerArticulosPorProveedor(Long proveedorId) throws Exception {
        List<ProveedorArticulo> proveedorArticulos = proveedorArticuloRepository.findArticulosByProveedorId(proveedorId);

        if (proveedorArticulos.isEmpty()) {
            throw new Exception("Proveedor no encontrado o no tiene artículos asociados");
        }

        List<ProveedorxArticuloDTO> proveedorxArticuloDTOs = new ArrayList<>();

        for (ProveedorArticulo pa : proveedorArticulos) {
            ProveedorxArticuloDTO proveedorxArticuloDTO = new ProveedorxArticuloDTO();
            Articulo articulo = pa.getArticulo();
            proveedorxArticuloDTO.setCostoAlmacenamiento(articulo.getCostoAlmacenamiento());
            proveedorxArticuloDTO.setNombre(articulo.getNombre());
            proveedorxArticuloDTO.setStockActual(articulo.getStockActual());
            proveedorxArticuloDTO.setEstadoArticulo(articulo.getEstadoArticulo());
            proveedorxArticuloDTO.setModeloInventario(articulo.getModeloInventario());
            proveedorxArticuloDTO.setCGI(pa.getCGI());
            proveedorxArticuloDTOs.add(proveedorxArticuloDTO);
        }

        return proveedorxArticuloDTOs;
    }
}