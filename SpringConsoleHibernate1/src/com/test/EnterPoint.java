package com.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.test.database.DatabaseFacade;
import com.test.database.dao.ActionsDao;
import com.test.database.model.Actions;

public class EnterPoint {
	public static void main(String[] args){
		System.out.println ("start");

		ApplicationContext appContext = 
    		new ClassPathXmlApplicationContext("resources/spring/config/BeanLocations.xml");
		
		ActionsDao dao = (ActionsDao)appContext.getBean("actionsDao");
		System.out.println("Dao:"+dao);
		List<Actions> listOfActions=dao.getAllActions();
		for(Actions value:listOfActions){
			System.out.println(">>>"+value.toString());
		}
		
		
		DatabaseFacade facade=(DatabaseFacade)appContext.getBean("databaseFacade");
		System.out.println("Database Facade:"+facade.getActionsDao());
		System.out.println("Database Facade.ActionDao:"+facade.getActionsDao());

		System.out.println(" end ");
	}
}
