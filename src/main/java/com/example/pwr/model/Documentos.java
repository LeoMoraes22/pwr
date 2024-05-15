package com.example.pwr.model;

public enum Documentos {
	
	DECLARACAO_MATRICULA("Declaração de Matrícula"),
	BOLETIM("Boletim"),
	REMATRICULA("Rematrícula"), 
	SALA_DE_VIDEO("Sala de Vídeo"), 
	LAB_INFORMATICA("Laboratório de Informática");
	
	
	Documentos() {
	}
	
	private String doc;
	
	Documentos(String doc){
		this.doc = doc;
	}
	
	public String getDoc() {
		return doc;
	}

	
	@Override
	public String toString() {
		return doc;
	}
}
