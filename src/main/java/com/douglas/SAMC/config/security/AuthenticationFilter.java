package com.douglas.SAMC.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.douglas.SAMC.model.Usuario;
import com.douglas.SAMC.repository.UsuarioRepository;

public class AuthenticationFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;

	// A classe filter não aceitar Injeção de dependência com @AutoWired, por tanto
	// essa injeção é efetuada através do construtor
	public AuthenticationFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;	
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = recuperarToken(request);
		boolean valido = tokenService.isTokenValido(token);
		if (valido) {
			autenticar(token);
		}

		filterChain.doFilter(request, response);
	}

	private void autenticar(String token) {
		Integer usuario_id = tokenService.getUsuario_id(token);
		Optional<Usuario> usuario = usuarioRepository.findById(usuario_id);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario.get(),
				null, usuario.get().getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization"); // Busca por Authorization no hearder da requisição
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;

		}
		return token.substring(7, token.length()); // Retorna somente o token, excluindo "Bearer "
	}

}
