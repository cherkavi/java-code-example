package tools.transformer.source_impl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import tools.transformer.core.common_model.IModel;
import tools.transformer.core.destination.INextInstanceListener;
import tools.transformer.core.exceptions.ETransformerException;
import tools.transformer.core.source.ISourceIterator;
import tools.transformer.utility.XmlParser;

public class JdbcSource implements ISourceIterator{
	private Logger logger=Logger.getLogger(this.getClass());
	
	private String url;
	private String login;
	private String password;
	private String driverClassName;
	
	private Connection connection;
	
	
	private INextInstanceListener instanceListener=null;
	/**
	 * set Instance Change listener
	 */
	public void setNextInstanceListener(INextInstanceListener destination) {
		this.instanceListener=destination;
	}

	
	/**
	 * need to be set as property: jdbcUrl
	 * @param url
	 */
	public void setJdbcUrl(String url){
		this.url=url;
	}
	
	/**
	 * need to be set as property: jdbcLogin
	 * @param login
	 */
	public void setJdbcLogin(String login){
		this.login=login;
	}
	
	/**
	 * need to be set as property: jdbcPassword
	 * @param password
	 */
	public void setJdbcPassword(String password){
		this.password=password;
	}
	
	/**
	 * need to be set as property: jdbcDriverClass
	 * @param driverClassName
	 */
	public void setJdbcDriverClass(String driverClassName){
		this.driverClassName=driverClassName;
	}

	/**
	 * need to be set as property: pathToXmlQuery
	 */
	public void setPathToXmlQuery(String pathToXml){
		try{
			// open Xml file
			Document document=XmlParser.getXmlByPath(pathToXml);
			// parse Xml file 
			listOfInstance=getListOfInstances(document);
		}catch(Exception ex){
			throw new ETransformerException("error set PathToXml file:"+ex.getMessage());
		}
	}
	
	private List<JdbcInstance> getListOfInstances(Document document) {
		ArrayList<JdbcInstance> returnValue=new ArrayList<JdbcInstance>();
		NodeList queries=XmlParser.getSubElements(document, "/sql_queries/sql");
		for(int index=0;index<queries.getLength();index++){
			Node currentNode=queries.item(index);
			if(currentNode instanceof Element){
				Element element=(Element)currentNode;
				returnValue.add(new JdbcInstance(element.getAttribute("id"), element.getTextContent().trim()));
			}
		}
		return returnValue;
	}

	public void deInit() throws ETransformerException {
		try{
			this.connection.close();
			this.logger.debug("DeInit OK");
		}catch(Exception ex){
			this.logger.debug("DeInit");
		}
	}

	public void init() throws ETransformerException {
		logger.debug("open connection");
		try{
			logger.debug("load driver");
			Class.forName(this.driverClassName);
			logger.debug("open connection");
			this.connection=DriverManager.getConnection(this.url, this.login, this.password);
		}catch(Exception ex){
			logger.error("open connection was exception:"+ex.getMessage());
			throw new ETransformerException("Exception when trying to open connection: "+ex.getMessage());
		}
	}

	private ResultSet rs=null;
	private List<JdbcInstance> listOfInstance=null;
	private int indexOfInstance=-1;
	private JdbcInstance currentInstance=null;
	
	/**
	 * {@inheritDoc}
	 * <b>Important: need to call {{@link #hasNext()} before ( iterator pattern )
	 */
	public IModel getNext() throws ETransformerException {
		return getModelFromResultSet(this.rs);
	}

	/**
	 * get model from ResultSet
	 * @param rs
	 * @return
	 */
	private JdbcModel getModelFromResultSet(ResultSet rs){
		try{
			Object[] returnValue=new Object[rs.getMetaData().getColumnCount()];
			for(int index=1;index<=rs.getMetaData().getColumnCount();index++){
				returnValue[index-1]=rs.getObject(index);
			}
			return new JdbcModel(returnValue);
		}catch(Exception ex){
			throw new ETransformerException("getModelFromresultSet Exception:"+ex.getMessage());
		}
	}
	
	private boolean openNextInstance(){
		if(this.indexOfInstance<(this.listOfInstance.size()-1)){
			this.indexOfInstance++;
			if(this.currentInstance!=null){
				this.instanceListener.nextInstanceEnd(currentInstance);
			}
			this.currentInstance=this.listOfInstance.get(indexOfInstance);
			try{
				this.rs=this.connection.createStatement().executeQuery(this.currentInstance.getQuery());
				this.currentInstance.setColumnNames(getColumnNamesFromResultSet(this.rs));
				this.currentInstance.setColumnTypes(getColumnTypesFromResultSet(this.rs));
				this.instanceListener.nextInstanceBegin(currentInstance);
			}catch(Exception ex){
				logger.error("Open next Query Exception:"+ex.getMessage());
				throw new ETransformerException("JdbcSource#hasNext Exception:"+ex.getMessage());
			}
			return true;
		}else{
			// last instance was processed
			return false;
		}
	}
	
	private int[] getColumnTypesFromResultSet(ResultSet resultSet) throws SQLException{
		int maxIndex=resultSet.getMetaData().getColumnCount();
		int[] returnValue=new int[maxIndex];
		for(int index=1;index<=maxIndex;index++){
			returnValue[index-1]=resultSet.getMetaData().getColumnType(index);
		}
		return returnValue;
	}


	private String[] getColumnNamesFromResultSet(ResultSet resultSet) throws SQLException{
		int maxIndex=resultSet.getMetaData().getColumnCount();
		String[] returnValue=new String[maxIndex];
		for(int index=1;index<=maxIndex;index++){
			returnValue[index-1]=resultSet.getMetaData().getColumnLabel(index);
		}
		return returnValue;
	}


	public boolean hasNext() throws ETransformerException {
		try{
			while(true){
				if(rs==null){
					if(openNextInstance()==false){
						return false;
					}
				}
				if(rs.next()){
					return true;
				}else{
					rs.getStatement().close();
					rs=null;
					continue;
				}
			}
		}catch(Exception ex){
			throw new ETransformerException("read ResultSet from Connection was exception: "+ex.getMessage());
		}
	}

	
}

