package org.vaadin.neo4j.vaadin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.domain.Customer;
import org.vaadin.neo4j.AppService;
import org.vaadin.neo4j.vaadin.events.CustomerChangedNotifier;
import org.vaadin.neo4j.vaadin.form.CustomerForm;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;

@org.springframework.stereotype.Component
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
    CustomerChangedNotifier eventBus;

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
        listCustomers();
        eventBus.subscribe(this::refreshListing);

    }
    void refreshListing() {
   	 	listing.removeAllItems();
   	 	listCustomers();
   }

    void listCustomers() {
        listing.addBeans(customerService.listAllCustomers());
    }

}
