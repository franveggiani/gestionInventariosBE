package com.yarmovezzoli.gestioninv.Entities;

import com.yarmovezzoli.gestioninv.Enums.TipoPeriodo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "demanda_historica")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class DemandaHistorica extends Base{

    @Column(name = "cantidadTotal")
    private int cantidadTotal;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    @Column(name = "fechaDesde")
    private LocalDate fechaDesde;

    @Column(name = "fechaHasta")
    private LocalDate fechaHasta;

    @Column(name = "tipoPeriodo")
    private TipoPeriodo tipoPeriodo;

}
