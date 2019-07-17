package com.test;

import java.net.URI;

import com.marklogic.xqrunner.XQResult;
import com.marklogic.xqrunner.XQRunner;
import com.marklogic.xqrunner.XQuery;
import com.marklogic.xqrunner.generic.GenericQuery;
import com.marklogic.xqrunner.xcc.XccDataSource;

public class _03_XQRunner {
	public static void main(String[] args) throws Exception {
		System.out.println("begin");

		String login="root";
		String password="root";
		String host="127.0.0.1";
		int port=8010;
		String database="test";

		String connectionString="xcc://"+login+":"+password+"@"+host+":"+port+"/"+database;
		System.out.println("ConnectionString:   "+connectionString);
		URI uri = new URI(connectionString);
		System.out.println("create DataSource");
		XccDataSource dataSource=new XccDataSource(host, port, login, password);

		System.out.println("create runner");
		XQRunner runner=dataSource.newSyncRunner();
		System.out.println("execute query");
		XQResult result=runner.runQuery(new GenericQuery("xquery version \"1.0-ml\"; doc()/root/property_4/text()"));
		System.out.println("Result: "+result.asString());
/*
		System.out.println("Create query");
		XQuery query=dataSource.newQuery("doc()/root/property_1/text()");
		System.out.println("Start query");
		dataSource.newAsyncRunner().startQuery(query);
*/		
		
		System.out.println("-end-");
	}
}
