package com.example.pwr.controller;

import org.springframework.stereotype.Service;

import com.example.pwr.exception.DefaultException;
import com.example.pwr.model.Usuario;
import com.example.pwr.repository.UsuarioRepository;

@Service
public class UsuarioController extends ControllerGeneric<Usuario, String, UsuarioRepository> {
	
	public UsuarioController(UsuarioRepository usuarioRepository) {
		this.repository = usuarioRepository;
	}
	
	@Override
	protected void validate(Usuario entity, Mode mode) {
		super.validate(entity, mode);
		
		switch (mode) {
			case SAVE:
				System.out.println("save");
				break;
			case UPDATE:
				System.out.println("update");
				if(!repository.existsByRa(entity.getRa())) new DefaultException("Usuário não existe");
				break;
			case DELETE:
				System.out.println("delete");
				if(!repository.existsByRa(entity.getRa())) new DefaultException("Usuário não existe");
				break;
		}
	}
}
