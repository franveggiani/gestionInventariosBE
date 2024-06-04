package com.yarmovezzoli.gestioninv;

import com.yarmovezzoli.gestioninv.Entities.Articulo;
import com.yarmovezzoli.gestioninv.Repositories.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GestioninvApplication {

	@Autowired
	ArticuloRepository articuloRepository;

	public static void main(String[] args) {
		SpringApplication.run(GestioninvApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(){
		return args -> {

			System.out.println("Corriendo aplicación...");

			Articulo articulo = new Articulo();
			articulo.setId(Long.valueOf(1L));
			articulo.setNombre("Articulo 1");

			articuloRepository.save(articulo);
		};
	}

}