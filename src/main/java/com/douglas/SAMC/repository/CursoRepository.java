package com.douglas.SAMC.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.douglas.SAMC.model.Curso;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Integer> {

	Optional<Curso> findByTurmaId(Integer id);

	Optional<Curso> findByIdAndTurmaCodigo(Integer idCurso, String codigo);

	Optional<Curso> findByTurmaCodigo(String codigo);

	Optional<Curso> findByTurmaIdOrderByModalidade(Integer id);

}
