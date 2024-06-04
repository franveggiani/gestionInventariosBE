package com.yarmovezzoli.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proveedor_articulo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Proveedor_articulo extends Base{
    @Column(name = "demoraprom")
    private int demora_promedio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_proveedor_articulo_id")
    private Estado_proveedor_articulo actual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo p_articulo;
}