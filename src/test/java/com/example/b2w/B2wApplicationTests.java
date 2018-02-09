package com.example.b2w;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.example.b2w.entity.Planeta;
import com.example.b2w.entity.Swapi;
import com.example.b2w.exception.BusinessException;
import com.example.b2w.exception.PlanetaDuplicadoException;
import com.example.b2w.exception.PlanetaInexistenteException;
import com.example.b2w.service.PlanetaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class B2wApplicationTests {

	@Autowired
	private PlanetaService planetaService;

	@Test
	public void testSwapi() {
		try {
			RestTemplate restTemplate = planetaService.obterRestTemplate();

			URL url = new URL(PlanetaService.URL_SWAPI);
			URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
			ResponseEntity<Swapi> entity = restTemplate.getForEntity(uri, Swapi.class);

			assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testInserirPlanetaSucesso() {
		try {
			Planeta planeta = planetaService.inserir("Dagobah", "temperate", "grasslands, mountains");
			assertThat(null != planeta.getId());
		} catch (BusinessException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testInserirPlanetaDuplicado() {
		try {
			planetaService.inserir("Alderaan", "temperate", "grasslands, mountains");
			planetaService.inserir("Alderaan", "temperate", "grasslands, mountains");
		} catch (PlanetaDuplicadoException e) {
			assertTrue(true);
			return;
		} catch (BusinessException e) {
		}
		assertTrue(false);
	}

	@Test
	public void testInserirPlanetaInexistente() {
		try {
			planetaService.inserir("AlderaanXXX", "temperate", "grasslands, mountains");
		} catch (PlanetaInexistenteException e) {
			assertTrue(true);
			return;
		} catch (BusinessException e) {
		}
		assertTrue(false);
	}

	@Test
	public void testBuscarTodosPlanetasSucesso() {
		try {
			planetaService.inserir("Alderaan", "temperate", "grasslands, mountains");

			assertTrue(!planetaService.buscarTodos().isEmpty());
		} catch (BusinessException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testInserirPlanetaVazio() {
		try {
			planetaService.inserir("", "temperate", "grasslands, mountains");
		} catch (PlanetaInexistenteException e) {
			assertTrue(true);
			return;
		} catch (BusinessException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testRemoverPlanetaSucesso() {
		try {
			Planeta planeta = planetaService.inserir("Bespin", "temperate", "grasslands, mountains");
			planetaService.remover(planeta.getId());
		} catch (BusinessException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testRemoverPlanetaVazio() {
		try {
			planetaService.remover("");
		} catch (BusinessException e) {
			assertTrue(true);
			return;
		}
		assertTrue(true);
	}

	@Test
	public void testPesquisaPorNomeSucesso() {
		try {
			Planeta planeta = planetaService.inserir("Coruscant", "temperate", "cityscape, mountains");
			planeta = planetaService.buscarPorNome("Coruscant");
			assertThat(null != planeta.getId());
		} catch (BusinessException e) {
			assertTrue(false);
		}
	}

}
