package com.douglas.SAMC.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglas.SAMC.model.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

	Optional<Funcionario> findByMatricula(Integer matricula);

	List<Funcionario> findAllByNomeContaining(String nome);

	Optional<Funcionario> findByTag(Integer tag);
	
	Page<Funcionario> findAll(Pageable paginacao);

}
