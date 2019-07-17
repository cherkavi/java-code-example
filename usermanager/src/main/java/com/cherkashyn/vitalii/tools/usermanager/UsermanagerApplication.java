package com.cherkashyn.vitalii.tools.usermanager;

import com.cherkashyn.vitalii.tools.usermanager.repository.UserRORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UsermanagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(UsermanagerApplication.class, args);
	}

	@Bean
	CommandLineRunner getUserByLoginAndPassword(@Autowired UserRORepository repository){
		return (args)->{
			System.out.println(String.format("not existing user: %s", repository.getUser("empty", "null")));
			System.out.println(String.format("existing user: %s", repository.getUser("cherkavi", "cherkavi")));
		};
	}

	@Bean
    String executeCommandLineRunner(@Qualifier("getUserByLoginAndPassword") CommandLineRunner commandLineRunner) throws Exception {
        commandLineRunner.run("");
        return "executed";
    }
}
