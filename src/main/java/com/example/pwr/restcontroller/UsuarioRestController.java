package com.example.pwr.restcontroller;

import java.util.List;

import javax.annotation.security.PermitAll;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pwr.controller.UsuarioController;
import com.example.pwr.exception.DefaultException;
import com.example.pwr.model.Usuario;

@RestController
@RequestMapping("/api/usuario")
@Validated
@PermitAll
public class UsuarioRestController {
	
	private final UsuarioController usuarioController;
	
	public UsuarioRestController(UsuarioController usuarioController) {
		this.usuarioController = usuarioController;
	}
	
	@PostMapping
	public ResponseEntity<Usuario> saveUsuario(@RequestBody Usuario usuario){
		Usuario savedUsuario = usuarioController.save(usuario);
		return new ResponseEntity<>(savedUsuario, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listUsuario(){
		List<Usuario> usuarios = usuarioController.list();
		return new ResponseEntity<>(usuarios, HttpStatus.OK);
	}

	@PutMapping("/usuarios")
	public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario){
		Usuario updateUsuario = usuarioController.update(usuario);
		return new ResponseEntity<>(updateUsuario, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{ra}")
	public ResponseEntity<Void> deleteUsuario(@PathVariable String ra){
		Usuario usuario = new Usuario();
		usuario.setRa(ra);
		usuarioController.delete(usuario);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@GetMapping("/{ra}")
	public ResponseEntity<Usuario> getUsuario(@PathVariable String ra){			
		try {
			Usuario usuario = usuarioController.load(ra);
			return new ResponseEntity<>(usuario, HttpStatus.OK);
		}catch (DefaultException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
