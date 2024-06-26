package com.yarmovezzoli.gestioninv.DTOs;

import com.yarmovezzoli.gestioninv.Enums.EstadoArticulo;
import com.yarmovezzoli.gestioninv.Enums.ModeloInventario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorxArticuloDTO {

    Double costoAlmacenamiento;
    String nombre;
    int stockActual;
    EstadoArticulo estadoArticulo;
    ModeloInventario modeloInventario;
    Double CGI;
}