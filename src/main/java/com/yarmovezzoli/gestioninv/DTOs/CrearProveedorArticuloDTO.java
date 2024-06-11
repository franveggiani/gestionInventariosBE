package com.yarmovezzoli.gestioninv.DTOs;

import lombok.Data;

@Data
public class CrearProveedorArticuloDTO {
    Long proveedorId;
    Long articuloId;
    int demora;
}
