package org.vaadin.neo4j.vaadin;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.domain.Person;
import org.vaadin.neo4j.AppService;
import org.vaadin.neo4j.vaadin.events.PersonsChangedNotifier;
import org.vaadin.neo4j.vaadin.events.PersonsModified;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.form.AbstractForm.SavedHandler;

import com.vaadin.spring.annotation.UIScope;

@UIScope
@Component
public class PersonFormController implements SavedHandler<Person>,
        AbstractForm.ResetHandler<Person> {

    @Autowired
    AppService appService;

    @Autowired
    PersonsChangedNotifier eventBus;

    @Override
    public void onSave(Person entity) {
    	StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
    	entity.setPassWord(passwordEncryptor.encryptPassword(entity.getPassWord()));
    	appService.save(entity);
        eventBus.onEvent();
//    	eventBus.publish(EventScope.UI, this, new PersonsModified());
    }

    @Override
    public void onReset(Person entity) {
    }

}
