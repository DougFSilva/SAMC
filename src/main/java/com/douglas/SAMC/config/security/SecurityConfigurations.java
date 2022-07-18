package com.douglas.SAMC.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.douglas.SAMC.repository.UsuarioRepository;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		http.authorizeRequests().antMatchers("/resources/**").permitAll()
				.antMatchers(HttpMethod.GET, "/acesso/*").permitAll()
				.antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
				.antMatchers(HttpMethod.GET, "/v3/**").permitAll()
				.antMatchers(HttpMethod.GET, "/actuator/**").permitAll() // Exibe informações sensíveis, desabilitar para produção
				.antMatchers(HttpMethod.POST, "/auth").permitAll() // Libera acesso sem autenticação para POST no endPoint /auth
				.anyRequest().authenticated() // Faz com que qualquer outra request necessite de autenticação
				.and().csrf().disable()// Desabilita csrf pois será utilizado jwt, protegendo a aplicação
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Garante que não seja criada sessões
				.and().addFilterBefore(new AuthenticationFilter(tokenService, usuarioRepository), // Chama o filtro criado (AuthenticationFilter)
						UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
