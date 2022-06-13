package com.douglas.SAMC.DTO;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public abstract class PessoaFORM implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Integer id;

	@NotBlank(message = "O campo nome é requerido")
	protected String nome;

	protected String sexo;

	protected Integer idade;

	@NotBlank(message = "O campo rg é requerido")
	protected String rg;

	protected String telefone;

	protected String email;

	@NotNull(message = "O campo data de nascimento é requerido")
	protected LocalDate dataNascimento;

	protected String cidade;

	@Column(unique = true)
	protected Integer tag;

	protected String foto;

	public PessoaFORM() {

	}

	public PessoaFORM(String nome, String sexo, Integer idade, String rg, String email, String telefone, String cidade,
			LocalDate dataNascimento, Integer tag, String foto) {
		super();
		this.nome = nome;
		this.sexo = sexo;
		this.idade = idade;
		this.rg = rg;
		this.email = email;
		this.telefone = telefone;
		this.cidade = cidade;
		this.dataNascimento = dataNascimento;
		this.tag = tag;
		this.foto = foto;
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

	public Integer getIdade() {
		return idade;
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

	public String getFoto() {
		return foto;
	}

}
