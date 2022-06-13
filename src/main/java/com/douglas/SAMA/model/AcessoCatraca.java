package com.douglas.SAMA.model;

import java.time.LocalDateTime;

import com.douglas.SAMA.enums.EntradaSaida;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AcessoCatraca {

	private String usuario;

	private String tipoUsuario;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDateTime timestamp;

	private String mensagem;

	private boolean acessoLiberado;

	private EntradaSaida entradaSaida;

	public AcessoCatraca() {
		super();
	}

	public AcessoCatraca(String usuario, String tipoUsuario, LocalDateTime timestamp, boolean acessoLiberado,
			String mensagem, EntradaSaida entradaSaida) {
		super();
		this.usuario = usuario;
		this.tipoUsuario = tipoUsuario;
		this.timestamp = timestamp;
		this.acessoLiberado = acessoLiberado;
		this.mensagem = mensagem;
		this.entradaSaida = entradaSaida;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public boolean isAcessoLiberado() {
		return acessoLiberado;
	}

	public void setAcessoLiberado(boolean acessoLiberado) {
		this.acessoLiberado = acessoLiberado;
	}

	public EntradaSaida getEntradaSaida() {
		return entradaSaida;
	}

	public void setEntradaSaida(EntradaSaida entradaSaida) {
		this.entradaSaida = entradaSaida;
	}

}
