package com.yarmovezzoli.gestioninv.Entities;

import jakarta.persistence.*;
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

public class Demanda_historica_detalle extends Base{
    @Column(name = "cantidad")
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo dhd_articulo;

    @ManyToOne
    @JoinColumn(name = "demanda_historica_id")
    private Demanda_historica demandaHistorica;
}
