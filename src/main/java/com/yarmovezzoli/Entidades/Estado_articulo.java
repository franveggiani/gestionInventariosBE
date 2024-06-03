package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

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

}
