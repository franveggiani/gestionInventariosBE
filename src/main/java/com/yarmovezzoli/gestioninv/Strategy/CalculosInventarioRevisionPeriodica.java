package com.yarmovezzoli.gestioninv.Strategy;

import com.yarmovezzoli.gestioninv.DTOs.ModuloInventarios.DTODatosInventario;
import com.yarmovezzoli.gestioninv.DTOs.ModuloInventarios.DTODatosInventarioOutput;
import com.yarmovezzoli.gestioninv.DTOs.PrediccionDemandaRequest;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.PrediccionDemanda;
import com.yarmovezzoli.gestioninv.Entities.ProveedorArticulo;
import com.yarmovezzoli.gestioninv.Entities.Venta;
import com.yarmovezzoli.gestioninv.Enums.TipoPeriodo;
import com.yarmovezzoli.gestioninv.Enums.TipoPrediccion;
import com.yarmovezzoli.gestioninv.Factory.PrediccionDemandaFactory;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.ProveedorArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CalculosInventarioRevisionPeriodica implements CalculosInventario{

    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    ArticuloRepository articuloRepository;
    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;
    @Autowired
    private PrediccionDemandaFactory prediccionDemandaFactory;

    public DTODatosInventarioOutput getDatosInventario(DTODatosInventario dtoDatosInventario) { //Cambiar el void después

        //Cálculo de stock de seguridad
        //Voy a suponer momentáneamente Z = 1.65 (98%)
        //L será por días

        float Z = dtoDatosInventario.getZ();
        int L = dtoDatosInventario.getL();
        int T = dtoDatosInventario.getT();
        Long idArticulo = dtoDatosInventario.getIdArticulo();
        float precioUnidad = dtoDatosInventario.getPrecioUnidad();
        float costoPedido = dtoDatosInventario.getCostoPedido();
        int year = dtoDatosInventario.getYear();
        Double costoAlmacenamiento = 0.0;
        Articulo articulo = new Articulo();
        int inventarioActual = 0;
        float stockSeguridad;
        float varianza;
        float desviacionEstandar;
        int demandaAnual;

        Optional<Articulo> articuloOptional = articuloRepository.findById(idArticulo);
        if (articuloOptional.isPresent()) {
            articulo = articuloOptional.get();
            inventarioActual = articulo.getStockActual();
            costoAlmacenamiento = articulo.getCostoAlmacenamiento();
        }

        //Hago una prediccion del mes que viene
        PrediccionDemandaRequest prediccionDemandaRequest = new PrediccionDemandaRequest();
        prediccionDemandaRequest.setCantidadPredicciones(1);
        prediccionDemandaRequest.setNumeroPeriodos(3);
        prediccionDemandaRequest.setTipoPrediccion(TipoPrediccion.PROM_MOVIL);
        prediccionDemandaRequest.setArticuloId(articulo.getId());
        prediccionDemandaRequest.setFechaDesdePrediccion(LocalDate.now());
        prediccionDemandaRequest.setTipoPeriodo(TipoPeriodo.MENSUAL);

        PrediccionDemandaStrategy prediccionDemandaStrategy = prediccionDemandaFactory.getPrediccionDemandaStrategy(TipoPrediccion.PROM_MOVIL);
        List<PrediccionDemanda> prediccionDemanda = prediccionDemandaStrategy.predecirDemanda(prediccionDemandaRequest);

        int prediccionMesQueViene = prediccionDemanda.get(0).getPrediccion();

        List<Integer> ventasPorDia = new ArrayList<>();
        int ventasTotales = 0;
        LocalDate fechaActual = LocalDate.now();
        fechaActual = fechaActual.minusDays(L);
        for (int i = 0; i < L; i++) {

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
        float demandaPromedio = ventasTotales / L;

        //Varianza
        float sumatoria = 0;
        for (int j = 0; j < L; j++) {
            sumatoria += (float) Math.pow(ventasPorDia.get(j) - demandaPromedio, 2);
        }
        varianza = sumatoria / (L-1);

        //Desviacion estandar
        desviacionEstandar = (float) Math.sqrt(varianza);

        //Stock de seguridad
        stockSeguridad = (float) (Z * desviacionEstandar * Math.sqrt(T+L));

        //Ahora calculamos el ROP
        int ROP = (int) (demandaPromedio * (T+L) + stockSeguridad);

        //Obteniendo demanda anual
        List<Venta> listasAnuales = ventaRepository.findByYearAndArticulo(year, articulo);

        int[] ventasAnuales = {0};
        listasAnuales.forEach(venta -> {
            ventasAnuales[0] += venta.getCantidad();
        });

        demandaAnual = ventasAnuales[0];

        //Ahora calculamos q
        // ESTA FORMULA SE USABA ANTERIORMENTE, PERO ES ÚLTIL PARA CALCULAR A FINAL DEL PERIODO T:
        // int q = Math.round(demandaPromedio*(T+L) + stockSeguridad - inventarioActual);
        // AHORA SE USA ESTA, PORQUE LO QUEREMOS SABER CON ANTERIORIDAD
        int q = Math.max(0, Math.round(prediccionMesQueViene - inventarioActual + stockSeguridad));

        //Calculamos CGI
        float CGI = (float) (precioUnidad * q + costoAlmacenamiento * (q/2) + costoPedido * demandaAnual/q);

        DTODatosInventarioOutput dtoDatosInventarioOutput = new DTODatosInventarioOutput();
        dtoDatosInventarioOutput.setQ(q);
        dtoDatosInventarioOutput.setROP(ROP);
        dtoDatosInventarioOutput.setStockSeguridad(stockSeguridad);
        dtoDatosInventarioOutput.setCGI(CGI);

        return dtoDatosInventarioOutput;

    }



}
