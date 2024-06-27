package com.yarmovezzoli.gestioninv.DTOs.CalculoError;

import com.yarmovezzoli.gestioninv.Enums.TipoPrediccion;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ErrorDTO {

    private float error;
    private String nombreArticulo;
    private LocalDate fechaCalculoError;
    private TipoPrediccion tipoPrediccion;

}
