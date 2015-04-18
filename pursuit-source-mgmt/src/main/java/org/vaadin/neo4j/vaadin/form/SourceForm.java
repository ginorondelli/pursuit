package org.vaadin.neo4j.vaadin.form;


import java.util.Collection;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.domain.Customer;
import org.vaadin.domain.CustomerSourceStatus;
import org.vaadin.domain.Person;
import org.vaadin.domain.PursuitMeta;
import org.vaadin.domain.Source;
import org.vaadin.maddon.ListContainer;
import org.vaadin.maddon.MBeanFieldGroup;
import org.vaadin.maddon.fields.MTable;
import org.vaadin.maddon.fields.MTextField;
import org.vaadin.maddon.form.AbstractForm;
import org.vaadin.maddon.layouts.MHorizontalLayout;
import org.vaadin.maddon.layouts.MVerticalLayout;
import org.vaadin.neo4j.AppService;
import org.vaadin.neo4j.vaadin.controller.SourceFormController;
import org.vaadin.neo4j.vaadin.events.CustomersModified;
import org.vaadin.neo4j.vaadin.events.SourcesModified;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventBusListener;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;


@UIScope
@VaadinComponent
public class SourceForm extends AbstractForm<Source> {

	private Source entity;
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TextField sourceName = new MTextField("Source Name");
    ComboBox agentSourced = new ComboBox("Agent Sourced");
    ComboBox agentAssigned = new ComboBox("Agent Assigned");
    
    TwinColSelect sourceSectors = new TwinColSelect("Source Sector");
   
    HorizontalSplitPanel sizeOfSourcePanel = new HorizontalSplitPanel();
    ComboBox turnoverBanding = new ComboBox("Turnover Banding");
    ComboBox employeeBanding = new ComboBox("Employee Banding");
    
    Label geographicalCriteriaLabel = new Label("Geographical Criteria");
    ComboBox region = new ComboBox("Region");
    ComboBox postCodes = new ComboBox("Post Codes");

    Label typeofSourceProject = new Label("Type of Source Project");
    TwinColSelect solutions = new TwinColSelect("Solutions");
    TwinColSelect consultancy = new TwinColSelect("Consultancy");
    TwinColSelect managedServices = new TwinColSelect("Managed Services");
    TwinColSelect telecoms = new TwinColSelect("Telecoms");
    
    Table customerSourcesTable;
     
    @Autowired
    SourceFormController sourceFormController;

    @Autowired
    AppService service;

    @Autowired
    EventBus eventBus;

    private Window window;
    private Window matchesWindow;

    @PostConstruct
    void init() {

        sourceSectors.setRows(6);
        sourceSectors.setNullSelectionAllowed(true);
        sourceSectors.setMultiSelect(true);
        sourceSectors.setImmediate(true);
        sourceSectors.setLeftColumnCaption("Available options");
        sourceSectors.setRightColumnCaption("Selected options");
        
        turnoverBanding.setMultiSelect(false);
        employeeBanding.setMultiSelect(false);
        
        sizeOfSourcePanel.setCaption("Size of Source");
        sizeOfSourcePanel.setSplitPosition(50, Unit.PERCENTAGE);
        sizeOfSourcePanel.setFirstComponent(turnoverBanding);
        sizeOfSourcePanel.setSecondComponent(employeeBanding);
        
        region.setNullSelectionAllowed(true);
        region.setMultiSelect(false);

        postCodes.setNullSelectionAllowed(true);
        postCodes.setMultiSelect(false);

        initTypeOfSourceProject();
        
        setSavedHandler(sourceFormController);
        setResetHandler(sourceFormController);

        agentSourced.setPageLength(6);

        populateMeta();

        eventBus.subscribe(new EventBusListener<SourcesModified>() {

            /**
			 * 
			 */
			private static final long serialVersionUID = 530211851909626466L;

			@Override
            public void onEvent(
                    org.vaadin.spring.events.Event<SourcesModified> event) {
            	populateMeta();
            }
        });
        eventBus.subscribe(new EventBusListener<CustomersModified>() {

			private static final long serialVersionUID = 1L;

			@Override
            public void onEvent(
                    org.vaadin.spring.events.Event<CustomersModified> event) {
				customerSourcesTable.removeAllItems();
				refreshCustomerSourcesTable();
            }
        });


    }

