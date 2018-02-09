package com.example.b2w;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.b2w.service.PlanetaService;

/**
 * 
 * @author allan garcia
 *
 */
@SpringBootApplication
public class B2wApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(B2wApplication.class, args);
	}

	@Autowired
	private PlanetaService arquivoService;

	@Override
	public void run(String... args) throws Exception {

//		arquivoService.inserir("Alderaan", "temperate", "grasslands, mountains");
//		arquivoService.inserir("Yavin IV", "temperate, tropical", "jungle, rainforests");
//		arquivoService.inserir("Geonosis", "frozen", "tundra, ice caves, mountain ranges");
//		arquivoService.inserir("Coruscant", "temperate", "cityscape, mountains");
//		arquivoService.inserir("Naboo", "temperate", "grassy hills, swamps, forests, mountains");
//		arquivoService.inserir("Hoth", "frozen", "tundra, ice caves, mountain ranges");
//		arquivoService.inserir("Bespin", "temperate", "gas giant");
//		arquivoService.inserir("Endor", "frozen", "forests, mountains, lakes");
	}
	
}
