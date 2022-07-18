package com.douglas.SAMC.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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

import com.douglas.SAMC.DTO.AlunoDTO;
import com.douglas.SAMC.DTO.AlunoFORM;
import com.douglas.SAMC.DTO.ImageFORM;
import com.douglas.SAMC.enums.AlunoStatus;
import com.douglas.SAMC.enums.EntradaSaida;
import com.douglas.SAMC.model.Aluno;
import com.douglas.SAMC.service.AlunoService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/aluno")
public class AlunoController {

	@Autowired
	private AlunoService service;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/create")
	@CacheEvict(value = {"alunoFindAllByStatusLazy", "alunoFindAllByStatus"}, allEntries = true )
	public ResponseEntity<Aluno> create(@Valid @RequestBody AlunoFORM alunoFORM) {
		Aluno aluno = service.create(alunoFORM);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(aluno.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/create/all")
	@CacheEvict(value = {"alunoFindAllByStatusLazy", "alunoFindAllByStatus"}, allEntries = true )
	public ResponseEntity<List<Aluno>> createAll(@Valid @RequestBody List<AlunoFORM> alunosForm) {
		List<Aluno> alunos = service.createAll(alunosForm);
		return ResponseEntity.ok().body(alunos);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	@CacheEvict(value = {"alunoFindAllByStatusLazy", "alunoFindAllByStatus"}, allEntries = true )
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}")
	@CacheEvict(value = {"alunoFindAllByStatusLazy", "alunoFindAllByStatus"}, allEntries = true )
	public ResponseEntity<Aluno> update(@PathVariable Integer id, @Valid @RequestBody AlunoFORM alunoFORM) {
		Aluno aluno = service.update(id, alunoFORM);
		return ResponseEntity.ok().body(aluno);
	}
	
	@PreAuthorize("hasAnyRole('OPERATOR','ADMIN')")
	@PutMapping(value = "/tag/{id}/{tag}")
	public ResponseEntity<Aluno> updateTag(@PathVariable Integer id, @PathVariable Integer tag) {
		Aluno aluno = service.updateTag(id, tag);
		return ResponseEntity.ok().body(aluno);
	}

	@PreAuthorize("hasAnyRole('OPERATOR','ADMIN')")
	@PutMapping(value = "/entradaSaida/{id}/{entradaSaida}")
	@CacheEvict(value = {"alunoFindAllByStatusLazy", "alunoFindAllByStatus"}, allEntries = true )
	public ResponseEntity<AlunoDTO> updateEntradaSaida(@PathVariable Integer id,
			@PathVariable EntradaSaida entradaSaida) {
		AlunoDTO alunoDTO = service.updateEntradaSaida(id, entradaSaida);
		return ResponseEntity.ok().body(alunoDTO);
	}

	@PreAuthorize("hasAnyRole('OPERATOR','ADMIN')")
	@PutMapping(value = "/desbloqueioTemporario/{id}/{desbloqueioTemporario}")
	public ResponseEntity<AlunoDTO> updateDesbloqueioTemporario(@PathVariable Integer id,
			@PathVariable boolean desbloqueioTemporario) {
		AlunoDTO alunoDTO = service.updateDesbloqueioTemporario(id, desbloqueioTemporario);
		return ResponseEntity.ok().body(alunoDTO);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = "/{id}/status/{status}")
	@CacheEvict(value = {"alunoFindAllByStatusLazy", "alunoFindAllByStatus"}, allEntries = true )
	public ResponseEntity<Aluno> updateStatus(@PathVariable Integer id, @PathVariable AlunoStatus status) {
		Aluno aluno = service.updateStatus(id, status);
		return ResponseEntity.ok().body(aluno);
	}


	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public ResponseEntity<List<AlunoDTO>> findAll(@PageableDefault(sort = "nome", direction = Direction.ASC) @ParameterObject Pageable paginacao) throws IOException {
		List<AlunoDTO> alunosDTO = service.findAll(paginacao);
		return ResponseEntity.ok().body(alunosDTO);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/status/{status}")
	@Cacheable(value = "alunoFindAllByStatus")
	public ResponseEntity<List<AlunoDTO>> findAllByStatus( @PathVariable AlunoStatus status,@PageableDefault(sort = "nome", direction = Direction.ASC) @ParameterObject Pageable paginacao) throws IOException {
		List<AlunoDTO> alunosDTO = service.findAllByStatus(status, paginacao);
		return ResponseEntity.ok().body(alunosDTO);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/status/{status}/lazy")
	@Cacheable(value = "alunoFindAllByStatusLazy")
	@Operation(summary = "List all students by status", description = "Enter the student's status. The return will be a list of students without photos")
	public ResponseEntity<List<AlunoDTO>> findAllByStatusLazy( @PathVariable AlunoStatus status, @PageableDefault(sort = "nome", direction = Direction.ASC)@ParameterObject Pageable paginacao) throws IOException {
		List<AlunoDTO> alunosDTO = service.findAllByStatusLazy(status, paginacao);
		return ResponseEntity.ok().body(alunosDTO);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/{id}")
	public ResponseEntity<AlunoDTO> findByIdDTO(@PathVariable Integer id) {
		AlunoDTO alunoDTO = service.findByIdDTO(id);
		return ResponseEntity.ok().body(alunoDTO);

	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/tag/{tag}")
	public ResponseEntity<AlunoDTO> findByTag(@PathVariable Integer tag) {
		AlunoDTO alunoDTO = service.findByTag(tag);
		return ResponseEntity.ok().body(alunoDTO);
	}

	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/turma/{id}")
	public ResponseEntity<List<AlunoDTO>> findByTurma(@PathVariable Integer id, @PageableDefault(sort = "nome", direction = Direction.ASC) Pageable paginacao) {
		List<AlunoDTO> alunosDTO = service.findByTurmaDTO(id, paginacao);
		return ResponseEntity.ok().body(alunosDTO);
	}


	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/curso/{curso_id}")
	public ResponseEntity<List<AlunoDTO>> findByCursoId(@PathVariable Integer curso_id, @PageableDefault(sort = "nome", direction = Direction.ASC) Pageable paginacao) {
		List<AlunoDTO> alunosDTO = service.findByCurso(curso_id, paginacao);
		return ResponseEntity.ok().body(alunosDTO);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/curso/{curso_id}/status/{status}")
	public ResponseEntity<List<AlunoDTO>> findByCursoAndStatusOrderByNome(@PathVariable Integer curso_id,
			@PathVariable AlunoStatus status) {
		List<AlunoDTO> alunosDTO = service.findByCursoAndStatusOrderByNome(curso_id, status);
		return ResponseEntity.ok().body(alunosDTO);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/move/{turmaAtual_id}/to/{turmaDestino_id}")
	@CacheEvict(value = {"alunoFindAllByStatusLazy", "alunoFindAllByStatus"}, allEntries = true )
	public ResponseEntity<List<Aluno>> move(@PathVariable Integer turmaAtual_id,
			@PathVariable Integer turmaDestino_id, Pageable paginacao) {
		List<Aluno> alunos = service.move(turmaAtual_id, turmaDestino_id, paginacao);
		return ResponseEntity.ok().body(alunos);
	}

	@PreAuthorize("hasAnyRole('OPERATOR','ADMIN')")
	@PostMapping(value = "/saveImage/{id}")
	@CacheEvict(value = {"alunoFindAllByStatusLazy", "alunoFindAllByStatus"}, allEntries = true )
	public ResponseEntity<Void> saveImage(@PathVariable Integer id, @RequestBody ImageFORM imageFORM) {
		service.saveImage(id, imageFORM);
		return ResponseEntity.ok().build();

	}
	

}
