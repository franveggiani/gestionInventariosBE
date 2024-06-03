package com.example.demo.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    @Entity
    @Table(name = "familiaproductos")
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter

    public class FamiliaProductos extends Base{

        @Column(name = "nombre")
        private String nombre;

        @Column(name = "apellido")
        private String apellido;

        @Column(name = "dni")
        private int dni;

    }