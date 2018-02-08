package com.example.b2w.controller;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.b2w.entity.Planeta;
import com.example.b2w.service.PlanetaService;

@RestController
@RequestMapping("/planeta/")
public class PlanetaController {

	@Autowired
	PlanetaService planetaService;

	@RequestMapping(value = "/buscarTodos", method = RequestMethod.GET)
	public ResponseEntity<?> buscarTodos() {

		try {
			return new ResponseEntity<>(planetaService.buscarTodos(), HttpStatus.OK);
		} catch (MalformedURLException e) {
			return new ResponseEntity<>("Erro ao criar a URL do swapi.co !", HttpStatus.OK);
		} catch (URISyntaxException e) {
			return new ResponseEntity<>("Erro ao fazer a integração com o swapi.co !", HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/buscarPorNome", method = RequestMethod.GET)
	public ResponseEntity<?> buscarPorNome(@RequestParam(value = "nome") String nome) {

		Planeta planeta = planetaService.buscarPorNome(nome);

		if (null == planeta) {
			return new ResponseEntity<>("Planeta não encontrado.", HttpStatus.OK);
		}
		return new ResponseEntity<>(planeta, HttpStatus.OK);
	}

	@RequestMapping(value = "/buscarPorId", method = RequestMethod.GET)
	public ResponseEntity<?> buscarPorId(@RequestParam(value = "id") String id) {

		try {
			Planeta planeta = planetaService.buscarPorId(id);
			if (null == planeta) {
				return new ResponseEntity<>("Planeta não encontrado.", HttpStatus.OK);
			}
			return new ResponseEntity<>(planeta, HttpStatus.OK);
		} catch (MalformedURLException e) {
			return new ResponseEntity<>("Erro ao criar a URL do swapi.co !", HttpStatus.OK);
		} catch (URISyntaxException e) {
			return new ResponseEntity<>("Erro ao fazer a integração com o swapi.co !", HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/remover", method = RequestMethod.DELETE)
	public ResponseEntity<?> remover(@RequestParam(value = "id") String id) {

		Planeta planeta = planetaService.buscarPorIdSemIntegracao(id);
		if (null == planeta) {
			return new ResponseEntity<>("Planeta não encontrado.", HttpStatus.OK);
		}
		planetaService.delete(id);

		return new ResponseEntity<>("Planeta excluido com sucesso.", HttpStatus.OK);
	}

	@PostMapping(value = "/inserir")
	public ResponseEntity<?> inserir(@RequestBody Planeta planeta) {

		planeta = planetaService.inserir(planeta.getName(), planeta.getClimate(), planeta.getTerrain());

		return new ResponseEntity<>(planeta, HttpStatus.CREATED);
	}

}
