package com.douglas.SAMC.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.douglas.SAMC.model.Funcionario;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> {

	Optional<Funcionario> findByMatricula(Integer matricula);

	List<Funcionario> findAllByNomeContaining(String nome);

	Optional<Funcionario> findByTag(Integer tag);

}
