package org.vaadin.neo4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.domain.Customer;
import org.vaadin.domain.Person;
import org.vaadin.domain.Project;
import org.vaadin.domain.Source;
import org.vaadin.neo4j.repository.CustomerRepository;
import org.vaadin.neo4j.repository.SourceRepository;

import com.google.gwt.thirdparty.guava.common.base.Predicates;
import com.google.gwt.thirdparty.guava.common.collect.Collections2;

@Service
@Transactional
public class AppService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ProjectRepository projectRepository;
    
    @Autowired
    SourceRepository sourceRepository;
    
    @Autowired
    CustomerRepository customerRepository;

    public List<Person> allAsList() {
        return personRepository.findAll(new Sort("name")).as(ArrayList.class);
    }

    public List<Project> listAllProjects() {
        return projectRepository.findAll(new Sort("projectName")).as(ArrayList.class);
    }
    
    public Set<Customer> getCustomerMatches(Source source) {
    	Source reAttached = sourceRepository.findById(source.getId());
    	return customerRepository.getCustomerMatches(reAttached.getSourceName(), reAttached.getSourceSectors(), reAttached.getTurnoverBanding());
    }

    public void save(Person entity) {
        personRepository.save(entity);
    }
    
    public void save(Source entity) {
        sourceRepository.save(entity);
    }
    
    public void save(Customer entity) {
        customerRepository.save(entity);
    }
    
      
    public List<Source> listAllSources() {
    	return sourceRepository.findAll(new Sort("sourceName")).as(ArrayList.class);
    }
    public List<Customer> listAllCustomers() {
    	return customerRepository.findAll(new Sort("customerName")).as(ArrayList.class);
    }
    
    @SuppressWarnings("unchecked")
	public List<Customer> allCustomersExceptThis(Customer customer) {
		List<Customer>allBut=new ArrayList<Customer>();
		for (Customer current : listAllCustomers()) {
			if (current.getId()!=customer.getId()) {
				allBut.add(current);
			}
		}
    	return allBut;
   }

}
