package org.vaadin.neo4j.vaadin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.domain.Source;
import org.vaadin.neo4j.AppService;
import org.vaadin.neo4j.vaadin.events.SourceChangedNotifier;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.form.AbstractForm.SavedHandler;

import com.vaadin.spring.annotation.UIScope;

@UIScope
@Component
public class SourceFormController implements SavedHandler<Source>,
        AbstractForm.ResetHandler<Source> {

    @Autowired
    AppService appService;

    @Autowired
    SourceChangedNotifier eventBus;

    @Override
    public void onSave(Source entity) {
        appService.save(entity);
//        @SuppressWarnings("unused")
//		Set<Customer>customerMatches=appService.getCustomerMatches(entity);
//        eventBus.publish(EventScope.UI, this, new SourcesModified());
        eventBus.onEvent();
    }

	@Override
    public void onReset(Source entity) {
    }

}
