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

import com.markp.springboot.mssql.model.Cliente;
import com.markp.springboot.mssql.repository.ClienteRepository;
import com.markp.springboot.util.PichinchaUtil;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	ClienteRepository clienteRepository;

	@GetMapping
	public ResponseEntity<List<Cliente>> obtenerTodosClientes() {
		try {
			List<Cliente> clientes = new ArrayList<Cliente>();

			clienteRepository.findAll().forEach(clientes::add);

			if (clientes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(clientes, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable("id") long id) {
		Optional<Cliente> clienteData = clienteRepository.findById(id);

		if (clienteData.isPresent()) {
			return new ResponseEntity<>(clienteData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
		try {
			Cliente _cliente = clienteRepository.save(new Cliente(cliente.getNombre(), cliente.getGenero(),
					cliente.getEdad(), cliente.getIdentificacion(), cliente.getDireccion(), cliente.getTelefono(),
					cliente.getContrasena(), cliente.getEstado()));
			return new ResponseEntity<>(_cliente, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> updateCliente(@PathVariable("id") long id, @RequestBody Cliente cliente) {
		Optional<Cliente> clienteData = clienteRepository.findById(id);

		if (clienteData.isPresent()) {
			Cliente _cliente = clienteData.get();
			_cliente.setGenero(
					PichinchaUtil.esCampoLleno(cliente.getGenero()) ? cliente.getGenero() : _cliente.getGenero());
			_cliente.setNombre(
					PichinchaUtil.esCampoLleno(cliente.getNombre()) ? cliente.getNombre() : _cliente.getNombre());
			_cliente.setEdad(PichinchaUtil.esCampoLleno(cliente.getEdad()) ? cliente.getEdad() : _cliente.getEdad());
			_cliente.setIdentificacion(
					PichinchaUtil.esCampoLleno(cliente.getIdentificacion()) ? cliente.getIdentificacion()
							: _cliente.getIdentificacion());
			_cliente.setDireccion(PichinchaUtil.esCampoLleno(cliente.getDireccion()) ? cliente.getDireccion()
					: _cliente.getDireccion());
			_cliente.setTelefono(
					PichinchaUtil.esCampoLleno(cliente.getTelefono()) ? cliente.getTelefono() : _cliente.getTelefono());
			_cliente.setContrasena(PichinchaUtil.esCampoLleno(cliente.getContrasena()) ? cliente.getContrasena()
					: _cliente.getContrasena());
			_cliente.setEstado(
					PichinchaUtil.esCampoLleno(cliente.getEstado()) ? cliente.getEstado() : _cliente.getEstado());
			return new ResponseEntity<>(clienteRepository.save(_cliente), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> eliminarCliente(@PathVariable("id") long id) {
		try {
			clienteRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
