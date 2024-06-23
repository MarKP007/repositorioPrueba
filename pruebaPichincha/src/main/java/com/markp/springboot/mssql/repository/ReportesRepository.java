package com.markp.springboot.mssql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.markp.springboot.mssql.model.Movimientos;
import com.markp.springboot.mssql.model.VistaReportes;

public interface ReportesRepository extends JpaRepository<Movimientos, Long> {

	@Query(value = "select * from reportes where clientid = ?3 and fecha >= CAST(?1 AS datetime) and fecha <= CAST(?2 AS datetime)", nativeQuery = true)
	List<VistaReportes> reportePorFechas(String fechaInicio, String FechaFin, long cliente);

}
