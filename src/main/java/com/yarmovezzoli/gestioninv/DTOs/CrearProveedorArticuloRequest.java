package com.yarmovezzoli.gestioninv.DTOs;

import com.yarmovezzoli.gestioninv.Enums.ModeloInventario;
import com.yarmovezzoli.gestioninv.Enums.TipoPeriodo;
import lombok.Data;

@Data
public class CrearProveedorArticuloRequest {
    Long proveedorId;
    Long articuloId;
    Double demora;
    Double costoPedido;
    Long demanda;
    Double precioPorUnidad;
    ModeloInventario modeloInventario;
    int cantidadDiasLaborales;
    Double desviacionEstandarDemanda;
    Long tiempoRevision;
}

