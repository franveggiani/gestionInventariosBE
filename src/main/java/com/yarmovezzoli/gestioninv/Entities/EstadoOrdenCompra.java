package com.yarmovezzoli.gestioninv.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;
import java.time.LocalDate;

import com.yarmovezzoli.gestioninv.Enums.EstadoOrden;

@Entity
@Table(name = "estado_orden_compra")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class EstadoOrdenCompra extends Base{

    @Column (name = "fecha_hora_baja")
    private LocalDate fechaHoraBaja;

    @Column(name = "nombre_estado")
    private EstadoOrden nombreEstado;
/*
    @OneToMany(mappedBy = "estadoOrdenCompra")
    private List<OrdenCompra> ordenesCompra;

 */
}