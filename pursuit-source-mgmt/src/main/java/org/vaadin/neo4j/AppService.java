package org.vaadin.neo4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.domain.Customer;
import org.vaadin.domain.CustomerSourceStatus;
import org.vaadin.domain.Person;
import org.vaadin.domain.Project;
import org.vaadin.domain.Source;
import org.vaadin.neo4j.repository.CustomerRepository;
import org.vaadin.neo4j.repository.CustomerSourceStatusRepository;
import org.vaadin.neo4j.repository.SourceRepository;

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
    
    @Autowired
    CustomerSourceStatusRepository customerSourceRepository;

    public Person getUserByUserName(String userName) {
    	return personRepository.findByUserName(userName);
    }
    
    public List<Person> allAsList() {
        return personRepository.findAll(new Sort("name")).as(ArrayList.class);
    }

    public List<Project> listAllProjects() {
        return projectRepository.findAll(new Sort("projectName")).as(ArrayList.class);
    }

    public Source getSource(Long id) {
    	return sourceRepository.findById(id);
    }
    public Customer getCustomer(Long id) {
    	return customerRepository.findById(id);
    }
    public Set<Customer> getCustomerMatches(Source source) {
    	Source reAttached = sourceRepository.findById(source.getId());
    	Set<Customer>matches=new HashSet<Customer>();
    	if (reAttached.doMatch()) {
    		for (Customer customer: customerRepository.findAll()) {
    			if (reAttached.matchesCustomer(customer)) {
    				matches.add(customer);
    			}
    		}
    	}
    	return matches;
    }

    public void save(Person entity) {
        personRepository.save(entity);
    }
    
    public Source save(Source entity) {
        return sourceRepository.save(entity);
    }
    
    public Customer save(Customer entity) {
        return customerRepository.save(entity);
    }
    
    public CustomerSourceStatus save(CustomerSourceStatus entity) {
        return customerSourceRepository.save(entity);
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
