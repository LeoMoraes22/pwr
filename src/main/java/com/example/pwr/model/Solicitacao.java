package com.example.pwr.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "solicitacao")
public class Solicitacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "ra", referencedColumnName = "ra", foreignKey = @ForeignKey(name = "fh_usuario_solicitacao"))
	private Usuario usuario;
	
	@NotNull
	@Column(name = "data")
	private LocalDate data;
	
	@ElementCollection(targetClass = Documentos.class)
	@Enumerated(EnumType.STRING)
	@Column(name = "documentos")
	private Set<Documentos> documentos = new HashSet<>();
	
	public Solicitacao() {
		
	}

	public Solicitacao(long id, Usuario usuario, @NotNull LocalDate data, Set<Documentos> documentos) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.data = data;
		this.documentos = documentos;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Set<Documentos> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set<Documentos> documentos) {
		this.documentos = documentos;
	}

	@Override
	public String toString() {
		return "Solicitacao [id=" + id + ", usuario=" + usuario + ", data=" + data + ", documentos=" + documentos + "]";
	}
	
}
