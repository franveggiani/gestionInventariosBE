package com.yarmovezzoli.gestioninv.Services.ModuloVentas;

import com.yarmovezzoli.gestioninv.DTOs.DemandaHistoricaRequest;
import com.yarmovezzoli.gestioninv.DTOs.VentaRequestDTO;
import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.DemandaHistorica;
import com.yarmovezzoli.gestioninv.Entities.DemandaHistoricaDetalle;
import com.yarmovezzoli.gestioninv.Entities.Venta;
import com.yarmovezzoli.gestioninv.Enums.TipoPeriodo;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.BaseRepository;
import com.yarmovezzoli.gestioninv.Repositories.VentaRepository;
import com.yarmovezzoli.gestioninv.Services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImpl extends BaseServiceImpl<Venta, Long> implements VentaService{

    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    ArticuloRepository articuloRepository;

    public VentaServiceImpl(BaseRepository<Venta, Long> baseRepository, VentaRepository ventaRepository, ArticuloRepository articuloRepository){
        super(baseRepository);
        this.ventaRepository = ventaRepository;
        this.articuloRepository = articuloRepository;
    }

    @Override
    public Venta createVenta(VentaRequestDTO ventaRequest) throws Exception {
        try {
            Venta nuevaVenta = new Venta();

            nuevaVenta.setCantidad(ventaRequest.getCantidad());
            nuevaVenta.setFechaHoraAlta(LocalDate.now());

            Optional<Articulo> articulo = articuloRepository.findById(ventaRequest.getArticuloId());

            if (articulo.isPresent()){
                nuevaVenta.setArticulo(articulo.get());
                ventaRepository.save(nuevaVenta);

                return nuevaVenta;
            } else {
                throw new Exception("No se encontró el id del artículo");
            }

        } catch (Exception e){
            throw new Exception("Error al crear instancia de venta: " + e.getMessage());
        }
    }

    //Hacer demanda historica, ver de verificar el tipo de periodo y partir desde la fechaDesde
    //Recopilar todas las ventas en esas fechas
    @Override
    public DemandaHistorica createDemandaHistorica(DemandaHistoricaRequest demandaHistoricaRequest) throws Exception {
        try{
            TipoPeriodo tipoPeriodo;
            tipoPeriodo = demandaHistoricaRequest.getTipoPeriodo();

            //Si es Semestral serán 183 días, si es Trimestral serán 91 días, y así...
            Long cantidadDias = tipoPeriodo.getDias();

            LocalDate fechaDesde = demandaHistoricaRequest.getFechaDesde();
            LocalDate fechaHasta = demandaHistoricaRequest.getFechaDesde().plusDays(cantidadDias);

            DemandaHistorica demandaHistorica = new DemandaHistorica();

            demandaHistorica.setFechaDesde(fechaDesde);
            demandaHistorica.setFechaHasta(fechaHasta);

            List<Venta> ventaList = ventaRepository.listaPorPeriodo(fechaDesde, fechaHasta);

            if (!ventaList.isEmpty()) {
                List<DemandaHistoricaDetalle> demandaHistoricaDetalleList = new ArrayList<>();

                ventaList.forEach(venta -> {
                    DemandaHistoricaDetalle demandaHistoricaDetalle = new DemandaHistoricaDetalle();

                    demandaHistoricaDetalle.setArticulo(venta.getArticulo());
                    demandaHistoricaDetalle.setCantidad(venta.getCantidad());

                    demandaHistoricaDetalleList.add(demandaHistoricaDetalle);
                });

                demandaHistorica.setDemandaHistoricaDetalles(demandaHistoricaDetalleList);

            } else {
                throw new Exception("Error: No hay ventas registradas para este rango de fechas");
            }

            //Falta guardar en la base de datos pero estoy probando a ver si funca esta lógica

            return demandaHistorica;

        } catch(Exception e) {
            throw new Exception("Error: " + e.getMessage());
        }
    }
}
