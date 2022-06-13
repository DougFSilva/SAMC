package com.douglas.SAMC.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.douglas.SAMC.model.Ocorrencia;

@Repository
public interface OcorrenciaRepository extends CrudRepository<Ocorrencia, Integer> {

	List<Ocorrencia> findAllByAlunoId(Integer id);

	List<Ocorrencia> findAllByAlunoIdAndPrivado(Integer id, boolean bloqueio);

	List<Ocorrencia> findAllByAlunoIdAndBloqueio(Integer id, boolean bloqueio);

}
