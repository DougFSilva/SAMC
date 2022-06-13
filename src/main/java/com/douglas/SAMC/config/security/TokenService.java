package com.douglas.SAMC.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.douglas.SAMC.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${jwt.expiration}")
	private long expiration;

	@Value("${jwt.secret}")
	private String secret;

	public String gerarToken(Authentication authentication) {
		Usuario usuario = (Usuario) authentication.getPrincipal();// Busca o usuário autenticado
		return Jwts.builder().setIssuer("API de Gerenciamento e controle de acesso escolar")
				.setSubject(usuario.getId().toString()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret.getBytes()).parseClaimsJws(token);// Verifica se o token é válido
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public Integer getUsuario_id(String token) {
		// Cria um objeto com os dados dentro do token
		Claims claims = Jwts.parser().setSigningKey(this.secret.getBytes()).parseClaimsJws(token).getBody();
		return Integer.parseInt(claims.getSubject()); // Retorna o subject (este que foi declarado no metodo geraToken,
														// nesse cado o id do usuario)
	}
}
