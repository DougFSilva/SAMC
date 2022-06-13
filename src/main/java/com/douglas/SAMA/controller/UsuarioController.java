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

import com.douglas.SAMA.DTO.UsuarioDTO;
import com.douglas.SAMA.model.Usuario;
import com.douglas.SAMA.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuario")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@PostMapping(value = "/create")
	public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
		Usuario newUsuario = service.create(usuario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUsuario.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> update(@PathVariable Integer id, @RequestBody Usuario usuario) {
		UsuarioDTO usuarioDTO = service.update(id, usuario);
		return ResponseEntity.ok().body(usuarioDTO);
	}

	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> findAll() {
		List<UsuarioDTO> usuariosDTO = service.findAll();
		return ResponseEntity.ok().body(usuariosDTO);

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> findByIdDTO(@PathVariable Integer id) {
		UsuarioDTO usuarioDTO = service.findByIdDTO(id);
		return ResponseEntity.ok().body(usuarioDTO);
	}
}
