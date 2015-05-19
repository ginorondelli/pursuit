package org.vaadin.neo4j.vaadin.authentication;

import java.io.Serializable;

import org.vaadin.viritin.label.RichText;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * UI content when the user is not logged in yet.
 */
@SuppressWarnings("serial")
public class LoginScreen extends VerticalLayout{

    private TextField username;
    private PasswordField password;
    private Button login;
    private LoginListener loginListener;
    private AccessControl accessControl;

    public LoginScreen(AccessControl accessControl, LoginListener loginListener) {
        this.loginListener = loginListener;
        this.accessControl = accessControl;
        buildUI();
        username.focus();
    }

    private void buildUI() {
        // login form, centered in the available part of the screen
        Component loginForm = buildLoginForm();

        VerticalLayout centeringLayout = new VerticalLayout();
        centeringLayout.setMargin(true);
        centeringLayout.setSizeFull();
    	RichText header = new RichText().withMarkDownResource("/header.md");  	 
        header.setHeight(100, Unit.PIXELS);

	    final Panel loginPanel = new Panel("Login...");
	    loginPanel.setWidth("400px");
	    loginPanel.setHeight("250px");
	    centeringLayout.addComponent(header);
        centeringLayout.setComponentAlignment(header, Alignment.MIDDLE_CENTER);
	    centeringLayout.addComponent(loginPanel);
        loginPanel.setContent(loginForm);
        centeringLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);

        addComponent(centeringLayout);
    }

    private Component buildLoginForm() {
        FormLayout loginForm = new FormLayout();

        loginForm.setSizeUndefined();
        loginForm.setMargin(false);

        loginForm.addComponent(username = new TextField("Username"));
        loginForm.setComponentAlignment(username, Alignment.MIDDLE_CENTER);
        username.setWidth(15, Unit.EM);
        loginForm.addComponent(password = new PasswordField("Password"));
        loginForm.setComponentAlignment(password, Alignment.MIDDLE_CENTER);
        password.setWidth(15, Unit.EM);
        CssLayout buttons = new CssLayout();
        buttons.setStyleName("buttons");
        loginForm.addComponent(buttons);

        buttons.addComponent(login = new Button("Login"));
        login.setDisableOnClick(true);
        login.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    login();
                } finally {
                    login.setEnabled(true);
                }
            }
        });
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        return loginForm;
    }

    private void login() {
        if (accessControl.signIn(username.getValue(), password.getValue())) {
            loginListener.loginSuccessful();
        } else {
            showNotification(new Notification("Login failed",
                    "Please check your username and password and try again.",
                    Notification.Type.HUMANIZED_MESSAGE));
            username.focus();
        }
    }

    private void showNotification(Notification notification) {
        // keep the notification visible a little while after moving the
        // mouse, or until clicked
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
    }

    public interface LoginListener extends Serializable {
        void loginSuccessful();
    }
}
