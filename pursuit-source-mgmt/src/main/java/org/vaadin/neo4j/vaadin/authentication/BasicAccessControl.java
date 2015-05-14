package org.vaadin.neo4j.vaadin.authentication;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.domain.Person;
import org.vaadin.neo4j.AppService;
import org.vaadin.neo4j.vaadin.authentication.AccessControl;
import org.vaadin.neo4j.vaadin.authentication.CurrentUser;

/**
 * Default mock implementation of {@link AccessControl}. This implementation
 * accepts any string as a password, and considers the user "admin" as the only
 * administrator.
 */
@Component
public class BasicAccessControl implements AccessControl {

	@Autowired
	AppService appService;
	
    @Override
    public boolean signIn(String username, String password) {
        if (username != null) {
        	Person user = appService.getUserByUserName(username);
        	if (null!=user) {
        		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        		if (passwordEncryptor.checkPassword(password, user.getPassWord())) {
        			CurrentUser.set(username);
        	        return true;
        		}
        	}
        }
       return false;
    }

    @Override
    public boolean isUserSignedIn() {
        return !CurrentUser.get().isEmpty();
    }

    @Override
    public boolean isUserInRole(String role) {
        if ("admin".equals(role)) {
            // Only the "admin" user is in the "admin" role
            return getPrincipalName().equals("admin");
        }

        // All users are in all non-admin roles
        return true;
    }

    @Override
    public String getPrincipalName() {
        return CurrentUser.get();
    }
    @Override
    public void logout() {
    	CurrentUser.set(null);
    }

	public void setAppService(AppService appService) {
		this.appService = appService;
	}
    

}
