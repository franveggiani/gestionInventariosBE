package com.yarmovezzoli.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estadoprovart")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Estado_proveedor_articulo extends Base{
    @Column(name = "nombre_estado")
    private String nombre_estado;

    @OneToMany(mappedBy = "actual", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proveedor_articulo> proveedor_articulos = new ArrayList<>();
}