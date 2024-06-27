package com.yarmovezzoli.gestioninv.DTOs;

import com.yarmovezzoli.gestioninv.Enums.TipoPeriodo;
import com.yarmovezzoli.gestioninv.Enums.TipoPrediccion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PrediccionDemandaRequest {

    private int numeroPeriodos;
    private TipoPeriodo tipoPeriodo;
    private LocalDate fechaDesdePrediccion;
    private Long articuloId;
    private TipoPrediccion tipoPrediccion;
    private int cantidadPredicciones;
    private Double[] ponderaciones;
    private float alpha;

    //Buscar ventas que coincidan con el articulo proporcionado acá

}
