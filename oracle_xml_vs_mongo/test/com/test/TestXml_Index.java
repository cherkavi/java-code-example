package com.test;

import com.test.connection.OracleConnection;
import com.test.store.IXmlSearcher;
import com.test.store.pure_xml.WriteReadXml;
import com.test.xml_generator.values_generator.ISequenceValues;

public class TestXml_Index extends AbstractTestXml {

	@Override
	protected ISequenceValues getSequenceGenerator() {
		return super.getSequenceGenerator();
	}

	protected String[] getFindValues(){
		//                         CEF
		return new String[]{"CJA", "yyy", "CBI", "CDG", "z1z"};
	}
	
	@Override
	protected boolean isNeedToCreateData() {
		return false;
	}
	
	@Override
	protected String getTableName() {
		return "xml_table3";
	}
	
	@Override
	protected void setUp() throws Exception {
		System.out.println("begin");
		findXpath="/root/property_4";
		/** XPath to Leaf */
		this.connection=OracleConnection.getConnection("127.0.0.1", 1521, "XE", "SYSTEM", "root");
		this.findValues=this.getFindValues();
		this.xmlInDatabase=new WriteReadXml(connection, 
											this.getTableName(), 
											"temp_sequence");
		this.xmlSearcher=(IXmlSearcher) this.xmlInDatabase;
		
//		this.xmlInDatabase=new KeyDecoratorXml(connection, 
//		  "xml_table", 
//		  "temp_sequence",
//		  "xml_store_key",
//		  "root");

//XmlStore xmlInDatabase=new MongoKeyDecoratorXml(
//		connection,
//		"xml_table",
//		"temp_sequence",
//		new Mongo("127.0.0.1", 27017),
//		"test_mongo",
//		"root",
//		"test_collection");
	}
	
}
