package org.vaadin.ne04j.vaadin.domain.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.vaadin.domain.Customer;
import org.vaadin.domain.PursuitMeta;
import org.vaadin.domain.Source;


public class SourceTest {

	Customer customer1;
	Source source;
	
	@Before
	public void setUp() throws Exception {
		customer1 = new Customer();
		source = new Source();
	}

	@Test
	public void testEmptyDoesntMatch() {
		assertFalse(source.doMatch());
	}
	@Test
	public void testSectorsTurnoverRegionConsultancyMatch() {		
		source.setSourceSectors(PursuitMeta.allSourceSectors());
		source.setTurnoverBanding("A turnover");
		source.setRegion("A region");
		source.setConsultancy(PursuitMeta.allConsultancy());
		assertTrue(source.doMatch());
	}
	@Test
	public void testTurnoverRegionConsultancyNoMatch() {
		source.setTurnoverBanding("A turnover");
		source.setRegion("A region");
		source.setConsultancy(PursuitMeta.allConsultancy());
		assertFalse(source.doMatch());
	}
	@Test
	public void testNearlyMatchingSourceAndCustomer() {
		// Matching Sectors
		LinkedHashSet<String>sourceSectors=new LinkedHashSet<String>();
		sourceSectors.add("----Chemicals");
		source.setSourceSectors(sourceSectors);
		customer1.setCustomerSectors(PursuitMeta.allSourceSectors());
		// Matching Turnover
		source.setTurnoverBanding("A: 0 to 1M");
		customer1.setTurnoverBanding(PursuitMeta.allTurnovers());
		// Matching Region
		source.setRegion("Channel Islands");
		customer1.setRegion(PursuitMeta.allRegions());
		// Nearly Matching Solution
		LinkedHashSet<String>sourceSolution=new LinkedHashSet<String>();
		sourceSolution.add("ERP");
		source.setSolutions(sourceSolution);
		LinkedHashSet<String>customerSolution=new LinkedHashSet<String>();
		customerSolution.add("--ERP Implementation");
		customer1.setSolutions(customerSolution);
		// Should do the match
		assertTrue(source.doMatch());
		// But shouldn't match
		assertFalse(source.matchesCustomer(customer1));
	}
	@Test
	public void testMatchingSourceAndCustomer() {
		// Matching Sectors
		LinkedHashSet<String>sourceSectors=new LinkedHashSet<String>();
		sourceSectors.add("----Chemicals");
		source.setSourceSectors(sourceSectors);
		customer1.setCustomerSectors(PursuitMeta.allSourceSectors());
		// Matching Turnover
		source.setTurnoverBanding("A: 0 to 1M");
		customer1.setTurnoverBanding(PursuitMeta.allTurnovers());
		// Matching Region
		source.setRegion("Channel Islands");
		customer1.setRegion(PursuitMeta.allRegions());
		// Nearly Matching Solution
		LinkedHashSet<String>sourceSolution=new LinkedHashSet<String>();
		sourceSolution.add("ERP");
		source.setSolutions(sourceSolution);
		LinkedHashSet<String>customerSolution=new LinkedHashSet<String>();
		customerSolution.add("ERP");
		customer1.setSolutions(customerSolution);
		// Should do the match
		assertTrue(source.doMatch());
		// Should also match
		assertTrue(source.matchesCustomer(customer1));
	}
}
