package org.vaadin.neo4j.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.vaadin.domain.Source;

public interface SourceRepository extends GraphRepository<Source> {

    Source findBySourceName(String sourceName);
    
    Source findById(Long id);
//    Iterable<Source> findByProjectsName(String name);
    
}
