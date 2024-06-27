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

    @Column(name = "precio_por_unidad")
    private float precioPorUnidad;

    @Column(name = "nivel_de_servicio")
    private float nivelDeServicio;

    @Column(name = "anio_demanda_anual")
    private int year;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @Column(name = "es_predeterminado")
    private boolean esPredeterminado;

}