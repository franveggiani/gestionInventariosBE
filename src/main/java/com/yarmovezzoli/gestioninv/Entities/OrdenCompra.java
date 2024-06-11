package com.yarmovezzoli.gestioninv.Entities;

import com.yarmovezzoli.gestioninv.Enums.EstadoArticulo;
import com.yarmovezzoli.gestioninv.Enums.EstadoOrden;
import jakarta.persistence.*;
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

public class OrdenCompra extends Base{
    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "demora_estimada")
    private int demoraEstimada;

    @Column(name = "fecha_hora_alta")
    private int fechaHoraAlta;

    @Column(name = "nro_orden_compra")
    private int nroOrdenCompra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @JoinColumn(name = "estado_orden_compra")
    private EstadoOrden estadoOrdenCompra;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articuloId;
}