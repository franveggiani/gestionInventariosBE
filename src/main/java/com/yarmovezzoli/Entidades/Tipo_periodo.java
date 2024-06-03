package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipo_periodo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Tipo_periodo extends Base{
    @Column(name = "cantidad_periodo")
    private int cantidad_periodo;

    @Column(name= "nombre_tipo")
    private String nombre_tipo; //VER SI NO ES UNA EUNUM
}
