package com.douglas.SAMC.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douglas.SAMC.DTO.AlarmDTO;
import com.douglas.SAMC.service.AlarmService;

@RestController
@RequestMapping(value = "/alarm")
public class AlarmController {

	@Autowired
	private AlarmService service;

	@PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
	@GetMapping
	public ResponseEntity<List<AlarmDTO>> findAll() {
		List<AlarmDTO> alarmes = service.findAll();
		return ResponseEntity.ok().body(alarmes);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping
	public ResponseEntity<Void> deleteAll() {
		service.deleteAll();
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
	@PutMapping(value = "/{id}/status/{status}")
	public ResponseEntity<AlarmDTO> updateStatusById(@PathVariable Integer id, @PathVariable boolean status) {
		AlarmDTO alarmeDTO = service.updateStatusById(id, status);
		return ResponseEntity.ok().body(alarmeDTO);
	}

}
