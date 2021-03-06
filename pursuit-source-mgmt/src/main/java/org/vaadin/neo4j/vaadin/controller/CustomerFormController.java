package org.vaadin.neo4j.vaadin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.domain.Customer;
import org.vaadin.neo4j.AppService;
import org.vaadin.neo4j.vaadin.events.CustomerChangedNotifier;
import org.vaadin.neo4j.vaadin.events.CustomersModified;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.form.AbstractForm.SavedHandler;

import com.google.gwt.event.shared.EventBus;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@Component
public class CustomerFormController implements SavedHandler<Customer>,
        AbstractForm.ResetHandler<Customer> {

    @Autowired
    AppService customerService;

    @Autowired
    CustomerChangedNotifier eventBus;

    @Override
    public void onSave(Customer entity) {
        customerService.save(entity);
        eventBus.onEvent();
    }

    @Override
    public void onReset(Customer entity) {
    }

}
