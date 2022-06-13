package com.douglas.SAMA.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.douglas.SAMA.DTO.OcorrenciaDTO;
import com.douglas.SAMA.DTO.OcorrenciaFORM;
import com.douglas.SAMA.enums.TipoOcorrencia;
import com.douglas.SAMA.enums.TipoPerfil;
import com.douglas.SAMA.model.Aluno;
import com.douglas.SAMA.model.Ocorrencia;
import com.douglas.SAMA.model.Perfil;
import com.douglas.SAMA.model.Usuario;
import com.douglas.SAMA.repository.OcorrenciaRepository;
import com.douglas.SAMA.service.Exception.ObjectNotFoundException;

@Service
public class OcorrenciaService {

	@Autowired
	private OcorrenciaRepository repository;

	@Autowired
	private AlunoService alunoService;

	public Ocorrencia create(Integer aluno_id, OcorrenciaFORM ocorrenciaFORM) {
		return fromFORM(aluno_id, ocorrenciaFORM);

	}

	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
		return;
	}

	public Ocorrencia update(Integer id, OcorrenciaFORM ocorrenciaFORM) {
		Optional<Ocorrencia> ocorrencia = repository.findById(id);
		Aluno aluno = alunoService.findById(ocorrencia.get().getAluno().getId());
		ocorrencia.get().setPrivado(ocorrenciaFORM.isPrivado());
		ocorrencia.get().setAluno(aluno);
		ocorrencia.get().setDescricao(ocorrenciaFORM.getDescricao());
		ocorrencia.get().setRegistrador(ocorrenciaFORM.getRegistrador());
		ocorrencia.get().setBloqueio(ocorrenciaFORM.isBloqueio());
		ocorrencia.get().setTipo(ocorrenciaFORM.getTipo());

		return repository.save(ocorrencia.get());
	}

	public List<OcorrenciaDTO> findAll() {
		List<Ocorrencia> ocorrencias = (List<Ocorrencia>) repository.findAll();
		List<OcorrenciaDTO> ocorrenciasDTO = new ArrayList<>();
		ocorrencias.forEach(ocorrencia -> ocorrenciasDTO.add(new OcorrenciaDTO(ocorrencia)));
		return ocorrenciasDTO;
	}

	public Ocorrencia findById(Integer id) {
		Optional<Ocorrencia> ocorrencia = repository.findById(id);
		return ocorrencia
				.orElseThrow(() -> new ObjectNotFoundException("Ocorrência com id " + id + " não encontrada!"));
	}

	public List<Ocorrencia> findAllByAlunoId(Integer id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = (Usuario) authentication.getPrincipal();
		List<Perfil> perfis = usuario.getPerfis();
		List<TipoPerfil> tiposPerfil = new ArrayList<>();
		perfis.forEach(perfil -> {
			tiposPerfil.add(perfil.getTipo());
		});
		if (tiposPerfil.contains(TipoPerfil.ADMIN)) {
			return (List<Ocorrencia>) repository.findAllByAlunoId(id);

		} else {

			return (List<Ocorrencia>) repository.findAllByAlunoIdAndPrivado(id, false);

		}
	}

	public List<Ocorrencia> findAllByAlunoIdSystem(Integer id) {
		return (List<Ocorrencia>) repository.findAllByAlunoId(id);

	}

	public List<Ocorrencia> findAllByAlunoIdAndBloqueio(Integer id, boolean bloqueio) {
		return (List<Ocorrencia>) repository.findAllByAlunoIdAndBloqueio(id, bloqueio);
	}

	private Ocorrencia fromFORM(Integer aluno_id, OcorrenciaFORM ocorrenciaFORM) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setPrivado(ocorrenciaFORM.isPrivado());
		ocorrencia.setTipo(ocorrenciaFORM.getTipo());
		if (ocorrenciaFORM.getRegistrador() != "sistema") {
			Usuario usuario = (Usuario) authentication.getPrincipal();
			ocorrencia.setRegistrador(usuario.getNome());
		} else {
			ocorrencia.setRegistrador(ocorrenciaFORM.getRegistrador());
		}
		Aluno aluno = alunoService.findById(aluno_id);
		ocorrencia.setAluno(aluno);
		ocorrencia.setDescricao(ocorrenciaFORM.getDescricao());
		ocorrencia.setBloqueio(ocorrenciaFORM.isBloqueio());
		if (ocorrenciaFORM.getTipo() == TipoOcorrencia.ATRASO) {
			aluno.setDesbloqueioTemporario(true);
			alunoService.save(aluno);
		}
		return repository.save(ocorrencia);
	}

}
