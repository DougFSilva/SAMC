package com.douglas.SAMA.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.douglas.SAMA.model.PontoFuncionario;

public interface PontoFuncionarioRepository extends CrudRepository<PontoFuncionario, Integer> {

	List<PontoFuncionario> findByFuncionarioId(Integer id);

	List<PontoFuncionario> findByFuncionarioMatricula(Integer matricula);

}
