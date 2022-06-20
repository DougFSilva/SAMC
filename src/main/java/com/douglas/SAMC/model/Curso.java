package com.douglas.SAMC.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

@Entity
public class Curso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "O campo modalidade é requerido")
	private String modalidade;

	@NotEmpty(message = "O campo area tecnologica é requerido")
	private String areaTecnologica;

	@OneToMany(mappedBy = "curso", cascade = CascadeType.REMOVE)
	private List<Turma> turma;

	public Curso() {

	}

	public Curso(String modalidade, String areaTecnologica) {
		super();
		this.modalidade = modalidade;
		this.areaTecnologica = areaTecnologica;
	}

	public Integer getId() {
		return id;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public String getAreaTecnologica() {
		return areaTecnologica;
	}

	public void setAreaTecnologica(String areaTecnologica) {
		this.areaTecnologica = areaTecnologica;
	}

	public List<Turma> getTurma() {
		return turma;
	}

	public void setTurma(List<Turma> turma) {
		this.turma = turma;
	}

}
