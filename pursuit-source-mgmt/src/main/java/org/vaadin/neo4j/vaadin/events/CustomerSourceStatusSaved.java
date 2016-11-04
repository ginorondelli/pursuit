package org.vaadin.neo4j.vaadin.events;

import org.vaadin.domain.CustomerSourceStatus;

public class CustomerSourceStatusSaved {

	private CustomerSourceStatus customerSourceStatus;

	public CustomerSourceStatusSaved(CustomerSourceStatus customerSourceStatus) {
		super();
		this.customerSourceStatus = customerSourceStatus;
	}
	
}
