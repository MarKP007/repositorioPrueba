package com.markp.springboot.mssql.controller.exceptions;

public class SaldoNoDisponibleException extends RuntimeException {

	private static final String DESCRIPTION = "Saldo no disponible";

	public SaldoNoDisponibleException(String detail) {
		super(DESCRIPTION + ". " + detail);
	}

}
