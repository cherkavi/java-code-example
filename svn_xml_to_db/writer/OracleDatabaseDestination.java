package svn_xml_to_db.writer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import svn_xml_to_db.transit_format.LogEntry;
import svn_xml_to_db.transit_format.LogEntryFile;

/**
 * saver for LogEntryConnection 
 * @author vcherkashin
 *
 */
public class OracleDatabaseDestination implements IDestinationWriter{
	private Logger logger=Logger.getLogger(this.getClass());
	
	private String url;
	private String login;
	private String password;
	
	public OracleDatabaseDestination(String url, String login, String password){
		this.url=url;
		this.login=login;
		this.password=password;
	}
	
	@Override
	public void writeToDestination(List<LogEntry> list) throws EDestinationException {
		logger.trace("OracleDatabaseDestination#writeToDestination : begin");
		Connection connection=null;
		try{
			logger.debug("try to get connection ");			
			connection=getConnection();
			logger.debug("connection: "+connection);
			connection.setAutoCommit(false);
			logger.trace("init object");
			init(connection);
			logger.info("save to Connection");
			saveToConnection(connection, list, true);
			logger.trace("deinit object");
			deinit();
			logger.trace("OracleDatabaseDestination#writeToDestination : end - OK ");
		}catch(Exception ex){
			throw new EDestinationException(ex.getMessage());
		}finally{
			try{
				connection.close();
			}catch(Exception ex){};
		}
	}
	
	/**
	 * @param connection - connection with database
	 * @param list - list of entities for save
	 * @param saveAllOnly 
	 * <ul>
	 * 	<li><b>true</b> - save all or rollback </li>
	 * 	<li><b>false</b> </li>
	 * </ul>
	 */
	private void saveToConnection(Connection connection, 
								  List<LogEntry> list,
								  boolean saveAllOnly) throws Exception{
		if(saveAllOnly==true){
			logger.trace("mode of save: save All records or rollback all ");
			try{
				for(LogEntry entry: list){
					logger.trace("Save "+entry);
					saveLogEntry(connection, entry);
				}
				logger.debug("try to commit all entities");
				connection.commit();
				logger.debug("save OK");
			}catch(Exception ex){
				logger.error("save to Database Exception: "+ex.getMessage());
				connection.rollback();
				throw ex;
			}
		}else{
			logger.trace("mode of save: save records one by one");
			for(LogEntry entry:list){
				try{
					logger.trace("Save "+entry);
					saveLogEntry(connection,entry);
					connection.commit();
				}catch(Exception ex){
					logger.warn("Exception "+ex.getMessage()+" during saving Entry :"+entry);
				}
			}
		}
	}
	
	private static final String SEQUENCE_LOG_ENTRY="HIBERNATE_SEQUENCE";
	private static final String SEQUENCE_LOG_ENTRY_FILE="HIBERNATE_SEQUENCE";
	
	
	private int getNextValueFromSequence(Connection connection, String sequenceName) throws SQLException {
		ResultSet rs=connection.createStatement().executeQuery("select "+sequenceName+".nextval from dual");
		rs.next();
		int returnValue=rs.getInt(1);
		rs.getStatement().close();
		return returnValue;
	}
	
	// select HIBERNATE_SEQUENCE.nextval from dual
	private int getNextLogEntry(Connection connection) throws SQLException{
		return getNextValueFromSequence(connection, SEQUENCE_LOG_ENTRY);
	}

	private int getNextLogEntryFiles(Connection connection) throws SQLException{
		return getNextValueFromSequence(connection, SEQUENCE_LOG_ENTRY_FILE);
	}
	
	PreparedStatement statementEntry=null;
	PreparedStatement statementEntryFile=null;

	private void init(Connection connection) throws SQLException{
		statementEntry=connection.prepareStatement("insert into SVN_LOG_ENTRY (ID,REVISION,AUTHOR,DATE_WRITE,MESSAGE,TASK) values (?,?,?,?,?,?)");
		statementEntryFile=connection.prepareStatement("insert into SVN_LOG_ENTRY_FILE (ID, ID_SVN_LOG_ENTRY, KIND, ACTION, SVN_PATH) values (?,?,?,?,?)");
	}
	
	private void deinit(){
		try{
			statementEntry.close();
			statementEntry=null;
		}catch(Exception ex){};
		try{
			statementEntryFile.close();
			statementEntryFile=null;
		}catch(Exception ex){};
	}
	
	/**
	 * truncate string, if exceeded length 
	 * @param value
	 * @param maxLength
	 * @return
	 */
	private String truncateIfNeed(String value, int maxLength){
		if(value==null){
			return null;
		}
		if(value.length()>maxLength){
			return value.substring(0, maxLength);
		}else{
			return value;
		}
	}
	
	private void saveLogEntry(Connection connection, LogEntry entry) throws SQLException{
		this.statementEntry.clearParameters();
		// create ID;
		int idEntry=this.getNextLogEntry(connection);
		// SVN_LOG_ENTRY.ID
		statementEntry.setInt(1, idEntry);
		// SVN_LOG_ENTRY.REVISION
		statementEntry.setString(2, entry.getRevision());
		// SVN_LOG_ENTRY.AUTHOR ( 100 )
		statementEntry.setString(3, truncateIfNeed(entry.getAuthor(),100));
		// SVN_LOG_ENTRY.DATE_WRITE
		statementEntry.setTimestamp(4, new Timestamp(entry.getDate().getTime()));
		// SVN_LOG_ENTRY.MESSAGE ( 1024 )
		statementEntry.setString(5, truncateIfNeed(entry.getMessage(),1024));
		// SVN_LOG_ENTRY.TASK ( 20 )
		statementEntry.setString(6, truncateIfNeed(entry.getTask(),20));
		statementEntry.executeUpdate();
		if((entry.getListOfFile()!=null)&&(entry.getListOfFile().size()>0)){
			// walk through all LogEntryFiles
			for(LogEntryFile entryFile: entry.getListOfFile()){
				statementEntryFile.clearParameters();
				// create ID;
				// ID 
				statementEntryFile.setInt(1, getNextLogEntryFiles(connection));
				//	ID_SVN_LOG_ENTRY INTEGER 
				statementEntryFile.setInt(2, idEntry);
				// KIND VARCHAR2( 100 ) 
				statementEntryFile.setString(3, truncateIfNeed(entryFile.getKind(),100));
				// ACTION VARCHAR2( 10 )
				statementEntryFile.setString(4, truncateIfNeed(entryFile.getAction(),10));
				// SVN_PATH VARCHAR2( 512 ) 
				statementEntryFile.setString(5, truncateIfNeed(entryFile.getPath(),512));
				// save LogEntry
				statementEntryFile.executeUpdate();
			}
		}
	}
	

	private Connection getConnection() throws Exception{
		Connection connection = null;
		try {
		    // Load the JDBC driver
		    String driverName = "oracle.jdbc.driver.OracleDriver";
		    Class.forName(driverName);
		    connection = DriverManager.getConnection(url, login, password);
		} catch (ClassNotFoundException e) {
			logger.error("Could not find the database driver");
			throw e;
		} catch (SQLException e) {
		    logger.error("Could not connect to the database");
		    throw e;
		}
		return connection;
	}
	
}
