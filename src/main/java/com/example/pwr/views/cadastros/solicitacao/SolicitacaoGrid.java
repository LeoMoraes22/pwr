package com.example.pwr.views.cadastros.solicitacao;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.pwr.controller.SolicitacaoController;
import com.example.pwr.exception.DefaultException;
import com.example.pwr.model.Documentos;
import com.example.pwr.model.Solicitacao;
import com.example.pwr.views.layout.MainLayout;
import com.example.pwr.views.utils.GenericGrid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "solicitacoes", layout = MainLayout.class)
@PageTitle("Solicitações| Escola")
@PermitAll
public class SolicitacaoGrid extends GenericGrid<Solicitacao, Long, SolicitacaoController>  {

	private static final long serialVersionUID = 1L;

	public SolicitacaoGrid(@Autowired SolicitacaoController solicitacaoController) throws DefaultException {
		super(solicitacaoController, Solicitacao.class, Solicitacao::getId);
		setTitle("Solicitações");
		setRotaForm("solicitacao");
		configurarGrid();
	}

	private void configurarGrid() {
		getGrid().addColumn(Solicitacao::getId).setHeader("Id");
		getGrid().addColumn(solicitacao -> solicitacao.getUsuario().getNome()).setHeader("Usuário");
		getGrid().addColumn(solicitacao -> DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(solicitacao.getData())).setHeader("Data");
		getGrid().addColumn(solicitacao -> {
				List<Documentos> documentos = new ArrayList<>(solicitacao.getDocumentos());
				List<String> documentosNames = documentos.stream().map(Documentos::toString).collect(Collectors.toList());
				return String.join(", ", documentosNames);
		}).setHeader("Documentos");
	}
}
