package com.yarmovezzoli.gestioninv.Services.ModuloInventarios;

import com.yarmovezzoli.gestioninv.DTOs.ArticuloCrearProveedorRequest;
import com.yarmovezzoli.gestioninv.DTOs.CrearProveedorArticuloRequest;
import com.yarmovezzoli.gestioninv.DTOs.CrearProveedorRequest;
import com.yarmovezzoli.gestioninv.DTOs.EditarProveedorDTO;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import com.yarmovezzoli.gestioninv.Entities.ProveedorArticulo;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.BaseRepository;
import com.yarmovezzoli.gestioninv.Repositories.ProveedorArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.ProveedorRepository;
import com.yarmovezzoli.gestioninv.Services.BaseServiceImpl;
import com.yarmovezzoli.gestioninv.Services.ModuloInventarios.ProveedorService;
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

    public ProveedorServiceImpl(BaseRepository<Proveedor, Long> baseRepository, ArticuloRepository articuloRepository, ProveedorRepository proveedorRepository, ProveedorArticuloRepository proveedorArticuloRepository) {
        super(baseRepository);
        this.articuloRepository = articuloRepository;
        this.proveedorRepository = proveedorRepository;
        this.proveedorArticuloRepository = proveedorArticuloRepository;
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

            if (crearProveedorRequest.getArticulos() != null) {
                List<ArticuloCrearProveedorRequest> articulos = crearProveedorRequest.getArticulos();

                articulos.forEach(articulo -> {

                    ProveedorArticulo proveedorArticulo = new ProveedorArticulo();

                    //Buscamos el articulo y creamos el proveedorArticulo
                    //ACÁ USAR EL METODO crearProveedorArticulo() - PROXIMAMENTE
                    articuloRepository.findById(articulo.getIdArticulo())
                            .ifPresentOrElse(articulo1 -> {
                                proveedorArticulo.setArticulo(articulo1);
                                proveedorArticulo.setProveedor(proveedor);
                                proveedorArticulo.setDemoraPromedio(articulo.getDemora());
                                proveedorArticulo.setCostoPedido(articulo.getCostoPedido());
                                //Setear estado de proveedorArticulo
                                proveedorArticuloRepository.save(proveedorArticulo);
                            }, () -> {
                                throw new NoSuchElementException("El articulo no existe");
                            });
                });
            }

            proveedorRepository.save(proveedor);

            return proveedor;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    @Override
    public ProveedorArticulo crearProveedorArticulo(CrearProveedorArticuloRequest crearProveedorArticuloRequest) throws Exception {
        try {
            Long demanda = crearProveedorArticuloRequest.getDemanda();                                                                  //Agregar al Request opcional de crear proveedor con PA
            Double costoPedido = crearProveedorArticuloRequest.getCostoPedido();                                                        //Agregado
            Double demoraPromedio = crearProveedorArticuloRequest.getDemora();                                                          //Agregado
            Double precioPorUnidad = crearProveedorArticuloRequest.getPrecioPorUnidad();                                                //Esto agregarlo al Request para crear Proveedor con PA
            Optional<Articulo> articulo = articuloRepository.findById(crearProveedorArticuloRequest.getArticuloId());
            Optional<Proveedor> proveedor = proveedorRepository.findById(crearProveedorArticuloRequest.getProveedorId());

            if (articulo.isPresent() && proveedor.isPresent()) {                //PENSAR VARIACIÓN PARA EL MODELO DE INTERVALO FIJO
                ProveedorArticulo proveedorArticulo = new ProveedorArticulo();

                Articulo articulo1 = articulo.get();
                Proveedor proveedor1 = proveedor.get();

                proveedorArticulo.setArticulo(articulo1);                                                                               //Setear articulo
                proveedorArticulo.setProveedor(proveedor1);                                                                             //Setear proveedor
                proveedorArticulo.setDemoraPromedio(demoraPromedio);                                                                    //L
                proveedorArticulo.setCostoPedido(costoPedido);                                                                          //Cp
                proveedorArticulo.setDemanda(demanda);                                                                                  //D

                Double costoAlmacenamiento = articulo1.getCostoAlmacenamiento();                                                        //Ca                //Agregar al crear el articulo

                proveedorArticulo.calcularEOQ(costoPedido, costoAlmacenamiento, demanda);                                               //EOQ
                proveedorArticulo.calcularPuntoPedido(demanda, demoraPromedio);                                                         //PP
                proveedorArticulo.calcularCGI(costoPedido, costoAlmacenamiento, demanda, precioPorUnidad, proveedorArticulo.getEOQ());  //CGI

                proveedorArticuloRepository.save(proveedorArticulo);

                return proveedorArticulo;
            } else {
                throw new NoSuchElementException("El articulo o el proveedor no existe");
            }

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    }

