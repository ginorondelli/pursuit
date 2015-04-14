package org.vaadin.neo4j.vaadin.form;


import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.domain.Customer;
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
import org.vaadin.neo4j.vaadin.events.SourcesModified;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventBusListener;

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
  
    @Autowired
    SourceFormController sourceFormController;

    @Autowired
    AppService service;

    @Autowired
    EventBus eventBus;

    private Window window;
    private Window matchesWindow;

    @SuppressWarnings("serial")
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
        
//        region.setRows(6);
        region.setNullSelectionAllowed(true);
        region.setMultiSelect(false);
//        region.setImmediate(true);
//        region.setLeftColumnCaption("Available options");
//        region.setRightColumnCaption("Selected options");
      
//        postCodes.setRows(6);
        postCodes.setNullSelectionAllowed(true);
        postCodes.setMultiSelect(false);
//        postCodes.setImmediate(true);
//        postCodes.setLeftColumnCaption("Available options");
//        postCodes.setRightColumnCaption("Selected options");   	
// 
        initTypeOfSourceProject();
        
        setSavedHandler(sourceFormController);
        setResetHandler(sourceFormController);

        //agentSourced.setMultiSelect(true);
        //agentSourced.setSelectable(true);
        agentSourced.setPageLength(6);

        populateMeta();

        eventBus.subscribe(new EventBusListener<SourcesModified>() {

            @Override
            public void onEvent(
                    org.vaadin.spring.events.Event<SourcesModified> event) {
            	populateMeta();
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
                new ListContainer<String>(String.class, PursuitMeta.allAgents()));
  
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
	
    @Override
    protected Component createContent() {
    	MTable<Customer> table = new MTable<>(Customer.class).
                withProperties("id","customerName");
    	table.setWidth(50, Unit.PERCENTAGE);
    	table.setImmediate(true);
    	table.setColumnHeader("id","ID");
    	table.setColumnHeader("customerName","Customer");
    	if (null!=entity.getSourceName()) {
    		table.addBeans(getEntity().getCustomers());
    	}

    	table.addGeneratedColumn("Remove", 
    		      new Table.ColumnGenerator() {
    		        public Object generateCell(
    		          Table source,final Object itemId,Object columnId){
    		            Button removeButton = new Button("x");
    		            removeButton.addClickListener(new ClickListener(){
    		              public void buttonClick(ClickEvent event) {
    		                if (null!=itemId) {
      		            	  table.removeItem(itemId);
      		            	  getEntity().getCustomers().remove(itemId);
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
    		,table);
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
        window.setWidth(65, Unit.PERCENTAGE);
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
    	Collection<Customer>matches=service.getCustomerMatches(source);
    	if (matches.size()>0) {
        	MTable<Customer> table = new MTable<>(Customer.class).
                    withProperties("id","customerName");
            table.addBeans(service.getCustomerMatches(source));
            table.addMValueChangeListener(event -> {
                if (event.getValue() != null) {
            		Notification note = new Notification("Source and Customer related",
            			    "<br/>Customer: '"+event.getValue().getCustomerName()+"' has been attached to this Source?",
            			    Notification.TYPE_TRAY_NOTIFICATION, true);
            		note.setDelayMsec(500);
            		note.show(Page.getCurrent());            
                    this.entity=service.getSource(entity.getId());
            		this.entity.getCustomers().add(event.getValue());
            		event.getValue().getSources().add(this.entity);
            		service.save(this.entity);
            		matchesWindow.close();
                }
            });

            matchesWindow = new Window("Customers Matched");
            matchesWindow.setContent(table); 
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
