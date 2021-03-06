package org.vaadin.neo4j;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.vaadin.domain.Person;

public interface PersonRepository extends GraphRepository<Person> {

    Person findByName(String name);
    
    Person findByUserName(String userName);

    Iterable<Person> findByProjectsName(String name);
    
}
