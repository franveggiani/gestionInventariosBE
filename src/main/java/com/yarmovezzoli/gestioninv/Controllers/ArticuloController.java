package com.yarmovezzoli.gestioninv.Controllers;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Services.ArticuloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/articulo")
public class ArticuloController extends BaseControllerImpl<Articulo, ArticuloServiceImpl>{

    @Autowired
    ArticuloServiceImpl articuloService;
    @GetMapping("/buscarPorDenominacion")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String filtroNombre){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.buscarProductoPorNombre(filtroNombre));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

}

