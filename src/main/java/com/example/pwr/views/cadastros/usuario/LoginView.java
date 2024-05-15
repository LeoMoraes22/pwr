package com.example.pwr.views.cadastros.usuario;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "/login")
@PageTitle("Escola | Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private static final long serialVersionUID = 1L;

    private LoginForm login;

    public LoginView() {
        setPadding(true);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        LoginI18n i18n = LoginI18n.createDefault();

        LoginI18n.Form formLogin = i18n.getForm();

        formLogin.setTitle("Login | Escola");
        formLogin.setUsername("Username");
        formLogin.setPassword("Senha");
        formLogin.setSubmit("Login");
        formLogin.setForgotPassword("Cadastrar-se");
        i18n.setForm(formLogin);

        LoginI18n.ErrorMessage errorMessage = i18n.getErrorMessage();
        errorMessage.setTitle("Erro");
        errorMessage.setMessage("Erro");
        i18n.setErrorMessage(errorMessage);

        login = new LoginForm();
        login.getStyle().set("background-color", "var(--lumo-contrast-5pct)")
                .set("display", "flex").set("justify-content", "center")
                .set("padding", "var(--lumo-space-l)");
        login.setI18n(i18n);

        login.addForgotPasswordListener(e -> {
            getUI().ifPresent(ui -> ui.navigate("/cadastrar"));
        });
        
        login.addLoginListener(e ->{
        	getUI().ifPresent(ui -> ui.navigate(""));
        });

        login.setAction("login");

        add(login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            login.setError(true);
        }
    }

}
