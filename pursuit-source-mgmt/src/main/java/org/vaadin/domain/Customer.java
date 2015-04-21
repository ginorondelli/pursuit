package org.vaadin.domain;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.vaadin.neo4j.Utils;

@NodeEntity
public class Customer extends AbstractPositionableEntity implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String customerName;
	
	@Fetch
	private Project project;
	
	private String partnerType;
	private Set<Customer> crossOverExclusions;
	
	private Set<String> customerSectors;
	// Size criteria //
	private Set<String> turnoverBanding;
	private Set<String> employeeBanding;
	private Set<String> userNumbers;

	//Geographical Criteria
	private Set<String> region;
	private Set<String> postCodes;
	
	private Set<String> solutions;
	private Set<String> consultancy;
	private Set<String> managedServices;
	private Set<String> telecoms;
	
    @Fetch @RelatedToVia(type="CUSTOMER_SOURCE", direction = Direction.BOTH)
    Set <CustomerSourceStatus> customerSources;
    

    public CustomerSourceStatus customerSource(Source source, String status) {
    	CustomerSourceStatus customerSource = new CustomerSourceStatus(this, source, status);
    	customerSources.add(customerSource);
    	return customerSource;
    }
    
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getPartnerType() {
		return partnerType;
	}
	public Set<String> getCustomerSectors() {
		return customerSectors;
	}
	public void setCustomerSectors(Set<String> customerSectors) {
		this.customerSectors = Utils.enforceSetOrder(customerSectors);
	}
	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}
	public Set<Customer> getCrossOverExclusions() {
		return crossOverExclusions;
	}
	public void setCrossOverExclusions(Set<Customer> crossOverExclusions) {
		this.crossOverExclusions = crossOverExclusions;
	}

	public Set<String> getTurnoverBanding() {
		return turnoverBanding;
	}
	public void setTurnoverBanding(Set<String> turnoverBanding) {
		this.turnoverBanding = Utils.enforceSetOrder(turnoverBanding);
	}
	public Set<String> getEmployeeBanding() {
		return employeeBanding;
	}
	public void setEmployeeBanding(Set<String> employeeBanding) {
		this.employeeBanding = employeeBanding;
	}
	public Set<String> getUserNumbers() {
		return userNumbers;
	}
	public void setUserNumbers(Set<String> userNumbers) {
		this.userNumbers = userNumbers;
	}
	public Set<String> getRegion() {
		return region;
	}
	public void setRegion(Set<String> region) {
		this.region = region;
	}
	public Set<String> getPostCodes() {
		return postCodes;
	}
	public void setPostCodes(Set<String> postCodes) {
		this.postCodes = postCodes;
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
	@Override
	public String toString() {
		return customerName;
	}
}
