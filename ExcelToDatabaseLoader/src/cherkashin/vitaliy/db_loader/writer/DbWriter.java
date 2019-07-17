package cherkashin.vitaliy.db_loader.writer;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.log4j.Logger;

import cherkashin.vitaliy.db_loader.configurator.configuration.elements.sheet.ALoaderSheet;
import cherkashin.vitaliy.db_loader.configurator.configuration.elements.sheet.DictionaryKindOfSheet;
import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

/** writer to Database  */
public class DbWriter implements IWriter{
	private Connection connection;
	private PreparedStatement insertStatement=null;
	private Logger logger=Logger.getLogger(this.getClass().getName());
	
	public DbWriter(Connection connection){
		this.connection=connection;
	}

	
	public void init(ALoaderSheet<?> currentLoaderSheet) throws EDbLoaderException {
		try{
			logger.debug("init new Sheet:"+currentLoaderSheet.getName());
			this.fieldInQueryPosition=new HashMap<String,Integer>();
			String tableName=currentLoaderSheet.getTableName();
			String dictionaryColumn=(String)currentLoaderSheet.getProperty(DictionaryKindOfSheet.PROPERTY_DICTIONARY_COLUMN_NAME);
			if(dictionaryColumn!=null){
				logger.debug("current Sheet is Dictionary:  TableName:"+tableName+"   ColumnName:"+dictionaryColumn);
				String dictionaryValue=(String)currentLoaderSheet.getProperty(DictionaryKindOfSheet.PROPERTY_DICTIONARY_COLUMN_VALUE);
				
				logger.debug("delete data from table :"+tableName+"  and "+dictionaryColumn+"='"+dictionaryValue+"'");
				PreparedStatement deleteStatement=connection.prepareStatement("delete from "+tableName+" where "+dictionaryColumn+"=?");
				deleteStatement.setString(1, dictionaryValue);
				int deletedRecords=deleteStatement.executeUpdate();
				deleteStatement.close();
				logger.info("data was deleted from dictionary table:"+tableName+"  ("+deletedRecords+")");
			}else{
				logger.debug("delete data from table :"+tableName);
				Statement deleteStatement=connection.createStatement();
				int deletedRecords=deleteStatement.executeUpdate("delete from "+tableName);
				deleteStatement.close();
				logger.info("data was deleted from table:"+currentLoaderSheet.getTableName()+"  ("+deletedRecords+")");
			}
			StringBuffer query=new StringBuffer();
			// create insert statement for Dictionary
			query.append("insert into "+tableName+" ");
			query.append("(");
			StringBuffer additionalQuery=new StringBuffer();
			for(int index=0;index<currentLoaderSheet.getColumns().size();index++){
				query.append(currentLoaderSheet.getColumns().get(index).getDbFieldName());
				// fill the map
				this.fieldInQueryPosition.put(currentLoaderSheet.getColumns().get(index).getDbFieldName(), index+1);
				additionalQuery.append("?");
				if(index!=currentLoaderSheet.getColumns().size()-1){
					query.append(", ");
					additionalQuery.append(", ");
				}
			}
			query.append(")");
			query.append("  values (");
			query.append(additionalQuery);
			query.append(")");
			logger.debug("Query for insert: "+query.toString());
			this.insertStatement=this.connection.prepareStatement(query.toString());
			insertedSize=0;
		}catch(Exception ex){
			throw new EDbLoaderException("init Sheet Exception:"+ex.getMessage());
		}
	}

	private HashMap<String, Integer> fieldInQueryPosition=null;
	
	private Integer getQueryNumberByDbFieldName(String dbFieldName){
		return fieldInQueryPosition.get(dbFieldName);
	}
	
	public void writeData(ColumnDataAdapter[] nextData) throws EDbLoaderException {
		logger.debug("write data to Db:");
		try{
			this.insertStatement.clearWarnings();
			this.insertStatement.clearParameters();
			for(int index=0;index<nextData.length;index++){
				logger.debug("Field:"+nextData[index].getColumn().getDbFieldName()+"("+getQueryNumberByDbFieldName(nextData[index].getColumn().getDbFieldName())+")   "+nextData[index].getFormatValue());
				this.insertStatement.setObject(getQueryNumberByDbFieldName(nextData[index].getColumn().getDbFieldName()), 
											   nextData[index].getFormatValue());
			}
			logger.debug("data was readed");
			this.insertStatement.executeUpdate();
			insertedSize++;
		}catch(Exception ex){
			throw new EDbLoaderException("insert to DataBase Exception:"+ex.getMessage());
		}
	}

	private int insertedSize=0;
	
	public void deInit(ALoaderSheet<?> currentSheet){
		logger.info("  Count of inserted records:"+insertedSize+"  to table:"+currentSheet.getTableName());
		try{
			this.insertStatement.close();
		}catch(Exception ex){
		}
	}

	public void applyAllChanges() throws EDbLoaderException{
		try{
			this.connection.commit();
		}catch(SQLException ex){
			throw new EDbLoaderException(ex.getMessage());
		}
	}
	
	
}
