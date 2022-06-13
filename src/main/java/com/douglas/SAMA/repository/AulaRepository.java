package com.douglas.SAMA.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.douglas.SAMA.model.Aula;
import com.douglas.SAMA.model.Turma;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Integer> {

	List<Aula> findAllByTurma_id(Integer idTurma);

	Optional<Aula> findByDiaAulaAndTurma_id(LocalDate localDate, Integer id);

	List<Aula> findAllByDiaAula(LocalDate localDate);

	@Modifying
	@Transactional
	@Query(value = "DELETE a FROM aula a WHERE a.turma_id = ?1", nativeQuery = true)
	void deleteAllByTurma_id(Integer id);

	void deleteAllByTurma(Turma turma);

}
