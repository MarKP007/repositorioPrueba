package com.markp.springboot.mssql.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.markp.springboot.mssql.model.Persona;
import com.markp.springboot.mssql.repository.PersonaRepository;

@RestController
@RequestMapping("/api")
public class PersonaController {

	@Autowired
	PersonaRepository personaRepository;

	@GetMapping("/personas")
	public ResponseEntity<List<Persona>> getAllPersona() {
		try {
			List<Persona> personas = new ArrayList<Persona>();

			personaRepository.findAll().forEach(personas::add);

			if (personas.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(personas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/personas/{id}")
	public ResponseEntity<Persona> getPersonaById(@PathVariable("id") long id) {
		Optional<Persona> personaData = personaRepository.findById(id);

		if (personaData.isPresent()) {
			return new ResponseEntity<>(personaData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/personas")
	public ResponseEntity<Persona> createPersona(@RequestBody Persona persona) {
		try {
			Persona _persona = personaRepository.save(new Persona(persona.getNombre(), persona.getGenero(),
					persona.getEdad(), persona.getIdentificacion(), persona.getDireccion(), persona.getTelefono()));
			return new ResponseEntity<>(_persona, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/personas/{id}")
	public ResponseEntity<Persona> updatePersona(@PathVariable("id") long id, @RequestBody Persona persona) {
		Optional<Persona> personaData = personaRepository.findById(id);

		if (personaData.isPresent()) {
			Persona _persona = personaData.get();
			_persona.setGenero(persona.getGenero());
			_persona.setDireccion(persona.getDireccion());
			_persona.setTelefono(persona.getTelefono());
			return new ResponseEntity<>(personaRepository.save(_persona), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/personas/{id}")
	public ResponseEntity<HttpStatus> deletePersona(@PathVariable("id") long id) {
		try {
			personaRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
