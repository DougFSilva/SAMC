package com.douglas.SAMC.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.douglas.SAMC.enums.Periodo;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "codigo", "curso_id" }))
public class Turma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String codigo;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private Curso curso;

	@JsonIgnore
	@OneToMany(mappedBy = "turma", cascade = CascadeType.REMOVE)
	private List<Aluno> alunos;

	private LocalTime entrada;

	private LocalTime saida;

	private LocalTime almocoSaida;

	private LocalTime almocoEntrada;

	private Integer toleranciaEntrada;

	private Integer toleranciaSaida;

	@Enumerated(EnumType.STRING)
	private Periodo periodo;

	@OneToMany(mappedBy = "turma", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Aula> aulas;

	public Turma() {
		super();
	}

	public Turma(Curso curso, String codigo, LocalTime entrada, LocalTime saida, LocalTime almocoSaida,
			LocalTime almocoEntrada, Integer toleranciaEntrada, Integer toleranciaSaida, Periodo periodo) {
		super();
		this.curso = curso;
		this.codigo = codigo;
		this.entrada = entrada;
		this.saida = saida;
		this.almocoSaida = almocoSaida;
		this.almocoEntrada = almocoEntrada;
		this.toleranciaEntrada = toleranciaEntrada;
		this.toleranciaSaida = toleranciaSaida;
		this.periodo = periodo;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public LocalTime getEntrada() {
		return entrada;
	}

	public void setEntrada(LocalTime entrada) {
		this.entrada = entrada;
	}

	public LocalTime getSaida() {
		return saida;
	}

	public void setSaida(LocalTime saida) {
		this.saida = saida;
	}

	public LocalTime getAlmocoSaida() {
		return almocoSaida;
	}

	public void setAlmocoSaida(LocalTime almocoSaida) {
		this.almocoSaida = almocoSaida;
	}

	public LocalTime getAlmocoEntrada() {
		return almocoEntrada;
	}

	public void setAlmocoEntrada(LocalTime almocoEntrada) {
		this.almocoEntrada = almocoEntrada;
	}

	public List<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(List<Aula> aulas) {
		this.aulas = aulas;
	}

	public Integer getId() {
		return id;
	}

	public Integer getToleranciaEntrada() {
		return toleranciaEntrada;
	}

	public void setToleranciaEntrada(Integer toleranciaEntrada) {
		this.toleranciaEntrada = toleranciaEntrada;
	}

	public Integer getToleranciaSaida() {
		return toleranciaSaida;
	}

	public void setToleranciaSaida(Integer toleranciaSaida) {
		this.toleranciaSaida = toleranciaSaida;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

}
