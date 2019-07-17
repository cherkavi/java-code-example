package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.test.store.AbstractPrintXmlSearcher;
import com.test.xml_generator.values_generator.ISequenceValues;

public class TestXml_Index_Date extends TestXml_Index {

	@Override
	protected ISequenceValues getSequenceGenerator() {
		Date dateBegin=null;
		Date dateEnd=null;
		try{
			dateBegin=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("01.01.2011 00:00:00");
			dateEnd=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("31.12.2011 23:59:59");
		}catch(ParseException pe){
			throw new IllegalArgumentException("check input dates ");
		}
		return new com.test.xml_generator.values_generator.impl.DateRandomGenerator(dateBegin, dateEnd);
	}
	
	@Override
	protected String[] getFindValues() {
		return new String[]{">to_date('10.06.2011 12:00:00','DD.MM.YYYY HH24:MI:SS')",
							"<to_date('15.02.2011 12:00:00','DD.MM.YYYY HH24:MI:SS')",
							">to_date('01.12.2011 12:00:00','DD.MM.YYYY HH24:MI:SS')",
							"<to_date('10.03.2011 12:00:00','DD.MM.YYYY HH24:MI:SS')",
							">to_date('01.12.2011 12:00:00','DD.MM.YYYY HH24:MI:SS')",
							 };
	}
	
	@Override
	protected boolean isNeedToCreateData() {
		return false;
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
					sql.append("select id, to_char(to_clob(extract(xml_document,'/root'))) from ").append(this.getTableName());
					if(xpath!=null){
						sql.append(" where ");
						sql.append("to_date(to_char(to_clob(extractValue(xml_document, '/root/property_1/text()'))),'DD.MM.YYYY HH24:MI:SS') "+condition);
						sql.append(" and rownum<50 ");
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
