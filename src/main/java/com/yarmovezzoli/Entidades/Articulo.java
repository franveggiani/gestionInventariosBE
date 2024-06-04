package com.yarmovezzoli.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "modelo_inventario_id")
    private Modelo_inventario modeloInventario;

    @ManyToOne
    @JoinColumn(name = "estado_articulo_id")
    private Estado_articulo estadoArticulo;

    @OneToMany(mappedBy = "articulo")
    private List<Proveedor_articulo> proveedorArticulos;

    @OneToMany(mappedBy = "articulo")
    private List<Orden_compra> ordenesCompra;

    @OneToMany(mappedBy = "articulo")
    private List<Demanda_historica_detalle> demandaHistoricaDetalles;

    @OneToMany(mappedBy = "articulo")
    private List<Venta> ventas;
}
