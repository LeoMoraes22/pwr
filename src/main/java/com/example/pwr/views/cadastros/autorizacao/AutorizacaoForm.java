package com.example.pwr.views.cadastros.autorizacao;

import java.util.List;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.pwr.controller.AutorizacaoController;
import com.example.pwr.controller.UsuarioController;
import com.example.pwr.exception.DefaultException;
import com.example.pwr.model.Autorizacao;
import com.example.pwr.model.Usuario;
import com.example.pwr.security.SecurityService;
import com.example.pwr.views.layout.MainLayout;
import com.example.pwr.views.utils.GenericForm;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "autorizacao", layout = MainLayout.class)
@PageTitle("Autorização | Escola")
@PermitAll
public class AutorizacaoForm extends GenericForm<Autorizacao, Long, AutorizacaoController> implements HasUrlParameter<Long>{
	
	private static final long serialVersionUID = 1L;

	private TextField txtMotivo;
	
	private TextField txtResponsavel;
	
	private ComboBox<Usuario> cbxUsuario;
	
	private DatePicker.DatePickerI18n dtDataFormat;
	
	private DateTimePicker dtData;
	
	SecurityService securityService;
	
	UsuarioController usuarioController;

	public AutorizacaoForm(@Autowired AutorizacaoController autorizacaoController, @Autowired SecurityService securityService, @Autowired UsuarioController usuarioController) {
		super(Autorizacao.class, autorizacaoController, new Autorizacao());
		
		setTitle("Autorização");
		setSuccessRoute("autorizacoes");
		
		this.securityService = securityService;
		this.usuarioController = usuarioController;
		
		txtMotivo = new TextField("Motivo");
		txtResponsavel = new TextField("Responsável");
		dtDataFormat = new DatePicker.DatePickerI18n();
		dtDataFormat.setDateFormat("dd/MM/yyyy");	
		dtData = new DateTimePicker("Data");
		dtData.setDatePickerI18n(dtDataFormat);	
		cbxUsuario = new ComboBox<Usuario>("Usuário");
		cbxUsuario.setItemLabelGenerator(Usuario::getNome);
		
		addInForm(txtMotivo, 3, "motivo");
		addInForm(txtResponsavel, 3, "responsavel");
		addInForm(dtData, 3, "hora_saida");
		addInForm(cbxUsuario, 3, "usuario");
		
		createBinder();
		
		preencherCbUsuario();
	}
	
	private void preencherCbUsuario() {
        List<Usuario> usuarios = securityService.getCurrentUser().stream().toList();
        cbxUsuario.setItems(usuarios);
    }

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter Long parameter) {
		try {

			if (parameter != null) {
				setBean(controller.load(parameter)); 
			} else {
				setBean(new Autorizacao());
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
