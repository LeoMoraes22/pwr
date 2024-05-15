package com.example.pwr.views.cadastros.solicitacao;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.pwr.controller.SolicitacaoController;
import com.example.pwr.controller.UsuarioController;
import com.example.pwr.exception.DefaultException;
import com.example.pwr.model.Documentos;
import com.example.pwr.model.Solicitacao;
import com.example.pwr.model.Usuario;
import com.example.pwr.views.layout.MainLayout;
import com.example.pwr.views.utils.GenericForm;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "solicitacao", layout = MainLayout.class)
@PageTitle("Solicitação | Escola")
@PermitAll
public class SolicitacaoForm extends GenericForm<Solicitacao, Long, SolicitacaoController> implements HasUrlParameter<Long> {

	private static final long serialVersionUID = 1L;

	private DatePicker.DatePickerI18n dtDataFormat;
	
	private DatePicker dtData;
	
	private MultiSelectComboBox<Documentos> cbxDocumentos;
	
	private ComboBox<Usuario> cbxUsuario;
	
	private SolicitacaoController solicitacaoController;
	
	private UsuarioController usuarioController;
	
	public SolicitacaoForm(@Autowired SolicitacaoController solicitacaoController, @Autowired UsuarioController usuarioController) {
		super(Solicitacao.class, solicitacaoController, new Solicitacao());
		
		setTitle("Solicitação");
		setSuccessRoute("solicitacoes");
		
		this.solicitacaoController = solicitacaoController;
		this.usuarioController = usuarioController;
		
		dtDataFormat = new DatePicker.DatePickerI18n();
		dtDataFormat.setDateFormat("dd/MM/yyyy");	
		dtData = new DatePicker("Data");
		dtData.setI18n(dtDataFormat);	
		cbxUsuario = new ComboBox<Usuario>("Usuário");
		cbxUsuario.setItems(usuarioController.list());
		cbxUsuario.setItemLabelGenerator(Usuario::getNome);
		
		cbxDocumentos = new MultiSelectComboBox<Documentos>("Documentos");
		cbxDocumentos.setItems(Documentos.values());
		
		addInForm(dtData, 3, "data");
		addInForm(cbxDocumentos, 3, "documentos");
		addInForm(cbxUsuario, 3, "usuario");
		
		createBinder();
	}

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter Long parameter) {
		try {

			if (parameter != null) {
				Solicitacao solicitacao = controller.load(parameter);
				
				if (solicitacao != null) {
					setBean(solicitacao);					
				}
			} else {
				setBean(new Solicitacao());
			}

		} catch (DefaultException e) {
			mostrarAviso();
		}
		createBinder();
	}
	
	private void mostrarAviso() {
		ConfirmDialog dialogMessage = new ConfirmDialog();
		dialogMessage.setHeader("Registro não encontrado");
		dialogMessage.setText(new Html("<p>Clique no botão para visualizar todos os registros</p>"));

		dialogMessage.setConfirmText("Redirecionar");

		dialogMessage.addConfirmListener(ev -> {
			voltar();
			dialogMessage.close();
		});
		dialogMessage.open();
	}
}
