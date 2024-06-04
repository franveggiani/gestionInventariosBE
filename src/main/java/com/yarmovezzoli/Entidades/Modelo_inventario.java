package com.yarmovezzoli.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "modelo_inventario")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Modelo_inventario extends Base{
    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "modeloInventario")
    private List<Articulo> m_articulos;
}