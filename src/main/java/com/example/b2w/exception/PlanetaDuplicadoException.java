package com.example.b2w.exception;

public class PlanetaDuplicadoException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public PlanetaDuplicadoException() {
		super("Planeta já existe.");
	}

}
