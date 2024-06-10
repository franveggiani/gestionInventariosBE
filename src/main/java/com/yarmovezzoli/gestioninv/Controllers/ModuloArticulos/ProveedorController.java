package com.yarmovezzoli.gestioninv.Controllers.ModuloArticulos;

import com.yarmovezzoli.gestioninv.Controllers.BaseControllerImpl;
import com.yarmovezzoli.gestioninv.DTOs.CrearProveedorRequest;
import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import com.yarmovezzoli.gestioninv.Services.ProveedoresServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/proveedores")

public class ProveedorController extends BaseControllerImpl<Proveedor,ProveedoresServiceImpl> {

    @Autowired
    ProveedoresServiceImpl proveedoresService;
    @GetMapping("/buscarPorDenominacion")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String filtroNombre){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorNombre(filtroNombre));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearProveedor(@RequestBody CrearProveedorRequest crearProveedorRequest){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.crearProveedor(crearProveedorRequest));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
