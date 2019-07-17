package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.test.store.AbstractPrintXmlSearcher;
import com.test.xml_generator.values_generator.ISequenceValues;

public class TestXml_Index_Integer extends TestXml_Index {

	@Override
	protected ISequenceValues getSequenceGenerator() {
		return new com.test.xml_generator.values_generator.impl.RandomIntegerSequenceGenerator(50, 2000);
	}
	
	@Override
	protected boolean isNeedToCreateData() {
		return false;
	}

	@Override
	protected String[] getFindValues() {
		return new String[]{">100","=1486",">1400","<52","=1784"};
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.xmlSearcher=new AbstractPrintXmlSearcher() {

			@Override
			protected String getTableName() {
				return "xml_table";
			}
			
			@Override
			public List<String> readXml(Connection connection,
										String xpath, 
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
						sql.append("to_number(extractValue(xml_document, '/root/property_1/text()')) "+condition);
						// sql.append(" extractValue(xml_document,'"+xpath+"/text()') like '%"+condition+"%' "); // Oracle XML condition
						sql.append(" and rownum<10 ");
					}
					rs=connection.createStatement().executeQuery(sql.toString());
					while(rs.next()){
						returnValue.add(rs.getString(2));
					}
					return returnValue;
				}finally{
					rs.getStatement().close();
				}
			}

		};
	}

	
	
}
