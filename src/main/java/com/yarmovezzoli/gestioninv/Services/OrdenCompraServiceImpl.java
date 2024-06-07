package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.DTOs.OrdenCompraDTO;
import com.yarmovezzoli.gestioninv.Entities.OrdenCompra;
import com.yarmovezzoli.gestioninv.Repositories.BaseRepository;
import com.yarmovezzoli.gestioninv.Repositories.OrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.yarmovezzoli.gestioninv.Enums.EstadoOrden.PENDIENTE;

@Service
public class OrdenCompraServiceImpl extends BaseServiceImpl<OrdenCompra,Long> implements OrdenCompraService {
    @Autowired
    private OrdenCompraRepository ordencompraRepository;

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
        ordenCompra.setProveedor(ordenCompraDTO.getProveedor());
        ordenCompra.setNombreEstado(PENDIENTE);

        return ordenCompra;
    }
}