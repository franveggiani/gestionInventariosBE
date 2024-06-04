package com.yarmovezzoli.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "demanda_historica")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Demanda_historica extends Base{
    @Column(name = "cantidad_total")
    private int cantidad_total;

    @Column(name = "fecha_desde")
    private Data fecha_desde;

    @Column(name = "fecha_hasta")
    private Data fecha_hasta;

    @OneToMany(mappedBy = "demandaHistorica")
    private List<Demanda_historica_detalle> detallesDemandaHistorica;

    @ManyToOne
    @JoinColumn(name = "tipo_periodo_id")
    private Tipo_periodo tipoPeriodo;
}
