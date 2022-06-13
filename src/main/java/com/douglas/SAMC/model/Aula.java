package com.douglas.SAMC.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "diaAula", "turma_id" }))
public class Aula implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate diaAula;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private Turma turma;

	public Aula() {
		super();
	}

	public Aula(LocalDate diaAula, Turma turma) {
		super();
		this.diaAula = diaAula;
		this.turma = turma;
	}

	public Integer getId() {
		return id;
	}

	public LocalDate getdiaAula() {
		return diaAula;
	}

	public Turma getTurma() {
		return turma;
	}

}
