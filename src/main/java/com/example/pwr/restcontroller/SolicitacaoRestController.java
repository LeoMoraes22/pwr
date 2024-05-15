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

import com.example.pwr.controller.SolicitacaoController;
import com.example.pwr.exception.DefaultException;
import com.example.pwr.model.Solicitacao;

@RestController
@RequestMapping("/api/solicitacao")
@Validated
@PermitAll
public class SolicitacaoRestController {

	private final SolicitacaoController solicitacaoController;

	public SolicitacaoRestController(SolicitacaoController solicitacaoController) {
		this.solicitacaoController = solicitacaoController;
	}

	@PostMapping
	public ResponseEntity<Solicitacao> saveSolicitacao(@RequestBody Solicitacao solicitacao) {
		Solicitacao savedSolicitacao = solicitacaoController.save(solicitacao);
		return new ResponseEntity<>(savedSolicitacao, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Solicitacao>> listSolicitacao() {
		List<Solicitacao> solicitacaos = solicitacaoController.list();
		return new ResponseEntity<>(solicitacaos, HttpStatus.OK);
	}

	@PutMapping("/Solicitacaos")
	public ResponseEntity<Solicitacao> updateSolicitacao(@RequestBody Solicitacao solicitacao) {
		Solicitacao updateSolicitacao = solicitacaoController.update(solicitacao);
		return new ResponseEntity<>(updateSolicitacao, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSolicitacao(@PathVariable Long id) {
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setId(id);
		solicitacaoController.delete(solicitacao);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Solicitacao> getSolicitacao(@PathVariable Long id) {
		try {
			Solicitacao solicitacao = solicitacaoController.load(id);
			return new ResponseEntity<>(solicitacao, HttpStatus.OK);
		} catch (DefaultException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
