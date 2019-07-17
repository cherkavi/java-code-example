package com.test;

import com.mongodb.Mongo;
import com.test.connection.OracleConnection;
import com.test.store.IXmlSearcher;
import com.test.store.mongo_additional_table.MongoKeyDecoratorXml;

public class TestXml_MongoDB extends AbstractTestXml{


	@Override
	protected void setUp() throws Exception {
		System.out.println("begin");
		/** XPath to Leaf */
		this.findXpath="/root/property_1";
		
		this.connection=OracleConnection.getConnection("127.0.0.1", 1521, "XE", "SYSTEM", "root");

		this.xmlInDatabase=new MongoKeyDecoratorXml(
										connection,
										"xml_table",
										"temp_sequence",
										new Mongo("127.0.0.1", 27017),
										"test_mongo",
										"root",
										"test_collection");
		this.xmlSearcher=(IXmlSearcher) this.xmlInDatabase;
	}
	
}
