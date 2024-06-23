package com.markp.springboot.mssql.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.markp.springboot.mssql.controller.exceptions.SaldoNoDisponibleException;
import com.markp.springboot.mssql.model.Cuenta;
import com.markp.springboot.mssql.model.Movimientos;
import com.markp.springboot.mssql.repository.CuentaRepository;
import com.markp.springboot.mssql.repository.MovimientoRepository;
import com.markp.springboot.util.PichinchaUtil;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {

	@Autowired
	CuentaRepository cuentaRepository;

	@Autowired
	MovimientoRepository movimientoRepository;

	@GetMapping
	public ResponseEntity<List<Movimientos>> obtenerTodosMovimientos() {
		try {
			List<Movimientos> movimientos = new ArrayList<Movimientos>();

			movimientoRepository.findAll().forEach(movimientos::add);

			if (movimientos.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(movimientos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Movimientos> obtenerMovimientoPorId(@PathVariable("id") long id) {
		Optional<Movimientos> movimientoData = movimientoRepository.findById(id);

		if (movimientoData.isPresent()) {
			return new ResponseEntity<>(movimientoData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/{cuentaId}")
	public ResponseEntity<Movimientos> crearMovimiento(@PathVariable Long cuentaId,
			@RequestBody Movimientos movimiento) {
		Optional<Cuenta> cuentaeData = cuentaRepository.findById(cuentaId);
		Cuenta cuenta;
		if (cuentaeData.isPresent()) {
			cuenta = cuentaeData.get();
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		cuenta.setSaldoInicial(cuenta.getSaldoInicial() + movimiento.getValor());
		if (cuenta.getSaldoInicial() < 0) {
			throw new SaldoNoDisponibleException("");
		}
		cuentaRepository.save(cuenta);

		String tipoMovimiento = movimiento.getValor() < 0 ? "RETIRO" : "DEPOSITO";
		try {
			Movimientos _movimiento = movimientoRepository.save(new Movimientos(new Date(), tipoMovimiento,
					movimiento.getValor(), cuenta.getSaldoInicial(), cuenta));
			_movimiento.setCuenta(cuenta);
			return new ResponseEntity<>(_movimiento, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Movimientos> updateMovimiento(@PathVariable("id") long id,
			@RequestBody Movimientos movimiento) {
		Optional<Movimientos> movimientoData = movimientoRepository.findById(id);

		if (movimientoData.isPresent()) {
			Movimientos _movimiento = movimientoData.get();
			_movimiento.setFecha(
					PichinchaUtil.esCampoLleno(movimiento.getFecha()) ? movimiento.getFecha() : _movimiento.getFecha());
			_movimiento.setTipoMovimiento(
					PichinchaUtil.esCampoLleno(movimiento.getTipoMovimiento()) ? movimiento.getTipoMovimiento()
							: _movimiento.getTipoMovimiento());
			_movimiento.setValor(
					PichinchaUtil.esCampoLleno(movimiento.getValor()) ? movimiento.getValor() : _movimiento.getValor());
			_movimiento.setSaldo(
					PichinchaUtil.esCampoLleno(movimiento.getSaldo()) ? movimiento.getSaldo() : _movimiento.getSaldo());
			return new ResponseEntity<>(movimientoRepository.save(_movimiento), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> eliminarMovimiento(@PathVariable("id") long id) {
		try {
			movimientoRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
