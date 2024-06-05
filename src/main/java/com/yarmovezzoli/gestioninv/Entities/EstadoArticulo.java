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
import java.time.LocalDate;

@Entity
@Table(name = "estado_articulo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EstadoArticulo extends Base{
    @Column (name = "fecha_hora_alta_estado")
    private LocalDate fechaHoraAltaEstado;

    @Column(name = "nombre_estado")
    private int nombreEstado; // VER SI NO HACER UN ENUM
/*
    @OneToMany(mappedBy = "estadoArticulo")
    private List<Articulo> estadoArticulo;

 */
}
