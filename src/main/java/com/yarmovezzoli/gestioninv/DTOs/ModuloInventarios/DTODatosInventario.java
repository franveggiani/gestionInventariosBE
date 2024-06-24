package com.yarmovezzoli.gestioninv.DTOs.ModuloInventarios;

import com.yarmovezzoli.gestioninv.Enums.ModeloInventario;
import lombok.Getter;

@Getter
public class DTODatosInventario {
    //dias de demora
    int L;
    //periodo para revisión (revision periodica)
    int T;
    //nivel de servicio
    float Z;
    Long idArticulo;
    float costoPedido;
    int year;
    int diasLaborales;
    ModeloInventario modeloInventario;

    //estos se van a calcular, vamos a usar el mismo DTO.
    float stockSeguridad;
    int ROP;
    int q;
}
