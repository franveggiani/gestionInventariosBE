package com.yarmovezzoli.gestioninv.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tipo_periodo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class TipoPeriodo extends Base{
    @Column(name = "cantidad_periodo")
    private int cantidadPeriodo;

    @Column(name= "nombre_tipo")
    private String nombreTipo; //VER SI NO ES UNA EUNUM
/*
    @OneToMany(mappedBy = "tipoPeriodo")
    private List<DemandaHistorica> demandasHistoricas;
 */

}
