package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.test.connection.OracleConnection;
import com.test.store.AbstractPrintXmlSearcher;
import com.test.store.pure_xml.WriteReadXml;
import com.test.xml_generator.IXmlGenerator;
import com.test.xml_generator.generators.XmlMultiValues;

public class TestXml_Index_MultiAttribute_Value extends AbstractTestXml {

	protected String[] getFindValues(){
		return new String[]{"='CJA'", "='CEF'", "='CBI'", "='CDG'", "='CHC'"};
	}
	
	@Override
	protected IXmlGenerator getXmlGenerator() {
		return new XmlMultiValues("root", 
								  this.getSequenceGenerator(), 
								  "property_", 
								  5, 
								  "id", 
								  5);
	}

	@Override
	protected boolean isNeedToCreateData() {
		return false;
	}
	
	@Override
	protected void setUp() throws Exception {
		System.out.println("begin");
		/** XPath to Leaf */
		this.connection=OracleConnection.getConnection("127.0.0.1", 1521, "XE", 
													   "SYSTEM", "root");
		this.findValues=this.getFindValues();
		this.xmlInDatabase=new WriteReadXml(connection, 
											"xml_table", 
											"temp_sequence");
		this.xmlSearcher=new AbstractPrintXmlSearcher() {
			@Override
			public List<String> readXml(Connection connection, String xpath,
					String condition) throws SQLException {
				ResultSet rs=null;
				try{
					List<String> returnValue=new LinkedList<String>();
					StringBuilder sql=new StringBuilder();
					// example with use the index (50..2000)
					sql.append("select id, to_char(to_clob(extract(xml_document,'/root'))) from ").append(this.getTableName());
					if(xpath!=null){
						// add condition 
						sql.append(" where ");
						// sql.append(" existsNode(xml_document,'"+xpath+"')>0 ").append(" and "); // Oracle XML existing
						sql.append("extractValue(xml_document, '/root/property_1[@id=''1'']/text()') "+condition);
						// sql.append("extractValue(xml_document, '/root/property_1/value[1]/text()') "+condition);

						// sql.append(" extractValue(xml_document,'"+xpath+"/text()') like '%"+condition+"%' "); // Oracle XML condition
						sql.append(" and rownum<10 ");
					}
					System.out.println("SQL query: "+sql.toString());
					rs=connection.createStatement().executeQuery(sql.toString());
					while(rs.next()){
						returnValue.add(rs.getString(2));
					}
					return returnValue;
				}finally{
					rs.getStatement().close();
				}
			}
			
			@Override
			protected String getTableName() {
				return "xml_table";
			}
		}; 
	}
	
}
