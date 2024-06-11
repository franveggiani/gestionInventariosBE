package com.yarmovezzoli.gestioninv.Entities;

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

public class EstadoProveedorArticulo extends Base{
    @Column(name = "nombre_estado")
    private String nombreEstado;
}