	private void initTypeOfSourceProject() {
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
        agentSourced.setContainerDataSource(
                new ListContainer<Person>(Person.class, service.allAsList()));
        agentAssigned.setContainerDataSource(
                new ListContainer<Person>(Person.class, service.allAsList()));
		sourceSectors.setContainerDataSource(
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
		service.getSource(getEntity().getId()).getCustomerSources().forEach((customerSource)-> {customerSourcesTable.addItem(customerSource);});
	}
	
    @Override
    protected Component createContent() {

    	BeanItemContainer<CustomerSourceStatus> customerSourcesContainer =
    		    new BeanItemContainer<CustomerSourceStatus>(CustomerSourceStatus.class);
    	customerSourcesContainer.addNestedContainerProperty("customer.id");
    	customerSourcesContainer.addNestedContainerProperty("customer.customerName");
    	
    	customerSourcesTable = new Table("CustomerSources",customerSourcesContainer);
    	customerSourcesTable.setWidth(70, Unit.PERCENTAGE);
    	customerSourcesTable.setImmediate(true);
    	customerSourcesTable.setColumnHeader("customer.id","ID");
    	customerSourcesTable.setColumnHeader("customer.customerName","Customer");
    	customerSourcesTable.setColumnHeader("status","Status");
    	customerSourcesTable.setVisibleColumns("customer.id","customer.customerName", "status");
    	if (null!=entity.getSourceName()) {
    		refreshCustomerSourcesTable();
    	}

    	customerSourcesTable.addGeneratedColumn("Remove", 
    		      new Table.ColumnGenerator() {

					private static final long serialVersionUID = -3065600195788653096L;

					public Object generateCell(
    		          Table source,final Object itemId,Object columnId){
    		            Button removeButton = new Button("x");
    		            removeButton.addClickListener(new ClickListener(){
							private static final long serialVersionUID = -937780671666977070L;
							public void buttonClick(ClickEvent event) {
	    		                if (null!=itemId) {
	      		            	  customerSourcesTable.removeItem(itemId);
	      		            	  getEntity().getCustomerSources().remove(itemId);
	      		            	  entity=service.save(entity);
	    		                }
    		             }
    		          });
    		          return removeButton;
    		        }
    		      });
    	
    	return new 
    		MHorizontalLayout(new 	
	    		MVerticalLayout(
	                new FormLayout(
	                        sourceName,
	                        agentSourced,
	                        agentAssigned,
	                        sourceSectors,
	                        sizeOfSourcePanel,
	                        geographicalCriteriaLabel,
	                        region,
	                        postCodes,
	                        typeofSourceProject,
	                        solutions,
	                        consultancy,
	                        managedServices,
	                        telecoms
	                ),
	                getToolbar()
	    		)
    		,customerSourcesTable);
    }

    public SourceForm() {
    }

    @Override
    public MBeanFieldGroup<Source> setEntity(Source entity) {
        super.setEntity(entity);
    	this.entity=entity;
        if (null!=entity.getSourceName()) {
        	showInWindow("Edit Source");
        } else {
        	 showInWindow("Create new Source");
        }
       return new MBeanFieldGroup<Source>(Source.class);
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
        showCustomerMatches(getEntity());
        window.close();
    }

    
    
    @SuppressWarnings("deprecation")
	private void showCustomerMatches(Source source) {
    	HashMap<Customer,String>statusMap=new HashMap<Customer,String>();
    	Collection<Customer>matches=service.getCustomerMatches(source);
    	if (matches.size()>0) {
        	MTable<Customer> customerMatchesTable = new MTable<>(Customer.class).
                    withProperties("id","customerName");
        	customerMatchesTable.addGeneratedColumn("Status", 
        		      new Table.ColumnGenerator() {
        		        /**
						 * 
						 */
						private static final long serialVersionUID = -7121121916613529705L;

						public Object generateCell(
        		          Table source,final Object itemId,Object columnId){
        		        	ComboBox csStatus = new ComboBox("Status");
        		        	csStatus.addValueChangeListener(event -> {
        		        		if (null!=csStatus.getValue()) {
        		        			statusMap.put((Customer) itemId, csStatus.getValue().toString());
        		        		}
        		        	});
        		        	csStatus.setContainerDataSource(new ListContainer<String>(String.class,PursuitMeta.allStatus()));
	    		        	statusMap.put((Customer) itemId, null);
        		        	return csStatus;
        		        }
        	});
            customerMatchesTable.addBeans(service.getCustomerMatches(source));
            customerMatchesTable.addMValueChangeListener(event -> {
                if (event.getValue() != null) {
            		Notification note = new Notification("Source and Customer related",
            			    "<br/>Customer: '"+event.getValue().getCustomerName()+"' has been attached to this Source?",
            			    Notification.TYPE_TRAY_NOTIFICATION, true);
            		note.setDelayMsec(500);
            		note.show(Page.getCurrent());            
                    this.entity=service.getSource(entity.getId());
            		this.entity.getCustomerSources().add(service.save(this.entity.customerSource(event.getValue(), statusMap.get(event.getValue()))));
            		service.save(this.entity);
            		matchesWindow.close();
                }
            });
 	            

            matchesWindow = new Window("Customers Matched");
            matchesWindow.setContent(customerMatchesTable); 
            matchesWindow.setModal(true);
        	UI.getCurrent().addWindow(matchesWindow);		    		
    	} else {
    		Notification note = new Notification("Customer Matches",
    			    "<br/>There are no customer matches",
    			    Notification.TYPE_TRAY_NOTIFICATION, true);
    		note.setDelayMsec(500);
    		note.show(Page.getCurrent());
    	}
    		
	}

	@Override
    protected void reset(Button.ClickEvent e) {
        super.reset(e);
        window.close();
    }

	public Source getEntity() {
		return entity;
	}
	

}
