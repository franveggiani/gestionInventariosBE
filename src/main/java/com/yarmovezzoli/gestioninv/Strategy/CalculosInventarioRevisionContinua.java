package com.yarmovezzoli.gestioninv.Strategy;

import com.yarmovezzoli.gestioninv.DTOs.ModuloInventarios.DTODatosInventario;
import com.yarmovezzoli.gestioninv.DTOs.ModuloInventarios.DTODatosInventarioOutput;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.Venta;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CalculosInventarioRevisionContinua implements CalculosInventario{

    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    ArticuloRepository articuloRepository;

    @Override
    public DTODatosInventarioOutput getDatosInventario(DTODatosInventario dtoDatosInventario) throws Exception {

        float Z = dtoDatosInventario.getZ();
        int L = dtoDatosInventario.getL();
        Long idArticulo = dtoDatosInventario.getIdArticulo();
        float costoPedido = dtoDatosInventario.getCostoPedido();
        int year = dtoDatosInventario.getYear();
        int diasLaborales = dtoDatosInventario.getDiasLaborales();
        float precioUnidad = dtoDatosInventario.getPrecioUnidad();
        Articulo articulo = new Articulo();
        int stockSeguridad;
        float varianza;
        float desviacionEstandar;
        int demandaAnual;
        int EOQ;
        int ROP;

        //Obtener costo de almacenamiento y el articulo
        Double costoAlmacenamiento = 0.0;
        Optional<Articulo> articuloOptional = articuloRepository.findById(idArticulo);

        if (articuloOptional.isPresent()) {
            articulo = articuloOptional.get();
            costoAlmacenamiento = articuloOptional.get().getCostoAlmacenamiento();
        }

        //Obtener demanda promedio
        LocalDate fechaActual = LocalDate.now();

        float demandaPromedio;
        int ventasTotales = 0;
        List<Integer> ventasPorDia = new ArrayList<>();

        fechaActual = fechaActual.minusDays(L);

        for (int i = 0; i < L; i++) {

            //System.out.println(fechaActual);
            List<Venta> ventasList = ventaRepository.findByFechaAndArticulo(fechaActual, articulo);
            int ventasDelDia = 0;

            for (Venta venta : ventasList) {
                ventasDelDia += venta.getCantidad();
            }

            fechaActual = fechaActual.plusDays(1);

            ventasTotales += ventasDelDia;
            ventasPorDia.add(ventasDelDia);
        }

        System.out.println("Ventas totales: " + ventasTotales);

        //Promedio de demanda
        demandaPromedio = ventasTotales / L;

        System.out.println("Promedio de demanda: " + demandaPromedio);

        //Varianza
        float sumatoria = 0;
        for (int j = 0; j < L; j++) {
            sumatoria += (float) Math.pow(ventasPorDia.get(j) - demandaPromedio, 2);
        }
        varianza = sumatoria / (L-1);

        System.out.println("Varianza: " + varianza);

        //Desviacion estandar
        desviacionEstandar = (float) Math.sqrt(varianza);

        //Stock de seguridad
        stockSeguridad = (int) Math.round(Z * desviacionEstandar * Math.sqrt(L));

        //Obteniendo demanda anual
        List<Venta> listasAnuales = ventaRepository.findByYearAndArticulo(year, articulo);

        int[] ventasAnuales = {0};
        listasAnuales.forEach(venta -> {
            ventasAnuales[0] += venta.getCantidad();
        });

        demandaAnual = ventasAnuales[0];

        //Finalmente calculamos EOQ
        EOQ = (int) Math.round(Math.sqrt((2*demandaAnual*costoPedido)/(costoAlmacenamiento)));

        //Calculando ROP (o PP)
        ROP = (int) demandaPromedio * L + stockSeguridad;

        //Calculamos CGI
        float CGI = (float) (precioUnidad * EOQ + costoAlmacenamiento * (EOQ/2) + costoPedido * demandaAnual/EOQ);

        DTODatosInventarioOutput dtoDatosInventarioOutput = new DTODatosInventarioOutput();

        dtoDatosInventarioOutput.setStockSeguridad(stockSeguridad);
        dtoDatosInventarioOutput.setQ(EOQ);
        dtoDatosInventarioOutput.setROP(ROP);
        dtoDatosInventarioOutput.setCGI(CGI);

        return dtoDatosInventarioOutput;

    }
}
