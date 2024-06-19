package com.yarmovezzoli.gestioninv.DTOs;

import lombok.Data;

@Data
public class CrearProveedorArticuloRequest {
    Long proveedorId;
    Long articuloId;
    Double demora;
    Double costoPedido;
    Long demanda;
    Double precioPorUnidad;
}

