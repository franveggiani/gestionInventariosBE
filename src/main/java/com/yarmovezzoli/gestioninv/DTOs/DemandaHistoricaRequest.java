package com.yarmovezzoli.gestioninv.DTOs;

import com.yarmovezzoli.gestioninv.Enums.TipoPeriodo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DemandaHistoricaRequest {

    private LocalDate fechaDesde;
    private TipoPeriodo tipoPeriodo;
    private Long articuloId;

}
