package org.vaadin.neo4j.vaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.neo4j.vaadin.authentication.BasicAccessControl;
import org.vaadin.neo4j.vaadin.authentication.LoginScreen;
import org.vaadin.neo4j.vaadin.authentication.LoginScreen.LoginListener;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.label.RichText;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;

/**
 *
 * @author emorgan
 */
@SpringUI(path = "")
@Title("Pursuit Source Management")
@Theme("valo")
//@Widgetset("org.vaadin.neo4j.vaadin.AppWidgetSet")
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
    @Autowired
    BasicAccessControl accessControl;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
   	VaadinSession.getCurrent().setErrorHandler(this);
   	UI.getCurrent().setErrorHandler(this);
    if (!accessControl.isUserSignedIn()) {
        setSizeFull();
    	setContent(new LoginScreen(accessControl, new LoginListener() {
            @Override
            public void loginSuccessful() {
            	showLoggedIn();
            }
        }));
    } else {
    	showLoggedIn();
    }

}
    private void showLoggedIn() {
    	RichText header = new RichText().withMarkDownResource("/header.md");
    	MButton logout = new MButton("Logout");
    	logout.addClickListener(new ClickListener(){
            public void buttonClick(ClickEvent event) {
            	accessControl.logout();
            	setContent(new LoginScreen(accessControl, new LoginListener() {
                    @Override
                    public void loginSuccessful() {
                    	showLoggedIn();
                    }
                }));
            }	
         });
  
    	GridLayout headerLayout = new GridLayout(2,1);
    	headerLayout.setVisible(true);
    	headerLayout.setWidth(100, Unit.PERCENTAGE);
    	headerLayout.setHeight(50, Unit.PIXELS);;
    	headerLayout.addComponent(header);
    	headerLayout.setComponentAlignment(header, Alignment.TOP_LEFT);
    	headerLayout.addComponent(logout);
    	headerLayout.setComponentAlignment(logout, Alignment.BOTTOM_RIGHT);
	    		   
    	TabSheet tabSheet = new TabSheet(sourceView, customerView, projectView, personView);
        tabSheet.setSizeFull();
        setContent(
                new MVerticalLayout(
                        headerLayout,
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
