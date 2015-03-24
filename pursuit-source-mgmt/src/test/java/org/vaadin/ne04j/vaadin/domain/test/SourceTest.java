package org.vaadin.ne04j.vaadin.domain.test;

import static org.junit.Assert.*;

import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;
import org.vaadin.domain.Customer;
import org.vaadin.domain.Source;

public class SourceTest {

	Customer customer1;
	Source source1;
	
	@Before
	public void setUp() throws Exception {
		customer1 = new Customer();
		source1 = new Source();
	}

	@Test
	public void testMatchesCustomerEmpty() {
		assertTrue(source1.matchesCustomer(customer1));
	}

}
