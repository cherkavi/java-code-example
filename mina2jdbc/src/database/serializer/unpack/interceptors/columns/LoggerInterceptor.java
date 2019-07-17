package database.serializer.unpack.interceptors.columns;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;

import database.serializer.common.RecordWrap;

/** Тестовый объект: записать в указанный файл лог всех пришедших данных*/
public class LoggerInterceptor extends ColumnInterceptor{
	private String pathToSave;
	/** Тестовый объект: записать в указанный файл лог всех пришедших данных
	 * @param columnName - имя колонки, которую нужно перехватывать из удаленного объекта 
	 * @param pathToSave - полный путь к папке ( с последним разделителем ) для сохранения данных  
	 */
	public LoggerInterceptor(String columnName, String pathToSave){
		super(columnName);
		this.pathToSave=pathToSave;
	}
	
	@Override
	public boolean isForDatabaseSave() {
		return false;
	}

	/** получить из полного имени файла только имя - после возможных разделителей 
	 * @param fullPath - полный путь к файлу
	 * @return - только имя файла без разделителей 
	 */
	private String getFileNameFromFullPath(String fullPath){
		int separatorFirst=fullPath.lastIndexOf("/");
		int separatorSecond=fullPath.lastIndexOf("\\");
		int lastIndexOfSeparator=(separatorFirst>separatorSecond)?separatorFirst:separatorSecond;
		if(lastIndexOfSeparator>=0){
			return fullPath.substring(lastIndexOfSeparator+1);
		}else{
			return fullPath;
		}
	}
	
	@Override
	public Object processValue(Connection connection, 
							   RecordWrap recordWrap,
							   Object[] currentRow) {
		// получить объект из колонки, в которой хранится запись
		int index=this.getIndexInArray(recordWrap.getFieldNames(), this.getColumnName());
		if(index>=0){
			try{
				// из полученной колонки получить только имя файла
				String filePath=(String)currentRow[index];
				filePath=this.getFileNameFromFullPath(filePath);
				FileOutputStream fos=new FileOutputStream(this.pathToSave+filePath);
				PrintWriter writer=new PrintWriter(new OutputStreamWriter(fos));
				for(int counter=0;counter<currentRow.length;counter++){
					writer.write(counter+" : "+currentRow[counter]);
					writer.write("\n");
				}
				writer.flush();
				fos.close();
				// сохранить в полученном файле все значения 
			}catch(Exception ex){
				System.err.println("LoggerInterceptor#processValue Exception: "+ex.getMessage());
			}
		}else{
			System.err.println("LoggerInterceptor Object was not found: "+this.getColumnName());
		}
		return null;
	}

}
