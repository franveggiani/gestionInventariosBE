package com.yarmovezzoli.gestioninv.Controllers;

import com.yarmovezzoli.gestioninv.Entities.OrdenCompra;
import com.yarmovezzoli.gestioninv.Services.OrdenCompraServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/OrdenCompra")

public class OrdenCompraController extends BaseControllerImpl<OrdenCompra, OrdenCompraServiceImpl>{

    @Autowired
    OrdenCompraServiceImpl ordenCompraService;
    @GetMapping("/newOrdenCompra")
    public ResponseEntity<?> obtenerOrdenesCompraPendientesPorArticulo(@RequestBody Long articuloId){ //@PathVariable
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.obtenerOrdenesCompraPendientesPorArticulo(articuloId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
