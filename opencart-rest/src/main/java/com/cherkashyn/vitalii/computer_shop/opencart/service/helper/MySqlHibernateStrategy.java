package com.cherkashyn.vitalii.computer_shop.opencart.service.helper;

import org.hibernate.cfg.DefaultNamingStrategy;
import org.hibernate.dialect.Dialect;

public class MySqlHibernateStrategy  extends DefaultNamingStrategy{
	   private static Dialect _dialect = null;

	   @Override
	   public String classToTableName(String className)
	   {

	      System.out.println("Adding quotes to table from class "+className);
	      return addQuotes(super.classToTableName(className));
	   }
	   
	   @Override
	   public String tableName(String tableName)
	   {
	      System.out.println("Adding quotes to table "+tableName);
	      return addQuotes(super.tableName(tableName));
	   }

	   /**
	    * Adds opening and closing quotes as provided by the current dialect.
	    *
	    * @param input
	    *          the input to quote
	    * @return the qouted input
	    */
	   private static String addQuotes(String input)
	   {
	      return new StringBuffer().append(_dialect.openQuote())
	                         .append(input)
	                         .append(_dialect.closeQuote()).toString();
	   }
	}