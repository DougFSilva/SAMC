package com.douglas.SAMA.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.douglas.SAMA.DTO.SolicitacaoDTO;
import com.douglas.SAMA.DTO.SolicitacaoFORM;
import com.douglas.SAMA.model.Solicitacao;
import com.douglas.SAMA.service.SolicitacaoService;

@RestController
@RequestMapping(value = "/solicitacao")
public class SolicitacaoController {

	@Autowired
	private SolicitacaoService service;

	@PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
	@PostMapping(value = "/create/aluno/{id}")
	public ResponseEntity<Solicitacao> create(@RequestBody SolicitacaoFORM solicitacaoFORM, @PathVariable Integer id) {
		Solicitacao solicitacao = service.create(id, solicitacaoFORM);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(solicitacao.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
	@DeleteMapping
	public ResponseEntity<Void> deleteAll() {
		service.deleteAll();
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Solicitacao> updateById(@PathVariable Integer id, @RequestBody Solicitacao soliciacao) {
		Solicitacao newSolicitacao = service.updateById(id, soliciacao);
		return ResponseEntity.ok().body(newSolicitacao);
	}

	@PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
	@PutMapping(value = "/{id}/{status}")
	public ResponseEntity<Solicitacao> updateStatus(@PathVariable Integer id, @PathVariable boolean status) {
		Solicitacao solicitacao = service.updateStatus(id, status);
		return ResponseEntity.ok().body(solicitacao);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping()
	public ResponseEntity<List<SolicitacaoDTO>> findAll() {
		List<SolicitacaoDTO> solicitacoesDTO = service.findAll();
		return ResponseEntity.ok().body(solicitacoesDTO);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/{id}")
	public ResponseEntity<SolicitacaoDTO> findById(@PathVariable Integer id) {
		SolicitacaoDTO solicitacaoDTO = service.findByIdDTO(id);
		return ResponseEntity.ok().body(solicitacaoDTO);
	}

}
