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

import com.douglas.SAMC.DTO.CursoDTO;
import com.douglas.SAMC.model.Curso;
import com.douglas.SAMC.service.CursoService;

@RestController
@RequestMapping(value = "/curso")
public class CursoController {

	@Autowired
	private CursoService service;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/create")
	public ResponseEntity<Curso> create(@Valid @RequestBody Curso curso) {
		service.create(curso);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(curso.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Curso> update(@PathVariable Integer id, @Valid @RequestBody Curso curso) {
		Curso newCurso = service.update(id, curso);
		return ResponseEntity.ok().body(newCurso);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public ResponseEntity<List<CursoDTO>> findAll() {
		List<CursoDTO> cursosDTO = service.findAll();
		return ResponseEntity.ok().body(cursosDTO);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/{id}")
	public ResponseEntity<CursoDTO> findById(@PathVariable Integer id) {
		CursoDTO cursoDTO = service.findByIdDTO(id);
		return ResponseEntity.ok().body(cursoDTO);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "turma/{codigo}")
	public ResponseEntity<Curso> findByTurmaCodigo(@PathVariable String codigo) {
		Curso curso = service.findByTurmaCodigo(codigo);
		return ResponseEntity.ok().body(curso);
	}

}
