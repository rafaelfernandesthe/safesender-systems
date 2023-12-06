package br.com.rti.apiwebhookreceiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan( "br.com.rti" )
@EntityScan( basePackages = { "br.com.rti.domain.entities" } )
@EnableJpaRepositories( "br.com.rti.domain.repositories" )
@EnableScheduling
@SpringBootApplication
@EnableAsync
public class SafesenderApiWebhookReceiverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafesenderApiWebhookReceiverApplication.class, args);
	}

}
