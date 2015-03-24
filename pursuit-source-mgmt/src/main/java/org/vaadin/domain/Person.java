package org.vaadin.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Person extends AbstractPositionableEntity  implements Serializable{

    private String name;
    private String agentSourced;
    
    @RelatedTo(type = "PROJECT", direction = Direction.OUTGOING)
    @Fetch
    public Set<Project> projects;

    public Person() {
    }

    public Person(String name, int x, int y, String agentSourced) {
        super(x,y);
        this.name = name;
        this.agentSourced=agentSourced;
    }

    public void worksIn(Project person) {
        if (projects == null) {
            projects = new HashSet<>();
        }
        projects.add(person);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgentSourced() {
		return agentSourced;
	}

	public void setAgentSourced(String agentSourced) {
		this.agentSourced = agentSourced;
	}

	public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Person: "+ name;
    }

}
