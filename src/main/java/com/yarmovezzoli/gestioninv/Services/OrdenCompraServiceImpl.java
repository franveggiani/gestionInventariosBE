package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.Entities.OrdenCompra;
import com.yarmovezzoli.gestioninv.Enums.EstadoOrden;
import com.yarmovezzoli.gestioninv.Repositories.BaseRepository;
import com.yarmovezzoli.gestioninv.Repositories.OrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenCompraServiceImpl extends BaseServiceImpl<OrdenCompra,Long> implements OrdenCompraService {
    @Autowired
    private OrdenCompraRepository ordencompraRepository;

    public OrdenCompraServiceImpl(BaseRepository<OrdenCompra, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    public List<OrdenCompra> obtenerOrdenesCompraPendientesPorArticulo(Long articuloId) throws Exception {
        try {
            List<OrdenCompra> ordenComprasPendientes = ordencompraRepository.findOrdenesCompraPendientesByArticulo(articuloId, EstadoOrden.PENDIENTE);
            return ordenComprasPendientes;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}