package com.cherkashyn.vitalii.examples.springshell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.standard.ShellOption;

@SpringBootApplication
public class SpringshellApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringshellApplication.class, args);
	}


	@Bean
	public String exampleBean(){
		return "this is my own example";
	}
}
