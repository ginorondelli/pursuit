package org.vaadin.neo4j.vaadin.form;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.domain.Customer;
import org.vaadin.domain.Project;
import org.vaadin.domain.PursuitMeta;
import org.vaadin.maddon.ListContainer;
import org.vaadin.maddon.fields.MTextField;
import org.vaadin.maddon.form.AbstractForm;
import org.vaadin.maddon.layouts.MVerticalLayout;
import org.vaadin.neo4j.AppService;
import org.vaadin.neo4j.vaadin.controller.CustomerFormController;
import org.vaadin.neo4j.vaadin.events.CustomersModified;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventBusListener;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

@UIScope
@VaadinComponent
public class CustomerForm extends AbstractForm<Customer> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TextField customerName = new MTextField("Customer Name");
	ComboBox projectName = new ComboBox("Project Name");
    
	ComboBox partnerType = new ComboBox("Partner Type");
	TwinColSelect crossOverExclusions = new TwinColSelect("Crossover Exclusions");
	
	TwinColSelect customerSectors = new TwinColSelect("Customer Sector");
   
    HorizontalSplitPanel sizeOfCustomerPanel = new HorizontalSplitPanel();
    ListSelect turnoverBanding = new ListSelect("Turnover Banding");
    ListSelect employeeBanding = new ListSelect("Employee Banding");
    
    Label geographicalCriteriaLabel = new Label("Geographical Criteria");
    TwinColSelect region = new TwinColSelect("Region");
    TwinColSelect postCodes = new TwinColSelect("Post Codes");

    Label typeofCustomerProject = new Label("Type of Customer Project");
    TwinColSelect solutions = new TwinColSelect("Solutions");
    TwinColSelect consultancy = new TwinColSelect("Consultancy");
    TwinColSelect managedServices = new TwinColSelect("Managed Services");
    TwinColSelect telecoms = new TwinColSelect("Telecoms");
   
    Customer customer;

    Table Customerd = new Table("Customers");

    @Autowired
    CustomerFormController CustomerFormController;

    @Autowired
    AppService service;

    @Autowired
    EventBus eventBus;

    private Window window;

    @SuppressWarnings("serial")
	@PostConstruct
    void init() {

        customerSectors.setRows(6);
        customerSectors.setNullSelectionAllowed(true);
        customerSectors.setMultiSelect(true);
        customerSectors.setImmediate(true);
        customerSectors.setLeftColumnCaption("Available options");
        customerSectors.setRightColumnCaption("Selected options");
        
        turnoverBanding.setMultiSelect(true);
        employeeBanding.setMultiSelect(true);
        
        sizeOfCustomerPanel.setCaption("Size of Customer");
        sizeOfCustomerPanel.setSplitPosition(50, Unit.PERCENTAGE);
        sizeOfCustomerPanel.setFirstComponent(turnoverBanding);
        sizeOfCustomerPanel.setSecondComponent(employeeBanding);
        
        region.setRows(6);
        region.setNullSelectionAllowed(true);
        region.setMultiSelect(true);
        region.setImmediate(true);
        region.setLeftColumnCaption("Available options");
        
        postCodes.setRows(6);
        postCodes.setNullSelectionAllowed(true);
        postCodes.setMultiSelect(true);
        postCodes.setImmediate(true);
        region.setRightColumnCaption("Selected options");
      
        postCodes.setLeftColumnCaption("Available options");
        postCodes.setRightColumnCaption("Selected options");   	
 
        initTypeOfCustomerProject();
        
        setSavedHandler(CustomerFormController);
        setResetHandler(CustomerFormController);

        //agentCustomerd.setMultiSelect(true);
        //agentCustomerd.setSelectable(true);

        populateMeta();

        eventBus.subscribe(new EventBusListener<CustomersModified>() {

            @Override
            public void onEvent(
                    org.vaadin.spring.events.Event<CustomersModified> event) {
            	populateMeta();
            }
        });

    }

	private void initTypeOfCustomerProject() {
        solutions.setRows(6);
        solutions.setNullSelectionAllowed(true);
        solutions.setMultiSelect(true);
        solutions.setImmediate(true);
        solutions.setLeftColumnCaption("Available options");
        solutions.setRightColumnCaption("Selected options"); 
        
        consultancy.setRows(6);
        consultancy.setNullSelectionAllowed(true);
        consultancy.setMultiSelect(true);
        consultancy.setImmediate(true);
        consultancy.setLeftColumnCaption("Available options");
        consultancy.setRightColumnCaption("Selected options"); 

        managedServices.setRows(6);
        managedServices.setNullSelectionAllowed(true);
        managedServices.setMultiSelect(true);
        managedServices.setImmediate(true);
        managedServices.setLeftColumnCaption("Available options");
        managedServices.setRightColumnCaption("Selected options"); 
        
        telecoms.setRows(6);
        telecoms.setNullSelectionAllowed(true);
        telecoms.setMultiSelect(true);
        telecoms.setImmediate(true);
        telecoms.setLeftColumnCaption("Available options");
        telecoms.setRightColumnCaption("Selected options"); 	
	}

	private void populateMeta() {
        projectName.setContainerDataSource(
                new ListContainer<Project>(Project.class, service.listAllProjects()));
        partnerType.setContainerDataSource(
                new ListContainer<String>(String.class, PursuitMeta.allPartnerTypes()));
        customerSectors.setContainerDataSource(
				new ListContainer<String>(String.class, PursuitMeta.allSourceSectors()));		
    	turnoverBanding.setContainerDataSource(
    			new ListContainer<String>(String.class,PursuitMeta.allTurnovers()));
    	employeeBanding.setContainerDataSource(
    			new ListContainer<String>(String.class,PursuitMeta.allEmployeeBanding()));
    	region.setContainerDataSource(
    			new ListContainer<String>(String.class,PursuitMeta.allRegions()));
    	postCodes.setContainerDataSource(
    			new ListContainer<String>(String.class,PursuitMeta.allPostCodes()));
    	solutions.setContainerDataSource(
    			new ListContainer<String>(String.class,PursuitMeta.allSolutions()));
    	consultancy.setContainerDataSource(
    			new ListContainer<String>(String.class,PursuitMeta.allConsultancy()));
    	managedServices.setContainerDataSource(
    			new ListContainer<String>(String.class,PursuitMeta.allManagedServices()));
    	telecoms.setContainerDataSource(
    			new ListContainer<String>(String.class,PursuitMeta.allTelecoms()));
	}
	
    @Override
    protected Component createContent() {
        return new MVerticalLayout(
                new FormLayout(
                        customerName,
                        projectName,
                        partnerType,
                        crossOverExclusions,
                        customerSectors,
                        sizeOfCustomerPanel,
                        geographicalCriteriaLabel,
                        region,
                        postCodes,
                        typeofCustomerProject,
                        solutions,
                        consultancy,
                        managedServices,
                        telecoms
                ),
                getToolbar()
        );
    }

    public CustomerForm() {
    }

    @Override
    public void setEntity(Customer entity) {
        super.setEntity(entity);
        this.customer=entity;
        List<Customer>customers=null;
		
        if (null!=entity.getCustomerName()) {
        	showInWindow("Edit Customer");
        	customers=service.allCustomersExceptThis(this.customer);
       } else {
        	showInWindow("Create new Customer");
         	customers=service.listAllCustomers();
        }
        crossOverExclusions.setContainerDataSource(
                new ListContainer<Customer>(Customer.class, customers));        
       
    }

    private void showInWindow(String caption) {
        window = new Window(caption, this);
        window.setModal(true);
        window.setWidth(50, Unit.PERCENTAGE);
        window.setHeight(90, Unit.PERCENTAGE);
        window.setClosable(true);
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
