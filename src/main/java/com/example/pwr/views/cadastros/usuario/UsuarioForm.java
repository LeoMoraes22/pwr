package com.example.pwr.views.cadastros.usuario;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.pwr.controller.UsuarioController;
import com.example.pwr.exception.DefaultException;
import com.example.pwr.model.Usuario;
import com.example.pwr.model.UsuarioTipo;
import com.example.pwr.views.layout.MainLayout;
import com.example.pwr.views.utils.GenericForm;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "usuario", layout = MainLayout.class)
@PageTitle("Usuario | Escola")
@AnonymousAllowed
public class UsuarioForm extends GenericForm<Usuario, String, UsuarioController> implements HasUrlParameter<String>{
	
	private static final long serialVersionUID = 1L;

	TextField txtRa;
	
	TextField txtNome;
	
	TextField txtTelefone;
	
	TextField txtSerie;
	
	PasswordField txtSenha;
	
	EmailField txtEmail;
	
	TextField txtIdade;
	
	ComboBox<UsuarioTipo> cbxTipo;
	
	Upload upDocs;
	
	byte[] doc;
	
	MemoryBuffer buffer;
	
	Image image;
	
	Text txtNomeDoc;
	
	public UsuarioForm(@Autowired UsuarioController usuarioController) {
		super(Usuario.class, usuarioController, new Usuario());
		setTitle("Usuário");
		setSuccessRoute("usuarios");
		
		txtRa = new TextField("Username");
		txtNome = new TextField("Nome");
		txtTelefone = new TextField("Telefone");
		txtSerie = new TextField("Série");
		txtSenha = new PasswordField("Senha");
		txtEmail = new EmailField("E-mail");
		txtIdade = new TextField("Idade");
		cbxTipo = new ComboBox<UsuarioTipo>("Tipo");
		cbxTipo.setItems(UsuarioTipo.values());
		
		image = new Image();
		
		txtNomeDoc = new Text("");
		
		buffer = new MemoryBuffer();
		upDocs = new Upload(buffer);
		upDocs.setReceiver(buffer);
		upDocs.setDropAllowed(false);
		upDocs.setMaxFiles(1);
		upDocs.setAcceptedFileTypes("image/*");
		upDocs.addSucceededListener(e ->{
			try {
				InputStream inputStream = buffer.getInputStream();
				doc = IOUtils.toByteArray(inputStream);
				txtNomeDoc.setText(buffer.getFileName().toString());
				getCurrentBean().setDocumentos(doc);
				if(image.getParent() != null) {
					remove(image);
				}
				add(addDoc(doc));
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		});
		
		addInForm(txtRa, 3, "ra");
		addInForm(txtSenha, 3, "senha");
		addInForm(txtNome, 3, "nome");
		addInForm(txtTelefone, 3, "telefone");
		addInForm(txtIdade, 3, "idade");
		addInForm(txtSerie, 3, "serie");
		addInForm(txtEmail, 3, "email");
		addInForm(cbxTipo, 3, "tipo");
		
		add(upDocs, txtNomeDoc);
		
		createBinder();
		
	}

	private Image addDoc(byte[] doc) {
		String nomeArquivo = txtNomeDoc.getText().replace("[^a-zA-Z0-9]", " ");
		
		StreamResource resource = new StreamResource(nomeArquivo, () -> new ByteArrayInputStream(doc));
		image = new Image(resource, "");
		image.getStyle().set("align-self", "center");
		
		return image;
	}
	
	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		try {

			if (parameter != null) {
				Usuario usuario = controller.load(parameter);
				if (usuario != null) {
					setBean(usuario);
					if(getCurrentBean().getRa() != null) {
						txtRa.setEnabled(false);
						txtSenha.setEnabled(false);
						if (getCurrentBean().getDocumentos() != null) {
							doc = getCurrentBean().getDocumentos();
							add(addDoc(doc));
						}
						if(getCurrentBean().getTipo().toString() == UsuarioTipo.ALUNO.toString()) {
							cbxTipo.setEnabled(false);
						}
					}
				}
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
