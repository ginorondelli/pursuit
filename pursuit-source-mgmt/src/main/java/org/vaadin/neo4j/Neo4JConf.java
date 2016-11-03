package org.vaadin.neo4j;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.shell.ShellSettings;
//import org.neo4j.shell.ShellSettings;
//import org.neo4j.kernel.GraphDatabaseAPI;
//import org.neo4j.server.WrappingNeoServerBootstrapper;
//import org.neo4j.server.configuration.Configurator;
//import org.neo4j.server.configuration.ServerConfigurator;
//import org.neo4j.shell.ShellSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.vaadin.domain.Person;
import org.vaadin.domain.Project;
import org.vaadin.neo4j.repository.SourceRepository;

@Configuration
@EnableNeo4jRepositories("org.vaadin.neo4j")
@EnableTransactionManagement
public class Neo4JConf extends Neo4jConfiguration { //implements CommandLineRunner {
//	
//  private static final String DBNAME = "/home/tomcat/pursuit1.db";
  private static final String DBNAME = System.getProperty("user.home") + "/pursuit-source-mgmt.db";
  private Logger logger =  Logger.getLogger(Neo4JConf.class);


    public Neo4JConf() {
        setBasePackage("org.vaadin.domain");
        logger.info("Connecting to Neo4j database @: "+DBNAME);
    }

    @Bean(destroyMethod = "shutdown")
    GraphDatabaseService graphDatabaseService() {
    	return new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(DBNAME)
                .setConfig(ShellSettings.remote_shell_enabled, "true")
                .setConfig(ShellSettings.remote_shell_port, "5555")
                .setConfig(ShellSettings.remote_shell_host, "0.0.0.0")
                .setConfig(GraphDatabaseSettings.allow_store_upgrade, "true")
                .newGraphDatabase();
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

    @PostConstruct
    public void initData() {
        Person superUser = new Person("Evan Morgan", "emorgan", "kqV4qNNExNs5ibc7sfXkyaEp4zVNtOYuMrzOmcelm2hq6gDeMdBfGcR6T7XC+ciV");

        try (Transaction tx = graphDatabase.beginTx()) {
            Person existing = personRepository.findByName(superUser.getName());
            if (null==existing) {
            	personRepository.save(superUser);
            	logger.info("Creating super user for the first time");
            } else {
            	logger.info("Super user already exists, not creating");
            }
           tx.success();
        }
        
    }
    
    
//    @Autowired
//    GraphDatabaseService db;


//	@SuppressWarnings("deprecation")
//	@Override
//	public void run(String... args) throws Exception {
//	       // used for Neo4j browser
//        try {
//            WrappingNeoServerBootstrapper neoServerBootstrapper;
//            GraphDatabaseAPI api = (GraphDatabaseAPI) db;
//
//            ServerConfigurator config = new ServerConfigurator(api);
//            config.configuration()
//                    .addProperty(Configurator.WEBSERVER_ADDRESS_PROPERTY_KEY, InetAddress.getLocalHost().getHostAddress());
//            config.configuration()
//                    .addProperty(Configurator.WEBSERVER_PORT_PROPERTY_KEY, "8686");
//
//            neoServerBootstrapper = new WrappingNeoServerBootstrapper(api, config);
//            neoServerBootstrapper.start();
//        } catch(Exception e) {
//            //handle appropriately
//        }
//        // end of Neo4j browser config
//		
//	}
}
