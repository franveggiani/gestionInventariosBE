package com.yarmovezzoli.gestioninv.Entities;

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

public class Orden_compra extends Base{
    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "demora_estimada")
    private int demora_estimada;

    @Column(name = "fecha_hora_alta")
    private  int fecha_hora_alta;

    @Column(name = "nro_orden_compra")
    private  int nro_orden_compra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "estado_orden_compra_id")
    private Estado_orden_compra estado_orden_compra;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo oc_articulo;
}