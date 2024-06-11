package com.yarmovezzoli.gestioninv.Entities;

import com.yarmovezzoli.gestioninv.Enums.TipoPrediccion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrediccionDemanda extends Base{

    @Column(name = "prediccion")
    private Double prediccion;

    @Column(name = "fecha_hora_desde")
    private LocalDate fechaHoraDesde;

    @Column(name = "fecha_hora_hasta")
    private LocalDate FechaHoraHasta;

    @Column(name = "metodo_prediccion")
    private TipoPrediccion tipoPrediccion;

    @ManyToOne
    private Articulo articulo;

}
