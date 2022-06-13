package com.douglas.SAMC.DTO;

import java.io.Serializable;

public class SolicitacaoFORM implements Serializable {
	private static final long serialVersionUID = 1L;

	private String descricao;

	private boolean status;

	public SolicitacaoFORM() {
		super();
	}

	public SolicitacaoFORM(String descricao, boolean status) {
		super();
		this.descricao = descricao;
		this.status = status;
	}

	public String getDescricao() {
		return descricao;
	}

	public boolean isStatus() {
		return status;
	}

}
