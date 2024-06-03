package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orden_compra")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Orden_compra extends Base{
    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "demora_estimada")
    private int demora_estimada;

    @Column(name = "fecha_hora_alta")
    private  int fecha_hora_alta;

    @Column(name = "nro_orden_compra")
    private  int nro_orden_compra;
}
