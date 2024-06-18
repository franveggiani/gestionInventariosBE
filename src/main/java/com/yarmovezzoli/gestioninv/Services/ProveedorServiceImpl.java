package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.DTOs.ArticuloCrearProveedorRequest;
import com.yarmovezzoli.gestioninv.DTOs.CrearProveedorRequest;
import com.yarmovezzoli.gestioninv.DTOs.EditarProveedorDTO;
import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import com.yarmovezzoli.gestioninv.Entities.ProveedorArticulo;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.BaseRepository;
import com.yarmovezzoli.gestioninv.Repositories.ProveedorArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProveedorServiceImpl extends BaseServiceImpl<Proveedor,Long> implements ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ArticuloRepository articuloRepository;
    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;

    public ProveedorServiceImpl(BaseRepository<Proveedor, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    public List<Proveedor> buscarPorNombre(String filtroNombre) throws Exception {
        try {
            List<Proveedor> proveedores = proveedorRepository.buscarPorNombre(filtroNombre);
            return proveedores;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<Proveedor> mostrarProveedores() throws Exception {
        try {

            List<Proveedor> proveedores = proveedorRepository.findAll();
            return proveedores;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Optional<Proveedor> modificarDatosProveedor(Long id, EditarProveedorDTO editarProveedorDTO) throws Exception {
        Optional<Proveedor> proveedorCambiar = proveedorRepository.findById(id);
        if (proveedorCambiar.isEmpty()) {
            Proveedor proveedor = proveedorCambiar.get();
            proveedor.setNombre(editarProveedorDTO.getNombre());
            return Optional.ofNullable(proveedorRepository.save(proveedor));
        } else {
            throw new Exception("Proveedor no encontrado");
        }
    }

    @Override
    public Proveedor crearProveedor(CrearProveedorRequest crearProveedorRequest) throws Exception {
            try {
                Proveedor proveedor = new Proveedor();
                proveedor.setNombre(crearProveedorRequest.getNombre());

                List<ArticuloCrearProveedorRequest> articulos = crearProveedorRequest.getArticulos();
                proveedorRepository.save(proveedor);

                articulos.forEach(articulo -> {
                    ProveedorArticulo proveedorArticulo = new ProveedorArticulo();

                    //Buscamos el articulo y creamos el proveedorArticulo
                    articuloRepository.findById(articulo.getIdArticulo())
                            .ifPresentOrElse(articulo1 -> {
                                proveedorArticulo.setArticulo(articulo1);
                                proveedorArticulo.setProveedor(proveedor);
                                proveedorArticulo.setDemoraPromedio(articulo.getDemoraPromedio());
                                //Setear estado de proveedorArticulo
                                proveedorArticuloRepository.save(proveedorArticulo);
                            }, () -> {
                                throw new NoSuchElementException("El articulo no existe");
                            });
                });

                return proveedor;

            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
    }