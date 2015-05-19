package org.vaadin.neo4j.vaadin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.domain.Person;
import org.vaadin.neo4j.AppService;
import org.vaadin.neo4j.vaadin.events.PersonsChangedNotifier;
import org.vaadin.neo4j.vaadin.events.ProjectsModified;
import org.vaadin.viritin.MBeanFieldGroup;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;


@org.springframework.stereotype.Component
@UIScope
public class PersonForm extends AbstractForm<Person> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5430540403488763556L;

	TextField name = new MTextField("Name");
	TextField userName = new MTextField("User Name");
	PasswordField passWord = new PasswordField("Password");
 

    @Autowired
    PersonFormController personFormController;

    @Autowired
    AppService service;

    @Autowired
    PersonsChangedNotifier eventBus;

    private Window window;

    @PostConstruct
    void init() {

        setSavedHandler(personFormController);
        setResetHandler(personFormController);

//           eventBus.subscribe(new EventBusListener<ProjectsModified>() {
//
//            @Override
//            public void onEvent(
//                    org.vaadin.spring.events.Event<ProjectsModified> event) {
//            }
//        });

    }

    @Override
    protected Component createContent() {
        return new MVerticalLayout(
                new FormLayout(
                        name,
                        userName,
                        passWord
                ),
                getToolbar()
        );
    }

    public PersonForm() {
    }

    @Override
    public MBeanFieldGroup<Person> setEntity(Person entity) {
        super.setEntity(entity);
        showInWindow();
        return new MBeanFieldGroup<Person>(Person.class);

    }

    private void showInWindow() {
        window = new Window("Edit person", this);
        window.setModal(true);
        window.setClosable(false);
        UI.getCurrent().addWindow(window);
    }

    @Override
    protected void save(Button.ClickEvent e) {
    	super.save(e);
        window.close();
    }

    @Override
    protected void reset(Button.ClickEvent e) {
        super.reset(e);
        window.close();
    }

}
