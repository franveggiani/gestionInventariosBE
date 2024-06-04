package com.yarmovezzoli.gestioninv.Entities;

import java.util.Date;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Table(name = "venta")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Venta {

    @Column (name = "cantidad")
    private int cantidad;

    @Column (name = "fyh_alta")
    private Date fyh_alta;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo v_articulo;
}
