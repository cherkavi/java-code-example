import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;


/** класс, который "вынимает" всю информацию о базе данных, и преобразует таблицы в набор файлов Java для Hibernate */
public class DatabaseToHibernate {
	private Connection connection;
	private String pathToDirectory;
	
	/** класс, который "вынимает" всю информацию о базе данных, и преобразует таблицы в набор файлов Java для Hibernate */
	public DatabaseToHibernate(Connection connection, String pathToDirectory){
		this.connection=connection;
		this.pathToDirectory=pathToDirectory;
	}
	
	
	/** преобразовать данные из базы данных в файлы Hibernate 
	 * @return 
	 * <li>true - если все прошло успешно</li>
	 * <li>false - что-то не так </li>
	 * @throws  - выбрасывает исключение, в случае критических ошибок 
	 * */
	public boolean convert(Properties sqlTypeToJavaType) throws Exception {
		boolean returnValue=false;
		// получить все таблицы из соединения
		DatabaseMetaData databaseMetaData=this.connection.getMetaData();
		ResultSet rs=databaseMetaData.getTables(null, null, "%", new String[]{"TABLE"});
		ArrayList<DatabaseTable> list=new ArrayList<DatabaseTable>();
		while(rs.next()){
			list.add(new DatabaseTable(connection, rs.getString("TABLE_NAME")));
		}
		for(int counter=0;counter<list.size();counter++){
			String pathToCurrentFile=this.pathToDirectory+list.get(counter).getTableNameFirstUpperCase()+".java";
			System.out.println("File:"+pathToCurrentFile);
			//System.out.println(list.get(counter).getHibernateFile(sqlTypeToJavaType));
			this.saveStringBufferToFile(list.get(counter).getHibernateFile(sqlTypeToJavaType), pathToCurrentFile);
		}
		returnValue=true;
		return returnValue;
	}
	
	private boolean saveStringBufferToFile(StringBuffer data, String pathToFile){
		boolean returnValue=false;
		try{
			PrintWriter writer=new PrintWriter(new OutputStreamWriter(new FileOutputStream(pathToFile)));
			writer.write(data.toString());
			writer.close();
			returnValue=true;
		}catch(Exception ex){
			System.err.println("DatabaseToHibernate#saveStringBufferToFile Exception:"+ex.getMessage());
		}
		return returnValue;
	}
}
