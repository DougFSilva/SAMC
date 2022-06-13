package com.douglas.SAMC.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douglas.SAMC.DTO.LoginFORM;
import com.douglas.SAMC.DTO.TokenDTO;
import com.douglas.SAMC.config.security.TokenService;
import com.douglas.SAMC.model.Usuario;

@RestController
@RequestMapping("/auth")

public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<?> autenticar(@RequestBody @Valid LoginFORM loginFORM) {
		UsernamePasswordAuthenticationToken dadosLogin = loginFORM.convert();
		try {
			Authentication authentication = authenticationManager.authenticate(dadosLogin);
			Usuario usuario = (Usuario) authentication.getPrincipal();
			String token = tokenService.gerarToken(authentication);
			TokenDTO tokenDTO = new TokenDTO(token, "Bearer ", usuario.getNome(), usuario.getPerfis());
			return ResponseEntity.ok(tokenDTO);

		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
