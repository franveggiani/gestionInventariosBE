package com.yarmovezzoli.gestioninv.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "demanda_historica")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class DemandaHistorica extends Base{
    @Column(name = "cantidad_total")
    private int cantidadTotal;

    @Column(name = "fecha_desde")
    private LocalDate fechaDesde;

    @Column(name = "fecha_hasta")
    private LocalDate fechaHasta;

    @OneToMany(mappedBy = "demandaHistorica")
    private List<DemandaHistoricaDetalle> demandaHistoricaDetalles;

    @ManyToOne
    @JoinColumn(name = "tipo_periodo_id")
    private TipoPeriodo tipoPeriodo;
}
