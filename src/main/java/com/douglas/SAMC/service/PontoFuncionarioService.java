package com.douglas.SAMC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglas.SAMC.model.PontoFuncionario;
import com.douglas.SAMC.repository.PontoFuncionarioRepository;

@Service
public class PontoFuncionarioService {

	@Autowired
	private PontoFuncionarioRepository repository;

	@Autowired
	private FuncionarioService funcionarioService;

	public void register(PontoFuncionario pontoFuncionario) {
		repository.save(pontoFuncionario);
	}

	public List<PontoFuncionario> findByFuncionarioId(Integer id) {
		List<PontoFuncionario> pontosFuncionario = repository.findByFuncionarioId(id);
		return pontosFuncionario;
	}

	public List<PontoFuncionario> findByFuncionarioMatricula(Integer matricula) {
		funcionarioService.findByMatricula(matricula);
		List<PontoFuncionario> pontosFuncionario = repository.findByFuncionarioMatricula(matricula);
		return pontosFuncionario;
	}

}
