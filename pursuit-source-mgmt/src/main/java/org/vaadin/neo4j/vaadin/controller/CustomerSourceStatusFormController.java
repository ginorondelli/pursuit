package org.vaadin.neo4j.vaadin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.domain.Customer;
import org.vaadin.domain.CustomerSourceStatus;
import org.vaadin.neo4j.AppService;
import org.vaadin.neo4j.vaadin.events.CustomerSourceStatusChangedNotifier;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.form.AbstractForm.SavedHandler;

import com.vaadin.spring.annotation.UIScope;

@UIScope
@Component
public class CustomerSourceStatusFormController implements SavedHandler<CustomerSourceStatus>,
        AbstractForm.ResetHandler<CustomerSourceStatus> {

    @Autowired
    AppService customerSourceStatusService;

    @Autowired
    CustomerSourceStatusChangedNotifier eventBus;

    @Override
    public void onSave(CustomerSourceStatus entity) {
    	customerSourceStatusService.save(entity);
        eventBus.onEvent();
    }

    @Override
    public void onReset(CustomerSourceStatus entity) {
    }

}
