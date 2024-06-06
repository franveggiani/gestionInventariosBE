package com.yarmovezzoli.gestioninv.Services.ModuloVentas;

import com.yarmovezzoli.gestioninv.DTOs.DemandaHistoricaRequest;
import com.yarmovezzoli.gestioninv.DTOs.PrediccionDemandaRequest;
import com.yarmovezzoli.gestioninv.DTOs.VentaRequestDTO;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.DemandaHistorica;
import com.yarmovezzoli.gestioninv.Entities.Venta;
import com.yarmovezzoli.gestioninv.Enums.TipoPeriodo;
import com.yarmovezzoli.gestioninv.Enums.TipoPrediccion;
import com.yarmovezzoli.gestioninv.Factory.PrediccionDemandaFactory;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.BaseRepository;
import com.yarmovezzoli.gestioninv.Repositories.DemandaHistoricaRepository;
import com.yarmovezzoli.gestioninv.Repositories.VentaRepository;
import com.yarmovezzoli.gestioninv.Services.BaseServiceImpl;
import com.yarmovezzoli.gestioninv.Strategy.PrediccionDemandaStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class VentaServiceImpl extends BaseServiceImpl<Venta, Long> implements VentaService{

    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    ArticuloRepository articuloRepository;
    @Autowired
    DemandaHistoricaRepository demandaHistoricaRepository;

    public VentaServiceImpl(BaseRepository<Venta, Long> baseRepository, VentaRepository ventaRepository, ArticuloRepository articuloRepository, DemandaHistoricaRepository demandaHistoricaRepository){
        super(baseRepository);
        this.ventaRepository = ventaRepository;
        this.articuloRepository = articuloRepository;
        this.demandaHistoricaRepository = demandaHistoricaRepository;
    }

    @Override
    public Venta createVenta(VentaRequestDTO ventaRequest) throws Exception {
        try {
            Venta nuevaVenta = new Venta();

            nuevaVenta.setCantidad(ventaRequest.getCantidad());

            if (ventaRequest.getFechaHoraAlta() == null){
                nuevaVenta.setFechaHoraAlta(LocalDate.now());
            } else {
                nuevaVenta.setFechaHoraAlta(ventaRequest.getFechaHoraAlta());
            }

            Optional<Articulo> articulo = articuloRepository.findById(ventaRequest.getArticuloId());

            if (articulo.isPresent()){
                nuevaVenta.setArticulo(articulo.get());
                ventaRepository.save(nuevaVenta);

                return nuevaVenta;
            } else {
                throw new Exception("No se encontró el id del artículo");
            }

        } catch (Exception e){
            throw new Exception("Error al crear instancia de venta: " + e.getMessage());
        }
    }

    @Override
    public DemandaHistorica createDemandaHistorica(DemandaHistoricaRequest demandaHistoricaRequest) throws Exception {
        try{

            DemandaHistorica demandaHistorica = new DemandaHistorica();
            Optional<Articulo> articuloOptional = articuloRepository.findById(demandaHistoricaRequest.getArticuloId());

            if (articuloOptional.isPresent()) {

                Articulo articulo = articuloOptional.get();

                TipoPeriodo tipoPeriodo = demandaHistoricaRequest.getTipoPeriodo();
                Long cantidadDias = tipoPeriodo.getDias();                                                              //Si es Semestral serán 183 días, si es Trimestral serán 91 días, y así...

                LocalDate fechaHasta = demandaHistoricaRequest.getFechaDesde().plusDays(cantidadDias);
                LocalDate fechaDesde = demandaHistoricaRequest.getFechaDesde();

                List<Venta> ventaList = ventaRepository.listaPorPeriodo(fechaDesde, fechaHasta ,articulo);
                int[] cantidadTotalVentas = {0};

                if (!ventaList.isEmpty()) {
                    ventaList.forEach(venta -> {
                        cantidadTotalVentas[0] += venta.getCantidad();
                    });
                } else {
                    throw new Exception("Error: No hay ventas registradas para este rango de fechas");
                }

                demandaHistorica.setCantidadTotal(cantidadTotalVentas[0]);
                demandaHistorica.setFechaDesde(fechaDesde);
                demandaHistorica.setFechaHasta(fechaHasta);
                demandaHistorica.setArticulo(articulo);
                demandaHistorica.setTipoPeriodo(tipoPeriodo.name());

            } else {
                throw new Exception("Error: El artículo requerido no ha sido encontrado");
            }

            demandaHistoricaRepository.save(demandaHistorica);

            return demandaHistorica;

        } catch(Exception e) {
            throw new Exception("Error: " + e.getMessage());
        }
    }

    @Override
    public double getPrediccionDemanda(PrediccionDemandaRequest prediccionDemandaRequest) throws Exception {
        try {

            LocalDate fechaDesdePrediccion = prediccionDemandaRequest.getFechaDesdePrediccion();
            int numeroPeriodos = prediccionDemandaRequest.getNumeroPeriodos();
            TipoPeriodo tipoPeriodo = prediccionDemandaRequest.getTipoPeriodo();
            Long idArticulo = prediccionDemandaRequest.getArticuloId();
            TipoPrediccion tipoPrediccion = prediccionDemandaRequest.getTipoPrediccion();

            LocalDate comienzoDH = fechaDesdePrediccion.minusDays(tipoPeriodo.getDias()*numeroPeriodos);    //30días * 3periodos = 90 días atrás (si es mensual)
            //System.out.println(comienzoDH);

            List<DemandaHistorica> demandaHistoricaList = new ArrayList<>();
            Long cantDiasAgregados = tipoPeriodo.getDias();

            for (int i = 0; i < numeroPeriodos; i++) {

                DemandaHistoricaRequest demandaHistoricaRequest = new DemandaHistoricaRequest();

                demandaHistoricaRequest.setArticuloId(idArticulo);
                demandaHistoricaRequest.setFechaDesde(comienzoDH);
                demandaHistoricaRequest.setTipoPeriodo(tipoPeriodo);

                DemandaHistorica demandaHistorica = createDemandaHistorica(demandaHistoricaRequest);

                demandaHistoricaList.add(demandaHistorica);

                //System.out.println("ComienzoDH anterior del bucle" + i + ": " + comienzoDH);

                comienzoDH = comienzoDH.plusDays(cantDiasAgregados);

                //System.out.println("ComienzoDH posterior del bucle" + i + ": " + comienzoDH);

            }

            Map<String, Object> parametros = new HashMap<>();

            parametros.put("demandaHistoricaList", demandaHistoricaList);

            PrediccionDemandaFactory prediccionDemandaFactory = PrediccionDemandaFactory.getInstance();
            PrediccionDemandaStrategy prediccionDemandaStrategy = prediccionDemandaFactory.getPrediccionDemandaStrategy(tipoPrediccion);

            double promedioMovil = prediccionDemandaStrategy.predecirDemanda(parametros);

            return promedioMovil;

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
