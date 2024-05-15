package com.example.pwr.model;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "autorizacao")
public class Autorizacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank
	@Column(name = "motivo")
	private String motivo;
	
	@NotNull
	@Column(name = "hora_saida")
	private LocalDateTime hora_saida;
	
	@NotBlank
	@Column(name = "responsavel")
	private String responsavel;
	
	@ManyToOne
	@JoinColumn(name = "ra", referencedColumnName = "ra", foreignKey = @ForeignKey(name = "fh_usuario_autorizacao"))
	private Usuario usuario;

	public Autorizacao() {
		
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Autorizacao(long id, @NotBlank String motivo, @NotBlank LocalDateTime hora_saida,
			@NotBlank String responsavel) {
		super();
		this.id = id;
		this.motivo = motivo;
		this.hora_saida = hora_saida;
		this.responsavel = responsavel;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public LocalDateTime getHora_saida() {
		return hora_saida;
	}

	public void setHora_saida(LocalDateTime hora_saida) {
		this.hora_saida = hora_saida;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	@Override
	public String toString() {
		return "Autorizacao [id=" + id + ", motivo=" + motivo + ", hora_saida=" + hora_saida + ", responsavel="
				+ responsavel + "]";
	}
	
	
}
