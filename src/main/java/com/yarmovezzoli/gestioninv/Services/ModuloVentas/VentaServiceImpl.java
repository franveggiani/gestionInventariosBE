package com.yarmovezzoli.gestioninv.Services.ModuloVentas;

import com.yarmovezzoli.gestioninv.DTOs.DemandaHistoricaRequest;
import com.yarmovezzoli.gestioninv.DTOs.PrediccionDTO;
import com.yarmovezzoli.gestioninv.DTOs.PrediccionDemandaRequest;
import com.yarmovezzoli.gestioninv.DTOs.VentaRequestDTO;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.DemandaHistorica;
import com.yarmovezzoli.gestioninv.Entities.PrediccionDemanda;
import com.yarmovezzoli.gestioninv.Entities.Venta;
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

    public VentaServiceImpl(BaseRepository<Venta, Long> baseRepository, VentaRepository ventaRepository, ArticuloRepository articuloRepository, DemandaHistoricaRepository demandaHistoricaRepository, PrediccionDemandaRepository prediccionDemandaRepository){
        super(baseRepository);
        this.ventaRepository = ventaRepository;
        this.articuloRepository = articuloRepository;
        this.demandaHistoricaRepository = demandaHistoricaRepository;
        this.prediccionDemandaRepository = prediccionDemandaRepository;
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

            Optional<Articulo> articuloOptional = articuloRepository.findById(ventaRequest.getArticuloId());

            if (articuloOptional.isPresent()){

                Articulo articulo = articuloOptional.get();

                nuevaVenta.setArticulo(articulo);
                articulo.descontarStock(ventaRequest.getCantidad());

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
    public List<PrediccionDTO> getPrediccionDemanda(PrediccionDemandaRequest prediccionDemandaRequest) throws Exception {
        try {
            //Parámetros iniciales
            int cantidadPredicciones = prediccionDemandaRequest.getCantidadPredicciones();
            int numeroPeriodos = prediccionDemandaRequest.getNumeroPeriodos();
            Long idArticulo = prediccionDemandaRequest.getArticuloId();
            TipoPeriodo tipoPeriodo = prediccionDemandaRequest.getTipoPeriodo();
            TipoPrediccion tipoPrediccion = prediccionDemandaRequest.getTipoPrediccion();
            LocalDate fechaInicioPrediccion = prediccionDemandaRequest.getFechaDesdePrediccion();
            Articulo articulo;
            Long cantDiasPeriodo = tipoPeriodo.getDias();

            //Busqueda de artículo
            Optional<Articulo> articuloOptional = articuloRepository.findById(idArticulo);
            if(articuloOptional.isPresent()){
                articulo = articuloOptional.get();
            } else {
                throw new Exception("Error: El artículo requerido no ha sido encontrado");
            }

            //Creo instancia para calcular la predicción según el tipo
            PrediccionDemandaFactory prediccionDemandaFactory = PrediccionDemandaFactory.getInstance();
            PrediccionDemandaStrategy prediccionDemandaStrategy = prediccionDemandaFactory.getPrediccionDemandaStrategy(tipoPrediccion);

            //Parámetros que voy a pasar para calcular la predicción
            Map<String, Object> parametros = new HashMap<>();

            //Asigno parámetros para calcular la predicción según el tipo
            if (tipoPrediccion.equals(TipoPrediccion.PROM_MOVIL_PONDERADO)){
                parametros.put("ponderaciones", prediccionDemandaRequest.getPonderaciones());
            } else if (tipoPrediccion.equals(TipoPrediccion.EXPONENCIAL)){
                parametros.put("alpha", prediccionDemandaRequest.getAlpha());
            }

            LocalDate fechaInicioPeriodo = fechaInicioPrediccion.minusDays(tipoPeriodo.getDias()*numeroPeriodos);    //30días * 3periodos = 90 días atrás (si es mensual)
            LocalDate fechaFinPeriodo = fechaInicioPeriodo.plusDays(cantDiasPeriodo);
            List<Integer> arregloCantidades = new ArrayList<>();
            for (int i = 0; i < numeroPeriodos; i++) {
                List<Venta> ventaList = ventaRepository.findByPeriodoAndArticulo(fechaInicioPeriodo, fechaFinPeriodo, articulo);

                int ventasDelPeriodo = 0;
                for (Venta venta : ventaList) {
                    ventasDelPeriodo += venta.getCantidad();
                }
                arregloCantidades.add(ventasDelPeriodo);

                fechaInicioPeriodo = fechaInicioPeriodo.plusDays(cantDiasPeriodo);
                fechaFinPeriodo = fechaFinPeriodo.plusDays(cantDiasPeriodo);
            }

            parametros.put("arregloCantidades", arregloCantidades);
            parametros.put("articulo", articulo);
            parametros.put("fechaDesdePrediccion", fechaInicioPrediccion);
            parametros.put("fechaHastaPrediccion", fechaInicioPrediccion.plusDays(fechaInicioPrediccion.getMonth().length(false)));

            //Predicción
            List<PrediccionDemanda> prediccionDemandaList = new ArrayList<>();
            for (int i = 0; i < cantidadPredicciones; i++) {
                if (i == 0){
                    PrediccionDemanda prediccion = prediccionDemandaStrategy.predecirDemanda(parametros);
                    prediccionDemandaList.add(prediccion);
                    prediccionDemandaRepository.save(prediccion);

                } else if (!tipoPrediccion.equals(TipoPrediccion.PROM_MOVIL_PONDERADO)) {
                    //Elimino primer elemento y agrego el último que va a ser la última predicción
                    arregloCantidades.remove(0);
                    arregloCantidades.add(prediccionDemandaList.getLast().getPrediccion());

                    //Nueva fecha de predicción
                    fechaInicioPrediccion = fechaInicioPrediccion.plusDays(fechaInicioPrediccion.getMonth().length(false));
                    parametros.put("arregloCantidades", arregloCantidades);
                    parametros.put("fechaInicioPrediccion", fechaInicioPrediccion);
                    parametros.put("fechaHastaPrediccion", fechaInicioPrediccion.plusDays(fechaInicioPrediccion.getMonth().length(false)));

                    PrediccionDemanda prediccion = prediccionDemandaStrategy.predecirDemanda(parametros);
                    prediccionDemandaList.add(prediccion);
                    prediccionDemandaRepository.save(prediccion);
                }
            }

            //Creo lista de DTO's
            List<PrediccionDTO> prediccionDTOList = new ArrayList<>();
            for (int i = 0; i < prediccionDemandaList.size(); i++) {
                PrediccionDemanda prediccion = prediccionDemandaList.get(i);

                PrediccionDTO prediccionDTO = new PrediccionDTO();
                prediccionDTO.setFechaDesdePrediccion(prediccion.getFechaDesde());
                prediccionDTO.setFechaHastaPrediccion(prediccion.getFechaHasta());
                prediccionDTO.setCantidadPredecida(prediccion.getPrediccion());
                prediccionDTO.setNombreArticulo(prediccion.getArticulo().getNombre());
                prediccionDTO.setIdArticulo(prediccion.getArticulo().getId());

                prediccionDTOList.add(prediccionDTO);
            }
            
            return prediccionDTOList;

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<PrediccionDemanda> getPredicciones(Long idArticulo, int year) throws Exception {
        try {
            List<PrediccionDemanda> prediccionDemandaList;
            Optional<Articulo> articuloOptional = articuloRepository.findById(idArticulo);

            if (articuloOptional.isPresent()){
                Articulo articulo = articuloOptional.get();
                prediccionDemandaList = prediccionDemandaRepository.findByYearAndArticulo(articulo, year);
            } else if (idArticulo == 0 && year == 0) {
                prediccionDemandaList = prediccionDemandaRepository.findAll();
            } else {
                throw new Exception("Error: El artículo requerido no ha sido encontrado");
            }

            return prediccionDemandaList;

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
