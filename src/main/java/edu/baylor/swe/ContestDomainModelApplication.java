package edu.baylor.swe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import edu.baylor.swe.models.Contest;
import edu.baylor.swe.models.Person;
import edu.baylor.swe.models.Team;
import edu.baylor.swe.repositories.AmazonApplicationRepository;
import edu.baylor.swe.repositories.ContestRepository;
import edu.baylor.swe.validations.ContestValidator;
import edu.baylor.swe.validations.TeamValidator;


//@EnableWebSocket
//@EnableJpaRepositories("edu.baylor.swe.repositories")
@SpringBootApplication
public class ContestDomainModelApplication implements WebMvcConfigurer, RepositoryRestConfigurer {//, CommandLineRunner {

//	@Autowired
//	private ContestValidator contestValidator;
//	
//	@Autowired
//	private TeamValidator teamValidator;
	
	@Autowired
	private DataPopulator dataPopulator;
	
	@Autowired
	ContestRepository contestRepo;
	
	@Autowired
	AmazonApplicationRepository amazonRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(ContestDomainModelApplication.class, args);
	}

//	@Override
//	public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
//		validatingListener.addValidator("beforeSave", contestValidator);
//		validatingListener.addValidator("beforeCreate", teamValidator);
//		RepositoryRestConfigurer.super.configureValidatingRepositoryEventListener(validatingListener);
//	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
	}
	
//	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//		registry.addHandler(new SocketTeamStateHandler(), "/state").setAllowedOrigins("*");
//	}

	@Bean
	public ApplicationRunner dataLoader() {
		return args -> {
			dataPopulator.populate();
		};
	}
	
	@Bean
	public DataPopulator populator() {
		return new DataPopulator(amazonRepo);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		
//		System.err.println(contestRepo);
//		
//	}
	
//	@Bean
//	public Destination projectRequestQueue() {
//	  return new ActiveMQQueue("hiddenstore.request.queue");
//	}
	
}
