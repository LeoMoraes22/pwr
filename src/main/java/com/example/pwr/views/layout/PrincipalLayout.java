package com.example.pwr.views.layout;

import java.io.Serial;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.pwr.model.Usuario;
import com.example.pwr.security.SecurityService;
import com.example.pwr.views.cadastros.usuario.LoginView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

public class PrincipalLayout extends AppLayout{

	@Serial
	private static final long serialVersionUID = 1L;
	
	private Dialog dialog;
	
	private LoginView loginDialog;
	
	private Button btnLogin;
	private Button btnCadastrar;
	private Button btnLogout;
	
	public PrincipalLayout(@Autowired SecurityService securityService) {
        H1 title = new H1("Escola");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)").set("margin", "0")
                .set("position", "absolute");
        title.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("")));

        Tabs tabs = getTabs();
        
        dialog = new Dialog();
        loginDialog = new LoginView();
        btnCadastrar = new Button("Cadastrar");
        btnLogout = new Button("Logout"); 
        
        
        btnLogout.addClickListener(e -> securityService.logout());
        
        btnLogin = new Button("Login");
        btnLogin.addClickListener(e -> {
        	dialog.add(loginDialog);
        	dialog.open();
        });
        
        btnCadastrar.addClickListener(e ->{
        	getUI().ifPresent(ui -> ui.navigate("cadastrar"));
        });
        
        HorizontalLayout botoes = new HorizontalLayout();
        
        Optional<Usuario> userOptional = securityService.getCurrentUser();
        
        if (userOptional.isEmpty()) {
        	botoes.add(btnLogin, btnCadastrar);
        }else {
			Button btnIcon = new Button(userOptional.get().getNome(), new Icon(VaadinIcon.USER));
			
			btnIcon.addClickListener(e ->{
				getUI().ifPresent(ui -> ui.navigate("/usuario/" + userOptional.get().getRa()));
			});
			
			botoes.add(btnIcon, btnLogout);
		}
        
        HorizontalLayout layout = new HorizontalLayout(title, tabs, botoes);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setWidthFull();
        
        addToNavbar(layout);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.getStyle().set("margin", "auto");
        tabs.add(createTab("Principal"));
        return tabs;
    }

    private Tab createTab(String viewName) {
        RouterLink link = new RouterLink();
        link.add(viewName);
        // Demo has no routes
        //link.setRoute(viewClass);
        link.setTabIndex(-1);

        return new Tab(link);
    }
}
