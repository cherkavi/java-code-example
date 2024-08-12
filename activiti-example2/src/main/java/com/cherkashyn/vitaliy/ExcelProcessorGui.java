package com.cherkashyn.vitaliy;

import java.util.logging.Logger;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExcelProcessorGui {
	public static void main(String[] args){
		Logger logger=Logger.getLogger(ExcelProcessorGui.class.getName());
		logger.info("begin");
		ClassPathXmlApplicationContext applicationContext =new ClassPathXmlApplicationContext("classpath*:application.xml");
		// RuntimeService runtimeService=applicationContext.getBean(RuntimeService.class);
		logger.info("Context load OK :"+applicationContext.getId());
		logger.info("-end-");
	}
	
	
}
