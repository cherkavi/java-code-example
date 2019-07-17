package com.test.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.StringReader;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
 
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
 
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.sql.OPAQUE;
import oracle.xdb.XMLType;
import oracle.xml.parser.v2.XMLParseException;

public class XmlHandler implements TypeHandler<Object>{
	// final  static  Logger logger = LoggerFactory.getLogger(XMLTypeHandlerCallback.class);
	 
    @Override
    public Object getResult(ResultSet resultSet, String columnName) throws SQLException
    {
        if (resultSet instanceof OracleResultSet)
        {
            OracleResultSet oracleResultSet = (OracleResultSet) resultSet;
            OPAQUE opaqueValue = oracleResultSet.getOPAQUE(columnName);
            if (opaqueValue != null)
            {
                XMLType xmlResult = XMLType.createXML(opaqueValue);
                return xmlResult.getDocument();
            }
            else
            {
                return (Document) null;
            }
        }
        else
        {
            throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS LOL");
        }
    }
 
    @Override
    public Object getResult(CallableStatement callableStatement, int columnIndex) throws SQLException
    {
        if (callableStatement instanceof OracleCallableStatement)
        {
            OracleCallableStatement oracleCallableStatement = (OracleCallableStatement) callableStatement;
            OPAQUE opaqueValue = oracleCallableStatement.getOPAQUE(columnIndex);
            if (opaqueValue != null)
            {
                XMLType xmlResult = XMLType.createXML(opaqueValue);
                return xmlResult.getDocument();
            }
            else
            {
                return (Document) null;
            }
        }
        else
        {
            throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS");
        }
    }
 
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Object parameter, JdbcType jdbcType) throws SQLException
    {
        if (preparedStatement instanceof OraclePreparedStatement)
        {
            OraclePreparedStatement oraclePreparedStatement = (OraclePreparedStatement) preparedStatement;
            if (parameter == null)
            {
            	// oraclePreparedStatement.setNull(i, oracle.jdbc.OracleTypes.OPAQUE);
                oraclePreparedStatement.setNull(i, oracle.jdbc.OracleTypes.OPAQUE, "SYS.XMLTYPE");
            	// oraclePreparedStatement.setObject(i, null);
            	
            	// XMLType poXML = XMLType.createXML(preparedStatement.getConnection(), "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><NULL></NULL>");
            	// oraclePreparedStatement.setOPAQUE(i, poXML);
//				try {
//	        		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//	        		DocumentBuilder docBuilder;
//					docBuilder = docFactory.newDocumentBuilder();
//	        		// root elements
//	        		Document doc = docBuilder.newDocument();           
//	        		doc.appendChild(doc.createElement("null"));
//	        		XMLType poXML=XMLType.createXML(preparedStatement.getConnection(), doc);
//	            	oraclePreparedStatement.setObject(i, poXML);
//				} catch (ParserConfigurationException e) {
//					e.printStackTrace();
//				}
            }
            else
            {
                XMLType xmlInput = XMLType.createXML(oraclePreparedStatement.getConnection(), (Document) parameter);
                oraclePreparedStatement.setObject(i, xmlInput);
            }
        }
        else
        {
            throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS");
        }
 
    }
 
    public Object valueOf(String s)
    {
        try
        {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return db.parse(new InputSource(new StringReader(s)));
        }
        catch (Exception e)
        {
            if (e instanceof XMLParseException)
            {
                throw new IllegalArgumentException("Argument for valueOf() doesn't describe a XML Document");
            }
            else
            {
                throw new RuntimeException("Error creating XML document.  Cause: " + e);
            }
        }
    }

	@Override
	public Object getResult(ResultSet resultSet, int columnIndex) throws SQLException {
        if (resultSet instanceof OracleResultSet)
        {
            OracleResultSet oracleResultSet = (OracleResultSet) resultSet;
            OPAQUE opaqueValue = oracleResultSet.getOPAQUE(columnIndex);
            if (opaqueValue != null)
            {
                XMLType xmlResult = XMLType.createXML(opaqueValue);
                return xmlResult.getDocument();
            }
            else
            {
                return (Document) null;
            }
        }
        else
        {
            throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS LOL");
        }
	}
}
