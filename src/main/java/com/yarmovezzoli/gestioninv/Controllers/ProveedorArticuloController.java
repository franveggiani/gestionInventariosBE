package com.yarmovezzoli.gestioninv.Controllers;

import com.yarmovezzoli.gestioninv.DTOs.CrearProveedorArticuloDTO;
import com.yarmovezzoli.gestioninv.DTOs.OrdenCompraDTO;
import com.yarmovezzoli.gestioninv.Entities.ProveedorArticulo;
import com.yarmovezzoli.gestioninv.Services.ProveedorArticuloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
    @CrossOrigin(origins = "*")
    @RequestMapping(path = "api/v1/proveedorArticulo")
    public class ProveedorArticuloController extends BaseControllerImpl<ProveedorArticulo , ProveedorArticuloServiceImpl>{

        @Autowired
        ProveedorController proveedorController;

    @PostMapping("/create")
    public ResponseEntity<?> nuevoProveedorArticulo(@RequestBody CrearProveedorArticuloDTO crearProveedorArticuloDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.nuevoProveedorArticulo(crearProveedorArticuloDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    }
