package com.markp.springboot.mssql.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movimientos")
public class Movimientos {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "tipomovimiento")
	private String tipoMovimiento;

	@Column(name = "valor")
	private double valor;

	@Column(name = "saldo")
	private double saldo;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "cuentaid")
	private Cuenta cuentaMovimiento;

	public Movimientos() {

	}

	public Movimientos(Date fecha, String tipoMovimiento, double valor, double saldo, Cuenta cuenta) {
		this.fecha = fecha;
		this.tipoMovimiento = tipoMovimiento;
		this.valor = valor;
		this.saldo = saldo;
		this.cuentaMovimiento = cuenta;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Cuenta getCuenta() {
		return cuentaMovimiento;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuentaMovimiento = cuenta;
	}

	@Override
	public String toString() {
		return "Movimientos [id=" + id + ", fecha=" + fecha + ", tipoMovimiento=" + tipoMovimiento + ", valor=" + valor
				+ ", saldo=" + saldo + "]";
	}

}
