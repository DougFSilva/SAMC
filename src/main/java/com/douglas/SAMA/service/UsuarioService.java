package com.douglas.SAMA.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.douglas.SAMA.DTO.UsuarioDTO;
import com.douglas.SAMA.model.Usuario;
import com.douglas.SAMA.repository.UsuarioRepository;
import com.douglas.SAMA.service.Exception.DataIntegratyViolationException;
import com.douglas.SAMA.service.Exception.ObjectNotEmptyException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	public Usuario create(Usuario usuario) {

		if (repository.findByUsername(usuario.getUsername()).isPresent()) {
			throw new DataIntegratyViolationException("Usuario já cadastrado na base de dados!");
		}
		usuario.setPassword(bcrypt.encode(usuario.getPassword()));
		return repository.save(usuario);

	}

	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
		;
	}

	public UsuarioDTO update(Integer id, Usuario usuario) {
		Usuario oldUsuario = findById(id);
		oldUsuario.setEmpresa(usuario.getEmpresa());
		oldUsuario.setNome(usuario.getNome());
		if (usuario.getPassword() != null) {
			oldUsuario.setPassword(bcrypt.encode(usuario.getPassword()));

		}

		oldUsuario.setUsername(usuario.getUsername());
		repository.save(oldUsuario);
		return new UsuarioDTO(oldUsuario);
	}

	public List<UsuarioDTO> findAll() {
		List<Usuario> usuarios = (List<Usuario>) repository.findAll();
		List<UsuarioDTO> usuariosDTO = new ArrayList<>();
		usuarios.forEach(usuario -> {
			usuariosDTO.add(new UsuarioDTO(usuario));
		});
		return usuariosDTO;
	}

	public Usuario findById(Integer id) {
		Optional<Usuario> usuario = repository.findById(id);
		return usuario.orElseThrow(
				() -> new ObjectNotEmptyException("Usuário com id " + id + " não encontrado na base de dados"

				));
	}

	public UsuarioDTO findByIdDTO(Integer id) {
		Optional<Usuario> usuario = repository.findById(id);
		if (usuario.isPresent()) {
			UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.get());
			return usuarioDTO;
		}
		throw new ObjectNotEmptyException("Usuário com id " + id + " não encontrado na base de dados");
	}

}
