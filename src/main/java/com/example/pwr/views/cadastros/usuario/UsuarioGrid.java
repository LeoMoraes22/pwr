package com.example.pwr.views.cadastros.usuario;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.pwr.controller.UsuarioController;
import com.example.pwr.exception.DefaultException;
import com.example.pwr.model.Usuario;
import com.example.pwr.views.layout.MainLayout;
import com.example.pwr.views.utils.GenericGrid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "usuarios", layout = MainLayout.class)
@PageTitle("Usuarios | Escola")
@PermitAll
public class UsuarioGrid  extends GenericGrid<Usuario, String, UsuarioController>{
	
	private static final long serialVersionUID = 1L;

	public UsuarioGrid(@Autowired UsuarioController usuarioController) throws DefaultException {
		super(usuarioController, Usuario.class, Usuario::getRa);
		setTitle("Usuários");
		setRotaForm("usuario");
		configuraGrid();
	}

	private void configuraGrid() {
		getGrid().addColumn(usuario -> usuario.getRa() != null ? usuario.getRa(): "").setHeader("RA");
		getGrid().addColumn(usuario -> usuario.getNome() != null ? usuario.getNome(): "").setHeader("Nome");
		getGrid().addColumn(usuario -> usuario.getTipo() != null ? usuario.getTipo().getTipo(): "").setHeader("Tipo");
		getGrid().addColumn(usuario -> usuario.getSerie() != null ? usuario.getSerie(): "").setHeader("Série");
		getGrid().addColumn(usuario -> usuario.getEmail() != null ? usuario.getEmail(): "").setHeader("E-mail");
	}

}
