package com.yarmovezzoli.gestioninv.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrediccionDTO {

    Double cantidadPredecida;
    LocalDate fechaDesdePrediccion;
    LocalDate fechaHastaPrediccion;
    LocalDate fechaPrediccionRealizada;
    String nombreArticulo;
    Long idArticulo;

}
