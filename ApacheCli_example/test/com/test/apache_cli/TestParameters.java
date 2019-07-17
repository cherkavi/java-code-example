package com.test.apache_cli;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.test.apache_cli.CliHelper.ParserException;

public class TestParameters {
	
	@Test
	public void mainTest() throws ParserException{
		System.out.println(" begin ");
		
		String inputParameter="-jdbc_url jdbc://test    -jdbc_login root    -jdbc_password pass   -mongo_host  127.0.0.1   -mongo_port 27017   -mongo_db db_test   -update -mongo_collection col1  ";
		Map<String, Object> parameters=CliHelper
			.buildParser()
			.appendParameter("jdbc_url", CliHelper.Type.String, true)
			.appendParameter("jdbc_login", CliHelper.Type.String, true)
			.appendParameter("jdbc_password", CliHelper.Type.String, true)
			.appendParameter("mongo_host", CliHelper.Type.String, true)
			.appendParameter("mongo_port", CliHelper.Type.Integer, true)
			.appendParameter("mongo_db", CliHelper.Type.String, true)
			.appendParameter("mongo_collection", CliHelper.Type.String, true)
			.appendParameter("update", CliHelper.Type.Void, false)
			.appendParameter("create", CliHelper.Type.Void, false)
			.parseString(StringUtils.split(inputParameter," "))
			;

		System.out.println("jdbc_url: "+parameters.get("jdbc_url"));
		System.out.println("jdbc_login: "+parameters.get("jdbc_login"));
		System.out.println("jdbc_password: "+parameters.get("jdbc_password"));
		System.out.println("mongo_host: "+parameters.get("mongo_host"));
		System.out.println("mongo_port: "+parameters.get("mongo_port"));
		System.out.println("mongo_db: "+parameters.get("mongo_db"));
		System.out.println("mongo_collection: "+parameters.get("mongo_collection"));
		System.out.println("update: "+parameters.containsKey("update"));
		System.out.println("create: "+parameters.containsKey("create"));
			
		System.out.println(" -end- ");
	}
}
