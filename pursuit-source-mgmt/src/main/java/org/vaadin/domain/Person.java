package org.vaadin.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Person extends AbstractPositionableEntity  implements Serializable{

    @NotNull
	private String name;
    @NotNull
    private String userName;
    @NotNull
    private String passWord;
    
    @RelatedTo(type = "PROJECT", direction = Direction.OUTGOING)
    @Fetch
    public Set<Project> projects;

    public Person() {
    }

    public Person(String name, int x, int y) {
        super(x,y);
        this.name = name;
    }
    
    public Person(String name, String userName, String passWord) {
		super();
		this.name = name;
		this.userName = userName;
		this.passWord = passWord;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return name;
    }

}
