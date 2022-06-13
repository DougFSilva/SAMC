package com.douglas.SAMA.DTO;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AlunoFORM extends PessoaFORM implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "O campo matricula é requerido")
	private Integer matricula;

	@NotBlank(message = "O campo curso é requerido")
	private String turma;

	private Integer numeroTurma;

	@NotNull(message = "O campo data de matricula é requerido")
	private LocalDate dataMatricula;

	private String empresa;

	private boolean termoInternet;

	private boolean internetLiberada;

	public AlunoFORM() {
		super();
	}

	public AlunoFORM(Integer matricula, String turma, Integer numeroTurma, LocalDate dataMatricula, String empresa) {
		super();
		this.matricula = matricula;
		this.turma = turma;
		this.numeroTurma = numeroTurma;
		this.dataMatricula = dataMatricula;
		this.empresa = empresa;
		this.termoInternet = false;
		this.internetLiberada = false;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public String getTurma() {
		return turma;
	}

	public Integer getNumeroTurma() {
		return numeroTurma;
	}

	public LocalDate getDataMatricula() {
		return dataMatricula;
	}

	public String getEmpresa() {
		return empresa;
	}

	public boolean isTermoInternet() {
		return termoInternet;
	}

	public boolean isInternetLiberada() {
		return internetLiberada;
	}

}