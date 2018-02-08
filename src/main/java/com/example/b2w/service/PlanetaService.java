package com.example.b2w.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.b2w.entity.Planeta;
import com.example.b2w.entity.Swapi;
import com.example.b2w.repository.PlanetaRepository;

@Service
public class PlanetaService {

	@Autowired
	private PlanetaRepository arquivoRepository;

	public static final String URL_SWAPI = "https://swapi.co/api/";
	public static final String URL_SEARCH = URL_SWAPI + "planets/?search=";

	public Planeta inserir(String nome, String clima, String terreno) {
		return arquivoRepository.save(new Planeta(nome, clima, terreno));
	}

	public List<Planeta> buscarTodos() throws MalformedURLException, URISyntaxException {

		List<Planeta> listaPlaneta = arquivoRepository.findAll();
		RestTemplate restTemplate = obterRestTemplate();

		for (Planeta planeta : listaPlaneta) {
			planeta.setQtdeAparicoes(obterQtdeAparicoes(restTemplate, planeta));
		}
		return listaPlaneta;
	}

	private Integer obterQtdeAparicoes(RestTemplate restTemplate, Planeta planeta)
			throws MalformedURLException, URISyntaxException {
		URL url = new URL(URL_SEARCH + planeta.getName());
		URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);

		ResponseEntity<Swapi> entity = restTemplate.getForEntity(uri, Swapi.class);

		Integer qtdeAparicoes = entity.getBody().getResults().get(0).getFilms().size();
		return qtdeAparicoes;
	}

	public RestTemplate obterRestTemplate() {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier())
				.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;
	}

	public long count() {
		return arquivoRepository.count();
	}

	public Planeta buscarPorIdSemIntegracao(String id) {
		return arquivoRepository.findOne(id);
	}

	public Planeta buscarPorId(String id) throws MalformedURLException, URISyntaxException {

		RestTemplate restTemplate = obterRestTemplate();

		Planeta planeta = arquivoRepository.findOne(id);

		planeta.setQtdeAparicoes(obterQtdeAparicoes(restTemplate, planeta));

		return arquivoRepository.findOne(id);
	}

	public Planeta buscarPorNome(String nome) {
		return arquivoRepository.buscarPorNome(nome);
	}

	public void delete(String id) {
		arquivoRepository.delete(id);
	}

}
