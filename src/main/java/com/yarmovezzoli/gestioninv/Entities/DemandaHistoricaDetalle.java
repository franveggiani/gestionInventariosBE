package com.yarmovezzoli.gestioninv.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "demanda_historica_detalle")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class DemandaHistoricaDetalle extends Base {
    @Column(name = "cantidad")
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articuloId;
/*
    @ManyToOne
    @JoinColumn(name = "demanda_historica_id")
    private DemandaHistorica demandaHistorica;

 */
}
