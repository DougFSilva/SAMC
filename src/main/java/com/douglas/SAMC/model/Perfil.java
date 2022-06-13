package com.douglas.SAMC.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import com.douglas.SAMC.enums.TipoPerfil;

@Entity
public class Perfil implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private TipoPerfil tipo;

	public Perfil() {
		super();
	}

	public Perfil(TipoPerfil tipo) {
		super();
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoPerfil getTipo() {
		return tipo;
	}

	public void setTipo(TipoPerfil tipo) {
		this.tipo = tipo;
	}

	@Override
	public String getAuthority() {
		return tipo.getDescricao();
	}

}
