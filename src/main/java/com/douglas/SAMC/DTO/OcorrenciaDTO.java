package com.douglas.SAMC.DTO;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.douglas.SAMC.enums.TipoOcorrencia;
import com.douglas.SAMC.model.Ocorrencia;

public class OcorrenciaDTO {

	private Integer id;

	private LocalDateTime data = LocalDateTime.now();

	@Enumerated(EnumType.STRING)
	private TipoOcorrencia tipo;

	private String registrador;

	private String aluno;

	private String descricao;

	private boolean bloqueio;

	public OcorrenciaDTO() {
		super();
	}

	public OcorrenciaDTO(Ocorrencia ocorrencia) {
		super();
		this.id = ocorrencia.getId();
		this.data = ocorrencia.getData();
		this.tipo = ocorrencia.getTipo();
		this.registrador = ocorrencia.getRegistrador();
		this.aluno = ocorrencia.getAluno().getNome();
		this.descricao = ocorrencia.getDescricao();
		this.bloqueio = ocorrencia.isBloqueio();
	}

	public Integer getId() {
		return id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public TipoOcorrencia getTipo() {
		return tipo;
	}

	public String getRegistrador() {
		return registrador;
	}

	public String getAluno() {
		return aluno;
	}

	public String getDescricao() {
		return descricao;
	}

	public boolean isResolvido() {
		return bloqueio;
	}

}
