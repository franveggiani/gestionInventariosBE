package com.yarmovezzoli.gestioninv.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "proveedor_articulo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ProveedorArticulo extends Base{

    @Column(name = "demora_promedio")
    private int demoraPromedio;

    @Column(name = "costo_pedido")
    private float costoPedido;

    @Column(name = "eoq")
    private int EOQ;

    @Column(name = "cgi")
    private float CGI;

    @Column(name = "periodo_revision")
    private int periodoRevision;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "estado_proveedor_articulo_id")
    private EstadoProveedorArticulo estadoActual;   //Cambiar por el enum

}