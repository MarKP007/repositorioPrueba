package com.markp.springboot.mssql.model;

import java.util.Date;

public interface VistaReportes {

	long getCLIENTID();

	String getNUMEROCUENTA();

	Date getFECHA();

	String getTIPOMOVIMIENTO();

	double getVALOR();

	double getSALDO();
}
