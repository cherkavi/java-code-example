package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.test.dao_impl.PlaceDAO;
import com.test.database.db_beans.Place;

public class EnterPoint {
	public static void main(String[] args){
		System.out.println("begin");
		ApplicationContext appContext = new ClassPathXmlApplicationContext("resources/spring/config/spring_context.xml");
		PlaceDAO dao = (PlaceDAO)appContext.getBean("placeDAO");
		Place createdPlace=dao.createPlace("test place");
		System.out.println("created Place:"+createdPlace);
		System.out.println("-end-");
	}
}
