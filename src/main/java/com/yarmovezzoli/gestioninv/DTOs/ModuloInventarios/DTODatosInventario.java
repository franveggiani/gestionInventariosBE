package com.yarmovezzoli.gestioninv.DTOs.ModuloInventarios;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yarmovezzoli.gestioninv.Entities.EstadoProveedorArticulo;
import com.yarmovezzoli.gestioninv.Enums.ModeloInventario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTODatosInventario {

    @JsonProperty("L")
    private int L;
    @JsonProperty("T")
    private int T;
    @JsonProperty("Z")
    private float Z;
    @JsonProperty("idArticulo")
    private Long idArticulo;
    @JsonProperty("idProveedor")
    private Long idProveedor;
    @JsonProperty("costoPedido")
    private float costoPedido;
    @JsonProperty("year")
    private int year;
    @JsonProperty("diasLaborales")
    private int diasLaborales;
    @JsonProperty("modeloInventario")
    private ModeloInventario modeloInventario;
    @JsonProperty("precioUnidad")
    private float precioUnidad;

//    @JsonProperty("estadoArticulo")
//    private EstadoProveedorArticulo estadoProveedorArticulo;
}