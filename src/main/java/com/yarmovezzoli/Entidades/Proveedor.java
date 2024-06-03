package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "proveedor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data

public class Proveedor extends Base{
    @Column(name = "fecha_hora_baja")
    private Date fecha_hora_baja;

    @Column(name = "nombre")
    private String nombre;

}
