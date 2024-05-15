package com.example.pwr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.pwr.model.Usuario;
import com.example.pwr.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repository
			    .findById(username)
			    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
	
		return org.springframework.security.core.userdetails.User
			.withUsername(usuario.getRa())
			.password("{bcrypt}" + usuario.getSenha())
			.roles(usuario.getTipo().name())
			.build();
	}

}
