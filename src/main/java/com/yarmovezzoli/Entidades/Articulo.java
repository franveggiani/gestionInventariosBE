package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "articulo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Articulo extends Base{

    @Column(name = "costo_almacenamiento")
    private float costo_almacenamiento;

    @Column(name = "costo_orden")
    private float costo_orden;

    @Column(name = "lote_optimo")
    private int lote_optimo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "punto_pedido")
    private int punto_pedido;

    @Column(name = "stock_actual")
    private int stock_actual;

    @Column(name = "stock_seguridad")
    private int stock_seguridad;

    @Column(name = "tiempo_entre_pedidos")
    private int tiempo_entre_pedidos;
}