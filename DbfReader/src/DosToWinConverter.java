import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class DosToWinConverter {
	public static void main(String[] args){
		String pathToFile="c:\\dos.txt";
		try{
			FileInputStream fis=new FileInputStream(pathToFile);
			BufferedReader reader=new BufferedReader( new InputStreamReader(fis));
			String currentLine=null;
			while((currentLine=reader.readLine())!=null){
				for(int counter=0;counter<currentLine.length();counter++){
					System.out.println(Integer.valueOf(currentLine.charAt(counter))+"  ");
				}
			}
		}catch(Exception ex){
			System.out.println("Exception:"+ex.getMessage());
		}
	}
}
