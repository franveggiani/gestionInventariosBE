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
    private Double demoraPromedio;

    @Column(name = "punto_pedido")
    private Long puntoPedido;

    @Column(name = "stock_seguridad")
    private int stockSeguridad;

    @Column(name = "costo_pedido")
    private Double costoPedido;

    @Column
    private Long EOQ;

    @Column
    private Long demanda;

    @Column
    private Double CGI;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "estado_proveedor_articulo_id")
    private EstadoProveedorArticulo estadoActual;

    public void calcularEOQ(Double cp, Double ca, Long D){
        this.EOQ = Math.round(Math.sqrt((2*D*cp)/ca));
    }

    public void calcularPuntoPedido(Long D, Double demoraPromedio){
        this.puntoPedido = Math.round(D * demoraPromedio);
    }

    public void calcularCGI(Double cp, Double ca, Long D, Double P, Long Q){
        this.CGI = (P * Q) + (cp * D) + (ca * Q/2);
    }

}