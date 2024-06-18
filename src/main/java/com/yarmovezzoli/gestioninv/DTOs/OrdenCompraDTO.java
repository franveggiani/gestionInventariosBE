package com.yarmovezzoli.gestioninv.DTOs;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import lombok.Data;

@Data
public class OrdenCompraDTO {
    int cantidad;
    int demoraEstimada;
    Long proveedorId;
    Long articuloId;
}