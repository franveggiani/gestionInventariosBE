package com.yarmovezzoli.gestioninv.DTOs;

import lombok.Getter;

import java.util.List;

@Getter
public class CrearProveedorRequest {

    String nombre;
    List<ArticuloCrearProveedorRequest> articulos;

}
