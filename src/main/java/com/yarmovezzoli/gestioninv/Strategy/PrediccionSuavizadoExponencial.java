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
public class PrediccionSuavizadoExponencial implements PrediccionDemandaStrategy{

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
        Long cantMesesPeriodo = tipoPeriodo.getCantidadMeses();
        Articulo articulo = articuloRepository.findById(idArticulo).orElse(null);
        int numeroPeriodos = prediccionDemandaRequest.getNumeroPeriodos();
        float alpha = prediccionDemandaRequest.getAlpha();

        //Obtengo la primera prediccion
        LocalDate fechaInicioAuxiliar = fechaInicioPrediccion.minusMonths(cantMesesPeriodo); //Para calcular la prediccion del periodo anterior

        LocalDate fechaInicioPeriodo = fechaInicioAuxiliar.minusMonths(cantMesesPeriodo * numeroPeriodos);
        LocalDate fechaFinPeriodo = fechaInicioPeriodo.plusMonths(cantMesesPeriodo);

        List<Integer> ventasPorPeriodo = new ArrayList<>();

        for (int i = 0; i < numeroPeriodos; i++) {
            List<Venta> ventaList = ventaRepository.findByPeriodoAndArticulo(fechaInicioPeriodo, fechaFinPeriodo, articulo);
            int ventasDelPeriodo = ventaList.stream().mapToInt(Venta::getCantidad).sum();

            ventasPorPeriodo.add(ventasDelPeriodo);

            System.out.println("periodo: " + (i + 1) + "; ventas obtenidas del periodo: " + ventasDelPeriodo + "; desde: " + fechaInicioPeriodo + "; hasta: " + fechaFinPeriodo);

            fechaInicioPeriodo = fechaInicioPeriodo.plusMonths(cantMesesPeriodo);
            fechaFinPeriodo = fechaFinPeriodo.plusMonths(cantMesesPeriodo);
        }

        int sumatoria = ventasPorPeriodo.stream().mapToInt(Integer::intValue).sum();

        int X0 = sumatoria / numeroPeriodos;

        List<PrediccionDemanda> prediccionesObtenidas = new ArrayList<>();

        int X1 = Math.round(X0 + alpha*(ventasPorPeriodo.getLast() - X0));
        System.out.println("Prediccion en corrida 1: " + X1);

        prediccionesObtenidas.add(crearPrediccionDemanda(X1, fechaInicioPrediccion, articulo, cantMesesPeriodo));

        if (cantidadPredicciones > 1) {

            fechaInicioPeriodo = fechaInicioPrediccion;
            fechaFinPeriodo = fechaFinPeriodo.plusMonths(cantMesesPeriodo);

            for (int i = 0; i < cantidadPredicciones; i++) {
                List<Venta> ventaList = ventaRepository.findByPeriodoAndArticulo(fechaInicioPeriodo, fechaFinPeriodo, articulo);
                int ventasDelPeriodo = ventaList.stream().mapToInt(Venta::getCantidad).sum();

                int Xi = 0;
                if (i == 0) {
                    Xi = X0;
                } else {
                    Xi = X1;
                }
                X1 = Math.round(Xi + alpha*(ventasDelPeriodo - Xi));
                prediccionesObtenidas.add(crearPrediccionDemanda(X1, fechaInicioPrediccion, articulo, cantMesesPeriodo));

                System.out.println("Prediccion en corrida " + (i+2) + ": " + X1 + "; VentasDelPeriodo: " + ventasDelPeriodo + "; PrediccionAnterior: " + X1);

                fechaInicioPeriodo = fechaInicioPeriodo.plusMonths(cantMesesPeriodo);
                fechaFinPeriodo = fechaFinPeriodo.plusMonths(cantMesesPeriodo);
            }
        }

        return prediccionesObtenidas;
    }

    public PrediccionDemanda crearPrediccionDemanda(int prediccion, LocalDate fechaInicioPrediccion, Articulo articulo, Long cantMesesPeriodo) {
        PrediccionDemanda prediccionDemanda = new PrediccionDemanda();

        prediccionDemanda.setPrediccion(prediccion);
        prediccionDemanda.setTipoPrediccion(TipoPrediccion.PROM_MOVIL);
        prediccionDemanda.setFechaPrediccion(LocalDate.now());
        prediccionDemanda.setFechaDesde(fechaInicioPrediccion);
        prediccionDemanda.setFechaHasta(fechaInicioPrediccion.plusMonths(cantMesesPeriodo));
        prediccionDemanda.setArticulo(articulo);

        return prediccionDemanda;
    }

}
