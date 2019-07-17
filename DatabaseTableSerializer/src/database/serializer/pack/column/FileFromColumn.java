package database.serializer.pack.column;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.sql.ResultSet;

/** описание колонки, которая содержит имя файла для получения в виде объекта  */
public class FileFromColumn implements IRecordColumn {
	/** имя колонки для отображения в объекте хранения данных */
	private String columnName=null;
	/** имя колонки в базе данных для получения полного пути к файлу */
	private String columnWithPathToFile=null;
	/** префикс для прибавления к полученному файловому пути */
	private String prefixPath=null;
	
	/** описание колонки, которая содержит имя файла для получения в виде объекта  
	 * @param columnName имя колонки для отображения в объекте хранения данных
	 * @param columnWithPathToFile имя колонки в базе данных для получения полного пути к файлу
	 */
	public FileFromColumn(String columnName, String columnWithPathToFile){
		this.columnName=columnName;
		this.columnWithPathToFile=columnWithPathToFile;
	}

	/** описание колонки, которая содержит имя файла для получения в виде объекта  
	 * @param columnName имя колонки для отображения в объекте хранения данных
	 * @param columnWithPathToFile имя колонки в базе данных для получения полного пути к файлу
	 * @param prefixPath - префикс, который нужно прибавить к значению из столбца базы данных (для полного пути к файлу) 
	 * */
	public FileFromColumn(String columnName, String columnWithPathToFile, String prefixPath ){
		this(columnName, columnWithPathToFile);
		this.prefixPath=prefixPath;
	}
	
	@Override
	public String getColumnName() {
		return this.columnName;
	}

	@Override
	public Object getObject(ResultSet rs) {
		try{
			// получить имя файла
			String pathToFile=rs.getString(this.columnWithPathToFile);
			if(prefixPath!=null){
				pathToFile=prefixPath+pathToFile;
			}
			return this.getByteArrayFromFile(pathToFile);
		}catch(Exception ex){
			System.err.println("FileFromColumn#getObject Exception: "+ex.getMessage());
			return null;
		}
	}

	
	/** получить из указанного файла массив из байт */
	private byte[] getByteArrayFromFile(String pathToFile){
		try{
			FileInputStream fis=new FileInputStream(pathToFile);
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			byte[] buffer=new byte[1024];
			int size=0;
			while((size=fis.read(buffer))>=0){
				baos.write(buffer,0,size);
			}
			return baos.toByteArray();
		}catch(Exception ex){
			System.err.println("FileFromColumn Exception: "+ex.getMessage());
			return null;
		}
	}
}
