package com.yarmovezzoli.gestioninv.Services.ModuloInventarios;

import com.yarmovezzoli.gestioninv.DTOs.ArticuloCrearProveedorRequest;
import com.yarmovezzoli.gestioninv.DTOs.CrearProveedorRequest;
import com.yarmovezzoli.gestioninv.DTOs.EditarProveedorDTO;
import com.yarmovezzoli.gestioninv.DTOs.ModuloInventarios.DTODatosInventario;
import com.yarmovezzoli.gestioninv.DTOs.ModuloInventarios.DTODatosInventarioOutput;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.EstadoProveedorArticulo;
import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import com.yarmovezzoli.gestioninv.Entities.ProveedorArticulo;
import com.yarmovezzoli.gestioninv.Enums.ModeloInventario;
import com.yarmovezzoli.gestioninv.Factory.CalculosInventarioFactory;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.BaseRepository;
import com.yarmovezzoli.gestioninv.Repositories.ProveedorArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.ProveedorRepository;
import com.yarmovezzoli.gestioninv.Services.BaseServiceImpl;
import com.yarmovezzoli.gestioninv.Strategy.CalculosInventario;
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
    @Autowired
    private CalculosInventarioFactory calculosInventarioFactory;

    public ProveedorServiceImpl(BaseRepository<Proveedor, Long> baseRepository, ArticuloRepository articuloRepository, ProveedorRepository proveedorRepository, ProveedorArticuloRepository proveedorArticuloRepository, CalculosInventarioFactory calculosInventarioFactory) {
        super(baseRepository);
        this.articuloRepository = articuloRepository;
        this.proveedorRepository = proveedorRepository;
        this.proveedorArticuloRepository = proveedorArticuloRepository;
        this.calculosInventarioFactory = calculosInventarioFactory;
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
//                                proveedorArticulo.setDemoraPromedio(articulo.getDemora());
//                                proveedorArticulo.setCostoPedido(articulo.getCostoPedido());
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
    public ProveedorArticulo crearProveedorArticulo(DTODatosInventario datosInventario) throws Exception {
        try {

            ModeloInventario modeloInventario = datosInventario.getModeloInventario();

            Optional<Articulo> articuloOptional = articuloRepository.findById(datosInventario.getIdArticulo());
            Optional<Proveedor> proveedorOptional = proveedorRepository.findById(datosInventario.getIdProveedor());

            System.out.println(datosInventario.getL());
            System.out.println(datosInventario.getZ());
            System.out.println(datosInventario.getT());

            if (articuloOptional.isPresent() && proveedorOptional.isPresent()) {
                CalculosInventario calculosInventario = calculosInventarioFactory.getCalculosInventario(modeloInventario);
                DTODatosInventarioOutput dtoDatosInventarioOutput = calculosInventario.getDatosInventario(datosInventario);

                Articulo articulo = articuloOptional.get();
                articulo.setPuntoPedido(dtoDatosInventarioOutput.getROP());
                articulo.setStockSeguridad((int) dtoDatosInventarioOutput.getStockSeguridad());
                articulo.setModeloInventario(modeloInventario);

                ProveedorArticulo proveedorArticulo = new ProveedorArticulo();
                proveedorArticulo.setArticulo(articulo);
                proveedorArticulo.setProveedor(proveedorOptional.get());
                proveedorArticulo.setDemoraPromedio(datosInventario.getL());
                proveedorArticulo.setCostoPedido(datosInventario.getCostoPedido());
                proveedorArticulo.setEOQ(dtoDatosInventarioOutput.getQ());

                //proveedorArticulo.setEstadoActual(datosInventario.getEstadoProveedorArticulo());

                if (modeloInventario.equals(ModeloInventario.INTERVALO_FIJO)){
                    proveedorArticulo.setTiempoPeriodoRevision(datosInventario.getT());
                }

                articuloRepository.save(articulo);
                proveedorArticuloRepository.save(proveedorArticulo);

                return proveedorArticulo;

            } else {
                throw new NoSuchElementException("El articulo no existe o el proveedor no existe");
            }

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}

