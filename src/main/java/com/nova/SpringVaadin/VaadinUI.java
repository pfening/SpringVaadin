/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nova.SpringVaadin;

import com.vaadin.data.Binder;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringUI
public class VaadinUI extends UI {

    @Autowired
    private CustomerService service;

    private Customer customer;
    private Binder<Customer> binder = new Binder<>(Customer.class);

    private Grid<Customer> grid = new Grid(Customer.class);
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    
    private Button save = new Button("Update", e -> saveCustomer());
    private Button create = new Button("Create", e -> createCustomer());
    private Button delete = new Button("Delete", e -> deleteCustomer());

    @Override
    protected void init(VaadinRequest request) {
        final HorizontalLayout layout = new HorizontalLayout(); 
        
        updateGrid();
        grid.setColumns("firstName", "lastName");
        grid.addSelectionListener(e -> updateForm());

        binder.bindInstanceFields(this);
        VerticalLayout col1 = new VerticalLayout(grid);
        HorizontalLayout line = new HorizontalLayout(firstName, lastName);
        HorizontalLayout lineb = new HorizontalLayout(save, create, delete);
        VerticalLayout col2 = new VerticalLayout(line, lineb);
        layout.addComponents(col1, col2);
        setContent(layout);
    }

    private void updateGrid() {
        List<Customer> customers = service.findAll();
        grid.setItems(customers);
        setFormVisible(true);
    }

    private void updateForm() {  
        customer = grid.asSingleSelect().getValue();
        binder.setBean(customer);
        setFormVisible(true);
    }
    
    private void setFormVisible(boolean visible) {
        firstName.setVisible(visible);
        lastName.setVisible(visible);
        save.setVisible(visible);
    }

    private void saveCustomer() {
        service.update(customer);
        updateGrid();
    }
    
    private void createCustomer() {
        service.create(customer);
        updateGrid();
    }
    
    private void deleteCustomer() {
        service.delete(customer);
        updateGrid();
    }
}