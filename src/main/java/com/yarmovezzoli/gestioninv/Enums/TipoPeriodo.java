package com.yarmovezzoli.gestioninv.Enums;

public enum TipoPeriodo {
    MENSUAL(30L),
    ANUAL(365L),
    BIMESTRAL(60L),
    TRIMESTRAL(91L),
    SEMESTRAL(183L);

    private final Long dias;

    TipoPeriodo(Long dias){
        this.dias = dias;
    }

    public Long getDias(){
        return this.dias;
    }


}
