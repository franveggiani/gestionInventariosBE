package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.DTOs.OrdenCompraDTO;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.OrdenCompra;
import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.BaseRepository;
import com.yarmovezzoli.gestioninv.Repositories.OrdenCompraRepository;
import com.yarmovezzoli.gestioninv.Repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public OrdenCompra newOrdenCompra(OrdenCompraDTO ordenCompraDTO) throws Exception {

        OrdenCompra ultimaOrdenCompra = ordencompraRepository.findTopByOrderByNroOrdenCompraDesc();
        int nummasaltoOC = ultimaOrdenCompra.getNroOrdenCompra();

        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setNroOrdenCompra(nummasaltoOC+1);
        ordenCompra.setCantidad(ordenCompraDTO.getCantidad());
        ordenCompra.setDemoraEstimada(ordenCompraDTO.getDemoraEstimada());
        ordenCompra.setFechaHoraAlta(LocalDate.now());

        Optional<Proveedor> p1 =  proveedorRepository.findById(ordenCompraDTO.getProveedorId());

            Proveedor proveedor = p1.get();
            ordenCompra.setProveedor(proveedor);


        Optional<Articulo> a1 =  articuloRepository.findById(ordenCompraDTO.getArticuloId());

        Articulo articulo = a1.get();
        ordenCompra.setArticulo(articulo);

            if (p1.isPresent()) {
                System.out.println("Proveedor encontrado");
        }
            else {
                throw new Exception("No se encontró al proveedor");
            }

            if (a1.isPresent()){
            System.out.println("Articulo encontrado");
        }
            else {
                throw new Exception("No se enontró el articulo");
            }

        ordenCompra.setNombreEstado(PENDIENTE);

        return ordenCompra;
    }
}