package com.markp.springboot.mssql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.markp.springboot.mssql.model.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

	// List<Persona> findById(long id);

}
