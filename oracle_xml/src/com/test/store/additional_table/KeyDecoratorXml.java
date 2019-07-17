package com.test.store.additional_table;

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

import com.test.store.pure_xml.WriteReadXml;

/**
 * SQL environment:
 * -- create table xml_table ( id number(8), xml_document XMLType, constraint pk_xml_table PRIMARY KEY(id) )
 * 
 * 
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
public class KeyDecoratorXml extends WriteReadXml{

	/** name of table with keyStore */
	private String tableKeyStore;
	private String xmlRootElement;
	
	/**
	 * Xml store with external Keys
	 * @param connection - JDBC connection
	 * @param tableName - name of table for store XML 
	 * @param sequenceName - name of sequence for generate the ID
	 * @param tableKeyStore - table for store the keys
	 * @param xmlRoot - name of root element for XML file 
	 */
	public KeyDecoratorXml(Connection connection, 
						   String tableName, 
						   String sequenceName, 
						   String tableKeyStore, 
						   String xmlRoot) {
		super(connection, tableName, sequenceName);
		this.tableKeyStore=tableKeyStore;
		this.xmlRootElement=xmlRoot;
	}
	
	@Override
	public void clearData() throws SQLException {
		super.clearData();
		this.truncateTable(this.tableKeyStore);
	}
	
	public long writeXml(String xmlBody) throws SQLException, IOException{
		long id=super.writeXml(xmlBody);
		Map<String, String> keyMap=parseXmlBody(xmlBody, xmlRootElement);
		writeToKeyStore(id, keyMap);
		return id;
	}

	private void writeToKeyStore(long key, Map<String, String> keyMap) throws SQLException {
		PreparedStatement ps= connection.prepareStatement("insert into "+tableKeyStore+" (id, id_xml_store, key_name, key_value) values (?, ?, ?, ?)");
		Iterator<String> keyIterator=keyMap.keySet().iterator();
		while(keyIterator.hasNext()){
			ps.clearParameters();
			ps.setLong(1, this.getSequenceValue(connection));
			ps.setLong(2, key );
			String mapKey=keyIterator.next();
			String mapValue=keyMap.get(mapKey);
			ps.setString(3, mapKey);
			ps.setString(4, StringUtils.trimToEmpty(mapValue));
			ps.executeUpdate();
			connection.commit();
		}
		ps.close();
	}

	@Override
	public List<String> readXml(Connection connection,
								String xpath, 
								String condition) throws SQLException {
		String propertyName=StringUtils.substringAfterLast(xpath, "/");
		return readValues(readKeys(propertyName, condition));
	}
	
	protected List<Long> readKeys(String property, String value) throws SQLException {
		List<Long> returnValue=new LinkedList<Long>();
		PreparedStatement ps=null;
		try{
			ps=this.connection.prepareStatement("select id_xml_store from "+tableKeyStore+" where key_name like ? and key_value like ?");
			ps.setString(1, property);
			ps.setString(2, "%"+value+"%");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				returnValue.add(rs.getLong(1));
			}
		}finally{
			try{
				ps.close();
			}catch(Exception ex){
			}
		}
		return returnValue;
	}
	
	protected List<String> readValues(List<Long> keys) throws SQLException{
		if(keys.isEmpty()){
			return new ArrayList<String>();
		}
		ResultSet rs=null;
		try{
			List<String> returnValue=new LinkedList<String>();
			StringBuilder sql=new StringBuilder();
			
			sql.append("select id, to_clob(xml_document) from ").append(tableName).append(" where id in (");
			final String lineDelimiter=",\n";
			boolean isFirst=true;
			Iterator<Long> keyIterator=keys.iterator();
			while(keyIterator.hasNext()){
				Long nextValue=keyIterator.next();
				if(!isFirst){
					sql.append(lineDelimiter);
				}
				sql.append(nextValue);
				isFirst=false;
			}
			sql.append(")");
			rs=connection.createStatement().executeQuery(sql.toString());
			while(rs.next()){
				returnValue.add(rs.getString(2));
			}
			return returnValue;
		}finally{
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
		}
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
									nextElement.getTextContent());
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
