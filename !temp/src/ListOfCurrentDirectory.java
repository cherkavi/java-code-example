import java.io.*;

public class ListOfCurrentDirectory {
	public static void main(String[] args){
		File file=new File(".");
		if(file.exists()&&file.isDirectory()){
			System.out.println("Directory");
			String[] listOfFile=file.list();
			for(int counter=0;counter<listOfFile.length;counter++){
				System.out.println(counter+":"+listOfFile[counter]);
			}
		}else{
			System.out.println("ListOfCurrentDirecotry: this is not directory");
		}
	}
}
