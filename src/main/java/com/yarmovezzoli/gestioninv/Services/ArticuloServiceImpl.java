package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.Enums.EstadoOrden;
import com.yarmovezzoli.gestioninv.Enums.ModeloInventario;
import com.yarmovezzoli.gestioninv.Repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticuloServiceImpl extends BaseServiceImpl<Articulo, Long> implements ArticuloService{

    @Autowired
    private ArticuloRepository articuloRepository;

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
}

