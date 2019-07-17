
public class ExtractFileName {
	public static void main(String[] args){
		String path="d:\\photo\\2009_01_18-11_06_58-624_b.jpeg";
		System.out.println(path);
		System.out.println(getFileName(path,"\\"));
	}
	
	private static String getFileName(String pathToFile,String delimeter){
		int delimeterPosition=pathToFile.lastIndexOf(delimeter);
		if(delimeterPosition>=0){
			return pathToFile.substring(delimeterPosition+delimeter.length());
		}else{
			return pathToFile;
		}
	}
}
