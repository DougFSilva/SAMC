package com.douglas.SAMA.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.douglas.SAMA.enums.AlunoStatus;
import com.douglas.SAMA.enums.EntradaSaida;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "matricula" }) })
public class Aluno extends Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull(message = "O campo matricula é requerido")
	private Integer matricula;

	@ManyToOne
	@JoinColumn(name = "turma_id")
	private Turma turma;

	private Integer numeroTurma;

	@NotNull(message = "O campo data de matricula é requerido")
	private LocalDate dataMatricula;

	private String empresa;

	@OneToMany(mappedBy = "aluno", cascade = CascadeType.REMOVE)
	private List<Ocorrencia> ocorrencias;

	@OneToMany(mappedBy = "aluno", cascade = CascadeType.REMOVE)
	private List<PontoAluno> ponto;

	@OneToMany(mappedBy = "aluno", cascade = CascadeType.REMOVE)
	private List<Solicitacao> solicitacao;

	@OneToMany(mappedBy = "aluno", cascade = CascadeType.REMOVE)
	private List<Alarm> alarmes;

	private Integer faltasConsecutivas;

	private boolean termoInternet;

	private boolean internetLiberada;

	private boolean desbloqueioTemporario;

	@Enumerated(EnumType.STRING)
	private EntradaSaida entradaSaida;

	@Enumerated(EnumType.STRING)
	private AlunoStatus status;

	public Aluno() {
		super();
	}

	public Aluno(String nome, String sexo, String rg, String email, String telefone, String cidade,
			String dataNascimento, Integer tag, Integer matricula, Turma turma, Integer numeroTurma,
			String dataMatricula, String empresa) {
		super(nome, sexo, rg, email, telefone, cidade, dataNascimento, tag);
		this.matricula = matricula;
		this.turma = turma;
		this.numeroTurma = numeroTurma;
		setDataMatricula(dataMatricula);
		this.empresa = empresa;
		this.faltasConsecutivas = 0;
		this.termoInternet = false;
		this.internetLiberada = false;
		this.desbloqueioTemporario = false;
		this.entradaSaida = EntradaSaida.ENTRADA;
		this.status = AlunoStatus.ATIVO;

	}

	public Integer getId() {
		return id;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Integer getNumeroTurma() {
		return numeroTurma;
	}

	public void setNumeroTurma(Integer numeroTurma) {
		this.numeroTurma = numeroTurma;
	}

	public LocalDate getDataMatricula() {
		return dataMatricula;
	}

	public void setDataMatricula(String dataMatricula) {
		LocalDate dataMatriculaLd = LocalDate.parse(dataMatricula);
		this.dataMatricula = dataMatriculaLd;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public List<Ocorrencia> getOcorrencias() {
		return ocorrencias;
	}

	public void setOcorrencias(List<Ocorrencia> ocorrencias) {
		this.ocorrencias = ocorrencias;
	}

	public Integer getFaltasConsecutivas() {
		return faltasConsecutivas;
	}

	public void setFaltasConsecutivas(Integer faltasConsecutivas) {
		this.faltasConsecutivas = faltasConsecutivas;
	}

	public boolean isTermoInternet() {
		return termoInternet;
	}

	public void setTermoInternet(boolean termoInternet) {
		this.termoInternet = termoInternet;
	}

	public boolean isInternetLiberada() {
		return internetLiberada;
	}

	public void setInternetLiberada(boolean internetLiberada) {
		this.internetLiberada = internetLiberada;
	}

	public boolean isDesbloqueioTemporario() {
		return desbloqueioTemporario;
	}

	public void setDesbloqueioTemporario(boolean desbloqueioTemporario) {
		this.desbloqueioTemporario = desbloqueioTemporario;
	}

	public EntradaSaida getEntradaSaida() {
		return entradaSaida;
	}

	public void setEntradaSaida(EntradaSaida entradaSaida) {
		this.entradaSaida = entradaSaida;
	}

	public AlunoStatus getStatus() {
		return status;
	}

	public void setStatus(AlunoStatus status) {
		this.status = status;
	}

	public List<PontoAluno> getPonto() {
		return ponto;
	}

	public void setPonto(List<PontoAluno> ponto) {
		this.ponto = ponto;
	}

}
