package com.example.pwr.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.pwr.model.Usuario;
import com.example.pwr.model.UsuarioTipo;
import com.example.pwr.repository.UsuarioRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

@Component
public class SecurityService {

    private static final String LOGOUT_SUCCESS_URL = "/login";

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Optional<Authentication> getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return Optional.ofNullable(context.getAuthentication())
                .filter(authentication -> !(authentication instanceof AnonymousAuthenticationToken));
    }

    public Optional<Usuario> getCurrentUser() {
        Optional<Authentication> authentication = getAuthentication();

        return authentication.flatMap(value -> usuarioRepository.findById(value.getName()));
    }

    public boolean isUserAuthenticated() {
        Optional<Authentication> authentication = getAuthentication();
        return authentication.isPresent() && !(authentication.get() instanceof AnonymousAuthenticationToken);
    }

    public boolean isUserFuncionario() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && getCurrentUser().isPresent()) {
            return getCurrentUser().get().getTipo().equals(UsuarioTipo.FUNCIONARIO);
        }

        return false;
    }
    
    public boolean isUserAluno() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && getCurrentUser().isPresent()) {
            return getCurrentUser().get().getTipo().equals(UsuarioTipo.ALUNO);
        }

        return false;
    }
    
    public boolean isUserResponsavel() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && getCurrentUser().isPresent()) {
            return getCurrentUser().get().getTipo().equals(UsuarioTipo.PAIS_RESPONSAVEL);
        }

        return false;
    }
    
    public boolean isUserProfessor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && getCurrentUser().isPresent()) {
            return getCurrentUser().get().getTipo().equals(UsuarioTipo.PROFESSOR);
        }

        return false;
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }

}
