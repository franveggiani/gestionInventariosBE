package com.yarmovezzoli.gestioninv.Services.ModuloInventarios;

import com.yarmovezzoli.gestioninv.DTOs.CrearProveedorRequest;
import com.yarmovezzoli.gestioninv.DTOs.EditarProveedorDTO;
import com.yarmovezzoli.gestioninv.DTOs.ModuloInventarios.DTODatosInventario;
import com.yarmovezzoli.gestioninv.DTOs.ModuloInventarios.DTODatosInventarioOutput;
import com.yarmovezzoli.gestioninv.DTOs.PrediccionDemandaRequest;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.PrediccionDemanda;
import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import com.yarmovezzoli.gestioninv.Entities.ProveedorArticulo;
import com.yarmovezzoli.gestioninv.Enums.ModeloInventario;
import com.yarmovezzoli.gestioninv.Enums.TipoPrediccion;
import com.yarmovezzoli.gestioninv.Factory.CalculosInventarioFactory;
import com.yarmovezzoli.gestioninv.Factory.PrediccionDemandaFactory;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.BaseRepository;
import com.yarmovezzoli.gestioninv.Repositories.ProveedorArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.ProveedorRepository;
import com.yarmovezzoli.gestioninv.Services.BaseServiceImpl;
import com.yarmovezzoli.gestioninv.Strategy.CalculosInventario;
import com.yarmovezzoli.gestioninv.Strategy.PrediccionDemandaStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

            proveedorRepository.save(proveedor);

            if (crearProveedorRequest.getArticulos() != null) {

                List<ProveedorArticulo> proveedorArticuloList = new ArrayList<>();
                List<DTODatosInventario> articulos = crearProveedorRequest.getArticulos();

                for (DTODatosInventario articulo : articulos) {
                    articulo.setIdProveedor(0L);
                    proveedorArticuloList.add(crearProveedorArticulo(articulo));
                }

                for (ProveedorArticulo proveedorArticulo : proveedorArticuloList) {
                    proveedorArticulo.setProveedor(proveedor);
                    proveedorArticuloRepository.save(proveedorArticulo);
                }
            }
            return proveedor;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ProveedorArticulo crearProveedorArticulo(DTODatosInventario datosInventario) throws Exception {
        try {
            Optional<Articulo> articuloOptional = articuloRepository.findById(datosInventario.getIdArticulo());
            Optional<Proveedor> proveedorOptional = proveedorRepository.findById(datosInventario.getIdProveedor());

            System.out.println(datosInventario.getL());
            System.out.println(datosInventario.getZ());
            System.out.println(datosInventario.getT());

            if (articuloOptional.isPresent()) {
                Articulo articulo = articuloOptional.get();
                ModeloInventario modeloInventario = articulo.getModeloInventario();

                CalculosInventario calculosInventario = calculosInventarioFactory.getCalculosInventario(modeloInventario);
                DTODatosInventarioOutput dtoDatosInventarioOutput = calculosInventario.getDatosInventario(datosInventario);

                articulo.setPuntoPedido(dtoDatosInventarioOutput.getROP());
                articulo.setStockSeguridad((int) dtoDatosInventarioOutput.getStockSeguridad());
                articulo.setModeloInventario(modeloInventario);

                System.out.println("Articulo: " + articulo.getNombre() + " StockSeguridad: " + dtoDatosInventarioOutput.getStockSeguridad() + " ROP: " + dtoDatosInventarioOutput.getROP());

                ProveedorArticulo proveedorArticulo = new ProveedorArticulo();

                if (datosInventario.getIdProveedor()!= 0L && proveedorOptional.isPresent()) {
                    proveedorArticulo.setProveedor(proveedorOptional.get());
                } else if (datosInventario.getIdProveedor() == 0L) {
                    proveedorArticulo.setProveedor(null);
                } else {
                    throw new Exception("El proveedor no existe");
                }

                proveedorArticulo.setArticulo(articulo);
                proveedorArticulo.setDemoraPromedio(datosInventario.getL());
                proveedorArticulo.setCostoPedido(datosInventario.getCostoPedido());
                proveedorArticulo.setEOQ(dtoDatosInventarioOutput.getQ());
                proveedorArticulo.setCGI(dtoDatosInventarioOutput.getCGI());
                proveedorArticulo.setPrecioPorUnidad(datosInventario.getPrecioUnidad());
                proveedorArticulo.setNivelDeServicio(datosInventario.getZ());
                proveedorArticulo.setPrecioPorUnidad(datosInventario.getPrecioUnidad());

                //proveedorArticulo.setEstadoActual(datosInventario.getEstadoProveedorArticulo());

                if (modeloInventario.equals(ModeloInventario.INTERVALO_FIJO)){
                    proveedorArticulo.setPeriodoRevision(datosInventario.getT());
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

    @Override
    public ProveedorArticulo setPAPredeterminado(Long idProveedorArticulo) throws Exception{
        try{
            Optional<ProveedorArticulo> proveedorArticuloOptional = proveedorArticuloRepository.findById(idProveedorArticulo);
            ProveedorArticulo proveedorArticulo;
            if (proveedorArticuloOptional.isPresent()) {

                proveedorArticulo = proveedorArticuloOptional.get();
                Articulo articulo = proveedorArticulo.getArticulo();

                ModeloInventario modeloInventario = articulo.getModeloInventario();

                CalculosInventario calculosInventario = calculosInventarioFactory.getCalculosInventario(modeloInventario);

                List<ProveedorArticulo> proveedorArticuloList = proveedorArticuloRepository.findByPredeterminado(true, articulo);

                //Buscamos si hay alguno predeterminado y lo seteamos como no false
                for (ProveedorArticulo pa : proveedorArticuloList){
                    pa.setEsPredeterminado(false);
                    proveedorArticuloRepository.save(pa);
                }

                proveedorArticulo.setEsPredeterminado(true);

                DTODatosInventario datosInventario = new DTODatosInventario();
                datosInventario.setIdArticulo(articulo.getId());
                datosInventario.setIdProveedor(proveedorArticulo.getProveedor().getId());
                datosInventario.setL(proveedorArticulo.getDemoraPromedio());
                datosInventario.setCostoPedido(proveedorArticulo.getCostoPedido());
                datosInventario.setYear(proveedorArticulo.getYear());
                datosInventario.setT(proveedorArticulo.getPeriodoRevision());
                datosInventario.setZ(proveedorArticulo.getNivelDeServicio());

                DTODatosInventarioOutput dtoDatosInventarioOutput = calculosInventario.getDatosInventario(datosInventario);

                articulo.setStockSeguridad((int) dtoDatosInventarioOutput.getStockSeguridad());
                articulo.setPuntoPedido(dtoDatosInventarioOutput.getROP());

                articuloRepository.save(articulo);
                proveedorArticuloRepository.save(proveedorArticulo);

                return proveedorArticulo;

            } else {
                throw new NoSuchElementException("El articulo no existe o el proveedor no existe");
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }



}

