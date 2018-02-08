package com.example.b2w.controller;

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
import com.example.b2w.exception.BusinessException;
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
		} catch (BusinessException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/buscarPorNome", method = RequestMethod.GET)
	public ResponseEntity<?> buscarPorNome(@RequestParam(value = "nome") String nome) {

		try {
			return new ResponseEntity<>(planetaService.buscarPorNome(nome), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/buscarPorId", method = RequestMethod.GET)
	public ResponseEntity<?> buscarPorId(@RequestParam(value = "id") String id) {

		try {
			return new ResponseEntity<>(planetaService.buscarPorId(id), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/remover", method = RequestMethod.DELETE)
	public ResponseEntity<?> remover(@RequestParam(value = "id") String id) {

		try {
			planetaService.remover(id);
			return new ResponseEntity<>("Planeta excluido com sucesso.", HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
	}

	@PostMapping(value = "/inserir")
	public ResponseEntity<?> inserir(@RequestBody Planeta planeta) {

		try {
			planeta = planetaService.inserir(planeta.getName(), planeta.getClimate(), planeta.getTerrain());
			return new ResponseEntity<>("Planeta inserido com sucesso !", HttpStatus.CREATED);
		} catch (BusinessException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
	}

}
