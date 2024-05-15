package com.example.pwr.views.cadastros.usuario;

import java.io.Serial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.pwr.controller.UsuarioController;
import com.example.pwr.model.Usuario;
import com.example.pwr.model.UsuarioTipo;
import com.example.pwr.views.layout.PrincipalLayout;
import com.example.pwr.views.utils.NotificationComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "/cadastrar", layout = PrincipalLayout.class)
@PageTitle("Escola | Cadastro")
@AnonymousAllowed
public class CadastroView extends VerticalLayout{

	@Serial
    private static final long serialVersionUID = 1L;

    private final Binder<Usuario> binder = new Binder<>(Usuario.class);

    Usuario currentBean;

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public CadastroView(@Autowired UsuarioController controller) {

        currentBean = new Usuario();

        setAlignItems(Alignment.CENTER);
        Div loginContainer = new Div();
        loginContainer.setMaxWidth("500px");

        TextField ra = new TextField("Username");
        ra.setWidthFull();

        PasswordField senha = new PasswordField("Senha");
        senha.setWidthFull();

        Button registerButton = new Button("Cadastrar");
        registerButton.getStyle().set("margin-top", "15px");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.setWidthFull();

        TextField nome = new TextField("Nome");
        nome.setWidthFull();
        
        EmailField email = new EmailField("E-mail");
        email.setWidthFull();
        
        TextField telefone = new TextField("Telefone");
        telefone.setWidthFull();
        
        TextField idade = new TextField("Idade");
        idade.setWidthFull();


        binder.readBean(currentBean);
        binder.forField(nome).asRequired("Nome é obrigatório").bind("nome");
        binder.forField(ra).asRequired("Username é obrigatório").bind("ra");
        binder.forField(senha).asRequired("Senha é obrigatório").bind("senha");
        binder.forField(email).asRequired("E-mail é obrigatório").bind("email");
        binder.forField(telefone).asRequired("Telefone é obrigatório").bind("telefone");
        binder.forField(idade).asRequired("Idade é obrigatório").bind("idade");

        currentBean.setTipo(UsuarioTipo.PAIS_RESPONSAVEL);

        registerButton.addClickListener(event -> {

            NotificationComponent notification = null;

            try {

                binder.writeBean(currentBean);
                currentBean.setSenha(PASSWORD_ENCODER.encode(currentBean.getSenha()));
                controller.save(currentBean);

                notification = new NotificationComponent("Cadastro realizado com sucesso",
                        "success", "top_end");
                add(notification);

                String rota = "/usuario/" + currentBean.getRa();

                getUI().ifPresent(ui -> ui.navigate(""));
            } catch (ValidationException e) {

                notification = new NotificationComponent("Preencha todos os campos", "error",
                        "top_end");

                add(notification);
                e.printStackTrace();
            } catch (Exception e) {
                notification = new NotificationComponent(e.getMessage(), "error", "top_end");

                add(notification);
                e.printStackTrace();
            }

        });

        loginContainer.add(ra, senha, nome, email , telefone, idade, registerButton);

        add(loginContainer);

    }
}
