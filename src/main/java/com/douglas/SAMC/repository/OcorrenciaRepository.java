package com.douglas.SAMC.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglas.SAMC.model.Ocorrencia;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Integer> {

	List<Ocorrencia> findAllByAlunoId(Integer id);

	List<Ocorrencia> findAllByAlunoIdAndPrivado(Integer id, boolean bloqueio);

	List<Ocorrencia> findAllByAlunoIdAndBloqueio(Integer id, boolean bloqueio);

}
