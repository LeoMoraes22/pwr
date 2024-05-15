package com.example.pwr.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	@NotEmpty
	private String ra;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private UsuarioTipo tipo;
	
	@NotEmpty
	@Column(name = "senha")
	private String senha;
	
	@NotEmpty
	@Column(name = "nome")
	private String nome;
	
	@NotEmpty
	@Column(name = "telefone")
	private String telefone;
	
	@NotEmpty
	@Column(name = "email")
	private String email;
	
	@Lob
	@Column(name = "documentos", columnDefinition = "mediumblob")
	private byte[] documentos;
	
	@NotEmpty
	@Column(name = "idade")
	private String idade;
	
	@Column(name = "serie")
	private String serie;
	
	public Usuario() {
		
	}

	public Usuario(String ra, UsuarioTipo tipo,@NotEmpty String senha, @NotEmpty String nome, @NotEmpty String telefone, @NotEmpty String email, byte[] documentos,@NotEmpty String idade, String serie) {
		super();
		this.ra = ra;
		this.tipo = tipo;
		this.senha = senha;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.documentos = documentos;
		this.idade = idade;
		this.serie = serie;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

	public UsuarioTipo getTipo() {
		return tipo;
	}

	public void setTipo(UsuarioTipo tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getDocumentos() {
		return documentos;
	}

	public void setDocumentos(byte[] documentos) {
		this.documentos = documentos;
	}
	
	

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	@Override
	public String toString() {
		return "Usuario [ra=" + ra + ", tipo=" + tipo + ", nome=" + nome + ", telefone=" + telefone + ", email=" + email + ", docuemntos="+ documentos +", idade=" + idade + ", serie=" + serie + ", senha=" + senha + "]";
	}
	
	
	

}
