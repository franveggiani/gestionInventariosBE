package com.yarmovezzoli.gestioninv.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "estado_articulo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data

public class Estado_articulo extends Base{
    @Column (name = "fecha_hora_alta_estado")
    private Data fecha_hora_alta_estado;

    @Column(name = "nombre_estado")
    private int nombre_estado; // VER SI NO HACER UN ENUM

    @OneToMany(mappedBy = "estadoArticulo")
    private List<Articulo> e_articulos;
}
