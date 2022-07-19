package com.douglas.SAMC.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglas.SAMC.enums.AlunoStatus;
import com.douglas.SAMC.enums.EntradaSaida;
import com.douglas.SAMC.model.Aluno;
import com.douglas.SAMC.model.Turma;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

	Optional<Aluno> findByMatricula(Integer matricula);

	Optional<Aluno> findByTag(Integer tag);

	List<Aluno> findAllByNomeContaining(String nome);

	List<Aluno> findAllByTurmaOrderByNome(Turma turma, Pageable paginacao);

	Set<Aluno> findAllByEntradaSaidaOrderByNome(EntradaSaida entradaSaida);

	List<Aluno> findAllByTurmaCodigoOrderByNome(String codigo);

	Set<Aluno> findAllByDesbloqueioTemporarioOrderByNome(boolean resolvido);

	List<Aluno> findAllByStatusOrderByNome(AlunoStatus alunoStatus, Pageable paginacao);

	List<Aluno> findByTurmaAndStatusOrderByNome(Turma turma, AlunoStatus status);

	List<Aluno> findAllByTurmaCursoIdOrderByNome(Integer idCurso, Pageable paginacao);

	List<Aluno> findAllByTurmaCursoIdAndStatusOrderByNome(Integer idCurso, AlunoStatus status);

	List<Aluno> findByTurma(Turma turma);

	List<Aluno> findAllByTurmaIdOrderByNome(Integer idTurmaAtual);
	
	Page<Aluno> findAll(Pageable paginacao);

}
