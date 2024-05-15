package com.example.pwr.views.layout;

import java.io.Serial;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.pwr.model.Usuario;
import com.example.pwr.security.SecurityService;
import com.example.pwr.views.cadastros.autorizacao.AutorizacaoGrid;
import com.example.pwr.views.cadastros.solicitacao.SolicitacaoGrid;
import com.example.pwr.views.cadastros.usuario.UsuarioGrid;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

	@Serial
    private static final long serialVersionUID = 1L;
	
	boolean isFuncionario;
	
	boolean isAluno;
	
	boolean isResponsavel;
	
	boolean isProfessor;
	
	String user;
	
    public MainLayout(@Autowired SecurityService securityService) {
    	
    	if (!securityService.isUserAuthenticated()) {
    		UI.getCurrent().navigate("/login");
    		getUI().ifPresent(ui -> ui.getPage().reload());
    		return;
    	}

        DrawerToggle toggle = new DrawerToggle();
        
        isFuncionario = securityService.isUserFuncionario();
        
        isAluno = securityService.isUserAluno();
        
        isResponsavel = securityService.isUserResponsavel();
        
        isProfessor = securityService.isUserProfessor();
        
        user = securityService.getCurrentUser().get().getRa();
       
        H1 title = new H1("Escola");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tabs tabs = getTabs(isFuncionario, isAluno, isResponsavel, isProfessor);
        
        Button btnPainel = new Button("Painel");
        
        Optional<Usuario> userOptional = securityService.getCurrentUser();
        
        if(userOptional.isPresent()) {
        	btnPainel = new Button(
        			userOptional.get().getTipo().toString().toLowerCase() + ": " + userOptional.get().getNome(), 
        			new Icon(VaadinIcon.USER));
        	btnPainel.getStyle().set("margin", "0 10px 0 10px");
        	btnPainel.addClickListener(e -> {
        		getUI().ifPresent(ui -> ui.navigate("usuario/" + userOptional.get().getRa()));
        	});
        	
        }
        
        Button btnLogout = new Button("Logout");
        btnLogout.addClickListener(e -> {
        	securityService.logout();
        });
        
        HorizontalLayout layout = new HorizontalLayout(toggle, title);
        layout.setAlignItems(Alignment.CENTER);
        
        HorizontalLayout botoes = new HorizontalLayout(btnPainel, btnLogout);
        botoes.setAlignItems(Alignment.CENTER);
             
        HorizontalLayout horizontalLayout = new HorizontalLayout(layout, botoes);
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.setWidthFull();
               
        
        addToDrawer(tabs);
        addToNavbar(horizontalLayout);
    }
    
	private Tabs getTabs(boolean isFuncionario, boolean isAluno, boolean isResponsavel, boolean isProfessor) {
        Tabs tabs = new Tabs();
        
        if (isFuncionario) {
        	tabs.add(createTab(VaadinIcon.ACADEMY_CAP, "Usuário", UsuarioGrid.class),
           		 createTab(VaadinIcon.ARCHIVE, "Solicitações", SolicitacaoGrid.class),
           		 createTab(VaadinIcon.FILE_TEXT, "Autorizações", AutorizacaoGrid.class)
           		 );
        }else if (isResponsavel) {
        	tabs.add(createTab(VaadinIcon.FILE_TEXT, "Autorizações", AutorizacaoGrid.class));
		}else if (isAluno) {
			tabs.add(createTab(VaadinIcon.ACADEMY_CAP, "Usuário", UsuarioGrid.class));
		}else if (isProfessor) {
			tabs.add(createTab(VaadinIcon.ACADEMY_CAP, "Usuário", UsuarioGrid.class),
	           		 createTab(VaadinIcon.ARCHIVE, "Solicitações", SolicitacaoGrid.class)
	           		 );
		}
                
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }
    
    private Tab createTab(VaadinIcon viewIcon, String viewName, Class viewClass) {
        Icon icon = viewIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(icon, new Span(viewName));
        
        link.setRoute(viewClass);
        link.setTabIndex(-1);

        return new Tab(link);
    }

}

