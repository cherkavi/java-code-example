package dbfConverter;

import java.io.*;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
/** конвертирование из каталога c DBF файлами в базу данных JDBC, используя JDBC Connection */
public class Converter {
	/** Logger */
	private Logger logger=Logger.getLogger(this.getClass());
	{
		logger.setLevel(Level.WARN);
	}
	/** путь к каталогу с файлами DBF */
	private File directory;
	/** соединение с базой данных */
	private Connection connection;
	/** путь к файлу, в который нужно писать Log */
	private File logFile;
	/** флаг, который говорит о необходимости вывода данных в лог */
	private boolean flagOutToLog=false;
	/** Log Writer */
	private BufferedWriter logWriter;
	
	/** родительский фрейм для отображения панели коррекции полей базы данных */
	private JFrame frameParent;
	
	/** конвертирование из каталога c DBF файлами в базу данных JDBC, используя JDBC Connection 
	 * @param directory - каталог из которого нужно брать файлы DBF 
	 * @param connection - соединение с базой данных, в который нужно выливать SQL запросы 
	 * @param logFile - Log file куда нужно писать данные о переливке 
	 * */
	public Converter(JFrame parentFrame, 
					 File directory, 
					 Connection connection, 
					 File logFile){
		this.directory=directory;
		this.connection=connection;
		this.logFile=logFile;
		this.flagOutToLog=true;
		try{
			logWriter=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.logFile)));
			this.flagOutToLog=true;
		}catch(Exception ex){
			JOptionPane.showMessageDialog(parentFrame, "LogFile Error:"+ex.getMessage());
		};
	}
	
	public void finalize(){
		if(logWriter!=null){
			try{
				logWriter.close();
			}catch(Exception ex){};
		}
	}

	/** запуск переливки данных из DBF в Connection 
	 * @param true - если не произошло фатальных ошибок 
	 * */
	public boolean convert(){
		boolean returnValue=true;
		// получить все DBF файлы из каталога
		File[] listOfFiles=this.directory.listFiles(new ExtensionFileFilter("dbf"));
		logger.debug("List of file:"+listOfFiles.length);
		for(int counter=0;counter<listOfFiles.length;counter++){
			logger.debug(counter+":"+listOfFiles[counter]);
			// создать в базе одноименную таблицу с полями из DBF файла
			StringWrap tableName=new StringWrap(listOfFiles[counter].getName().substring(0,listOfFiles[counter].getName().length()-4));
			
			Field[] fields;
			if((fields=createTable(tableName,listOfFiles[counter],this.connection,true))!=null){
				// перелить все данные из DBF в Connection
				if(this.writeData(listOfFiles[counter],this.connection,tableName.getValue(),fields)==false){
					writeToLog("Error in create Table: "+listOfFiles[counter].getAbsolutePath());
					returnValue=false;
				}
			}else{
				writeToLog("Error in create Table: "+listOfFiles[counter].getAbsolutePath());
				returnValue=false;
			}
		}
		return returnValue;
	}
	
	
	/** создать на основании путь к DBF файлу таблицу в базе данных и вернуть ее имя 
	 * @param dbfFile - путь к DBF файлу 
	 * @param connection - соединение с базой данных
	 * @param removeBeforeCreate - попробовать удалить таблицу перед ее созданием 
	 * @return null если произошла ошибка во время создания   
	 * */
	private Field[] createTable(StringWrap tableName, File dbfFile, Connection connection, boolean removeBeforeCreate){
		Field[] returnValue=null;
		FileInputStream fis=null;
		try{
			// read all headers
			fis=new FileInputStream(dbfFile);
			DBFReader reader = new DBFReader(fis);
			int fieldCount=reader.getFieldCount();
			DBFField[] dbfFields=new DBFField[fieldCount];
			Field[] fields=new Field[fieldCount];
			for(int counter=0;counter<fieldCount;counter++){
				dbfFields[counter]=reader.getField(counter);
				fields[counter]=new Field(counter,dbfFields[counter]);
			}
			// all field was received
			
			if(removeBeforeCreate){
				// TODO remove table before create
				try{
					this.connection.createStatement().executeUpdate("DROP TABLE "+tableName);
					this.connection.commit();
					writeToLog("table was dropped");
				}catch(Exception ex){
					logger.debug("DropTable "+tableName+"Exception:"+ex.getMessage());
				}
			}
			
			while(true){
				String sql=null;
				try{
					sql=Field.getSqlCreateTable(tableName.getValue(), fields);
					logger.debug("Create Table: "+sql);
					this.connection.createStatement().executeUpdate(sql.toString());
					this.connection.commit();
					break;
				}catch(Exception ex){
					// SQL запрос не выполнен - причина - ex.getMessage()  
					if(JOptionPane.showConfirmDialog(this.frameParent, "Correct table:"+tableName,"",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
						tableName.setValue(Field.showModalPanel(frameParent, tableName.getValue(), fields, sql+"\n\n"+ex.getMessage()));
					}else{
						break;
					}
				}
			}
			returnValue=fields;
		}catch(Exception ex){
			writeToLog(ex.getMessage());
		}finally{
			if(fis!=null){
				try{
					fis.close();
				}catch(Exception ex){};
			}
		}
		
		return returnValue;
	}
	
	
	/** 
	 * перелить данные из таблицы-файла DBF в Connection c одноименными полями и уникальным именем
	 * @param dbfFile - файл DBF
	 * @param connection - соединение с базой данных
	 * @param tableName - имя таблицы в базе данных, в которую нужно делать Insert
	 * @param fields - поля, которые необходимы для переливки данных
	 */
	private boolean writeData(File dbfFile, 
							  Connection connection, 
							  String tableName, 
							  Field[] fields){
		boolean returnValue=true;
		FileInputStream fis=null;
		try{
			fis=new FileInputStream(dbfFile);
			DBFReader reader=new DBFReader(fis);
			PreparedStatement statement=this.getPreparedStatement(connection, tableName,fields);
			Object[] row;
			while( (row=reader.nextRecord())!=null){
				statement.clearParameters();
				for(int counter=0;counter<fields.length;counter++){
					if(row[counter] instanceof String){
						logger.debug(counter+" : "+Field.convertDosString((String)fields[counter].getObjectForSql(row[counter])));
						statement.setObject(counter+1, Field.convertDosString((String)fields[counter].getObjectForSql(row[counter])));
					}else{	
						if(row[counter]!=null){
							logger.debug(counter+":"+row[counter].getClass().getName());
							statement.setObject(counter+1, fields[counter].getObjectForSql(row[counter]));
						}else{
							statement.setObject(counter+1, fields[counter].getObjectForSql(row[counter]));
						}
						
					}
					
				}
				try{
					statement.executeUpdate();
					connection.commit();
				}catch(Exception ex){
					this.writeToLog("Execute Exception: "+ex.getMessage());
					returnValue=false;
				}
			}
		}catch(Exception ex){
			this.writeToLog("writeData:"+ex.getMessage());
			returnValue=false;
		}finally{
			if(fis!=null){
				try{
					fis.close();
				}catch(Exception ex){};
			}
		}
		 
		return returnValue;
	}
	
	/** получить PreparedStatement 
	 * @throws SQLException */
	private PreparedStatement getPreparedStatement(Connection connection, String tableName, Field[] fields) throws SQLException{
		StringBuffer query=new StringBuffer();
		query.append("INSERT INTO "+tableName+" (");
		for(int counter=0;counter<fields.length;counter++){
			query.append(fields[counter].getSqlName());
			if(counter!=(fields.length-1)){
				query.append(", ");
			}
		}
		query.append(")");
		query.append(" VALUES(");
		for(int counter=0;counter<fields.length;counter++){
			query.append("?");
			if(counter!=(fields.length-1)){
				query.append(", ");
			}
		}
		query.append(")");
		return connection.prepareStatement(query.toString());		
	}
	
	
	private void writeToLog(Object information){
		if(this.flagOutToLog==true){
			try{
				logWriter.write(information.toString());
				logWriter.flush();
				logger.info(information.toString());
			}catch(Exception ex){
				logger.error("writeToLog Exception:"+ex.getMessage());
			};
		}
	}
}


class ExtensionFileFilter implements java.io.FileFilter{

	private String[] ext;
	
	public ExtensionFileFilter(String ... ext){
		if(ext==null){
			this.ext=new String[]{"txt"};
		}
		this.ext=ext;
	}

	@Override
	public boolean accept(File arg0) {
		return isFilter(getExtension(arg0));
	}
	
	/** вернуть True если данное расширение есть в массиве  */
	private boolean isFilter(String value){
		boolean returnValue=false;
		for(int counter=0;counter<ext.length;counter++){
			if(ext[counter].equalsIgnoreCase(value)){
				returnValue=true;
				break;
			}
		}
		return returnValue;
	}
	
	/** получить расширение файла */
	private String getExtension(File file){
		String fileName=file.getName();
		int dotPosition=fileName.lastIndexOf(".");
		if(dotPosition>0){
			return fileName.substring(dotPosition+1);
		}else{
			return "";
		}
	}

}

class StringWrap{
	private String value;
	
	public StringWrap(String value){
		this.value=value;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public String toString(){
		return value;
	}
}