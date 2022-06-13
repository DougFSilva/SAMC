package com.douglas.SAMC.DTO;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.douglas.SAMC.enums.TipoOcorrencia;

public class OcorrenciaFORM implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean privado;

	@Enumerated(EnumType.STRING)
	private TipoOcorrencia tipo;

	private String registrador;

	private String descricao;

	private boolean bloqueio;

	public OcorrenciaFORM() {

	}

	public OcorrenciaFORM(boolean privado, TipoOcorrencia tipo, String descricao, boolean bloqueio) {
		super();
		this.privado = privado;
		this.tipo = tipo;
		this.descricao = descricao;
		this.bloqueio = bloqueio;
	}

	public boolean isPrivado() {
		return privado;
	}

	public void setPrivado(boolean privado) {
		this.privado = privado;
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

}
