package database;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import org.hsqldb.jdbc.jdbcDataSource;

public class HsqldbConnection extends Connector{
	/** уникальное имя базы данных */
	private String name;
	/** соединение с базой данных HsqlDb*/
	private jdbcDataSource dataSource;
	/** current connection */
	private Connection connection;
	/** extensions for remove files */
	private String[] fileExtension=new String[]{".log",".script",".properties"};
	
	
	/** проверить файл на такой, который нужно удалить для рационального использования места на диске
	 * - файл относится к HSQLDB 
	 * @param fileName - имя файла, который нужно проверить на принадлежность к базе 
	 * */
	private boolean isFileForRemove(String prefix, String fileName){
		boolean returnValue=false;
		for(int counter=0;counter<fileExtension.length;counter++){
			if(fileName.equals(prefix+fileExtension[counter])){
				returnValue=true;
				break;
			}
		}
		return returnValue;
	}
	
	/** создать соединение с базой данных на основании HSQLDB базы данных 
	 * @param уникальное имя для базы данных 
	 * @throws SQLException если не удалось создать соединение с базой данных по указанному типу  
	 * */
	public HsqldbConnection(String databaseName) {
		File file=new File(".");
		if(file.exists()&&file.isDirectory()){
			System.out.println("Directory");
			String[] listOfFile=file.list();
			for(int counter=0;counter<listOfFile.length;counter++){
				if(isFileForRemove(databaseName, listOfFile[counter])){
					File fileForRemove=new File(listOfFile[counter]);
					if(fileForRemove.delete()){
						System.out.println("File was removed:"+listOfFile[counter]);
					}else{
						System.out.println("File was not removed:"+listOfFile[counter]);
					}
				}
			}
		}else{
			System.out.println("ListOfCurrentDirecotry: this is not directory");
		}
		
		this.name=databaseName;
        dataSource = new jdbcDataSource();
        dataSource.setDatabase("jdbc:hsqldb:" + this.name);
	}
	
	
	@Override
	public Connection getConnection() {
		try{
			if((this.connection==null)||(this.connection.isClosed())){
				this.connection=this.dataSource.getConnection("sa","");
			}
		}catch(Exception ex){
			return this.connection;
		}
		return connection;
	}

}
