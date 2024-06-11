package com.yarmovezzoli.gestioninv.Controllers;

import com.yarmovezzoli.gestioninv.DTOs.EditarProveedorDTO;
import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import com.yarmovezzoli.gestioninv.Services.ProveedorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/proveedores")

public class ProveedorController extends BaseControllerImpl<Proveedor, ProveedorServiceImpl> {

    @Autowired
    ProveedorServiceImpl proveedoresService;

    @GetMapping("/buscarPorDenominacion")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String filtroNombre) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorNombre(filtroNombre));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/mostrarproveedores")
    public ResponseEntity<?> mostrarProveedores() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.mostrarProveedores());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/editardatosproveedor/{id}")
    public ResponseEntity<?> modificarDatosProveedor(@PathVariable Long id, @RequestBody EditarProveedorDTO editarProveedorDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.modificarDatosProveedor(id, editarProveedorDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}