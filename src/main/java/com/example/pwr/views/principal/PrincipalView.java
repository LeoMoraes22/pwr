package com.example.pwr.views.principal;

import com.example.pwr.views.layout.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Escola | Homme")
@AnonymousAllowed
public class PrincipalView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

}
