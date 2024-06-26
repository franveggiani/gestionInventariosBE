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
    private int prediccion;

    @Column(name = "fecha_hora_desde")
    private LocalDate fechaDesde;

    @Column(name = "fecha_hora_hasta")
    private LocalDate fechaHasta;

    @Column(name = "metodo_prediccion")
    private TipoPrediccion tipoPrediccion;

    @Column(name = "fecha_prediccion")
    private LocalDate fechaPrediccion;

    @ManyToOne(optional = true)
    private Articulo articulo;

}
