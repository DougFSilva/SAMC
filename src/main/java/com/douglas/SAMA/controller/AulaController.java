package com.douglas.SAMA.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douglas.SAMA.DTO.AulaFORM;
import com.douglas.SAMA.model.Aula;
import com.douglas.SAMA.service.AulaService;

@RestController
@RequestMapping(value = "/aula")
public class AulaController {

	@Autowired
	private AulaService service;

	@PostMapping(value = "/turma/{turma_id}/createAll")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<Aula>> createAll(@Valid @RequestBody List<AulaFORM> aulasFORM,
			@PathVariable Integer turma_id) {
		List<Aula> aulas = service.createAll(turma_id, aulasFORM);
		return ResponseEntity.ok().body(aulas);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Aula> findById(@PathVariable Integer id) {
		Aula aula = service.findById(id);
		return ResponseEntity.ok().body(aula);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/data/{diaAula}")
	public ResponseEntity<List<Aula>> findByDiaAula(@PathVariable String diaAula) {
		List<Aula> aulas = service.findByDiaAula(diaAula);
		return ResponseEntity.ok().body(aulas);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/turma/{turma_id}")
	public ResponseEntity<List<Aula>> findAllByTurma_id(@PathVariable Integer turma_id) {
		List<Aula> aulas = service.findAllByTurma_id(turma_id);
		return ResponseEntity.ok().body(aulas);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/turma/{turma_id}/deleteAll")
	public ResponseEntity<Void> deleteAllByTurma_id(@PathVariable Integer turma_id) {
		service.deleteAllByTurma_id(turma_id);
		return ResponseEntity.noContent().build();
	}

}
