package org.vaadin.domain;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.vaadin.neo4j.Utils;
@NodeEntity
public class Source extends AbstractPositionableEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String sourceName;
	private String agentSourced;

    @RelatedTo(type = "CUSTOMER", direction = Direction.OUTGOING)
    @Fetch
    public Set<Customer> customers;
	
	private LinkedHashSet<String>sourceSectors; 
	
	//Size of source
	private Set<String> turnoverBanding;
	private Set<String> employeeBanding;
	
	//Geographical Criteria
	private Set<String> region;
	private Set<String> postCodes;
	
	private Set<String> solutions;
	private Set<String> consultancy;
	private Set<String> managedServices;
	private Set<String> telecoms;
	
	
	public Set<String> getSourceSectors() {
		return sourceSectors;
	}
	public void setSourceSectors(LinkedHashSet<String> sourceSectors) {
		this.sourceSectors = Utils.enforceSetOrder(sourceSectors);
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
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getAgentSourced() {
		return agentSourced;
	}
	public void setAgentSourced(String agentSourced) {
		this.agentSourced = agentSourced;
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
	
	public boolean matchesCustomer(Customer customer) {
//		EqualsBuilder equals = new EqualsBuilder();
//		equals.append(customer.getCustomerName(), getSourceName());
//	
		boolean equals = false;
		if (null != customer.getCustomerName() && null != getSourceName()
				&& !customer.getCustomerName().equals(getSourceName())) {
			return false;
		}
//		if (null!=customer.getCustomerSectors().size()==getSourceSectors().size()
//				&&!customer.getCustomerSectors().equals(getSourceSectors())) {
//			return false;
//		}
//
//		if (
//				equals=customer.getTurnoverBanding().equals(getTurnoverBanding())){
//			return false;
//		}
//		if (!customer.getEmployeeBanding().equals(getEmployeeBanding())){
//			return false;
//		}
//		if (!customer.getRegion().equals(getRegion())){
//			return false;
//		}
//		if (!customer.getPostCodes().equals(getPostCodes())){
//			return false;
//		}
//		if (!customer.getSolutions().equals(getSolutions())){
//			return false;
//		}
//		if (!customer.getConsultancy().equals(getConsultancy())){
//			return false;
//		}
//		if (!customer.getManagedServices().equals(getManagedServices())){
//			return false;
//		}
//		if (!customer.getTelecoms().equals(getTelecoms())){
//			return false;
//		}
	return equals;
	}
	
	
	@Override
	public String toString() {
		return sourceName;
	}
	
}
