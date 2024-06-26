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

        LocalDate fechaInicioPrediccion = prediccionDemandaRequest.getFechaDesdePrediccion() != null ? prediccionDemandaRequest.getFechaDesdePrediccion() : LocalDate.now();
        TipoPeriodo tipoPeriodo = prediccionDemandaRequest.getTipoPeriodo();
        Long idArticulo = prediccionDemandaRequest.getArticuloId();
        Long cantMesesPeriodo = tipoPeriodo.getCantidadMeses();
        Articulo articulo = articuloRepository.findById(idArticulo).orElse(null);
        int numeroPeriodos = prediccionDemandaRequest.getNumeroPeriodos();
        Double[] ponderaciones = prediccionDemandaRequest.getPonderaciones();

        if (ponderaciones.length!= numeroPeriodos) {
            throw new IllegalArgumentException("La cantidad de ponderaciones debe ser igual a la cantidad de periodos");
        }

        LocalDate fechaInicioPeriodo = fechaInicioPrediccion.minusMonths(cantMesesPeriodo*numeroPeriodos);
        LocalDate fechaFinPeriodo = fechaInicioPeriodo.plusMonths(cantMesesPeriodo);

        List<Integer> ventasPorPeriodo = new ArrayList<>();

        for (int i = 0; i < ponderaciones.length; i++) {

            List<Venta> ventaList = ventaRepository.findByPeriodoAndArticulo(fechaInicioPeriodo, fechaFinPeriodo, articulo);

            int sumatoria = 0;
            for (Venta venta : ventaList) {
                sumatoria += venta.getCantidad();
            }
            System.out.println("periodo: " + (i + 1) + "; ventas obtenidas del periodo: " + sumatoria + "; desde: " + fechaInicioPeriodo + "; hasta: " + fechaFinPeriodo);
            ventasPorPeriodo.add(sumatoria);

            fechaInicioPeriodo = fechaInicioPeriodo.plusMonths(cantMesesPeriodo);
            fechaFinPeriodo = fechaInicioPeriodo.plusMonths(cantMesesPeriodo);
        }

        float denominador = 0;
        float numerador = 0;
        int promedioPonderado;
        for (int i = 0; i < ponderaciones.length; i++) {
            numerador += (float) ventasPorPeriodo.get(i) * ponderaciones[i];
            denominador += ponderaciones[i];

            System.out.println("Periodo: " + i + "; Ponderacion: " + ponderaciones[i] + "; Ventas del periodo: " + ventasPorPeriodo.get(i));
        }

        promedioPonderado = Math.round(numerador / denominador);
        System.out.println("promedioPonderado: " + promedioPonderado);

        List<PrediccionDemanda> prediccionDemandaList = new ArrayList<>();

        PrediccionDemanda prediccion = crearPrediccionDemanda(promedioPonderado, fechaInicioPrediccion, articulo, cantMesesPeriodo);

        prediccionDemandaList.add(prediccion);

        return prediccionDemandaList;
    }

    public PrediccionDemanda crearPrediccionDemanda(int prediccion, LocalDate fechaInicioPrediccion, Articulo articulo, Long cantMesesPeriodo) {
        PrediccionDemanda prediccionDemanda = new PrediccionDemanda();

        prediccionDemanda.setPrediccion(prediccion);
        prediccionDemanda.setTipoPrediccion(TipoPrediccion.PROM_MOVIL_PONDERADO);
        prediccionDemanda.setFechaPrediccion(LocalDate.now());
        prediccionDemanda.setFechaDesde(fechaInicioPrediccion);
        prediccionDemanda.setFechaHasta(fechaInicioPrediccion.plusMonths(cantMesesPeriodo));
        prediccionDemanda.setArticulo(articulo);

        return prediccionDemanda;
    }
}
