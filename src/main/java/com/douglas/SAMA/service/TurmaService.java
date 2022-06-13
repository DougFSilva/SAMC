package com.douglas.SAMA.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.douglas.SAMA.DTO.TurmaDTO;
import com.douglas.SAMA.DTO.TurmaFORM;
import com.douglas.SAMA.model.Aluno;
import com.douglas.SAMA.model.Curso;
import com.douglas.SAMA.model.Turma;
import com.douglas.SAMA.repository.AlunoRepository;
import com.douglas.SAMA.repository.TurmaRepository;
import com.douglas.SAMA.service.Exception.DataIntegratyViolationException;
import com.douglas.SAMA.service.Exception.ObjectNotFoundException;
import com.douglas.SAMA.service.Exception.OperationNotAllowedException;

@Service
public class TurmaService {

	@Autowired
	private TurmaRepository repository;

	@Autowired
	private CursoService cursoService;

	@Autowired
	private AlunoRepository alunoRepository;

	@Value("${turmaPhotoLocation}")
	private String turmaPhotoLocation;

	public Turma create(Integer curso_id, TurmaFORM turmaFORM) {
		if (repository.findByCodigoAndCurso_id(turmaFORM.getCodigo(), curso_id).isPresent()) {
			throw new DataIntegratyViolationException(
					"Turma " + turmaFORM.getCodigo() + " já cadastrada na base de dados");

		}
		Curso curso = cursoService.findById(curso_id);
		Turma turma = new Turma(curso, turmaFORM.getCodigo(), turmaFORM.getEntrada(), turmaFORM.getSaida(),
				turmaFORM.getAlmocoSaida(), turmaFORM.getAlmocoEntrada(), turmaFORM.getToleranciaEntrada(),
				turmaFORM.getToleranciaSaida(), turmaFORM.getPeriodo());
		return repository.save(turma);
	}

	public void delete(Integer id) {
		Turma turma = findById(id);
		if (turma.getCodigo().equals("EVADIDO") || turma.getCodigo().equals("FORMANDO")) {
			throw new OperationNotAllowedException("Não é permitido deletar essa turma");
		}
		List<Aluno> alunos = alunoRepository.findByTurma(turma);
		if (alunos.size() > 0) {
			throw new OperationNotAllowedException("Essa turma possuí alunos");
		}
		repository.deleteById(id);
		return;
	}

	public Turma update(Integer id, TurmaFORM turmaFORM) {
		Turma turma = findById(id);
		if (turma.getCodigo().equals("EVADIDO") || turma.getCodigo().equals("FORMANDO")) {
			throw new OperationNotAllowedException("Não é permitido editar essa turma");
		}
		turma.setCodigo(turmaFORM.getCodigo());
		turma.setEntrada(turmaFORM.getEntrada());
		turma.setSaida(turmaFORM.getSaida());
		turma.setAlmocoSaida(turmaFORM.getAlmocoSaida());
		turma.setAlmocoEntrada(turmaFORM.getAlmocoEntrada());
		turma.setToleranciaEntrada(turmaFORM.getToleranciaEntrada());
		turma.setToleranciaSaida(turmaFORM.getToleranciaSaida());
		turma.setPeriodo(turmaFORM.getPeriodo());
		return repository.save(turma);
	}

	public Turma findByCodigo(String codigo) {
		Optional<Turma> turma = repository.findByCodigo(codigo);
		return turma.orElseThrow(
				() -> new ObjectNotFoundException("Turma " + codigo + " não encontrada na base de dados!"));
	}

	public TurmaDTO findByCodigoDTO(String codigo) {
		Optional<Turma> turma = repository.findByCodigo(codigo);
		if (turma.isPresent()) {
			return toDTO(turma.get());
		}

		throw new ObjectNotFoundException("Turma " + codigo + " não encontrada na base de dados!");
	}

	public TurmaDTO findByIdDTO(Integer id) {
		Optional<Turma> turma = repository.findById(id);
		if (turma.isPresent()) {
			return toDTO(turma.get());
		}
		throw new ObjectNotFoundException("Turma com " + id + " não encontrada na base de dados!");
	}

	public Turma findById(Integer id) {
		Optional<Turma> turma = repository.findById(id);
		return turma.orElseThrow(
				() -> new ObjectNotFoundException("Turma com " + id + " não encontrado na base de dados!"));
	}

	public List<Turma> findAll() {
		return (List<Turma>) repository.findAll();
	}

	public List<TurmaDTO> findAllDTO() {
		List<Turma> turmas = (List<Turma>) repository.findAll();
		return toListDTO(turmas);
	}

	public List<TurmaDTO> findAllByCursoIdDTO(Integer curso_id) {
		List<Turma> turmas = (List<Turma>) repository.findAllByCurso_id(curso_id);
		return toListDTO(turmas);
	}

	public Turma findByCodigoAndCursoid(String codigo, Integer curso_id) {
		Optional<Turma> turma = repository.findByCodigoAndCurso_id(codigo, curso_id);
		return turma.orElseThrow(
				() -> new ObjectNotFoundException("Turma " + codigo + " não encontrado na base de dados!"));
	}

	public List<Turma> findAllByAulasDiaAula(LocalDate localDate) {
		return repository.findAllByAulasDiaAula(localDate);
	}

	public TurmaDTO toDTO(Turma turma) {
		TurmaDTO turmaDTO = new TurmaDTO(turma);
		turmaDTO.setImagem(getImageBase64(turma));
		return turmaDTO;
	}

	public List<TurmaDTO> toListDTO(List<Turma> turmas) {
		List<TurmaDTO> turmasDTO = new ArrayList<>();
		turmas.forEach(turma -> {
			TurmaDTO turmaDTO = new TurmaDTO(turma);
			turmaDTO.setImagem(getImageBase64(turma));
			turmasDTO.add(turmaDTO);
		});
		return turmasDTO;
	}

	@SuppressWarnings("resource")
	private String getImageBase64(Turma turma) {
		String imageName = turma.getId().toString() + '_' + turma.getCodigo() + ".JPG";
		try {
			File file = new File(this.turmaPhotoLocation + imageName);
			FileInputStream fileInputStream;
			try {
				fileInputStream = new FileInputStream(file);
				byte imageData[] = new byte[(int) file.length()];
				fileInputStream.read(imageData);
				String imageBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageData);
				return imageBase64;
			} catch (IOException e) {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

}
