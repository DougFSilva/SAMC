package com.douglas.SAMC.DTO;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import com.douglas.SAMC.enums.Bloqueio;
import com.douglas.SAMC.enums.EntradaSaida;

public class AlunoAcessoFORM {

	@NotBlank
	@Enumerated(EnumType.STRING)
	private Bloqueio bloqueio;

	@NotBlank
	@Enumerated(EnumType.STRING)
	private EntradaSaida entradaSaida;

	public AlunoAcessoFORM() {
		super();
	}

	public AlunoAcessoFORM(Bloqueio bloqueio, EntradaSaida entradaSaida) {
		super();
		this.bloqueio = bloqueio;
		this.entradaSaida = entradaSaida;
	}

	public Bloqueio getBloqueio() {
		return bloqueio;
	}

	public void setBloqueio(Bloqueio bloqueio) {
		this.bloqueio = bloqueio;
	}

	public EntradaSaida getEntradaSaida() {
		return entradaSaida;
	}

	public void setEntradaSaida(EntradaSaida entradaSaida) {
		this.entradaSaida = entradaSaida;
	}

}
