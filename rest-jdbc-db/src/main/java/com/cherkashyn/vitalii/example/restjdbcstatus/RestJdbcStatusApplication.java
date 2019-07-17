package com.cherkashyn.vitalii.example.restjdbcstatus;

import com.cherkashyn.vitalii.example.restjdbcstatus.domain.DeployStatus;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class RestJdbcStatusApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestJdbcStatusApplication.class, args);
	}


	@Bean
	RepositoryRestConfigurerAdapter deployStatusConfiguration(){
		return new RepositoryRestConfigurerAdapter(){
			public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
				config.exposeIdsFor(DeployStatus.class);
			}
		};
	}

}
