package com.cherkashyn.vitali.example.restfileserver;

import com.cherkashyn.vitali.example.restfileserver.service.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class RestfileserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfileserverApplication.class, args);
	}

}
