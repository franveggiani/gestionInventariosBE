package com.yarmovezzoli.gestioninv.DTOs;

import com.yarmovezzoli.gestioninv.DTOs.ModuloInventarios.DTODatosInventario;
import lombok.Getter;

import java.util.List;

@Getter
public class CrearProveedorRequest {

    String nombre;
    List<DTODatosInventario> articulos;

}
