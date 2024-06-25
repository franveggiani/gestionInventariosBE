package com.yarmovezzoli.gestioninv.Strategy;

import com.yarmovezzoli.gestioninv.DTOs.ModuloInventarios.DTODatosInventario;
import com.yarmovezzoli.gestioninv.DTOs.ModuloInventarios.DTODatosInventarioOutput;

import java.util.Map;

public interface CalculosInventario {

    public DTODatosInventarioOutput getDatosInventario(DTODatosInventario dtoDatosInventario) throws Exception;

}
