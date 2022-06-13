package com.douglas.SAMA.DTO;

import java.time.LocalDate;

import com.douglas.SAMA.model.Alarm;

public class AlarmDTO {

	private Integer id;

	private Integer alunoId;

	private LocalDate data;

	private String turma;

	private String aluno;

	private String descricao;

	private boolean status;

	public AlarmDTO() {
		super();
	}

	public AlarmDTO(Alarm alarm) {
		super();
		this.id = alarm.getId();
		this.alunoId = alarm.getAluno().getId();
		this.data = alarm.getData();
		this.turma = alarm.getAluno().getTurma().getCodigo();
		this.aluno = alarm.getAluno().getNome();
		this.descricao = alarm.getDescricao();
		this.status = alarm.isStatus();
	}

	public Integer getId() {
		return id;
	}

	public Integer getAlunoId() {
		return alunoId;
	}

	public LocalDate getData() {
		return data;
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
