package com.example.b2w.service;

import java.net.URI;
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
import com.example.b2w.exception.BusinessException;
import com.example.b2w.exception.PlanetaDuplicadoException;
import com.example.b2w.exception.PlanetaInexistenteException;
import com.example.b2w.repository.PlanetaRepository;

/**
 * 
 * @author allan garcia
 *
 */
@Service
public class PlanetaService {

	@Autowired
	private PlanetaRepository arquivoRepository;

	public static final String MSG_PLANETA_INE = "Planeta inexistente ";

	public static final String URL_SWAPI = "https://swapi.co/api/";
	public static final String URL_SEARCH = URL_SWAPI + "planets/?search=";

	public Planeta inserir(String nome, String clima, String terreno) throws BusinessException {

		validarInserir(nome);
		return arquivoRepository.save(new Planeta(nome, clima, terreno));
	}

	private void validarInserir(String nome) throws BusinessException {

		if ("".equals(nome)) {
			throw new PlanetaInexistenteException();
		}
		Planeta planeta = arquivoRepository.buscarPorNome(nome);

		if (null != planeta) {
			throw new PlanetaDuplicadoException();
		}
		planeta = obterPlaneta(obterRestTemplate(), nome);

		if (null == planeta) {
			throw new PlanetaInexistenteException();
		}
	}

	public List<Planeta> buscarTodos() throws BusinessException {

		List<Planeta> listaPlaneta = arquivoRepository.findAll();

		if (listaPlaneta.isEmpty()) {
			throw new BusinessException("Nenhum planeta encontrado.");
		}
		RestTemplate restTemplate = obterRestTemplate();

		for (Planeta planeta : listaPlaneta) {
			planeta.setQtdeAparicoes(obterPlaneta(restTemplate, planeta.getName()).getFilms().size());
		}
		return listaPlaneta;
	}

	private Planeta obterPlaneta(RestTemplate restTemplate, String nome) throws BusinessException {

		try {
			URL url = new URL(URL_SEARCH + nome);
			URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);

			ResponseEntity<Swapi> entity = restTemplate.getForEntity(uri, Swapi.class);
			List<Planeta> listaPlaneta = entity.getBody().getResults();

			return listaPlaneta.isEmpty() ? null : listaPlaneta.get(0);
		} catch (Exception e) {
			throw new BusinessException("Erro ao criar a URL do swapi.co!");
		}
	}

	public RestTemplate obterRestTemplate() {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier())
				.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;
	}

	public Planeta buscarPorId(String id) throws BusinessException {

		Planeta planeta = arquivoRepository.findOne(id);
		if (null == planeta) {
			throw new BusinessException("Planeta não encontrado.");
		}
		planeta.setQtdeAparicoes(obterPlaneta(obterRestTemplate(), planeta.getName()).getFilms().size());

		return planeta;
	}

	public Planeta buscarPorNome(String nome) throws BusinessException {

		Planeta planeta = arquivoRepository.buscarPorNome(nome);

		if (null == planeta) {
			throw new BusinessException("Planeta não encontrado.");
		}
		planeta.setQtdeAparicoes(obterPlaneta(obterRestTemplate(), planeta.getName()).getFilms().size());

		return planeta;
	}

	public void remover(String id) throws BusinessException {

		Planeta planeta = validarRemover(id);
		arquivoRepository.delete(planeta.getId());
	}

	private Planeta validarRemover(String id) throws BusinessException {

		Planeta planeta = arquivoRepository.findOne(id);
		if (null == planeta) {
			planeta = arquivoRepository.buscarPorNome(id);
			if (null == planeta) {
				throw new BusinessException("Planeta não encontrado.");
			}
		}
		return planeta;
	}

}
