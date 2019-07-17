package com.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;

import org.apache.commons.lang.time.StopWatch;

import com.test.connection.OracleConnection;
import com.test.xml_generator.IXmlGenerator;
import com.test.xml_generator.generators.XmlGenerator;
import com.test.xml_generator.values_generator.ISequenceValues;
import com.test.xml_generator.values_generator.impl.LetterSequenceGenerator;

import com.test.store.IXmlSearcher;
import com.test.store.XmlStore;
import com.test.store.pure_xml.WriteReadXml;

import junit.framework.TestCase;

/**
 * SQL environment:   
 * -- create table xml_table ( id number(8), xml_document XMLType, constraint pk_xml_table PRIMARY KEY(id) )
 * -- create sequence temp_sequence start with 1000 increment by 1 nocache nocycle
 * create error: CREATE INDEX xml_table_index ON xml_table (extractValue(xml_document, '/root'));
 * CREATE INDEX xml_table_index_1 ON xml_table (extractValue(xml_document, '/root/property_1'));
 */
public abstract class AbstractTestXml extends TestCase {
	
	/**
	 * generate data into Database: 
	 * @param xmlInDatabase - store for XML
	 * @param xmlGenerator - generator for XML
	 * @param maxFileCount - max file for write to Database Store
	 * @throws SQLException
	 * @throws IOException
	 */
	private void generateData(XmlStore xmlInDatabase,
							  IXmlGenerator xmlGenerator,
							  int maxFileCount) throws SQLException, IOException {
		System.out.println("    clear Data: ");
		StopWatch timer=new StopWatch();
		timer.start();
		xmlInDatabase.clearData();
		timer.stop();
		System.out.println("    done:"+timer.getTime());
		System.out.println("    generate and save data: ");
		timer.reset();timer.start();
		// xmlInDatabase.writeXml(FileUtils.readFileToString(new File("c:\\test2.xml")));
		int percentForOutput=0;
		for(int fileIndex=0;fileIndex<maxFileCount;fileIndex++){
			if(getPercent(fileIndex, maxFileCount)!=percentForOutput){
				percentForOutput=getPercent(fileIndex, maxFileCount);
				System.out.println("%"+percentForOutput+"     time: "+timer.getTime());
			}
			xmlInDatabase.writeXml(xmlGenerator.getNextXml());
		}
		timer.stop();
		System.out.println("    done: "+timer.getTime());
	}
	
	private int getPercent(int counter, int size){
		return ((int)(((double)counter/(double)size)*10))*10;
	}
	
	protected void findData(IXmlSearcher searcher,
							String xpathValue, 
						    String[] values) throws SQLException{
		System.out.println("Find data ");
		StopWatch timer=new StopWatch();
		long[] intervals=new long[values.length];
		for(int index=0;index<values.length;index++){
			String each=values[index];
			System.out.println("Next Element:"+each);
			timer.start();
			searcher.printList(searcher.readXml(this.connection, xpathValue, each));
			timer.stop();
			intervals[index]=timer.getTime();
			timer.reset();
		}
		printStatistics(intervals);
		System.out.println("-end-  :");
	}
	
	private void printStatistics(long[] intervals){
		System.out.println("results: ");
		long sum=0;
		for(int index=1;index<intervals.length;index++){
			System.out.println(index+" : "+intervals[index]);
			sum+=intervals[index];
		}
		System.out.println("Average: "+sum/(intervals.length-1));
	}
	
	/**
	 * @return
	 * <ul>
	 * 	<li><b>true</b> - need to remove all data in storage and create it</li>
	 * 	<li><b></b> - don't need to remove data, need to use existing</li>
	 * </ul>
	 */
	protected boolean isNeedToCreateData(){
		return false;
	}

	
	protected Connection connection;
	
	protected XmlStore xmlInDatabase;
	protected IXmlSearcher xmlSearcher;
	
	protected String findXpath="/root/property_1";
	//                                          14     16     16     16     
	protected String[] findValues=new String[]{"CJA", "CEF", "CBI", "CDG", "CHC"};

	/** 
	 * @return - xml table name ( from database )
	 * <br />
	 * DDL: 
	 * create table xml_table ( id number(8), xml_document XMLType, constraint pk_xml_table PRIMARY KEY(id) ) 
	 */
	protected String getTableName(){
		return "xml_table";
	}
	
	/**
	 * get generator for Values ( which stored in XML )
	 * @return
	 */
	protected ISequenceValues getSequenceGenerator(){
		 return new LetterSequenceGenerator(
				 	(byte)3,  // xxxx 
				 	(char)65, // A - 65 
				 	(char)75  // Z - 90 
				 	);
	}
	
	/**
	 * get generator of XML text 
	 * @return
	 */
	protected IXmlGenerator getXmlGenerator(){
		return new XmlGenerator(10,  // <property_0..9
							    "property_", // <property_
							    this.getSequenceGenerator()
								);
	}
	
	@Override
	protected void setUp() throws Exception {
		System.out.println("begin");
		/** XPath to Leaf */
		this.connection=OracleConnection.getConnection("127.0.0.1", 1521, "XE", "SYSTEM", "root");
		this.xmlInDatabase=new WriteReadXml(connection, this.getTableName(), "temp_sequence");
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

	/**
	 * @return
	 */
	protected int getMaxRecordCount(){
		return 20000;
	}
	
	public void testMain() throws Exception{
		if(this.isNeedToCreateData()){
			System.out.println("Need to generate the data ");
			this.generateData(xmlInDatabase, 
							  this.getXmlGenerator(), 
							  this.getMaxRecordCount());
		}
		try{
			findData(this.xmlSearcher, 
					 this.findXpath, 
					 this.findValues);
		}catch(Exception ex){
			System.err.println("Exception:"+ex.getMessage());
		}
		
	}
	
	@Override
	protected void tearDown() throws Exception {
		if(connection!=null){
			try{
				connection.close();
			}catch(Exception ex){
				// it does no matter,  if connection not closed or not exists 
			}
			
		}
	}
}
