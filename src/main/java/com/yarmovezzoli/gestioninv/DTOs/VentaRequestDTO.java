package com.yarmovezzoli.gestioninv.DTOs;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class VentaRequestDTO {

    private int cantidad;
    private Long articuloId;
    private LocalDate fechaHoraAlta; //Opcional, sino se pone fecha entonces se establece la actual

}
