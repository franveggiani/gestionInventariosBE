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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_proveedor_articulo_id")
    private EstadoProveedorArticulo estadoActual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articuloId;
}