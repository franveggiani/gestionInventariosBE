package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "proveedor_articulo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Proveedor_articulo extends Base{
    @Column(name = "demoraprom")
    private int demora_promedio;
}
