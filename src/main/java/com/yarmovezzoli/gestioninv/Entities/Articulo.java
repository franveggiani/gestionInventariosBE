package com.yarmovezzoli.gestioninv.Entities;

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
    private float costoAlmacenamiento;

    @Column(name = "costo_orden")
    private float costoOrden;

    @Column(name = "lote_optimo")
    private int loteOptimo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "punto_pedido")
    private int puntoPedido;

    @Column(name = "stock_actual")
    private int stockActual;

    @Column(name = "stock_seguridad")
    private int stockSeguridad;

    @Column(name = "tiempo_entre_pedidos")
    private int tiempoEntrePedidos;

    @ManyToOne
    @JoinColumn(name = "estado_articulo_id")
    private EstadoArticulo estadoArticulo;

    @ManyToOne
    @JoinColumn(name = "modelo_inventario_id")
    private ModeloInventario modeloInventario;

    /*

    @OneToMany(mappedBy = "articuloId")
    private List<ProveedorArticulo> proveedorArticulos;

    @OneToMany(mappedBy = "articuloId")
    private List<OrdenCompra> ordenesCompra;

    @OneToMany(mappedBy = "articuloId")
    private List<DemandaHistoricaDetalle> demandaHistoricaDetalles;

    @OneToMany(mappedBy = "articuloId")
    private List<Venta> ventas;

 */
}
