package org.vaadin.neo4j.vaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.maddon.label.RichText;
import org.vaadin.maddon.layouts.MVerticalLayout;
import org.vaadin.spring.VaadinUI;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;

/**
 *
 * @author emorgan
 */
@VaadinUI()
@Title("Pursuit Source Management")
@Theme("dawn")
@Widgetset("org.vaadin.neo4j.vaadin.AppWidgetSet")
@JavaScript("http://cdn.alloyui.com/2.5.0/aui/aui-min.js")
@StyleSheet("http://cdn.alloyui.com/2.5.0/aui-css/css/bootstrap.min.css")
class MainUI extends UI implements ErrorHandler{
    
    
	/**
	 * 
	 */

	@Autowired
	SourceView sourceView;
	@Autowired
	CustomerView customerView;	
	@Autowired
    PersonView personView;
    @Autowired
    ProjectView projectView;
    @Autowired
    VisualEditor visualEditor;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
   	VaadinSession.getCurrent().setErrorHandler(this);
   	UI.getCurrent().setErrorHandler(this);
//        TabSheet tabSheet = new TabSheet(sourceView, customerView, personView, projectView);
        TabSheet tabSheet = new TabSheet(sourceView, customerView, projectView, personView);
        tabSheet.setSizeFull();
        setContent(
                new MVerticalLayout(
                        new RichText().withMarkDownResource("/welcome.md"),
                        tabSheet
                ).withFullHeight().expand(tabSheet)
        );
    }

	@Override
	public void error(com.vaadin.server.ErrorEvent event) {
		event.getThrowable().getCause().printStackTrace();
		Notification.show(event.getThrowable().getCause().toString(), Notification.Type.ERROR_MESSAGE);
		//setContent(null);
        return;
	}
}
