package org.vaadin.neo4j;

import java.net.InetAddress;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.neo4j.server.configuration.Configurator;
import org.neo4j.server.configuration.ServerConfigurator;
import org.neo4j.shell.ShellSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.vaadin.neo4j.repository.SourceRepository;

@Configuration
@EnableNeo4jRepositories("org.vaadin.neo4j")
@EnableTransactionManagement
public class Neo4JConf extends Neo4jConfiguration implements CommandLineRunner {
//	
//  private static final String DBNAME = "/home/tomcat/pursuit1.db";
  private static final String DBNAME = System.getProperty("user.home") + "/pursuit1.db";

    public Neo4JConf() {
        setBasePackage("org.vaadin.domain");

    }

    @Bean(destroyMethod = "shutdown")
    GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(DBNAME)
                .setConfig(ShellSettings.remote_shell_enabled, "true")
                .setConfig(ShellSettings.remote_shell_port, "5555")
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


    @Autowired
    GraphDatabaseService db;


	@SuppressWarnings("deprecation")
	@Override
	public void run(String... args) throws Exception {
	       // used for Neo4j browser
        try {
            WrappingNeoServerBootstrapper neoServerBootstrapper;
            GraphDatabaseAPI api = (GraphDatabaseAPI) db;

            ServerConfigurator config = new ServerConfigurator(api);
            config.configuration()
                    .addProperty(Configurator.WEBSERVER_ADDRESS_PROPERTY_KEY, InetAddress.getLocalHost().getHostAddress());
            config.configuration()
                    .addProperty(Configurator.WEBSERVER_PORT_PROPERTY_KEY, "8686");

            neoServerBootstrapper = new WrappingNeoServerBootstrapper(api, config);
            neoServerBootstrapper.start();
        } catch(Exception e) {
            //handle appropriately
        }
        // end of Neo4j browser config
		
	}
}
