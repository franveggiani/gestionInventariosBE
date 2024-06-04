package com.yarmovezzoli.gestioninv.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

import com.yarmovezzoli.gestioninv.Enums.EstadoOrden;

@Entity
@Table(name = "estado_orden_compra")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
@Setter

public class Estado_orden_compra extends Base{

    @Column (name = "fecha_hora_baja")
    private Data fecha_hora_baja;

    @Column(name = "nombbre_estado")
    private EstadoOrden nombre_estado;

    @OneToMany(mappedBy = "estado_orden_compra")
    private List<Orden_compra> ordenesCompra;
}