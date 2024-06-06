package com.yarmovezzoli.gestioninv;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Entities.Venta;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
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

	public static void main(String[] args) {
		SpringApplication.run(GestioninvApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(){
		return args -> {

			System.out.println("Corriendo aplicación...");

			Articulo articulo1 = new Articulo();
			articulo1.setId(Long.valueOf(1L));
			articulo1.setNombre("Articulo 1");

			Articulo articulo2 = new Articulo();
			articulo2.setId(Long.valueOf(2L));
			articulo2.setNombre("Articulo 2");

			Articulo articulo3 = new Articulo();
			articulo2.setId(Long.valueOf(3L));
			articulo2.setNombre("Articulo 2");

			articuloRepository.save(articulo1);
			articuloRepository.save(articulo2);
			articuloRepository.save(articulo3);

			Random random = new Random();

			Long id = 0L;
			for (int i = 0; i < 12; i++) {

				int daysInMonth = Month.of(i + 1).length(false); // false means non-leap year

				for (int j = 0; j < daysInMonth; j++) {
					id += 1;

					Venta venta = new Venta();
					venta.setId(id);
					venta.setCantidad(random.nextInt(100));
					venta.setArticulo(articulo1);

					venta.setFechaHoraAlta(LocalDate.of(2023, i + 1, j + 1));
					ventaRepository.save(venta);
				}
			}

			//Para probar la demanda histórica

		};
	}

}