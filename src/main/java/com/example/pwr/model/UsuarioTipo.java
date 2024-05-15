package com.example.pwr.model;

public enum UsuarioTipo {
	
	ALUNO("Aluno"),
	PROFESSOR("Professor"),
	FUNCIONARIO("Funcionário"), 
	PAIS_RESPONSAVEL("Pais/Responsável");
	
	private String tipo;
	
	UsuarioTipo(String tipo){
		this.tipo = tipo;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	@Override
	public String toString() {
		return tipo;
	}

}
