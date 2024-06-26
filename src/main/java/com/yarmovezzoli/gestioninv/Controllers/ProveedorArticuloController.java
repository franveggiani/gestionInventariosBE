package com.yarmovezzoli.gestioninv.Controllers;

import com.yarmovezzoli.gestioninv.Controllers.ModuloArticulos.ProveedorController;
import com.yarmovezzoli.gestioninv.DTOs.CrearProveedorArticuloRequest;
import com.yarmovezzoli.gestioninv.DTOs.EditarProveedorArticuloDTO;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.ProveedorArticulo;
import com.yarmovezzoli.gestioninv.Services.ProveedorArticuloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
    @CrossOrigin(origins = "*")
    @RequestMapping(path = "api/v1/proveedorArticulo")
    public class ProveedorArticuloController extends BaseControllerImpl<ProveedorArticulo , ProveedorArticuloServiceImpl> {

    @Autowired
    ProveedorController proveedorController;

    @PostMapping("/create")
    public ResponseEntity<?> nuevoProveedorArticulo(@RequestBody CrearProveedorArticuloRequest crearProveedorArticuloRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.nuevoProveedorArticulo(crearProveedorArticuloRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/editardatosproveedorarticulo/{id}")
    public ResponseEntity<?> modificarDatosProveedorArticulo(@PathVariable Long id, @RequestBody EditarProveedorArticuloDTO editarProveedorArticuloDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.modificarDatosProveedorArticulo(id, editarProveedorArticuloDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/articulosxproveedor/{id}")
    public ResponseEntity<?> obtenerArticulosPorProveedor(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.obtenerArticulosPorProveedor(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}