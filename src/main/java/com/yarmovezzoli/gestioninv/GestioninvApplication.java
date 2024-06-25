package com.yarmovezzoli.gestioninv;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import com.yarmovezzoli.gestioninv.Entities.Venta;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
import com.yarmovezzoli.gestioninv.Repositories.ProveedorRepository;
import com.yarmovezzoli.gestioninv.Repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.Month;
import java.util.Random;

@SpringBootApplication
public class GestioninvApplication {

	@Autowired
	ArticuloRepository articuloRepository;

	@Autowired
	VentaRepository ventaRepository;

	@Autowired
	ProveedorRepository proveedorRepository;


	public static void main(String[] args) {
		SpringApplication.run(GestioninvApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(){

		return args -> {

			System.out.println("Corriendo aplicación...");

			Proveedor proveedor1 = new Proveedor();
			proveedor1.setNombre("Federico");
			proveedorRepository.save(proveedor1);

			Articulo articulo1 = new Articulo();
			articulo1.setId(Long.valueOf(1L));
			articulo1.setNombre("Palo de escoba");
			articulo1.setStockActual(34);
			articulo1.setCostoAlmacenamiento(Double.valueOf(50));

			Articulo articulo2 = new Articulo();
			articulo2.setId(Long.valueOf(2L));
			articulo2.setNombre("Tractor Challenger MT875E");
			articulo2.setStockActual(100);

			Articulo articulo3 = new Articulo();
			articulo3.setId(Long.valueOf(3L));
			articulo3.setNombre("Coche Alfa Romeo 148 2012");
			articulo3.setStockActual(10);

			articuloRepository.save(articulo1);
			articuloRepository.save(articulo2);
			articuloRepository.save(articulo3);

			LocalDate fechaActual = LocalDate.now();
			LocalDate fechaFinal = fechaActual.minusYears(1);

			Random random = new Random();

			Long id = 0L;
			int diaActual = fechaActual.getDayOfMonth();

			System.out.println(diaActual);

			for (int i = 0; i < 13; i++) {
				int diasDeMesActual = fechaActual.getMonth().length(fechaActual.isLeapYear());
				int dia = 1;

				if (i == 0) {;
					dia = diaActual;
				} else if (i == 12) {
					diasDeMesActual = diaActual + 1;
				}

				for (int j = dia; j <= diasDeMesActual; j++) {
					id = id + 1;
					Venta venta = new Venta();
					venta.setId(id);
					venta.setCantidad(random.nextInt(100));
					venta.setArticulo(articulo1);
					venta.setFechaHoraAlta(fechaFinal);
					ventaRepository.save(venta);

					fechaFinal = fechaFinal.plusDays(1);
				}

				fechaActual = fechaActual.plusMonths(1);

			}
			//Para probar la demanda histórica

		};
	}

}