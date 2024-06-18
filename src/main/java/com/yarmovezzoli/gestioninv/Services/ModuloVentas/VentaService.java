package com.yarmovezzoli.gestioninv.Services.ModuloVentas;

import com.yarmovezzoli.gestioninv.DTOs.DemandaHistoricaRequest;
import com.yarmovezzoli.gestioninv.DTOs.PrediccionDTO;
import com.yarmovezzoli.gestioninv.DTOs.PrediccionDemandaRequest;
import com.yarmovezzoli.gestioninv.DTOs.VentaRequestDTO;
import com.yarmovezzoli.gestioninv.Entities.DemandaHistorica;
import com.yarmovezzoli.gestioninv.Entities.Venta;
import com.yarmovezzoli.gestioninv.Services.BaseService;

import java.util.List;
import java.util.Map;

public interface VentaService extends BaseService<Venta, Long> {

    public Venta createVenta(VentaRequestDTO ventaRequestDTO) throws Exception;

    public DemandaHistorica createDemandaHistorica(DemandaHistoricaRequest demandaHistoricaRequest) throws Exception;

    public List<PrediccionDTO> getPrediccionDemanda(PrediccionDemandaRequest prediccionDemandaRequest) throws Exception;

}
