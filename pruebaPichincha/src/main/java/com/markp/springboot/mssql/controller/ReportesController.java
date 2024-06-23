package com.markp.springboot.mssql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.markp.springboot.mssql.model.VistaReportes;
import com.markp.springboot.mssql.repository.ReportesRepository;

@RestController
@RequestMapping()
public class ReportesController {

	@Autowired
	ReportesRepository reportesRepository;

	@GetMapping("/reportes")
	public ResponseEntity<List<VistaReportes>> reporteMovimientos(@RequestParam("id") long id,
			@RequestParam("fechaInicio") String fechaInicio, @RequestParam("fechaFin") String fechaFin) {
		try {
			List<VistaReportes> reporteMovimientos = reportesRepository.reportePorFechas(fechaInicio, fechaFin, id);

			if (reporteMovimientos.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(reporteMovimientos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
