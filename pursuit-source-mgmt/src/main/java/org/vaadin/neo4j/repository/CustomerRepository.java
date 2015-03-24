package org.vaadin.neo4j.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.vaadin.domain.Customer;
import org.vaadin.domain.Source;

public interface CustomerRepository extends GraphRepository<Customer> {

    Customer findByCustomerName(String customerName);
    
    Customer findById(Long id);
 

    @Query("MATCH (c:Customer) "
    		+ "WHERE c.customerName = {0} "
    		+ "AND c.customerSectors = {1} "
    		+ "AND c.turnoverBanding = {2} RETURN c")
    Set<Customer>getCustomerMatches(String sourceName, Set<String> sectors, Set<String> turnoverBanding);
    
}
