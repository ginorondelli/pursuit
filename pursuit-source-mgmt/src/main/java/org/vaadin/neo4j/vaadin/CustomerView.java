package org.vaadin.neo4j.vaadin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.domain.Customer;
import org.vaadin.maddon.button.MButton;
import org.vaadin.maddon.fields.MTable;
import org.vaadin.maddon.layouts.MHorizontalLayout;
import org.vaadin.maddon.layouts.MVerticalLayout;
import org.vaadin.neo4j.AppService;
import org.vaadin.neo4j.vaadin.events.CustomersModified;
import org.vaadin.neo4j.vaadin.events.SourcesModified;
import org.vaadin.neo4j.vaadin.form.CustomerForm;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventBusListener;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;

@Component
@UIScope
class CustomerView extends MVerticalLayout {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
    AppService customerService;

    @Autowired
    CustomerForm customerForm;

    @Autowired
    EventBus eventBus;

    MTable<Customer> listing = new MTable<>(Customer.class).
            withProperties("id","customerName", "project", "partnerType");

    Button addNew = new MButton(FontAwesome.PLUS, e->{
    	customerForm.setEntity(new Customer());
    	customerForm.setCaption("Create new Customer");
    });
  
    
    public CustomerView() {
        MHorizontalLayout buttonContainer = new MHorizontalLayout();
        buttonContainer.setWidth(100, Unit.PERCENTAGE);
    	addNew.setCaption("Add new Customer");
        buttonContainer.add(addNew);
    	setCaption("Customers");
        listing.addMValueChangeListener(event -> {
            if (event.getValue() != null) {
                customerForm.setEntity(event.getValue());
                listing.setValue(null);
            }
        });

        listing.setColumnHeader("id", "ID");
        listing.setColumnHeader("customerName", "Customer Name");
        listing.setColumnHeader("project", "Project");
        listing.setColumnHeader("partnerType", "Partner Type");
        
        addComponents(
        		buttonContainer,
                listing
        );
        expand(listing).withFullHeight();

    }

    @PostConstruct
    void init() {
        listSources();
        eventBus.subscribe(new EventBusListener<CustomersModified>() {

            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void onEvent(
                    org.vaadin.spring.events.Event<CustomersModified> event) {
                listing.removeAllItems();
				listSources();
            }
        });
    }

    void listSources() {
        listing.addBeans(customerService.listAllCustomers());
    }

}
