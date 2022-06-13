package com.douglas.SAMA.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglas.SAMA.DTO.AulaFORM;
import com.douglas.SAMA.model.Aula;
import com.douglas.SAMA.model.Turma;
import com.douglas.SAMA.repository.AulaRepository;
import com.douglas.SAMA.service.Exception.ObjectNotFoundException;

@Service
public class AulaService {

	@Autowired
	private AulaRepository repository;

	@Autowired
	private TurmaService turmaService;

	public List<Aula> createAll(Integer turma_id, List<AulaFORM> aulasFORM) {
		List<Aula> aulas = new ArrayList<>();
		Turma turma = turmaService.findById(turma_id);
		aulasFORM.forEach(aulaFORM -> {
			LocalDate diaAula = LocalDate.parse(aulaFORM.getAula());
			aulas.add(new Aula(diaAula, turma));
		});
		List<Aula> oldAulas = repository.findAllByTurma_id(turma_id);
		repository.deleteAll(oldAulas);

		return (List<Aula>) repository.saveAll(aulas);

	}

	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
		return;
	}

	public void deleteAllByTurma_id(Integer id) {
		repository.deleteAllByTurma_id(id);
		return;

	}

	public Aula findById(Integer id) {
		Optional<Aula> aula = repository.findById(id);
		return aula.orElseThrow(
				() -> new ObjectNotFoundException("Aula com id " + id + "não encontrado na base de dados"));
	}

	public List<Aula> findByDiaAula(String diaAula) {
		LocalDate localDate = LocalDate.parse(diaAula);
		return (List<Aula>) repository.findAllByDiaAula(localDate);
	}

	public List<Aula> findAllByTurma_id(Integer turma_id) {
		return (List<Aula>) repository.findAllByTurma_id(turma_id);
	}

}
