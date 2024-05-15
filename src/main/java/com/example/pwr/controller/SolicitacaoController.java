package com.example.pwr.controller;

import org.springframework.stereotype.Service;

import com.example.pwr.exception.DefaultException;
import com.example.pwr.model.Solicitacao;
import com.example.pwr.repository.SolicitacaoRepository;

@Service
public class SolicitacaoController extends ControllerGeneric<Solicitacao, Long, SolicitacaoRepository> {
	
	public SolicitacaoController(SolicitacaoRepository solicitacaoRepository) {
		this.repository = solicitacaoRepository;
	}
	
	@Override
	protected void validate(Solicitacao entity, Mode mode) {
		super.validate(entity, mode);
		
		switch (mode) {
			case SAVE:
				System.out.println("save");
				break;
			case UPDATE:
				System.out.println("update");
				if(!repository.existsById(entity.getId())) new DefaultException("Solicitação não existe");
				break;
			case DELETE:
				System.out.println("delete");
				if(!repository.existsById(entity.getId())) new DefaultException("Solicitação não existe");
				break;
		}
	}
} 