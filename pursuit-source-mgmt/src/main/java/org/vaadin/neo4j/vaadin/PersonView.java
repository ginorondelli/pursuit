package org.vaadin.neo4j.vaadin;

import org.vaadin.neo4j.vaadin.events.PersonsModified;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.domain.Person;
import org.vaadin.domain.Source;
import org.vaadin.maddon.button.MButton;
import org.vaadin.maddon.fields.MTable;
import org.vaadin.maddon.label.RichText;
import org.vaadin.maddon.layouts.MHorizontalLayout;
import org.vaadin.maddon.layouts.MVerticalLayout;
import org.vaadin.neo4j.AppService;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventBusListener;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;

@Component
@UIScope
class PersonView extends MVerticalLayout {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
    AppService appService;

    @Autowired
    PersonForm personForm;

    @Autowired
    EventBus eventBus;

    MTable<Person> listing = new MTable<>(Person.class).
            withProperties("name", "userName");

    Button addNew = new MButton(FontAwesome.PLUS, e->{
    	personForm.setEntity(new Person());
    	personForm.setCaption("Create new User");
    });
    public PersonView() {
        MHorizontalLayout buttonContainer = new MHorizontalLayout();
        buttonContainer.setWidth(100, Unit.PERCENTAGE);
    	addNew.setCaption("Add new User");
        buttonContainer.add(addNew);
        listing.setColumnHeaders("Name","User name");
        setCaption("Users");
        listing.addMValueChangeListener(event -> {
            if (event.getValue() != null) {
                personForm.setEntity(event.getValue());
                listing.setValue(null);
            }
        });
        addComponents(
        		buttonContainer,
                listing
        );
        expand(listing).withFullHeight();

    }

    @PostConstruct
    void init() {
        listPersons();
        eventBus.subscribe(new EventBusListener<PersonsModified>() {

            @Override
            public void onEvent(
                    org.vaadin.spring.events.Event<PersonsModified> event) {
                listPersons();
            }
        });
    }

    void listPersons() {
        listing.setBeans(appService.allAsList());
    }

}
