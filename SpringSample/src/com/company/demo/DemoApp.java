package com.company.demo;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;


public class DemoApp {
	private static final Logger logger = Logger.getLogger(DemoApp.class);
	{
		if(!logger.getAllAppenders().hasMoreElements()){
			BasicConfigurator.configure();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource(
				"demo-beans.xml"));

		SoftwareCompany company = (SoftwareCompany) beanFactory
				.getBean("softwareCompany");
		System.out.println("Lead Developer: " + company.getLeadDeveloper().getName());
		System.out.println("Developer     : " + company.getDeveloper().getName());
		//logger.info("Lead Developer: " + company.getLeadDeveloper().getName());
		//logger.info("Developer     : " + company.getDeveloper().getName());
	}
}
