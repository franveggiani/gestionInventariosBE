package com.yarmovezzoli.gestioninv.Controllers.ModuloArticulos;

import com.yarmovezzoli.gestioninv.Controllers.BaseControllerImpl;
import com.yarmovezzoli.gestioninv.DTOs.PrediccionDemandaRequest;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Enums.TipoPeriodo;
import com.yarmovezzoli.gestioninv.Services.ModuloArticulos.ArticuloServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/articulo")
public class ArticuloController extends BaseControllerImpl<Articulo, ArticuloServiceImpl> {

    @Autowired
    ArticuloServiceImpl articuloService;
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String filtro){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.buscar(filtro));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/getTiposInventario")
    public ResponseEntity<?> getTiposInventario(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getTiposInventario());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/getEstadoOrdenDeCompra")
    public ResponseEntity<?> getEstadoOrdenDeCompra(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getEstadoOrdenDeCompra());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/{articuloId}/demandasHistoricas/{tipoPeriodo}")
    public ResponseEntity<?> getDemandaHistorica(@PathVariable Long articuloId, @PathVariable TipoPeriodo tipoPeriodo){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(articuloService.getDemandaHistorica(articuloId, tipoPeriodo));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
}

