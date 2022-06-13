package com.douglas.SAMC.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.douglas.SAMC.enums.TipoOcorrencia;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Ocorrencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private boolean privado;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime data = LocalDateTime.now();

	@Enumerated(EnumType.STRING)
	private TipoOcorrencia tipo;

	private String registrador;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "aluno_id")
	@JsonIgnore
	private Aluno aluno;

	private String descricao;

	private boolean bloqueio;

	public Ocorrencia() {

	}

	public Ocorrencia(boolean privado, TipoOcorrencia tipo, String registrador, Aluno aluno, String descricao,
			boolean bloqueio) {
		super();
		this.privado = privado;
		this.tipo = tipo;
		this.registrador = registrador;
		this.aluno = aluno;
		this.descricao = descricao;
		this.bloqueio = bloqueio;
	}

	public boolean isPrivado() {
		return privado;
	}

	public void setPrivado(boolean privado) {
		this.privado = privado;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public TipoOcorrencia getTipo() {
		return tipo;
	}

	public void setTipo(TipoOcorrencia tipo) {
		this.tipo = tipo;
	}

	public String getRegistrador() {
		return registrador;
	}

	public void setRegistrador(String registrador) {
		this.registrador = registrador;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isBloqueio() {
		return bloqueio;
	}

	public void setBloqueio(boolean resolvido) {
		this.bloqueio = resolvido;
	}

	public Integer getId() {
		return id;
	}

}
