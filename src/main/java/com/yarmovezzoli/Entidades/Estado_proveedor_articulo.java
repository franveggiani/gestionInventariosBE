package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estadoprovart")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Estado_proveedor_articulo extends Base{
    @Column(name = "nombre_estado")
    private String nombre_estado;
}
