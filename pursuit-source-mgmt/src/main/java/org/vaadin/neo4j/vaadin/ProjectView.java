package org.vaadin.neo4j.vaadin;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.domain.Project;
import org.vaadin.neo4j.AppService;
import org.vaadin.neo4j.ProjectRepository;
import org.vaadin.neo4j.vaadin.events.ProjectsChangedNotifier;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.label.RichText;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;

@Component
@UIScope
class ProjectView extends MVerticalLayout {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
    AppService service;

    @Autowired
    ProjectRepository repo;

    @Autowired
    ProjectsChangedNotifier eventBus;

    private List<Project> allProjects;

    MTable<Project> listing = new MTable<>(Project.class).
            withProperties("name");
    
    Button saveChanges = new MButton(FontAwesome.FLOPPY_O, e -> {
        repo.save(allProjects);
        eventBus.onEvent();
    });
    
    Button addNew = new MButton(FontAwesome.PLUS, e->{
        Project p = new Project("-- New Project --", 250, 5);
        listing.addBeans(p);
        listing.setCurrentPageFirstItemId(p);
    });
    
    Button reset = new MButton(FontAwesome.UNDO, e->{
        listProjects();
    });

    public ProjectView() {
        setCaption("Projects");
        listing.setEditable(true);
        addComponents(
                new RichText().withMarkDownResource("/projectsview.md"),
                new MHorizontalLayout(saveChanges,addNew,reset),
                listing
        );
        expand(listing).withFullHeight();

    }

    @PostConstruct
    void init() {
    	listProjects();
        eventBus.subscribe(this::refreshListing);
    }

    void refreshListing() {
   	 	listing.removeAllItems();
   	 	listProjects();
   }
    
    void listProjects() {
        allProjects = service.listAllProjects();
        listing.setBeans(allProjects);
    }

}
