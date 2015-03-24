package org.vaadin.domain;

import java.io.Serializable;

import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Project extends AbstractPositionableEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1283872605154273187L;
	private String name;

    public Project() {
    }

    public Project(String name, int x, int y) {
        super(x, y);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String projectName) {
        this.name = projectName;
    }

    @Override
    public String toString() {
        return name;
    }

}
