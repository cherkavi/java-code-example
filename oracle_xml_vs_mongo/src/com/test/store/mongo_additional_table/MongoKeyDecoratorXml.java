package com.test.store.mongo_additional_table;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.test.store.additional_table.KeyDecoratorXml;

/**
 * SQL environment:
 * -- create table xml_table ( id number(8), xml_document XMLType, constraint pk_xml_table PRIMARY KEY(id) )

	create table xml_store_key
	(
	id number(8),
	id_xml_store number,
	key_name varchar2(100),
	key_value varchar2(100),
	constraint pk_xml_store_key PRIMARY KEY(id)
	)
 *
 */
public class MongoKeyDecoratorXml extends KeyDecoratorXml{
	/**  Mongo collection key for FK to JDBC.TableName.ID*/
	private static final String mongoCollectionKey="jdbc_id";
	
	/** mongo database */
	private DB mongoDB;
	/** mongo collection  */
	private DBCollection mongoCollection;
	
	
	private String xmlRootElement;
	
	public MongoKeyDecoratorXml(Connection connection, 
			String tableName, 
			String sequenceName,
			Mongo mongo,
			String mongoTableName, 
			String xmlRoot) {
		this(connection, tableName, sequenceName, mongo, mongoTableName, xmlRoot, null);
	}
	
	public MongoKeyDecoratorXml(Connection connection, 
								String tableName, 
								String sequenceName, 
								Mongo mongo,
								String mongoTableName, 
								String xmlRoot,
								String mongoDatabaseName) {
		super(connection, tableName, sequenceName, mongoTableName, xmlRoot);
		this.xmlRootElement=xmlRoot;
		if(mongoDatabaseName!=null){
			mongoDB=mongo.getDB(mongoTableName);
		}else{
			mongoDB=mongo.getDB("test");
		}
		this.mongoCollection=mongoDB.getCollection(mongoTableName);
	}
	
	private Long convertObjectToLong(Object jdbcId){
		long id=0;
		if(jdbcId instanceof Long){
			id=(Long)jdbcId;
		}else{
			id=Long.parseLong(StringUtils.trimToEmpty(jdbcId.toString()));
		}
		return id;
	}
	
	@Override
	protected List<Long> readKeys(String property, String value)
			throws SQLException {
		// read data from Mongo
		// DBCursor cursor=this.mongoCollection.find(new BasicDBObject(propertyName,".*"+condition+".*"));
		// DBCursor cursor=this.mongoCollection.find(new BasicDBObject(propertyName, condition));
		Map<String, String> regmap=new HashMap<String, String>();
		regmap.put("$regex", value);
		DBCursor cursor=this.mongoCollection.find(new BasicDBObject(property, 
																	regmap)
												  );
												  // .skip(500);
												  // .limit(100);
		List<Long> keys=new LinkedList<Long>();
		while(cursor.hasNext()){
			Object jdbcId=cursor.next().get(mongoCollectionKey);
			keys.add(convertObjectToLong(jdbcId));
			
		}
		return keys;
	}
	
	@Override
	protected List<String> readValues(List<Long> keys) throws SQLException {
		List<String> returnValue=new ArrayList<String>(keys.size());
		Iterator<Long> iterator=keys.iterator();
		while(iterator.hasNext()){
			returnValue.add(this.readXmlById(iterator.next()));
		}
		return returnValue;
	}
	
	private PreparedStatement readByIdStatement=null;
	
	private String readXmlById(Long jdbcId) {
		ResultSet rs=null;
		try{
			if(readByIdStatement==null){
				StringBuilder sql=new StringBuilder();
				sql.append("select id, to_clob(xml_document) from ").append(tableName).append(" where id=?");
				readByIdStatement=connection.prepareStatement(sql.toString());
			}
			readByIdStatement.clearParameters();
			readByIdStatement.setLong(1, jdbcId);
			rs=readByIdStatement.executeQuery();
			if(rs.next()){
				return rs.getString(2);
			}
			throw new SQLException("value does not found !!! ");
		}catch(SQLException ex){
			System.err.println("Read data from JDBC ("+jdbcId+") Exception:"+ex.getMessage());
			return null;
		}finally{
			try{
				rs.close();
			}catch(Exception ex){};
		}
	}

	@Override
	public void clearData() throws SQLException {
		this.truncateTable(this.tableName);
		// clear MongoDB.Collection
		this.mongoCollection.remove(new BasicDBObject());
	}
	
	public long writeXml(String xmlBody) throws SQLException, IOException{
		long id=writeXmlBody(xmlBody);
		
		Map<String, String> keyMap=parseXmlBody(xmlBody, xmlRootElement);
		writeToKeyStore(id, keyMap);
		return id;
	}

	private void writeToKeyStore(long key, Map<String, String> keyMap) throws SQLException {
		// add keyMap 
		keyMap.put(mongoCollectionKey, Long.toString(key));
		// insert to Mongo
		this.mongoDB.requestStart();
		this.mongoCollection.insert(new BasicDBObject(keyMap));
		this.mongoDB.requestDone();
	}

	/**
	 * @param xmlBody
	 * @return - map of leaf-name leaf-value of second level 
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> parseXmlBody(String xmlBody, String rootName){
		try{
			Document document=getXmlByPathWithoutDtd(xmlBody);
			Element rootElement=this.getRootElement(document);// document.getElementById(rootName); // (Element)document.getChildNodes().item(0);
			NodeList nodeList=rootElement.getChildNodes();
			Map<String, String> returnValue=new HashMap<String, String>();
			for( int index=0; index<nodeList.getLength();index++){
				Node node=nodeList.item(index);
				if(node instanceof Element){
					Element nextElement=(Element)node;
					returnValue.put(nextElement.getTagName(),
									StringUtils.trimToEmpty(nextElement.getTextContent()));
				}
			}
			return returnValue;
		}catch(Exception ex){
			System.err.println("parse XML Exception:"+ex.getMessage());
			return Collections.EMPTY_MAP;
		}
	}
	
	private Element getRootElement(Document document){
		NodeList list=document.getChildNodes();
		Element returnValue=null;
		for(int index=0;index<list.getLength();index++){
			if(list.item(index) instanceof Element){
				returnValue=(Element)list.item(index);
				break;
			}
		}
		return returnValue;
	}
	
	
	private Document getXmlByPathWithoutDtd(String xmlString) throws Exception{
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();

	        builder.setEntityResolver(new EntityResolver() {
	            @Override
	            public InputSource resolveEntity(String publicId, String systemId)
	                    throws SAXException, IOException {
	                System.out.println("Ignoring " + publicId + ", " + systemId);
	                return new InputSource(new StringReader(""));
	            }
	        });
	        return builder.parse(new InputSource(new StringReader(xmlString)));	
	  }
	
	
}
