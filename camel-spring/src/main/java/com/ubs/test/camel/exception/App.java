package com.ubs.test.camel.exception;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.model.RoutesDefinition;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	private final static String SPRING_CONTEXT_PATH="classpath:/applicationContext.xml";
	private final static String ROUTE="route_1.xml";
	
    public static void main( String[] args ) throws Exception
    {
        ApplicationContext springContext=new ClassPathXmlApplicationContext(SPRING_CONTEXT_PATH);
        // System.out.println("Context: "+springContext);
        
        // CamelContext camelContext = new DefaultCamelContext();
        CamelContext camelContext = createCamelContext(springContext);
        
        fillCamelContext(camelContext);
        
        try{
            camelContext.start();
            TimeUnit.SECONDS.sleep(10L);
            
        }finally{
            camelContext.stop();        
        }
        
    }
    
    
    @SuppressWarnings("deprecation")
	private static void fillCamelContext(CamelContext camelContext) throws Exception {
        InputStream is = App.class.getClassLoader().getResourceAsStream(ROUTE);
        RoutesDefinition routes = camelContext.loadRoutesDefinition(is);
        camelContext.addRouteDefinitions(routes.getRoutes());
	}

	private static CamelContext createCamelContext(ApplicationContext context){
    	return new SpringCamelContext(context);
    }
	
}
