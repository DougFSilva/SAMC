package com.douglas.SAMA.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douglas.SAMA.model.AcessoCatraca;
import com.douglas.SAMA.service.AcessoCatracaService;

@RestController
@RequestMapping(value = "/acesso")
public class AcessoCatracaController {

	@Autowired
	private AcessoCatracaService service;

	@GetMapping(value = "/{tag}")
	public ResponseEntity<AcessoCatraca> acesso(@Valid @PathVariable Integer tag) {
		AcessoCatraca acessoCatraca = service.acessoCatraca(tag);
		return ResponseEntity.ok().body(acessoCatraca);
	}

}
