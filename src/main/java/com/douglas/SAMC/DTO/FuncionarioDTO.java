package com.douglas.SAMC.DTO;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.douglas.SAMC.enums.EntradaSaida;
import com.douglas.SAMC.model.Funcionario;

public class FuncionarioDTO {

	private Integer id;

	private String nome;

	private String sexo;

	private String rg;

	private String telefone;

	private String email;

	private LocalDate dataNascimento;

	private String cidade;

	private Integer tag;

	private Integer matricula;

	private String empresa;

	@Enumerated(EnumType.STRING)
	private EntradaSaida entradaSaida;

	private String foto;

	public FuncionarioDTO() {
		super();
	}

	public FuncionarioDTO(Funcionario funcionario) {
		super();
		this.id = funcionario.getId();
		this.nome = funcionario.getNome();
		this.sexo = funcionario.getSexo();
		this.rg = funcionario.getRg();
		this.telefone = funcionario.getTelefone();
		this.email = funcionario.getEmail();
		this.dataNascimento = funcionario.getDataNascimento();
		this.cidade = funcionario.getCidade();
		this.tag = funcionario.getTag();
		this.matricula = funcionario.getMatricula();
		this.empresa = funcionario.getEmpresa();
		this.entradaSaida = funcionario.getEntradaSaida();
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getSexo() {
		return sexo;
	}

	public String getRg() {
		return rg;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getEmail() {
		return email;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public String getCidade() {
		return cidade;
	}

	public Integer getTag() {
		return tag;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public String getEmpresa() {
		return empresa;
	}

	public EntradaSaida getEntradaSaida() {
		return entradaSaida;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

}
