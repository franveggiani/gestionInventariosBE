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
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class PrediccionPromedioMovilPonderado implements PrediccionDemandaStrategy{

    @Autowired
    ArticuloRepository articuloRepository;
    @Autowired
    VentaRepository ventaRepository;

    @Override
    public List<PrediccionDemanda> predecirDemanda(PrediccionDemandaRequest prediccionDemandaRequest) {

        LocalDate fechaInicioPrediccion = prediccionDemandaRequest.getFechaDesdePrediccion();
        TipoPeriodo tipoPeriodo = prediccionDemandaRequest.getTipoPeriodo();
        Long idArticulo = prediccionDemandaRequest.getArticuloId();
        Long cantDiasPeriodo = tipoPeriodo.getDias();
        Articulo articulo = articuloRepository.findById(idArticulo).orElse(null);
        int numeroPeriodos = prediccionDemandaRequest.getNumeroPeriodos();
        Double[] ponderaciones = prediccionDemandaRequest.getPonderaciones();

        LocalDate fechaInicioPeriodo = fechaInicioPrediccion.minusDays(cantDiasPeriodo*numeroPeriodos);
        LocalDate fechaFinPeriodo = fechaInicioPeriodo.plusDays(cantDiasPeriodo);

        List<Integer> ventasPorPeriodo = new ArrayList<>();

        for (int i = 0; i < ponderaciones.length; i++) {

            List<Venta> ventaList = ventaRepository.findByPeriodoAndArticulo(fechaInicioPeriodo, fechaFinPeriodo, articulo);

            int sumatoria = 0;
            for (Venta venta : ventaList) {
                sumatoria += venta.getCantidad();
            }

            ventasPorPeriodo.add(sumatoria);

            fechaInicioPeriodo = fechaInicioPeriodo.plusDays(cantDiasPeriodo);
            fechaFinPeriodo = fechaInicioPeriodo.plusDays(cantDiasPeriodo);
        }

        float denominador = 0;
        float numerador = 0;
        int promedioPonderado;
        for (int i = 0; i < ponderaciones.length; i++) {
            numerador += (float) ventasPorPeriodo.get(i) * ponderaciones[i];
            denominador += ponderaciones[i];

            System.out.println("periodo: " + i + " ponderacion: " + ponderaciones[i] + " sumatoria: " + ventasPorPeriodo.get(i));
        }

        promedioPonderado = Math.round(numerador / denominador);
        System.out.println("promedioPonderado: " + promedioPonderado);

        List<PrediccionDemanda> prediccionDemandaList = new ArrayList<>();

        PrediccionDemanda prediccion = crearPrediccionDemanda(promedioPonderado, fechaInicioPrediccion, articulo, cantDiasPeriodo);

        prediccionDemandaList.add(prediccion);

        return prediccionDemandaList;
    }

    public PrediccionDemanda crearPrediccionDemanda(int prediccion, LocalDate fechaInicioPrediccion, Articulo articulo, Long cantDiasPeriodo) {
        PrediccionDemanda prediccionDemanda = new PrediccionDemanda();

        prediccionDemanda.setPrediccion(prediccion);
        prediccionDemanda.setTipoPrediccion(TipoPrediccion.PROM_MOVIL_PONDERADO);
        prediccionDemanda.setFechaPrediccion(LocalDate.now());
        prediccionDemanda.setFechaDesde(fechaInicioPrediccion);
        prediccionDemanda.setFechaHasta(fechaInicioPrediccion.plusDays(cantDiasPeriodo));
        prediccionDemanda.setArticulo(articulo);

        return prediccionDemanda;
    }
}
