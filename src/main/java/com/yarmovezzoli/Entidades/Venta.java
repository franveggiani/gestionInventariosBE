package com.example.demo.entities;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
}
