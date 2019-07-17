package database.serializer.unpack.interceptors.columns;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;

import database.serializer.common.RecordWrap;

/*перехватчик дл€ колонки, котора€ содержит byte[] данные дл€ записи в файл и им€ файла в другой €чейке*/
public class ByteArrayToFileInterceptor extends ColumnInterceptor{
	private String columnFileName;
	private String path;
	
	/** перехватчик дл€ колонки, котора€ содержит byte[] данные дл€ записи в файл и им€ файла в другой €чейке  
	 * @param columnName - им€ колонки, в которой находитс€ массив из byte[]
	 * @param columnFileName - им€ колонки, в которой лежит им€ файла
	 * @param path - полный путь к месту дл€ сохранени€ 
	 */
	public ByteArrayToFileInterceptor(String columnName,String columnFileName, String path){
		super(columnName);
		this.columnFileName=columnFileName;
		this.path=path;
	}

	@Override
	public boolean isForDatabaseSave() {
		return false;	
	}

	/** получить из полного имени файла только им€ - после возможных разделителей 
	 * @param fullPath - полный путь к файлу
	 * @return - только им€ файла без разделителей 
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
	
	/** записать массив байт в файл */
	private void writeByteArrayToFile(String pathToFile, byte[] arrayOfByte) throws IOException{
		byte[] buffer=new byte[1024];
		int readedByte=0;
		FileOutputStream fos=new FileOutputStream(pathToFile);
		ByteArrayInputStream bais=new ByteArrayInputStream(arrayOfByte);
		while((readedByte=bais.read(buffer))!=(-1)){
			fos.write(buffer,0,readedByte);
		}
		bais.close();
		fos.flush();
		fos.close();
	}
	
	
	@Override
	public Object processValue(Connection connection, RecordWrap recordWrap,Object[] currentRow) {
		try{
			
			int indexOfArray=this.getIndexInArray(recordWrap.getFieldNames(), this.getColumnName());
			int indexOfFile=this.getIndexInArray(recordWrap.getFieldNames(), this.columnFileName);
			// получить массив из байт
			byte[] arrayOfByte=(byte[])currentRow[indexOfArray];
			// получить им€ файла
			String pathToFile=this.getFileNameFromFullPath((String)currentRow[indexOfFile]);
			// сохранить в файле массив из байт
			if(this.path!=null){
				pathToFile=this.path+pathToFile;
			}
			this.writeByteArrayToFile(pathToFile, arrayOfByte);
		}catch(Exception ex){
			System.err.println("ByteArrayToFileInterceptor#processValue Exception: "+ex.getMessage());
		}
		// TODO Auto-generated method stub
		return null;
	}

}
