package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "demanda_historica_detalle")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Demanda_historica_detalle extends Base{
    @Column(name = "cantidad")
    private int cantidad;
}
