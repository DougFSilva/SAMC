package com.douglas.SAMC.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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

import com.douglas.SAMC.DTO.OcorrenciaDTO;
import com.douglas.SAMC.DTO.OcorrenciaFORM;
import com.douglas.SAMC.model.Ocorrencia;
import com.douglas.SAMC.service.OcorrenciaService;

@RestController
@RequestMapping(value = "/ocorrencia")
public class OcorrenciaController {

	@Autowired
	private OcorrenciaService service;

	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/create/{aluno_id}")
	public ResponseEntity<Ocorrencia> create(@PathVariable Integer aluno_id,
			@Valid @RequestBody OcorrenciaFORM ocorrenciaFORM) {
		Ocorrencia ocorrencia = service.create(aluno_id, ocorrenciaFORM);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ocorrencia.getId())
				.toUri();
		return ResponseEntity.created(uri).build();

	}

	@PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Ocorrencia> update(@PathVariable Integer id,
			@Valid @RequestBody OcorrenciaFORM ocorrenciaFORM) {
		Ocorrencia ocorrencia = service.update(id, ocorrenciaFORM);
		return ResponseEntity.ok().body(ocorrencia);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Ocorrencia> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public ResponseEntity<List<OcorrenciaDTO>> findAll() {
		List<OcorrenciaDTO> ocorrencias = service.findAll();
		return ResponseEntity.ok().body(ocorrencias);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Ocorrencia> findById(@PathVariable Integer id) {
		Ocorrencia ocorrencia = service.findById(id);
		return ResponseEntity.ok().body(ocorrencia);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/aluno/{id}")
	public ResponseEntity<List<Ocorrencia>> findByAlunoId(@PathVariable Integer id) {
		List<Ocorrencia> ocorrencias = service.findAllByAlunoId(id);
		return ResponseEntity.ok().body(ocorrencias);
	}

}
