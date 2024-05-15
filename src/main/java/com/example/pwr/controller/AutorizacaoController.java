package com.example.pwr.controller;

import org.springframework.stereotype.Service;

import com.example.pwr.exception.DefaultException;
import com.example.pwr.model.Autorizacao;
import com.example.pwr.repository.AutorizacaoRepository;

@Service
public class AutorizacaoController extends ControllerGeneric<Autorizacao, Long, AutorizacaoRepository> {
	
	public AutorizacaoController(AutorizacaoRepository autorizacaoRepository) {
		this.repository = autorizacaoRepository;
	}
	
	@Override
	protected void validate(Autorizacao entity, Mode mode) {
		super.validate(entity, mode);
		
		switch (mode) {
			case SAVE:
				System.out.println("save");
				break;
			case UPDATE:
				System.out.println("update");
				if(!repository.existsById(entity.getId())) new DefaultException("Autorização não existe");
				break;
			case DELETE:
				System.out.println("delete");
				if(!repository.existsById(entity.getId())) new DefaultException("Autorização não existe");
				break;
		}
	}
} 
