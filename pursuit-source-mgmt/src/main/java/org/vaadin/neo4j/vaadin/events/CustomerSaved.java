package org.vaadin.neo4j.vaadin.events;

import org.vaadin.domain.Customer;

public class CustomerSaved {

	private Customer customer;

	public CustomerSaved(Customer customer) {
		super();
		this.customer = customer;
	}
	
}
