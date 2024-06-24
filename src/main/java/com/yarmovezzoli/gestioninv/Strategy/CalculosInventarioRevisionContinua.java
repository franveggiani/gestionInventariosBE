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
public class CalculosInventarioRevisionContinua implements CalculosInventario{

    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    ArticuloRepository articuloRepository;

    @Override
    public void getDatosInventario(Map<String, Object> parametros) throws Exception {
        //Cálculo de stock de seguridad
        //Voy a suponer momentáneamente Z = 1.65 (98%)
        //L será por días

        float Z = 1.65f;
        int L = parametros.get("L") == null? 1 : (int) parametros.get("L");
        Long idArticulo = parametros.get("idArticulo") == null? 0 : (Long) parametros.get("idArticulo");
        float costoPedido = parametros.get("costoPedido") == null? 0 : (float) parametros.get("costoPedido");
        int year = parametros.get("year") == null? 0 : (int) parametros.get("year");
        int diasLaborales = parametros.get("diasLaborales") == null? 0 : (int) parametros.get("diasLaborales");

        int stockSeguridad;
        float varianza;
        float desviacionEstandar;
        int demandaAnual;
        int EOQ;
        int ROP;

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
        stockSeguridad = (int) Math.round(Z * desviacionEstandar * Math.sqrt(L));

        //Calcular EOQ
        //Obteniendo demanda anual
        List<Venta> listasAnuales = ventaRepository.findByYear(year);

        int[] ventasAnuales = {0};
        listasAnuales.forEach(venta -> {
            ventasAnuales[0] += venta.getCantidad();
        });

        demandaAnual = ventasAnuales[0];

        //Obtener costo de almacenamiento

        Double costoAlmacenamiento = 0.0;

        Optional<Articulo> articuloOptional = articuloRepository.findById(idArticulo);
        if (articuloOptional.isPresent()) {
            costoAlmacenamiento = articuloOptional.get().getCostoAlmacenamiento();
        }

        //Finalmente calculamos EOQ
        EOQ = (int) Math.round(Math.sqrt((2*demandaAnual*costoPedido)/(costoAlmacenamiento)));

        //Calculando ROP (o PP)
        ROP = (int) demandaPromedio * L + stockSeguridad;
    }
}
