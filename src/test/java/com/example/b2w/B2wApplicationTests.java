package com.example.b2w;

import static org.assertj.core.api.Assertions.assertThat;

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

import com.example.b2w.entity.Swapi;
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
			assertThat(false);
		}

	}

}