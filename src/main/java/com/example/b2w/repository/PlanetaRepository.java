package com.example.b2w.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.b2w.entity.Planeta;

public interface PlanetaRepository extends MongoRepository<Planeta, String> {

	@Query("{name : ?0}")
	public Planeta buscarPorNome(String nome);

}
