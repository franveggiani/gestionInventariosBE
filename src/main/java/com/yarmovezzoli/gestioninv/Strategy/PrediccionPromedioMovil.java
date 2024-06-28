package com.yarmovezzoli.gestioninv.Strategy;

import com.yarmovezzoli.gestioninv.DTOs.PrediccionDemandaRequest;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.PrediccionDemanda;
import com.yarmovezzoli.gestioninv.Entities.Venta;
import com.yarmovezzoli.gestioninv.Enums.TipoPeriodo;
import com.yarmovezzoli.gestioninv.Enums.TipoPrediccion;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class PrediccionPromedioMovil implements PrediccionDemandaStrategy {

    @Autowired
    ArticuloRepository articuloRepository;
    @Autowired
    VentaRepository ventaRepository;

    @Override
    public List<PrediccionDemanda> predecirDemanda(PrediccionDemandaRequest prediccionDemandaRequest) {

        int cantidadPredicciones = prediccionDemandaRequest.getCantidadPredicciones();
        LocalDate fechaInicioPrediccion = prediccionDemandaRequest.getFechaDesdePrediccion() != null ? prediccionDemandaRequest.getFechaDesdePrediccion() : LocalDate.now();
        TipoPeriodo tipoPeriodo = prediccionDemandaRequest.getTipoPeriodo();
        Long idArticulo = prediccionDemandaRequest.getArticuloId();
        Long cantDiasPeriodo = tipoPeriodo.getDias();
        Articulo articulo = articuloRepository.findById(idArticulo).orElse(null);
        int numeroPeriodos = prediccionDemandaRequest.getNumeroPeriodos();
        List<Integer> predicciones = new ArrayList<>();
        List<PrediccionDemanda> prediccionDemandaList = new ArrayList<>();

        LocalDate fechaInicioPeriodo = fechaInicioPrediccion.minusDays(cantDiasPeriodo*numeroPeriodos);
        LocalDate fechaFinPeriodo = fechaInicioPeriodo.plusDays(cantDiasPeriodo);

        List<Integer> ventasPorPeriodo = new ArrayList<>();

        for (int i = 0; i < numeroPeriodos; i++) {
            List<Venta> ventaList = ventaRepository.findByPeriodoAndArticulo(fechaInicioPeriodo, fechaFinPeriodo, articulo);

            int ventasDelPeriodo = 0;
            for (Venta venta : ventaList) {
                ventasDelPeriodo += venta.getCantidad();
            }
            ventasPorPeriodo.add(ventasDelPeriodo);

            System.out.println("periodo: " + i + "; ventas obtenidas del periodo: " + ventasDelPeriodo + "; desde: " + fechaInicioPeriodo + "; hasta: " + fechaFinPeriodo);

            fechaInicioPeriodo = fechaInicioPeriodo.plusDays(cantDiasPeriodo);
            fechaFinPeriodo = fechaFinPeriodo.plusDays(cantDiasPeriodo);

        }

        int sumatoria = 0;

        for (Integer ventas : ventasPorPeriodo) {
            sumatoria += ventas;
        }

        int prediccion = sumatoria / numeroPeriodos;

        System.out.println("Prediccion: " + prediccion + "; sumatoria: " + sumatoria + "; numeroPeriodos: " + numeroPeriodos);

        predicciones.add(prediccion);

        PrediccionDemanda prediccionDemanda = crearPrediccionDemanda(prediccion, fechaInicioPrediccion, articulo, cantDiasPeriodo);
        prediccionDemandaList.add(prediccionDemanda);

        //Cuando hay más corridas
        if (cantidadPredicciones != 0) {
            int prediccionExtra;
            for (int i = 1; i < cantidadPredicciones; i++) {
                ventasPorPeriodo.remove(0);
                ventasPorPeriodo.add(predicciones.getLast());

                sumatoria = 0;
                for (Integer ventas : ventasPorPeriodo) {
                    sumatoria += ventas;
                }
                prediccionExtra = sumatoria / numeroPeriodos;
                predicciones.add(Math.round(prediccion));

                PrediccionDemanda prediccionDemanda1 = crearPrediccionDemanda(prediccionExtra, fechaInicioPrediccion, articulo, cantDiasPeriodo);
                prediccionDemandaList.add(prediccionDemanda1);

                fechaInicioPrediccion = fechaInicioPrediccion.plusDays(cantDiasPeriodo);
            }
        }
        return prediccionDemandaList;
    }

    public PrediccionDemanda crearPrediccionDemanda(int prediccion, LocalDate fechaInicioPrediccion, Articulo articulo, Long cantDiasPeriodo) {
        PrediccionDemanda prediccionDemanda = new PrediccionDemanda();

        prediccionDemanda.setPrediccion(prediccion);
        prediccionDemanda.setTipoPrediccion(TipoPrediccion.PROM_MOVIL);
        prediccionDemanda.setFechaPrediccion(LocalDate.now());
        prediccionDemanda.setFechaDesde(fechaInicioPrediccion);
        prediccionDemanda.setFechaHasta(fechaInicioPrediccion.plusDays(cantDiasPeriodo));
        prediccionDemanda.setArticulo(articulo);

        return prediccionDemanda;
    }

}
