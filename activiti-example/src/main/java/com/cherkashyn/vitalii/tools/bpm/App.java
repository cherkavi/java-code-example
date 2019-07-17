package com.cherkashyn.vitalii.tools.bpm;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

public class App {
	
	
    public static void main( String[] args ) throws InterruptedException{
        System.out.println("---begin---");

        // create Context
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        
        // get Runtime service 
        RuntimeService service=context.getBean(RuntimeService.class);
        
        // build process 
        ProcessInstanceBuilder processBuilder=service.createProcessInstanceBuilder().processDefinitionKey("photo-processing");
        //    add variables 
        processBuilder.addVariable("var1", "variable1");
        processBuilder.addVariable("var2", "this is second var");
        //    start process 
        processBuilder.start();
        // service.startProcessInstanceByKey("photo-processing");
        
        System.out.println("--- end ---");
    }

    
    
}
