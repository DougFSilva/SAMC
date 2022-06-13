package com.douglas.SAMC.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglas.SAMC.model.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer> {

	Optional<Turma> findByCodigo(String codigo);

	List<Turma> findAllByAulasDiaAula(LocalDate localDate);

	Optional<Turma> findByCodigoAndCurso_id(String codigo, Integer turmaId);

	List<Turma> findAllByCurso_id(Integer idCurso);

}
