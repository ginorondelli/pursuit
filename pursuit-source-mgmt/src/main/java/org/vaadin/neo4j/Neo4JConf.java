package org.vaadin.neo4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.vaadin.domain.Person;
import org.vaadin.domain.Project;
import org.vaadin.domain.Source;
import org.vaadin.neo4j.repository.SourceRepository;

@Configuration
@EnableNeo4jRepositories("org.vaadin.neo4j")
@EnableTransactionManagement
public class Neo4JConf extends Neo4jConfiguration {

    private static final String DBNAME = System.getProperty("user.home") + "/pursuit.db";

    public Neo4JConf() {
        setBasePackage("org.vaadin.domain");
//        try {
//            // reset neo4jdb, this is a demo app...
//            FileUtils.deleteRecursively(new File(DBNAME));
//        	;
//        } catch (IOException ex) {
//            Logger.getLogger(Neo4JConf.class.getName()).log(Level.SEVERE, null,
//                    ex);
//        }
    }

    @Bean(destroyMethod = "shutdown")
    GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase(DBNAME);
    }
    
    @Bean
    AppService personService() {
        return new AppService();
    }

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ProjectRepository projectRepository;
    
    @Autowired
    SourceRepository sourceRepository;

    @Autowired
    GraphDatabaseService graphDatabase;

    /**
     * Adds some demo data to DB
     */
    @PostConstruct
    public void initData() {
        Person greg = new Person("Greg", 50, 50,"Greg Agent");
        //greg.setAgentSourced("Greg agent");
        Person roy = new Person("Roy", 50, 150, "Roy agent");
        //roy.setAgentSourced("Roy agent");
        Person craig = new Person("Craig", 50, 250, "Craig agent");
        //craig.setAgentSourced("Graig agent");
        
        Project maintenance = new Project("Maintenance", 450, 100);
        Project featureX = new Project("Feature 'X'", 450, 250);

        Source source1 = new Source();
        source1.setSourceName("Amedeo Adjusting");
        Source source2 = new Source();
        source2.setSourceName("Talisman Marketing Solutions");
        Source source3 = new Source();
        source3.setSourceName("Fishlake Commercial Motors Limited");
        
        System.out.println("Before linking up with Neo4j...");
        for (Person person : new Person[]{greg, roy, craig}) {
            System.out.println(person);
        }

        try (Transaction tx = graphDatabase.beginTx()) {
//            sourceRepository.save(source1);
//            sourceRepository.save(source2);
//            sourceRepository.save(source3);
//            
//            personRepository.save(greg);
//            roy = personRepository.save(roy);
//            craig = personRepository.save(craig);
//            maintenance = projectRepository.save(maintenance);
//            featureX = projectRepository.save(featureX);
//            
//            greg = personRepository.findByName(greg.getName());
//            greg.worksIn(maintenance);
//            greg.worksIn(featureX);
//            personRepository.save(greg);
//
//            roy.worksIn(featureX);
//            personRepository.save(roy);
//            
//            craig.worksIn(featureX);
//            personRepository.save(craig);

            tx.success();
        }
        
    }
}
