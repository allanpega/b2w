package com.example.b2w.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "planeta")
public class Planeta {

	@Id
	private String id;

	private String name;
	private String climate;

	private String terrain;

	private List<String> films = null;

	private Integer qtdeAparicoes;

	public Integer getQtdeAparicoes() {
		return qtdeAparicoes;
	}

	public void setQtdeAparicoes(Integer qtdeAparicoes) {
		this.qtdeAparicoes = qtdeAparicoes;
	}

	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Planeta() {

	}

	public Planeta(String name, String climate, String terrain) {
		this.id = new Random().nextLong() + "";
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public List<String> getFilms() {
		return films;
	}

	public void setFilms(List<String> films) {
		this.films = films;
	}

	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
