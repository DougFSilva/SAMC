package com.douglas.SAMA.DTO;

import java.util.ArrayList;
import java.util.List;

import com.douglas.SAMA.model.Perfil;

public class TokenDTO {

	private String token;
	private String tipo;
	private String usuario;
	private List<String> perfis = new ArrayList<>();

	public TokenDTO(String token, String tipo, String usuario, List<Perfil> perfis) {
		this.token = token;
		this.tipo = tipo;
		this.usuario = usuario;
		perfis.forEach(perfil -> this.perfis.add(perfil.getTipo().toString()));

	}

	public String getToken() {
		return token;
	}

	public String getTipo() {
		return tipo;
	}

	public String getUsuario() {
		return usuario;
	}

	public List<String> getPerfis() {
		return perfis;
	}

}
