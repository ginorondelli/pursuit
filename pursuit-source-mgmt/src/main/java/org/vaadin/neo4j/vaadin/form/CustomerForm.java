package org.vaadin.neo4j.vaadin.form;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.domain.Customer;
import org.vaadin.domain.CustomerSourceStatus;
import org.vaadin.domain.Project;
import org.vaadin.domain.PursuitMeta;
import org.vaadin.neo4j.AppService;
import org.vaadin.neo4j.vaadin.controller.CustomerFormController;
import org.vaadin.neo4j.vaadin.events.CustomerChangedNotifier;
import org.vaadin.neo4j.vaadin.events.CustomerSourceStatusChangedNotifier;
import org.vaadin.neo4j.vaadin.events.SourceChangedNotifier;
import org.vaadin.viritin.ListContainer;
import org.vaadin.viritin.MBeanFieldGroup;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.UIScope;
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

@org.springframework.stereotype.Component
@UIScope
public class CustomerForm extends AbstractForm<Customer> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TextField customerName = new MTextField("Customer Name");
	ComboBox project = new ComboBox("Project Name");
    
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
    Table customerSourcesTable;
    

    @Autowired
    CustomerFormController CustomerFormController;

    @Autowired
    AppService service;

    @Autowired
    SourceChangedNotifier sourceEvents;

    @Autowired
    CustomerChangedNotifier customerEvents;
 
    @Autowired
    CustomerSourceStatusChangedNotifier customerSourceStatusEvents;

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

        populateMeta();
        
        customerEvents.subscribe(this::populateMeta);
        sourceEvents.subscribe(this::populateMeta);
        sourceEvents.subscribe(this::refreshCustomerSourcesTable);
        customerEvents.subscribe(this::refreshCustomerSourcesTable);
        customerSourceStatusEvents.subscribe(this::refreshCustomerSourcesTable);
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
        project.setContainerDataSource(
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

	private void refreshCustomerSourcesTable() {
		customerSourcesTable.removeAllItems();
		if (null!=customer.getCustomerSources()&&customer.getCustomerSources().size()>0) {
			service.getCustomer(customer.getId()).getCustomerSources()
					.forEach((customerSource) -> {
						customerSourcesTable.addItem(customerSource);
					});
		}
	}	
	
    @Override
    protected Component createContent() {

    	BeanItemContainer<CustomerSourceStatus> customerSourcesContainer =
    		    new BeanItemContainer<CustomerSourceStatus>(CustomerSourceStatus.class);
    	customerSourcesContainer.addNestedContainerProperty("source.id");
    	customerSourcesContainer.addNestedContainerProperty("source.sourceName");
    	
    	customerSourcesTable = new Table("CustomerSources",customerSourcesContainer);
    	customerSourcesTable.setWidth(70, Unit.PERCENTAGE);
    	customerSourcesTable.setImmediate(true);
    	customerSourcesTable.setColumnHeader("source.id","ID");
    	customerSourcesTable.setColumnHeader("source.sourceName","Source");
    	customerSourcesTable.setColumnHeader("status","Status");
    	customerSourcesTable.setVisibleColumns("source.id","source.sourceName", "status");
    	if (null!=customer&&null!=customer.getCustomerName()) {
    		refreshCustomerSourcesTable();
    	}

    	return new MHorizontalLayout(
    			new MVerticalLayout(
    				new FormLayout(
	                        customerName,
	                        project,
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
	        ),customerSourcesTable
	        );
    }

    public CustomerForm() {
    }

    @Override
    public MBeanFieldGroup<Customer> setEntity(Customer entity) {
        this.customer=entity;
        super.setEntity(entity);
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

        return new MBeanFieldGroup<Customer>(Customer.class);
    }


    private void showInWindow(String caption) {
        window = new Window(caption, this);
        window.setModal(true);
        window.setWidth(80, Unit.PERCENTAGE);
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
