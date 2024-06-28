package com.yarmovezzoli.gestioninv.Entities;

import com.yarmovezzoli.gestioninv.Enums.EstadoArticulo;
import com.yarmovezzoli.gestioninv.Enums.ModeloInventario;
import com.yarmovezzoli.gestioninv.Enums.TipoPrediccion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "articulo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Articulo extends Base{

    @Column(name = "costo_almacenamiento")
    private Double costoAlmacenamiento;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "stock_seguridad")
    private int stockSeguridad;

    @Column(name = "punto_pedido")
    private int puntoPedido;

    @Column(name = "stock_actual")
    private int stockActual;

    @Column(name = "tipo_prediccion")
    private TipoPrediccion tipoPrediccion;

    @JoinColumn(name = "estado_articulo")
    private EstadoArticulo estadoArticulo;

    @JoinColumn(name = "modelo_inventario")
    private ModeloInventario modeloInventario;

    public void descontarStock(int cantidad){
        this.stockActual -= cantidad;
    }

}
