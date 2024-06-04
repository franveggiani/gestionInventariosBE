package com.yarmovezzoli.gestioninv.DTOs;

import com.yarmovezzoli.gestioninv.Enums.TipoPeriodo;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DemandaHistoricaRequest {

    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private TipoPeriodo tipoPeriodo;

}
