package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.Repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;

import java.util.List;
@Service
public class ArticuloServiceImpl extends BaseServiceImpl<Articulo, Long> implements ArticuloService{

    @Autowired
    private ArticuloRepository articuloRepository;

    public ArticuloServiceImpl(BaseRepository<Articulo, Long> baseRepository, ArticuloRepository articuloRepository) {
        super(baseRepository);
        this.articuloRepository = articuloRepository;

    }

    public List<Articulo> buscarProductoPorNombre(String filtro) throws Exception {
        try {
            List<Articulo> articulos = articuloRepository.buscarArticuloPorNombre(filtro);
            return articulos;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

}

