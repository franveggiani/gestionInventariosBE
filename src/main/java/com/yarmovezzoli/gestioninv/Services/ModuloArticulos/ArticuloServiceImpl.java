package com.yarmovezzoli.gestioninv.Services.ModuloArticulos;

import com.yarmovezzoli.gestioninv.Entities.DemandaHistorica;
import com.yarmovezzoli.gestioninv.Enums.EstadoOrden;
import com.yarmovezzoli.gestioninv.Enums.ModeloInventario;
import com.yarmovezzoli.gestioninv.Enums.TipoPeriodo;
import com.yarmovezzoli.gestioninv.Repositories.BaseRepository;
import com.yarmovezzoli.gestioninv.Repositories.DemandaHistoricaRepository;
import com.yarmovezzoli.gestioninv.Services.BaseServiceImpl;
import com.yarmovezzoli.gestioninv.Services.ModuloArticulos.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticuloServiceImpl extends BaseServiceImpl<Articulo, Long> implements ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private DemandaHistoricaRepository demandaHistoricaRepository;

    public ArticuloServiceImpl(BaseRepository<Articulo, Long> baseRepository, ArticuloRepository articuloRepository) {
        super(baseRepository);
        this.articuloRepository = articuloRepository;

    }

    public List<Articulo> buscar(String filtro) throws Exception {
        try {

            List<Articulo> articulos;

            if (filtro == null){
                articulos = articuloRepository.findAll();
            } else {
                articulos = articuloRepository.buscarArticuloPorNombre(filtro);
            }

            return articulos;

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<ModeloInventario> getTiposInventario() throws Exception {
        try {
            List<ModeloInventario> modeloInventarios = new ArrayList<>();
            modeloInventarios.add(ModeloInventario.INTERVALO_FIJO);
            modeloInventarios.add(ModeloInventario.LOTE_FIJO);
            return modeloInventarios;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public List<EstadoOrden> getEstadoOrdenDeCompra() throws Exception {
        try {
            List<EstadoOrden> estadoOrdens = new ArrayList<>();
            estadoOrdens.add(EstadoOrden.CONFIRMADO);
            estadoOrdens.add(EstadoOrden.CANCELADO);
            estadoOrdens.add(EstadoOrden.PENDIENTE);
            estadoOrdens.add(EstadoOrden.RECIBIDO);
            return estadoOrdens;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public List<DemandaHistorica> getDemandaHistorica(Long articuloId, TipoPeriodo tipoPeriodo) throws Exception {
        try {
            Optional<Articulo> articulo = articuloRepository.findById(articuloId);
            if (articulo.isEmpty()) {
                throw new Exception("No se encontro el articulo");
            } else {
                Articulo articulo1 = articulo.get();
                return demandaHistoricaRepository.findAllByArticuloYTipoPeriodo(articulo1, tipoPeriodo);
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

    }
}

