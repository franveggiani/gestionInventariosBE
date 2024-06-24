package com.yarmovezzoli.gestioninv.Strategy;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.Venta;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CalculosInventarioRevisionPeriodica implements CalculosInventario{

    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    ArticuloRepository articuloRepository;

    public void getDatosInventario(Map<String, Object> parametros) { //Cambiar el void después

        //Cálculo de stock de seguridad
        //Voy a suponer momentáneamente Z = 1.65 (98%)
        //L será por días

        float Z = 1.65f;
        int L = parametros.get("L") == null? 1 : (int) parametros.get("L");
        int T = parametros.get("T") == null? 1 : (int) parametros.get("T");
        Long idArticulo = parametros.get("idArticulo") == null? 0 : (Long) parametros.get("idArticulo");
        float stockSeguridad = 0;
        float varianza;
        float desviacionEstandar;

        //Obtener demanda promedio
        LocalDate fechaActual = LocalDate.now();

        float demandaPromedio = 0;
        int[] ventasTotales = {0};
        int[] ventasPorDia = {0};

        for (int i = 0; i < L; i++) {
            fechaActual = fechaActual.minusDays(5);

            List<Venta> ventasList = ventaRepository.findByFecha(fechaActual);

            int[] ventasDelDia = {0};

            ventasList.forEach(venta -> {
                ventasTotales[0] += venta.getCantidad();
                ventasDelDia[0] = venta.getCantidad();
            });

            ventasPorDia[i] = ventasDelDia[0];
        }

        //Promedio de demanda
        demandaPromedio = (float) (demandaPromedio / L);

        //Varianza
        float sumatoria = 0;
        for (int j = 0; j < L; j++) {
            sumatoria += (float) Math.pow(ventasPorDia[j] - demandaPromedio, 2);
        }
        varianza = sumatoria / (L-1);

        //Desviacion estandar
        desviacionEstandar = (float) Math.sqrt(varianza);

        //Stock de seguridad
        stockSeguridad = (float) (Z * desviacionEstandar * Math.sqrt(T+L));

        //Ahora calculamos el ROP
        int ROP = (int) (demandaPromedio * (T+L) + stockSeguridad);

        Optional<Articulo> articuloOptional = articuloRepository.findById(idArticulo);

        int inventarioActual;
        if (articuloOptional.isPresent()) {
            inventarioActual = articuloOptional.get().getStockActual();
        } else {
            throw new IllegalArgumentException("No se encontró el artículo");
        }

        //Ahora calculamos q
        int q = Math.round(demandaPromedio*(T+L) + stockSeguridad - inventarioActual);

    }

}
