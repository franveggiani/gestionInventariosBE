package com.yarmovezzoli.gestioninv.Enums;

public enum TipoPeriodo {
    MENSUAL(30L, 1L),
    ANUAL(365L, 12L),
    BIMESTRAL(60L, 2L),
    TRIMESTRAL(91L, 3L),
    SEMESTRAL(183L, 6L);

    private final Long dias;
    private final Long cantMeses;

    TipoPeriodo(Long dias, Long cantMeses){
        this.dias = dias;
        this.cantMeses = cantMeses;
    }
    
    public Long getCantidadMeses(){
        return this.cantMeses;
    }

    public Long getDias(){
        return this.dias;
    }


}
