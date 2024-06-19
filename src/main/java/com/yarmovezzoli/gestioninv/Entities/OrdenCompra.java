package com.yarmovezzoli.gestioninv.Entities;

import com.yarmovezzoli.gestioninv.Enums.EstadoOrden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate fechaHoraAlta;

    @Column(name = "nro_orden_compra")
    private int nroOrdenCompra;

    @Column(name = "nombre_estado")
    private EstadoOrden nombreEstado;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;
}