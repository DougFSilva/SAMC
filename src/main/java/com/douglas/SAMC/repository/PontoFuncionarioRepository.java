package com.douglas.SAMC.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.douglas.SAMC.model.PontoFuncionario;

public interface PontoFuncionarioRepository extends JpaRepository<PontoFuncionario, Integer> {

	List<PontoFuncionario> findByFuncionarioId(Integer id);

	List<PontoFuncionario> findByFuncionarioMatricula(Integer matricula);

}
