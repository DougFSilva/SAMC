package com.douglas.SAMA.DTO;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.douglas.SAMA.enums.AlunoStatus;
import com.douglas.SAMA.enums.EntradaSaida;
import com.douglas.SAMA.model.Aluno;

public class AlunoDTO {

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

	private String turma;

	private Integer numeroTurma;

	private String curso;

	private LocalDate dataMatricula;

	private String empresa;

	private Integer ocorrencias;

	private boolean termoInternet;

	private boolean internetLiberada;

	private boolean desbloqueioTemporario;

	@Enumerated(EnumType.STRING)
	private EntradaSaida entradaSaida;

	@Enumerated(EnumType.STRING)
	private AlunoStatus status;

	private String foto;

	public AlunoDTO(Aluno aluno) {
		this.id = aluno.getId();
		this.nome = aluno.getNome();
		this.sexo = aluno.getSexo();
		this.rg = aluno.getRg();
		this.telefone = aluno.getTelefone();
		this.email = aluno.getEmail();
		this.dataNascimento = aluno.getDataNascimento();
		this.cidade = aluno.getCidade();
		this.tag = aluno.getTag();
		this.matricula = aluno.getMatricula();
		this.turma = aluno.getTurma().getCodigo();
		this.numeroTurma = aluno.getNumeroTurma();
		this.curso = aluno.getTurma().getCurso().getAreaTecnologica();
		this.dataMatricula = aluno.getDataMatricula();
		this.empresa = aluno.getEmpresa();
		this.ocorrencias = aluno.getOcorrencias().size();
		this.termoInternet = aluno.isTermoInternet();
		this.internetLiberada = aluno.isInternetLiberada();
		this.desbloqueioTemporario = aluno.isDesbloqueioTemporario();
		this.entradaSaida = aluno.getEntradaSaida();
		this.status = aluno.getStatus();

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

	public String getTurma() {
		return turma;
	}

	public Integer getNumeroTurma() {
		return numeroTurma;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public LocalDate getDataMatricula() {
		return dataMatricula;
	}

	public String getEmpresa() {
		return empresa;
	}

	public Integer getOcorrencias() {
		return ocorrencias;
	}

	public boolean isTermoInternet() {
		return termoInternet;
	}

	public boolean isInternetLiberada() {
		return internetLiberada;
	}

	public boolean isDesbloqueioTemporario() {
		return desbloqueioTemporario;
	}

	public EntradaSaida getEntradaSaida() {
		return entradaSaida;
	}

	public AlunoStatus getStatus() {
		return status;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

}
