package org.vaadin.neo4j.vaadin.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.domain.Customer;
import org.vaadin.domain.Source;
import org.vaadin.maddon.form.AbstractForm;
import org.vaadin.maddon.form.AbstractForm.SavedHandler;
import org.vaadin.neo4j.AppService;
import org.vaadin.neo4j.vaadin.events.SourcesModified;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;

@UIScope
@Component
public class SourceFormController implements SavedHandler<Source>,
        AbstractForm.ResetHandler<Source> {

    @Autowired
    AppService appService;

    @Autowired
    EventBus eventBus;

    @Override
    public void onSave(Source entity) {
        appService.save(entity);
//        @SuppressWarnings("unused")
//		Set<Customer>customerMatches=appService.getCustomerMatches(entity);
        eventBus.publish(EventScope.UI, this, new SourcesModified());
    }

	@Override
    public void onReset(Source entity) {
    }

}
