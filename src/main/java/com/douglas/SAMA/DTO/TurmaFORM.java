package com.douglas.SAMA.DTO;

import java.time.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.douglas.SAMA.enums.Periodo;

public class TurmaFORM {

	@NotBlank(message = "O campo codigo é requerido")
	private String codigo;

	private LocalTime entrada;

	private LocalTime saida;

	private LocalTime almocoSaida;

	private LocalTime almocoEntrada;

	@NotNull(message = "O campo tolerancia de entrada é requerido")
	private Integer toleranciaEntrada;

	@NotNull(message = "O campo tolerancia de saida é requerido")
	private Integer toleranciaSaida;

	private Periodo periodo;

	public TurmaFORM(String codigo, LocalTime entrada, LocalTime saida, LocalTime almocoSaida, LocalTime almocoEntrada,
			Integer toleranciaEntrada, Integer toleranciaSaida, Periodo periodo) {
		super();
		this.codigo = codigo;
		this.entrada = entrada;
		this.saida = saida;
		this.almocoSaida = almocoSaida;
		this.almocoEntrada = almocoEntrada;
		this.toleranciaEntrada = toleranciaEntrada;
		this.toleranciaSaida = toleranciaSaida;
		this.periodo = periodo;
	}

	public String getCodigo() {
		return codigo;
	}

	public LocalTime getEntrada() {
		return entrada;
	}

	public LocalTime getSaida() {
		return saida;
	}

	public LocalTime getAlmocoSaida() {
		return almocoSaida;
	}

	public LocalTime getAlmocoEntrada() {
		return almocoEntrada;
	}

	public Integer getToleranciaEntrada() {
		return toleranciaEntrada;
	}

	public Integer getToleranciaSaida() {
		return toleranciaSaida;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

}
