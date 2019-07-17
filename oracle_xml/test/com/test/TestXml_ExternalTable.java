package com.test;

import com.test.connection.OracleConnection;
import com.test.store.IXmlSearcher;
import com.test.store.additional_table.KeyDecoratorXml;

public class TestXml_ExternalTable extends AbstractTestXml{


	@Override
	protected void setUp() throws Exception {
		System.out.println("begin");
		/** flag for create data into store */
		/** XPath Leaf */
		this.findXpath="/root/property_1";
		
		this.connection=OracleConnection.getConnection("127.0.0.1", 1521, "XE", "SYSTEM", "root");
		this.xmlInDatabase=new KeyDecoratorXml(connection, 
											  "xml_table", 
											  "temp_sequence",
											  "xml_store_key",
											  "root");
		this.xmlSearcher=(IXmlSearcher) this.xmlInDatabase;
		
	}
	
}
