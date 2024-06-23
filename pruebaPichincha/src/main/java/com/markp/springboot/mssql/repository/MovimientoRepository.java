package com.markp.springboot.mssql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.markp.springboot.mssql.model.Movimientos;

public interface MovimientoRepository extends JpaRepository<Movimientos, Long> {

}
