package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.DTOs.OrdenCompraDTO;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.OrdenCompra;
import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import com.yarmovezzoli.gestioninv.Enums.EstadoOrden;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.BaseRepository;
import com.yarmovezzoli.gestioninv.Repositories.OrdenCompraRepository;
import com.yarmovezzoli.gestioninv.Repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.yarmovezzoli.gestioninv.Enums.EstadoOrden.PENDIENTE;

@Service
public class OrdenCompraServiceImpl extends BaseServiceImpl<OrdenCompra,Long> implements OrdenCompraService {
    @Autowired
    private OrdenCompraRepository ordencompraRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    public OrdenCompraServiceImpl(BaseRepository<OrdenCompra, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    public List<OrdenCompra> mostrarOrdenesCompra () throws Exception{
        try {
            List<OrdenCompra> ordenCompras = ordencompraRepository.findAll();
            return ordenCompras;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public OrdenCompra confirmarOrden(Long id) throws Exception {
        try {
            Optional<OrdenCompra> ordenCompra = ordencompraRepository.findById(id);
            if (ordenCompra.isPresent()) {
                OrdenCompra orden = ordenCompra.get();
                orden.setNombreEstado(EstadoOrden.CONFIRMADO);
                ordencompraRepository.save(orden);

                //Actualizamos el stock de los articulos
                Articulo articulo = orden.getArticulo();
                articulo.setStockActual(articulo.getStockActual() + orden.getCantidad());
                articuloRepository.save(articulo);

                return orden;

            } else {
                throw new Exception("No se encontró la orden de compra");
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public OrdenCompra newOrdenCompra(OrdenCompraDTO ordenCompraDTO) throws Exception {

        Optional<OrdenCompra> ultimaOrdenCompra = ordencompraRepository.findTopByOrderByNroOrdenCompraDesc();
        OrdenCompra ordenCompra = new OrdenCompra();

        if (ultimaOrdenCompra.isEmpty()) {
            ordenCompra.setNroOrdenCompra(0);
        } else {
            int nummasaltoOC = ultimaOrdenCompra.get().getNroOrdenCompra();
            ordenCompra.setNroOrdenCompra(nummasaltoOC + 1);
        }

        ordenCompra.setCantidad(ordenCompraDTO.getCantidad());
        ordenCompra.setDemoraEstimada(ordenCompraDTO.getDemoraEstimada());
        ordenCompra.setFechaHoraAlta(LocalDate.now());
        ordenCompra.setNombreEstado(PENDIENTE);

        Optional<Proveedor> p1 = proveedorRepository.findById(ordenCompraDTO.getProveedorId());
        if (p1.isPresent()) {
            Proveedor proveedor = p1.get();
            ordenCompra.setProveedor(proveedor);

            Optional<Articulo> a1 = articuloRepository.findById(ordenCompraDTO.getArticuloId());

            if (a1.isPresent()) {
                Articulo articulo = a1.get();
                ordenCompra.setArticulo(articulo);

                ordencompraRepository.save(ordenCompra);
            } else {
                throw new Exception("No se encontró el articulo");
            }
        } else {
            throw new Exception("No se encontró al proveedor");
        }

        return ordenCompra;
    }
}