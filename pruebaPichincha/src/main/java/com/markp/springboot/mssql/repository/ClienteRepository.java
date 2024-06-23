package com.markp.springboot.mssql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.markp.springboot.mssql.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
