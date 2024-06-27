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
            List<PrediccionDTO> prediccionDTOList = new ArrayList<>();
            for (int i = 0; i < prediccionDemandaList.size(); i++) {
                PrediccionDemanda prediccion = prediccionDemandaList.get(i);

                PrediccionDTO prediccionDTO = new PrediccionDTO();
                prediccionDTO.setFechaDesdePrediccion(prediccion.getFechaDesde());
                prediccionDTO.setFechaHastaPrediccion(prediccion.getFechaHasta());
                prediccionDTO.setCantidadPredecida(prediccion.getPrediccion());
                prediccionDTO.setNombreArticulo(prediccion.getArticulo().getNombre());
                prediccionDTO.setIdArticulo(prediccion.getArticulo().getId());
                prediccionDTO.setFechaPrediccionRealizada(prediccion.getFechaPrediccion());

                prediccionDTOList.add(prediccionDTO);
            }
            
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

            Optional<Articulo> articuloOptional = articuloRepository.findById(idArticulo);

            if (articuloOptional.isPresent()) {
                Articulo articulo = articuloOptional.get();

            }



            return null;
        } catch (Exception e) {
            throw new Exception("Error al obtener las predicciones: " + e.getMessage());
        }
    }

    @Override
    public List<Venta> getVentasPorArticulo(Long articuloId) throws Exception {
        try {
            Optional<Articulo> articuloOptional = articuloRepository.findById(articuloId);

            if (articuloOptional.isPresent()){
                Articulo articulo = articuloOptional.get();
                return ventaRepository.findByArticulo(articulo);
            }  else {
                throw new Exception("Error: El artículo requerido no ha sido encontrado");
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
