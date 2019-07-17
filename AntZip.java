import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;


public class AntZip {
	private static final int READ_WRITE_BUFFER_SIZE = 1024;

	public static void main(String[] args) throws Exception {
		System.out.println("Begin");
		createZip(new String[]{"/home/vcherkashin/Tasks/Task_CFIT-1233/Temp/Кардиф_2011_07_28.xls"},"/home/vcherkashin/Tasks/Task_CFIT-1233/Temp/ау_ау_ау.zip");
		System.out.println("-end-");
	}
	
	private static String createZip(String[] files, String returnFileName) throws Exception {
		if( (files!=null) && (files.length>0) ){
			try{
				BufferedInputStream inputStream = null;
				ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(returnFileName)));
				byte data[] = new byte[READ_WRITE_BUFFER_SIZE];
				ZipEntry zipEntry = null;
				int count;
				// Iterate over the file list and add them to the zip file.
				for (int counter=0; counter <files.length; counter++){
					inputStream = new BufferedInputStream(new FileInputStream(files[counter]), READ_WRITE_BUFFER_SIZE);
					zipEntry = new ZipEntry(extractFileName(files[counter]));
					zip.putNextEntry(zipEntry);
					while((count = inputStream.read(data,0,READ_WRITE_BUFFER_SIZE)) != -1){
						zip.write(data,0,count);
					}
					count = 0;
					inputStream.close();
					zip.flush();
				 }
				 zip.close();		
			}catch(IOException ex){
				System.out.println("create Zip archiv exception:"+ex.getMessage());
				throw ex;
			}
		}else{
			System.out.println("no such files for sent fia E-Mail for ZIP compressing ");
			throw new Exception("no such files for sent via E-mail ");
		}
		return returnFileName;
	}

	private static String extractFileName(String value) {
		int index=value.lastIndexOf(System.getProperty("file.separator"));
		if(index>=0){
			return value.substring(index+1);
		}else{
			return value;
		}
	}

}
