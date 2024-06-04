package com.yarmovezzoli.gestioninv.Entities;

import java.util.Date;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "venta")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Venta extends Base{

    @Column (name = "cantidad")
    private int cantidad;

    @Column (name = "fyh_alta")
    private LocalDate fechaHoraAlta;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articuloId;
}
