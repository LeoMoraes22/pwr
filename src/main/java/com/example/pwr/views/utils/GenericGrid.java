package com.example.pwr.views.utils;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.example.pwr.controller.ControllerGeneric;
import com.example.pwr.exception.DefaultException;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public abstract class GenericGrid<T, ID, C extends ControllerGeneric<T, ID, ?>> extends VerticalLayout {
	
	protected Function<T, ID> idProvider;
	protected C controller;
	private Grid<T> grid;
	private String formRoute;
	private H2 h2Title;
	private Button btnNovo;
	private Button btnDelete;
	private TextField search;
	
	public GenericGrid(C controller, Class<T> entityClass, Function<T, ID> idProvider) throws DefaultException{
		this.controller = controller;
		this.idProvider = idProvider;
		
		setPadding(true);
		
		grid = new Grid<>(entityClass, false);
		grid.setSelectionMode(Grid.SelectionMode.MULTI);
		
		refreshList();
		
		grid.addItemClickListener(e -> {
			T item = e.getItem();
			ID itemId = idProvider.apply(item);
			getUI().ifPresent(ui -> ui.navigate(formRoute + "/" + itemId));
		});
		
		h2Title = new H2("");
		h2Title.getStyle().set("margin", "0");
		
		btnNovo = new Button("Novo", VaadinIcon.PLUS.create());
		btnNovo.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btnNovo.addClickListener(e -> adicionarNovo());
		
		btnDelete = new Button("Delete", VaadinIcon.TRASH.create());
        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnDelete.addClickListener(e -> deletaRegistro(controller, entityClass));
        
        search = new TextField();
		search.setPlaceholder("Search");
		search.setPrefixComponent(VaadinIcon.SEARCH.create());
		search.addValueChangeListener(this::searchValueChange);        
		
		add(configurarCabecalho(), grid);
	}

	private HorizontalLayout configurarCabecalho() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		HorizontalLayout botoes = new HorizontalLayout(btnNovo, btnDelete);
		botoes.setSpacing(true);
		horizontalLayout.setWidthFull();
		horizontalLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
		horizontalLayout.setAlignItems(Alignment.CENTER);
		horizontalLayout.add(h2Title,search, botoes);
		
		return horizontalLayout;
	}
	
	protected void setTitle(String title) {
		this.h2Title.setText(title);
	}
	
	protected void setRotaForm(String route) {		
		this.formRoute = route;
	}
	
	protected void refreshList() throws DefaultException {
		grid.setItems(controller.list());
	}
	
    protected void refreshList(List<T> items) throws DefaultException {
        grid.setItems(items);
    }
    
    private void searchValueChange(HasValue.ValueChangeEvent<String> event) {
    	String filter = event.getValue();
    	try {
    		List<T> filtereds = controller.list().stream().filter(item -> item.toString().contains(filter)).collect(Collectors.toList());
			refreshList(filtereds);
		} catch (DefaultException e) {
			e.printStackTrace();
		}
    }
	
	protected Grid<T> getGrid() {
		return grid;
	}
	
	protected String getSearch() {
		return search.getValue();
	}
	
	private void adicionarNovo() {
		getUI().ifPresent(ui -> ui.navigate(formRoute));
	}
	
	private void deletaRegistro(C controller2, Class<T> entityClass) {
		this.controller = controller2;
		Set<T> item = grid.getSelectedItems();
		
		if (!item.isEmpty()) {
			ConfirmDialog confirmDialog = new ConfirmDialog();
			confirmDialog.setHeader("Deletar item");
			confirmDialog.setText("Deseja realmente deletar o item selecionado?");
			confirmDialog.setCancelable(true);
			confirmDialog.setConfirmText("Deletar");
			confirmDialog.addConfirmListener(e -> {
				for (T selectedT : item) {
					controller2.delete(selectedT);
				}
				UI.getCurrent().getPage().reload();
			});
			confirmDialog.open();
		}
		
	}
}

