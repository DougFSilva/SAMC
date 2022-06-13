package com.douglas.SAMA.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglas.SAMA.DTO.SolicitacaoDTO;
import com.douglas.SAMA.DTO.SolicitacaoFORM;
import com.douglas.SAMA.model.Aluno;
import com.douglas.SAMA.model.Solicitacao;
import com.douglas.SAMA.repository.SolicitacaoRepository;
import com.douglas.SAMA.service.Exception.ObjectNotFoundException;

@Service
public class SolicitacaoService {

	@Autowired
	private SolicitacaoRepository repository;

	@Autowired
	private AlunoService alunoService;

	public Solicitacao create(Integer id, SolicitacaoFORM solicitacaoFORM) {

		return fromFORM(solicitacaoFORM, id);
	}

	public void deleteById(Integer id) {
		findById(id);
		repository.deleteById(id);
		return;
	}

	public Solicitacao updateById(Integer id, Solicitacao solicitacao) {
		Solicitacao oldSolicitacao = findById(id);
		oldSolicitacao.setDescricao(solicitacao.getDescricao());
		if (solicitacao.isStatus() == true) {
			oldSolicitacao.setStatus(true);
			oldSolicitacao.setDataConclusao(LocalDate.now());
		}
		return repository.save(oldSolicitacao);
	}

	public void deleteAll() {
		repository.deleteAll();

	}

	public Solicitacao updateStatus(Integer id, boolean status) {
		Solicitacao solicitacao = findById(id);

		solicitacao.setStatus(status);
		if (status == true) {
			if (solicitacao.getDescricao().equals("Liberação de internet da AAPM")) {
				alunoService.updateInternetLiberada(solicitacao.getAluno().getId(), true);
			}
			solicitacao.setDataConclusao(LocalDate.now());
		} else {
			solicitacao.setDataConclusao(null);
			solicitacao.setDataSolicitacao(LocalDate.now());
		}

		return repository.save(solicitacao);
	}

	public List<SolicitacaoDTO> findAll() {
		List<SolicitacaoDTO> solicitacoesDTO = new ArrayList<>();
		List<Solicitacao> solicitacoes = repository.findAll();
		solicitacoes.forEach(solicitacao -> {
			solicitacoesDTO.add(new SolicitacaoDTO(solicitacao));
		});
		return solicitacoesDTO;
	}

	public SolicitacaoDTO findByIdDTO(Integer id) {
		Solicitacao solicitacao = findById(id);
		return new SolicitacaoDTO(solicitacao);
	}

	private Solicitacao findById(Integer id) {
		Optional<Solicitacao> solicitacao = repository.findById(id);
		return solicitacao.orElseThrow(
				() -> new ObjectNotFoundException("Objeto com id: " + id + " não encontrado na base de dados!"));
	}

	private Solicitacao fromFORM(SolicitacaoFORM solicitacaoFORM, Integer id) {
		Aluno aluno = alunoService.findById(id);
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setAluno(aluno);
		solicitacao.setDescricao(solicitacaoFORM.getDescricao());
		solicitacao.setStatus(solicitacaoFORM.isStatus());
		solicitacao.setDataSolicitacao(LocalDate.now());
		return repository.save(solicitacao);
	}

}
