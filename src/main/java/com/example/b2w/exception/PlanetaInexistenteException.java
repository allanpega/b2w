package com.example.b2w.exception;

public class PlanetaInexistenteException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public PlanetaInexistenteException() {
		super("Planeta inexistente no universo de Star Wars.");
	}

}
