package com.douglas.SAMA.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.douglas.SAMA.enums.EntradaSaida;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PontoFuncionario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime timestamp;

	@Enumerated(EnumType.STRING)
	private EntradaSaida entradaSaida;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private Funcionario funcionario;

	public PontoFuncionario() {
		super();
	}

	public PontoFuncionario(LocalDateTime timestamp, EntradaSaida entradaSaida, Funcionario funcionario) {
		super();
		this.timestamp = timestamp;
		this.entradaSaida = entradaSaida;
		this.funcionario = funcionario;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public EntradaSaida getEntradaSaida() {
		return entradaSaida;
	}

	public void setEntradaSaida(EntradaSaida entradaSaida) {
		this.entradaSaida = entradaSaida;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Integer getId() {
		return id;
	}

}
