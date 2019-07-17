import java.io.*;
public class ExecuteCommand {
	public static void main(String[] args){
		System.out.println("begin:");
		try{
			// создать каталог 
			Runtime.getRuntime().exec("cmd /c md c:\\temp2\\");
			// переместить файлы
			Process process=Runtime.getRuntime().exec("cmd /c move c:\\out*.txt c:\\temp2\\");
			BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while((line=reader.readLine())!=null){
				System.out.println(line);
			}
		}catch(Exception ex){
			System.out.println("Exception: "+ex.getMessage());
		}
		System.out.println("end");
	}
}
