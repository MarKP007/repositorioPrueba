package com.markp.springboot.mssql.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cuenta")
public class Cuenta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "clientid")
	private Cliente cuentasCliente;

	@Column(name = "numerocuenta")
	private String numeroCuenta;

	@Column(name = "tipocuenta")
	private String tipoCuenta;

	@Column(name = "saldoinicial")
	private double saldoInicial;

	@Column(name = "estado")
	private String estado;

	@JsonIgnore
	@OneToMany(mappedBy = "cuentaMovimiento", fetch = FetchType.EAGER)
	private List<Movimientos> movimientos;

	public Cuenta() {

	}

	public Cuenta(String numeroCuenta, String tipoCuenta, double saldoInicial, String estado, Cliente cliente) {
		this.cuentasCliente = cliente;
		this.numeroCuenta = numeroCuenta;
		this.tipoCuenta = tipoCuenta;
		this.saldoInicial = saldoInicial;
		this.estado = estado;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public double getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Cliente getCliente() {
		return cuentasCliente;
	}

	public void setCliente(Cliente cliente) {
		this.cuentasCliente = cliente;
	}

	public List<Movimientos> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<Movimientos> movimientos) {
		this.movimientos = movimientos;
	}

	@Override
	public String toString() {
		return "Cuenta [id=" + id + ", numeroCuenta=" + numeroCuenta + ", tipoCuenta=" + tipoCuenta + ", saldoInicial="
				+ saldoInicial + ", estado=" + estado + "]";
	}

}
