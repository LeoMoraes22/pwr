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

import com.example.pwr.controller.AutorizacaoController;
import com.example.pwr.exception.DefaultException;
import com.example.pwr.model.Autorizacao;

@RestController
@RequestMapping("/api/Autorizacao")
@Validated
@PermitAll
public class AutorizacaoRestController {
	
	private final AutorizacaoController autorizacaoController;

	public AutorizacaoRestController(AutorizacaoController autorizacaoController) {
		this.autorizacaoController = autorizacaoController;
	}

	@PostMapping
	public ResponseEntity<Autorizacao> saveAutorizacao(@RequestBody Autorizacao autorizacao) {
		Autorizacao savedAutorizacao = autorizacaoController.save(autorizacao);
		return new ResponseEntity<>(savedAutorizacao, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Autorizacao>> listAutorizacao() {
		List<Autorizacao> autorizacaos = autorizacaoController.list();
		return new ResponseEntity<>(autorizacaos, HttpStatus.OK);
	}

	@PutMapping("/Autorizacaos")
	public ResponseEntity<Autorizacao> updateAutorizacao(@RequestBody Autorizacao autorizacao) {
		Autorizacao updateAutorizacao = autorizacaoController.update(autorizacao);
		return new ResponseEntity<>(updateAutorizacao, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAutorizacao(@PathVariable Long id) {
		Autorizacao autorizacao = new Autorizacao();
		autorizacao.setId(id);
		autorizacaoController.delete(autorizacao);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Autorizacao> getAutorizacao(@PathVariable Long id) {
		try {
			Autorizacao autorizacao = autorizacaoController.load(id);
			return new ResponseEntity<>(autorizacao, HttpStatus.OK);
		} catch (DefaultException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
