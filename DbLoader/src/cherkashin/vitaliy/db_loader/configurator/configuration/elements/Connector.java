package cherkashin.vitaliy.db_loader.configurator.configuration.elements;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

/** database connector  */
public class Connector {
	private Logger logger=Logger.getLogger(this.getClass().getName());
	private String url;
	private String login;
	private String password;
	
	/** Create Database connector */
	public Connector(String url, String login, String password) throws EDbLoaderException{
		logger.debug("Try connect to Database "); 
		this.url=url;
		this.login=login;
		this.password=password;
		if(url==null) throw new EDbLoaderException("Database URL is NULL ");
	}
	
	public Connection getConnection() throws EDbLoaderException{
		try{
			return java.sql.DriverManager.getConnection(url,login,password);
		}catch(SQLException sqlExc){
			throw new EDbLoaderException("SQL Connection Exception:"+sqlExc);
		}catch(Exception ex){
			throw new EDbLoaderException("Exception when trying to connect to DB: "+ex.getMessage());
		}
	}
}
