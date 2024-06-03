package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "demanda_historica")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Demanda_historica extends Base{
    @Column(name = "cantidad_total")
    private int cantidad_total;

    @Column(name = "fecha_desde")
    private Data fecha_desde;

    @Column(name = "fecha_hasta")
    private Data fecha_hasta;
}
