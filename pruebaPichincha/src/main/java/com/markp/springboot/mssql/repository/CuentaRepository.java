package com.markp.springboot.mssql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.markp.springboot.mssql.model.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

}
