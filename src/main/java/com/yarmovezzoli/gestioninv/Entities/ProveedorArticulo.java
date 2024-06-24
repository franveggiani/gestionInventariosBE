package com.yarmovezzoli.gestioninv.Entities;

import com.yarmovezzoli.gestioninv.Enums.TipoPeriodo;
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

    @Column(name = "costo_pedido")
    private Double costoPedido;

    @Column(name = "stock_seguridad")
    private Double stockSeguridad;

    @Column(name = "eoq")
    private Long EOQ;

    @Column(name = "demanda")
    private Long demanda;

    @Column(name = "cgi")
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

    public void calcularStockSeguridad(Double desviacionEstandarDemanda, Double demoraPromedio){
        float z = 1.68f;
        this.stockSeguridad = z * desviacionEstandarDemanda * Math.sqrt(demoraPromedio);
    }

}