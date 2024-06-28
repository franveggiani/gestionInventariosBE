package com.yarmovezzoli.gestioninv.Services.ModuloVentas;

import com.yarmovezzoli.gestioninv.DTOs.CalculoError.ErrorDTO;
import com.yarmovezzoli.gestioninv.DTOs.DemandaHistoricaRequest;
import com.yarmovezzoli.gestioninv.DTOs.PrediccionDTO;
import com.yarmovezzoli.gestioninv.DTOs.PrediccionDemandaRequest;
import com.yarmovezzoli.gestioninv.DTOs.VentaRequestDTO;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.DemandaHistorica;
import com.yarmovezzoli.gestioninv.Entities.PrediccionDemanda;
import com.yarmovezzoli.gestioninv.Entities.Venta;
import com.yarmovezzoli.gestioninv.Enums.EstadoArticulo;
import com.yarmovezzoli.gestioninv.Enums.TipoPeriodo;
import com.yarmovezzoli.gestioninv.Enums.TipoPrediccion;
import com.yarmovezzoli.gestioninv.Factory.PrediccionDemandaFactory;
import com.yarmovezzoli.gestioninv.Repositories.*;
import com.yarmovezzoli.gestioninv.Services.BaseServiceImpl;
import com.yarmovezzoli.gestioninv.Strategy.PrediccionDemandaStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl extends BaseServiceImpl<Venta, Long> implements VentaService{

    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    ArticuloRepository articuloRepository;
    @Autowired
    DemandaHistoricaRepository demandaHistoricaRepository;
    @Autowired
    PrediccionDemandaRepository prediccionDemandaRepository;
    @Autowired
    PrediccionDemandaFactory prediccionDemandaFactory;

    public VentaServiceImpl(BaseRepository<Venta, Long> baseRepository, VentaRepository ventaRepository, ArticuloRepository articuloRepository, DemandaHistoricaRepository demandaHistoricaRepository, PrediccionDemandaRepository prediccionDemandaRepository, PrediccionDemandaFactory prediccionDemandaFactory) {
        super(baseRepository);
        this.ventaRepository = ventaRepository;
        this.articuloRepository = articuloRepository;
        this.demandaHistoricaRepository = demandaHistoricaRepository;
        this.prediccionDemandaRepository = prediccionDemandaRepository;
        this.prediccionDemandaFactory = prediccionDemandaFactory;
    }

    @Override
    public Venta createVenta(VentaRequestDTO ventaRequest) throws Exception {
        try {
            Optional<Articulo> articuloOptional = articuloRepository.findById(ventaRequest.getArticuloId());

            if (articuloOptional.isPresent()) {
                Articulo articulo = articuloOptional.get();

                Venta nuevaVenta = new Venta();
                nuevaVenta.setCantidad(ventaRequest.getCantidad());
                nuevaVenta.setArticulo(articulo);

                int stockActual = articulo.getStockActual();
                int puntoPedido = articulo.getPuntoPedido();
                int cantidadVenta = ventaRequest.getCantidad();

                if (stockActual < cantidadVenta) {
                    throw new Exception("No hay suficiente stock para la venta");
                } else {
                    articulo.setStockActual(articulo.getStockActual() - ventaRequest.getCantidad());

                    stockActual = articulo.getStockActual();

                    if (stockActual <= puntoPedido){
                        articulo.setEstadoArticulo(EstadoArticulo.A_REPONER);
                        if (stockActual == 0){
                            articulo.setEstadoArticulo(EstadoArticulo.NO_DISPONIBLE);
                        }
                    }

                }

                if (ventaRequest.getFechaHoraAlta() == null) {
                    nuevaVenta.setFechaHoraAlta(LocalDate.now());
                } else {
                    nuevaVenta.setFechaHoraAlta(ventaRequest.getFechaHoraAlta());
                }

                articuloRepository.save(articulo);
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

                List<Venta> ventaList = ventaRepository.findByPeriodoAndArticulo(fechaDesde, fechaHasta ,articulo);
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
                demandaHistorica.setTipoPeriodo(tipoPeriodo);

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
    public List<PrediccionDTO> calcularPrediccionDemanda(PrediccionDemandaRequest prediccionDemandaRequest) throws Exception {
        try {
            TipoPrediccion tipoPrediccion = prediccionDemandaRequest.getTipoPrediccion();

            //Creo instancia para calcular la predicción según el tipo
            PrediccionDemandaStrategy prediccionDemandaStrategy = prediccionDemandaFactory.getPrediccionDemandaStrategy(tipoPrediccion);

            //Predicción
            List<PrediccionDemanda> prediccionDemandaList = prediccionDemandaStrategy.predecirDemanda(prediccionDemandaRequest);

            for (PrediccionDemanda prediccion : prediccionDemandaList) {
                prediccionDemandaRepository.save(prediccion);
            }

            //Creo lista de DTO's
            List<PrediccionDTO> prediccionDTOList = prediccionDemandaList.stream().map(prediccion -> getPrediccionDTO(prediccion)).collect(Collectors.toList());
            
            return prediccionDTOList;

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<PrediccionDemanda> getPredicciones(Long idArticulo, int year) throws Exception {
        return null;
    }

    @Override
    public List<ErrorDTO> getErrorPrediccion(Long idArticulo) throws Exception {
        try {
            LocalDate fechaActual = LocalDate.now();
            Optional<Articulo> articuloOptional = articuloRepository.findById(idArticulo);

            if (articuloOptional.isPresent()) {
                Articulo articulo = articuloOptional.get();

                List<Integer> ventasPorPeriodo = new ArrayList<>();
                LocalDate fechaInicioPeriodo = fechaActual.minusMonths(6);
                LocalDate fechaFinPeriodo = fechaInicioPeriodo.plusMonths(1);
                for (int i = 0; i < 6; i++) {
                    List<Venta> ventasPeriodo = ventaRepository.findByPeriodoAndArticulo(fechaInicioPeriodo, fechaFinPeriodo, articulo);

                    int ventas = 0;
                    for (Venta venta : ventasPeriodo){
                        ventas += venta.getCantidad();
                    }
                    ventasPorPeriodo.add(ventas);

                    fechaInicioPeriodo = fechaInicioPeriodo.plusMonths(1);
                    fechaFinPeriodo = fechaInicioPeriodo.plusMonths(1);

                }

                PrediccionDemandaStrategy prediccionDemandaStrategyPM = prediccionDemandaFactory.getPrediccionDemandaStrategy(TipoPrediccion.PROM_MOVIL);
                PrediccionDemandaStrategy prediccionDemandaStrategyPMP = prediccionDemandaFactory.getPrediccionDemandaStrategy(TipoPrediccion.PROM_MOVIL_PONDERADO);
                PrediccionDemandaStrategy prediccionDemandaStrategySE = prediccionDemandaFactory.getPrediccionDemandaStrategy(TipoPrediccion.EXPONENCIAL);

                List<PrediccionDemanda> prediccionDemandaPM = new ArrayList<>();
                List<PrediccionDemanda> prediccionDemandaPMP = new ArrayList<>();
                List<PrediccionDemanda> prediccionDemandaSE = new ArrayList<>();

                fechaInicioPeriodo = fechaActual.minusMonths(6);

                for (int i = 0; i < ventasPorPeriodo.size(); i++) {
                    PrediccionDemandaRequest prediccionDemandaRequestPM = crearPrediccionDemandaRequest(1, 6, TipoPeriodo.MENSUAL, articulo.getId(), fechaInicioPeriodo, TipoPrediccion.PROM_MOVIL, null, 0.0f);
                    PrediccionDemandaRequest prediccionDemandaRequestPMP = crearPrediccionDemandaRequest(1, 6, TipoPeriodo.MENSUAL, articulo.getId(), fechaInicioPeriodo, TipoPrediccion.PROM_MOVIL_PONDERADO, new Double[]{0.1, 0.1, 0.1, 0.2, 0.2, 0.3}, 0.0f);
                    PrediccionDemandaRequest prediccionDemandaRequestSE = crearPrediccionDemandaRequest(1, 6, TipoPeriodo.MENSUAL, articulo.getId(), fechaInicioPeriodo, TipoPrediccion.EXPONENCIAL, null, 0.5f);

                    List<PrediccionDemanda> prediccionDemandaTempPM = prediccionDemandaStrategyPM.predecirDemanda(prediccionDemandaRequestPM);
                    List<PrediccionDemanda> prediccionDemandaTempPMP = prediccionDemandaStrategyPMP.predecirDemanda(prediccionDemandaRequestPMP);
                    List<PrediccionDemanda> prediccionDemandaTempSE = prediccionDemandaStrategySE.predecirDemanda(prediccionDemandaRequestSE);

                    prediccionDemandaPM.add(prediccionDemandaTempPM.get(0));
                    prediccionDemandaPMP.add(prediccionDemandaTempPMP.get(0));
                    prediccionDemandaSE.add(prediccionDemandaTempSE.get(0));

                    fechaInicioPeriodo = fechaInicioPeriodo.plusMonths(1);
                }

                int sumatoriaPM = 0;
                int sumatoriaPMP = 0;
                int sumatoriaSE = 0;
                for (int i = 0; i < ventasPorPeriodo.size(); i++) {
                    sumatoriaPM += Math.abs(ventasPorPeriodo.get(i) - prediccionDemandaPM.get(i).getPrediccion());
                    sumatoriaPMP += Math.abs(ventasPorPeriodo.get(i) - prediccionDemandaPMP.get(i).getPrediccion());
                    sumatoriaSE += Math.abs(ventasPorPeriodo.get(i) - prediccionDemandaSE.get(i).getPrediccion());
                }

                float errorPM = sumatoriaPM / 6;
                float errorPMP = sumatoriaPMP / 6;
                float errorSE = sumatoriaSE / 6;

                List<ErrorDTO> errorDTOList = new ArrayList<>();

                ErrorDTO errorDTOPM = new ErrorDTO();
                errorDTOPM.setError(errorPM);
                errorDTOPM.setFechaCalculoError(fechaActual);
                errorDTOPM.setTipoPrediccion(TipoPrediccion.PROM_MOVIL);
                errorDTOPM.setNombreArticulo(articulo.getNombre());

                ErrorDTO errorDTOPMP = new ErrorDTO();
                errorDTOPMP.setError(errorPMP);
                errorDTOPMP.setFechaCalculoError(fechaActual);
                errorDTOPMP.setTipoPrediccion(TipoPrediccion.PROM_MOVIL_PONDERADO);
                errorDTOPMP.setNombreArticulo(articulo.getNombre());

                System.out.println(errorSE + " "+ fechaActual + " " + articulo.getNombre());

                ErrorDTO errorDTOSE = new ErrorDTO();
                errorDTOSE.setError(errorSE);
                errorDTOSE.setFechaCalculoError(fechaActual);
                errorDTOSE.setTipoPrediccion(TipoPrediccion.EXPONENCIAL);
                errorDTOSE.setNombreArticulo(articulo.getNombre());

                errorDTOList.add(errorDTOPM);
                errorDTOList.add(errorDTOPMP);
                errorDTOList.add(errorDTOSE);

                return errorDTOList;

            } else {
                throw new Exception("No se encontró el artículo: " + idArticulo);
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener las predicciones: " + e.getMessage());
        }
    }

    public PrediccionDemandaRequest crearPrediccionDemandaRequest(int cantPred, int numPeriodos, TipoPeriodo tipoPeriodo, Long articuloId, LocalDate fechaDesdePrediccion, TipoPrediccion tipoPrediccion, Double[] ponderaciones, float alpha) {
        PrediccionDemandaRequest prediccionDemandaRequest = new PrediccionDemandaRequest();

        prediccionDemandaRequest.setCantidadPredicciones(cantPred);
        prediccionDemandaRequest.setNumeroPeriodos(numPeriodos);
        prediccionDemandaRequest.setTipoPeriodo(tipoPeriodo);
        prediccionDemandaRequest.setArticuloId(articuloId);
        prediccionDemandaRequest.setFechaDesdePrediccion(fechaDesdePrediccion);
        prediccionDemandaRequest.setTipoPrediccion(tipoPrediccion);
        prediccionDemandaRequest.setPonderaciones(ponderaciones);
        prediccionDemandaRequest.setAlpha(alpha);

        return prediccionDemandaRequest;
    }

    //Crear DTO de predicciones
    private static PrediccionDTO getPrediccionDTO(PrediccionDemanda prediccion) {
        PrediccionDTO prediccionDTO = new PrediccionDTO();
        prediccionDTO.setFechaDesdePrediccion(prediccion.getFechaDesde());
        prediccionDTO.setFechaHastaPrediccion(prediccion.getFechaHasta());
        prediccionDTO.setCantidadPredecida(prediccion.getPrediccion());
        prediccionDTO.setNombreArticulo(prediccion.getArticulo().getNombre());
        prediccionDTO.setIdArticulo(prediccion.getArticulo().getId());
        prediccionDTO.setFechaPrediccionRealizada(prediccion.getFechaPrediccion());

        return prediccionDTO;
    }

}
