package com.douglas.SAMA.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglas.SAMA.model.PontoAluno;
import com.douglas.SAMA.repository.PontoAlunoRepository;

@Service
public class PontoAlunoService {

	@Autowired
	private PontoAlunoRepository repository;

	public void register(PontoAluno pontoAluno) {
		repository.save(pontoAluno);

	}

	public List<PontoAluno> findByAlunoId(Integer id) {
		List<PontoAluno> pontosAluno = repository.findByAlunoId(id);
		return pontosAluno;
	}

	public List<PontoAluno> findByAlunoMatricula(Integer matricula) {
		List<PontoAluno> pontosAluno = repository.findByAlunoMatricula(matricula);
		return pontosAluno;
	}

	public List<PontoAluno> findAll() {
		return (List<PontoAluno>) repository.findAll();
	}

	public List<PontoAluno> findByTimestampBetween(LocalDateTime localDateTimeAfter,
			LocalDateTime localDateTimeBefore) {
		return (List<PontoAluno>) repository.findByTimestampBetween(localDateTimeAfter, localDateTimeBefore);
	}

}
