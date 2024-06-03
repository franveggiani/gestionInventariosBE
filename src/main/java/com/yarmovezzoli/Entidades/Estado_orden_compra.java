package com.yarmovezzoli.Entidades;

import com.yarmovezzoli.Enumeraciones.EstadoOrden;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

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
}
