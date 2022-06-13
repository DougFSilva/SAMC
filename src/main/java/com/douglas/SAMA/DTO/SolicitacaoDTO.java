package com.douglas.SAMA.DTO;

import java.time.LocalDate;

import com.douglas.SAMA.model.Solicitacao;

public class SolicitacaoDTO {

	private Integer id;

	private Integer alunoId;

	private LocalDate dataSolicitacao;

	private LocalDate dataConclusao;

	private String turma;

	private String aluno;

	private String descricao;

	private boolean status;

	public SolicitacaoDTO() {
		super();
	}

	public SolicitacaoDTO(Solicitacao solicitacao) {
		this.id = solicitacao.getId();
		this.alunoId = solicitacao.getAluno().getId();
		this.dataSolicitacao = solicitacao.getDataSolicitacao();
		this.dataConclusao = solicitacao.getDataConclusao();
		this.turma = solicitacao.getAluno().getTurma().getCodigo();
		this.aluno = solicitacao.getAluno().getNome();
		this.descricao = solicitacao.getDescricao();
		this.status = solicitacao.isStatus();
	}

	public Integer getId() {
		return id;
	}

	public Integer getAlunoId() {
		return alunoId;
	}

	public LocalDate getDataSolicitacao() {
		return dataSolicitacao;
	}

	public LocalDate getDataConclusao() {
		return dataConclusao;
	}

	public String getTurma() {
		return turma;
	}

	public String getAluno() {
		return aluno;
	}

	public String getDescricao() {
		return descricao;
	}

	public boolean isStatus() {
		return status;
	}

}
