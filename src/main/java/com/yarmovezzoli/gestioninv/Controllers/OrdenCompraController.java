package com.yarmovezzoli.gestioninv.Controllers;

import com.yarmovezzoli.gestioninv.DTOs.OrdenCompraDTO;
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

    @PutMapping("/newOrdenCompraV1")
    public ResponseEntity<?> nuevaOrdenCompraArt(@RequestBody OrdenCompraDTO ordenCompraDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.newOrdenCompra(ordenCompraDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
