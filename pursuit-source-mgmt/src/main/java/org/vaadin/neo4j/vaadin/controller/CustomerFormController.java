package org.vaadin.neo4j.vaadin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.domain.Customer;
import org.vaadin.maddon.form.AbstractForm;
import org.vaadin.maddon.form.AbstractForm.SavedHandler;
import org.vaadin.neo4j.AppService;
import org.vaadin.neo4j.vaadin.events.CustomersModified;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;

@UIScope
@Component
public class CustomerFormController implements SavedHandler<Customer>,
        AbstractForm.ResetHandler<Customer> {

    @Autowired
    AppService customerService;

    @Autowired
    EventBus eventBus;

    @Override
    public void onSave(Customer entity) {
        customerService.save(entity);
        eventBus.publish(EventScope.UI, this, new CustomersModified());
    }

    @Override
    public void onReset(Customer entity) {
    }

}
