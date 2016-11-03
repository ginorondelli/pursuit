package org.vaadin.neo4j.vaadin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.domain.Source;
import org.vaadin.neo4j.AppService;
import org.vaadin.neo4j.vaadin.events.SourceChangedNotifier;
import org.vaadin.neo4j.vaadin.events.SourcesModified;
import org.vaadin.neo4j.vaadin.form.SourceForm;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;


@UIScope
@org.springframework.stereotype.Component
class SourceView extends MVerticalLayout {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
    AppService sourceService;

    @Autowired
    SourceForm sourceForm;

    @Autowired
    SourceChangedNotifier eventBus;

    MTable<Source> listing = new MTable<>(Source.class).
            withProperties("id","sourceName", "agentSourced", "agentAssigned", "customerSources");

    Button addNew = new MButton(FontAwesome.PLUS, e->{
    	sourceForm.setEntity(new Source());
    	sourceForm.setCaption("Create new Source");
    });
  
    
    public SourceView() {
        MHorizontalLayout buttonContainer = new MHorizontalLayout();
        buttonContainer.setWidth(100, Unit.PERCENTAGE);
    	addNew.setCaption("Add new Source");
        buttonContainer.add(addNew);
    	setCaption("Sources");
        listing.addMValueChangeListener(event -> {
            if (event.getValue() != null) {
                sourceForm.setEntity(event.getValue());
                listing.setValue(null);
            }
        });
        listing.setColumnHeader("id", "ID");
        listing.setColumnHeader("sourceName", "Source Name");
        listing.setColumnHeader("agentSourced", "Agent Sourced");
        listing.setColumnHeader("agentAssigned", "Agent Assigned");
        listing.setColumnHeader("customerSources", "Matched Customers");
             addComponents(
        		buttonContainer,
                listing
        );
        expand(listing).withFullHeight();

    }

    @PostConstruct
    void init() {
        listSources();
        eventBus.subscribe(this::refreshListing);
//        eventBus.subscribe(new EventBusListener<SourcesModified>() {
//
//            /**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//            public void onEvent(
//                    org.vaadin.spring.events.Event<SourcesModified> event) {
//                listing.removeAllItems();
//				listSources();
//            }
//        });
    }
    void refreshListing() {
    	 listing.removeAllItems();
    	 listSources();
    }

    void listSources() {
        listing.addBeans(sourceService.listAllSources());
    }

}
