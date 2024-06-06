package com.yarmovezzoli.gestioninv.DTOs;

import lombok.Getter;

import java.util.Date;

@Getter
public class FechasOrdenDTO {

    private Date fechaDesde;
    private Date fechaHasta;

    public FechasOrdenDTO(Date FechaDesde, Date FechaHasta){
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

}