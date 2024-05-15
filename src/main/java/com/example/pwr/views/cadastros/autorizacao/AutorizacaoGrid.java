package com.example.pwr.views.cadastros.autorizacao;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.pwr.controller.AutorizacaoController;
import com.example.pwr.exception.DefaultException;
import com.example.pwr.model.Autorizacao;
import com.example.pwr.views.layout.MainLayout;
import com.example.pwr.views.utils.GenericGrid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "autorizacoes", layout = MainLayout.class)
@PageTitle("Autorizações | Escola")
@PermitAll
public class AutorizacaoGrid extends GenericGrid<Autorizacao, Long, AutorizacaoController> {

	private static final long serialVersionUID = 1L;

	public AutorizacaoGrid(@Autowired AutorizacaoController autorizacaoController) throws DefaultException {
		super(autorizacaoController, Autorizacao.class, Autorizacao::getId);
		setTitle("Autorizações");
		setRotaForm("autorizacao");
		configurarGrid();
	}

	private void configurarGrid() {
		getGrid().addColumn(Autorizacao::getId).setHeader("Id");
		getGrid().addColumn(Autorizacao::getResponsavel).setHeader("Responsável");
		getGrid().addColumn(Autorizacao::getMotivo).setHeader("Motivo");
		getGrid().addColumn(autorizacao -> DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(autorizacao.getHora_saida())).setHeader("Data");	
		getGrid().addColumn(autorizacao -> autorizacao.getUsuario().getNome()).setHeader("Usuário");	
	}

}
