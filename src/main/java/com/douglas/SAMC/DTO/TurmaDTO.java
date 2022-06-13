package com.douglas.SAMC.DTO;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import com.douglas.SAMC.enums.Periodo;
import com.douglas.SAMC.model.Aula;
import com.douglas.SAMC.model.Turma;

public class TurmaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String codigo;

	private Integer cursoId;

	private LocalTime entrada;

	private LocalTime saida;

	private LocalTime almocoSaida;

	private LocalTime almocoEntrada;

	private Integer toleranciaEntrada;

	private Integer toleranciaSaida;

	private Periodo periodo;

	private List<Aula> aulas;

	private String imagem;

	public TurmaDTO() {
		super();
	}

	public TurmaDTO(Turma turma) {
		super();
		this.id = turma.getId();
		this.codigo = turma.getCodigo();
		this.cursoId = turma.getCurso().getId();
		this.entrada = turma.getEntrada();
		this.saida = turma.getSaida();
		this.almocoSaida = turma.getAlmocoSaida();
		this.almocoEntrada = turma.getAlmocoEntrada();
		this.toleranciaEntrada = turma.getToleranciaEntrada();
		this.toleranciaSaida = turma.getToleranciaSaida();
		this.periodo = turma.getPeriodo();
		this.aulas = (List<Aula>) turma.getAulas();

	}

	public Integer getId() {
		return id;
	}

	public String getCodigo() {
		return codigo;
	}

	public Integer getCursoId() {
		return cursoId;
	}

	public LocalTime getEntrada() {
		return entrada;
	}

	public LocalTime getSaida() {
		return saida;
	}

	public LocalTime getAlmocoSaida() {
		return almocoSaida;
	}

	public LocalTime getAlmocoEntrada() {
		return almocoEntrada;
	}

	public Integer getToleranciaEntrada() {
		return toleranciaEntrada;
	}

	public Integer getToleranciaSaida() {
		return toleranciaSaida;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public List<Aula> getAulas() {
		return aulas;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

}
