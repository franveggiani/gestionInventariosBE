package com.yarmovezzoli.gestioninv.Controllers.ModuloVentas;

import com.yarmovezzoli.gestioninv.Controllers.BaseControllerImpl;
import com.yarmovezzoli.gestioninv.DTOs.DemandaHistoricaRequest;
import com.yarmovezzoli.gestioninv.DTOs.PrediccionDemandaRequest;
import com.yarmovezzoli.gestioninv.DTOs.VentaRequestDTO;
import com.yarmovezzoli.gestioninv.Entities.Venta;
import com.yarmovezzoli.gestioninv.Services.ModuloVentas.VentaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/ventas")
public class VentaController extends BaseControllerImpl<Venta, VentaServiceImpl> {

    @PostMapping("createVenta")
    public ResponseEntity<?> createVenta(@RequestBody VentaRequestDTO ventaRequestDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.createVenta(ventaRequestDTO));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @PostMapping("createDemandaHistorica")
    public ResponseEntity<?> createDemandaHistorica(@RequestBody DemandaHistoricaRequest demandaHistoricaRequest){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.createDemandaHistorica(demandaHistoricaRequest));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @PostMapping("prediccionDemanda")
    public ResponseEntity<?> createDemandaHistorica(@RequestBody PrediccionDemandaRequest prediccionDemandaRequest){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.calcularPrediccionDemanda(prediccionDemandaRequest));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @GetMapping("getPredicciones")
    public ResponseEntity<?> getPredicciones(@RequestParam(name = "idArticulo") Long idArticulo, @RequestParam(name = "year") int year){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getPredicciones(idArticulo, year));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }


    @GetMapping("calcularError")
    public ResponseEntity<?> calcularError(@RequestParam(name = "idArticulo") Long idArticulo){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getErrorPrediccion(idArticulo));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @GetMapping("/articulos/{articuloId}")
    public ResponseEntity<?> getVentasPorArticulo(@PathVariable Long articuloId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getVentasPorArticulo(articuloId));

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
}