package com.douglas.SAMA.DTO;

import java.util.ArrayList;
import java.util.List;

import com.douglas.SAMA.model.Perfil;
import com.douglas.SAMA.model.Usuario;

public class UsuarioDTO {

	private Integer id;

	private String nome;

	private String empresa;

	private String username;

	private List<Perfil> perfis = new ArrayList<>();

	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(Usuario usuario) {
		super();
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.empresa = usuario.getEmpresa();
		this.username = usuario.getUsername();
		this.perfis = usuario.getPerfis();
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmpresa() {
		return empresa;
	}

	public String getUsername() {
		return username;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

}
