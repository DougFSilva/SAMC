package com.douglas.SAMC.DTO;

import java.util.List;

import com.douglas.SAMC.model.Curso;
import com.douglas.SAMC.model.Turma;

public class CursoDTO {

	private Integer id;

	private String modalidade;

	private String areaTecnologica;

	private List<Turma> turma;

	private String imagem;

	public CursoDTO() {
		super();
	}

	public CursoDTO(Curso curso) {
		super();
		this.id = curso.getId();
		this.modalidade = curso.getModalidade();
		this.areaTecnologica = curso.getAreaTecnologica();
		this.turma = curso.getTurma();
	}

	public Integer getId() {
		return id;
	}

	public String getModalidade() {
		return modalidade;
	}

	public String getAreaTecnologica() {
		return areaTecnologica;
	}

	public List<Turma> getTurma() {
		return turma;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

}
