package com.test.store.pure_xml;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.test.store.IXmlSearcher;
import com.test.store.XmlStore;

public class WriteReadXml implements XmlStore, IXmlSearcher{

	protected Connection connection;
	/** name of table */
	protected final String tableName;
	/** name of sequence */
	private final String sequenceName;
	
	public WriteReadXml(Connection connection){
		this(connection, "xml_table","temp_sequence");
	}

	public WriteReadXml(Connection connection, String tableName, String sequenceName){
		this.connection=connection;
		this.tableName=tableName;
		this.sequenceName=sequenceName;
	}

	@Override
	public void clearData() throws SQLException{
		this.truncateTable(this.tableName);
	}
	
	protected void truncateTable(String tableName) throws SQLException{
		Statement statement=null;
		try{
			statement=connection.createStatement();
			statement.executeUpdate("truncate table "+tableName);
			// statement.executeUpdate("delete from "+tableName);
			connection.commit();
		}finally{
			statement.close();
		}
	}
	
	/**
	 * get next value from sequence  
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	protected Long getSequenceValue(Connection connection) throws SQLException{
		// System.out.println("getSequenceValue");
		ResultSet rs=null;
		try{
			rs=connection.createStatement().executeQuery("select "+sequenceName+".nextval from dual");
			if(!rs.next()){
				throw new IllegalArgumentException("Sequence does not found: "+this.sequenceName);
			}
			return rs.getLong(1);
		}finally{
			try{
				rs.close();
			}catch(Exception ex){};
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
		}
	}
	
	/**
	 * print existing list 
	 * @param list
	 */
	@Override 
	public void printList(List<String> list) {
		if(list==null){
			System.out.println("list is Empty");
		}else{
			System.out.println("===== existing list ======");
			for(String value:list){
				System.out.println(value);
				System.out.println("---------------");
			}
			System.out.println("==================");
		}
	}

	/**
	 * write XML to Oracle Database
	 * @param connection
	 * @param xmlBody
	 * @throws SQLException
	 * @throws IOException
	 */
	@Override
	public long writeXml(String xmlBody) throws SQLException, IOException{
		return writeXmlBody(xmlBody);
	}

	private PreparedStatement insertXml=null;
	
	protected long writeXmlBody(String xmlBody) throws SQLException, IOException{
		if(insertXml==null){
			insertXml=connection.prepareStatement("insert into "+tableName+" (id, xml_document) values (? , ?)");
		}
		long returnValue=getSequenceValue(connection);
		insertXml.setLong(1, returnValue);
		insertXml.setString(2, xmlBody);
		insertXml.executeUpdate();
		connection.commit();
		return returnValue;
	}
	
	@Override
	protected void finalize() throws Throwable {
		try{
			insertXml.close();
		}finally{
			super.finalize();
		}
	}
	
	/**
	 * @param xpath - XPath to XML element
	 * @param condition - condition of XML element 
	 * @return list of the XML body ( as String )
	 * @throws SQLException
	 */
	@Override
	public List<String> readXml(Connection connection,
								String xpath,
							    String condition) throws SQLException{
		ResultSet rs=null;
		try{
			List<String> returnValue=new LinkedList<String>();
			StringBuilder sql=new StringBuilder();
			// example with not using the index (AAA..ZZZ )
			/*
			sql.append("select id, to_clob(xml_document) from ").append(tableName);
			if(xpath!=null){
				// add condition 
				sql.append(" where ");
				sql.append(" existsNode(xml_document,'"+xpath+"')>0 ").append(" and "); // Oracle XML existing
				sql.append(" extract(xml_document,'"+xpath+"/text()') like '%"+condition+"%' "); // Oracle XML condition
				sql.append(" and rownum<10 ");
			}
			*/
			// example with use the index (AAA..ZZZ):
			// sql.append("select id, to_char(to_clob(extract(xml_document,'/root'))) from ").append(tableName);
			// sql.append("select id, to_char(to_clob(xml_document)) from ").append(tableName);
			sql.append("select to_char(to_clob(xml_document)) from ").append(tableName);
			if(xpath!=null){
				// add condition 
				sql.append(" where ");
				// sql.append(" existsNode(xml_document,'"+xpath+"')>0 ").append(" and "); // Oracle XML existing
				sql.append(" extractValue(xml_document,'"+xpath+"') like '%"+condition+"%' "); // Oracle XML condition
				sql.append(" and rownum<10 ");
			}
			// example with use the index (50..2000)
			/*
			sql.append("select id, to_char(to_clob(extract(xml_document,'/root'))) from ").append(tableName);
			if(xpath!=null){
				// add condition 
				sql.append(" where ");
				// sql.append(" existsNode(xml_document,'"+xpath+"')>0 ").append(" and "); // Oracle XML existing
				sql.append("to_number(extractValue(xml_document, '/root/property_1/text()')) "+condition);
				// sql.append(" extractValue(xml_document,'"+xpath+"/text()') like '%"+condition+"%' "); // Oracle XML condition
				sql.append(" and rownum<10 ");
			}
			*/
			System.out.println("SQL Query: "+sql.toString());
			rs=connection.createStatement().executeQuery(sql.toString());
			while(rs.next()){
				returnValue.add(rs.getString(1));
			}
			return returnValue;
		}finally{
			if(rs!=null){
				rs.getStatement().close();
			}
		}
		
		
		
	}
	
}
