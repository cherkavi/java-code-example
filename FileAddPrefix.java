import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/** функция, которая добавляет префикс к файлу*/
public class FileAddPrefix {
	
	public static void main(String[] args){
		System.out.println((new FileAddPrefix()).getFileWithPrefix("c:\\temp_value\\impossible_file",dateFormat.format(new Date())));
		System.out.println((new FileAddPrefix()).getFileWithPrefix("impossible_file.txt",dateFormat.format(new Date())));
	}
	
	public String getFileWithPrefix(String pathToFile, String prefix){
		File f=new File(pathToFile);
		if(f.getParent()!=null){
			return f.getParent()+System.getProperty("file.separator")+prefix+f.getName();
		}else{
			return dateFormat.format(new Date())+f.getName();
		}
	}
	private static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_ssss");
	
}
