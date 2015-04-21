package org.vaadin.domain;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.vaadin.neo4j.Utils;
@NodeEntity
public class Source extends AbstractPositionableEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String sourceName;
	@Fetch
	private Person agentSourced;
	@Fetch
	private Person agentAssigned;

    @Fetch @RelatedToVia(type="CUSTOMER_SOURCE", direction = Direction.BOTH)
    Set <CustomerSourceStatus> customerSources;
    
	private LinkedHashSet<String>sourceSectors; 
	
	//Size of source
	private String turnoverBanding;
	private String employeeBanding;
	
	//Geographical Criteria
	private String region;
	private String postCodes;
	
	private Set<String> solutions;
	private Set<String> consultancy;
	private Set<String> managedServices;
	private Set<String> telecoms;

    public CustomerSourceStatus customerSource(Customer customer, String status) {
    	CustomerSourceStatus customerSource = new CustomerSourceStatus(customer, this, status);
    	customerSources.add(customerSource);
    	return customerSource;
    }
	
	public Set<String> getSourceSectors() {
		return sourceSectors;
	}
	public void setSourceSectors(LinkedHashSet<String> sourceSectors) {
		this.sourceSectors = Utils.enforceSetOrder(sourceSectors);
	}	
	
	public String getTurnoverBanding() {
		return turnoverBanding;
	}
	public void setTurnoverBanding(String turnoverBanding) {
		this.turnoverBanding = turnoverBanding;
	}
	public String getEmployeeBanding() {
		return employeeBanding;
	}
	public void setEmployeeBanding(String employeeBanding) {
		this.employeeBanding = employeeBanding;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getPostCodes() {
		return postCodes;
	}
	public void setPostCodes(String postCodes) {
		this.postCodes = postCodes;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public Person getAgentSourced() {
		return agentSourced;
	}

	public void setAgentSourced(Person agentSourced) {
		this.agentSourced = agentSourced;
	}

	public Person getAgentAssigned() {
		return agentAssigned;
	}

	public void setAgentAssigned(Person agentAssigned) {
		this.agentAssigned = agentAssigned;
	}

	public Set<String> getSolutions() {
		return solutions;
	}
	public void setSolutions(Set<String> solutions) {
		this.solutions = solutions;
	}
	public Set<String> getConsultancy() {
		return consultancy;
	}
	public void setConsultancy(Set<String> consultancy) {
		this.consultancy = consultancy;
	}
	public Set<String> getManagedServices() {
		return managedServices;
	}
	public void setManagedServices(Set<String> managedServices) {
		this.managedServices = managedServices;
	}
	public Set<String> getTelecoms() {
		return telecoms;
	}
	public void setTelecoms(Set<String> telecoms) {
		this.telecoms = telecoms;
	}
	
	public Set<CustomerSourceStatus> getCustomerSources() {
		return customerSources;
	}
	public void setCustomerSources(Set<CustomerSourceStatus> customerSources) {
		this.customerSources = customerSources;
	}
	/** 
	 * Contains the algorithm for matching this Source with a given Customer
	 * Brevity in style and likely some performance is sacrificed for readability. 
	 * @param customer
	 * @return
	 */
	public boolean matchesCustomer(Customer customer) {

		/*
		 *  Algo for matching as explained by P Byrne
		 * 	not name
		 * 	not Agent source
		 *	Any source sector matches 
		 *	AND
		 *	(Size of source OR employee)
		 *	AND 
		 *	(Region OR postcode)
		 *	AND 
		 *	(one of solutions OR one of consultancy OR one of managed services OR one of telecoms) == Match
		 *
		 */
		
		boolean matchesSoFar = false;
		//Any source sector
		matchesSoFar=Utils.checkContentsOfCollections(sourceSectors, customer.getCustomerSectors());
		// AND Either employeeBanding OR turnoverBanding
		if (matchesSoFar) {
			if (Utils.checkContentsOfCollections(turnoverBanding, customer.getTurnoverBanding())||
					Utils.checkContentsOfCollections(employeeBanding, customer.getEmployeeBanding())) {
				matchesSoFar=true;
			} else {
				matchesSoFar=false;
			}
		}
		// AND Either Region OR Postcode
		if (matchesSoFar) {
			if (Utils.checkContentsOfCollections(region, customer.getRegion())||
					Utils.checkContentsOfCollections(postCodes, customer.getPostCodes())) {
				matchesSoFar=true;
			} else {
				matchesSoFar=false;
			}
			
		}
		// AND Any Solutions OR Any Consultancy OR Any Managed Service OR Any Telecoms
		if (matchesSoFar) {
			if (Utils.checkContentsOfCollections(solutions, customer.getSolutions())||
					Utils.checkContentsOfCollections(consultancy, customer.getConsultancy())||
					Utils.checkContentsOfCollections(telecoms, customer.getTelecoms())) {
				matchesSoFar=true;
			} else {
				matchesSoFar=false;
			}
		}

	return matchesSoFar;
	}
	
	public boolean doMatch() {
		return (null!=sourceSectors&&sourceSectors.size()>0&&
				null!=turnoverBanding||null!=employeeBanding&&
				null!=region||null!=postCodes&&
				null!=solutions&&solutions.size()>0&&null!=consultancy&&consultancy.size()>0&&null!=managedServices&&managedServices.size()>0&&null!=telecoms&&telecoms.size()>0);
	}
	
	@Override
	public String toString() {
		return sourceName;
	}
	
}
