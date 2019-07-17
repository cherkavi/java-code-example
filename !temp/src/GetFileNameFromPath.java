

public class GetFileNameFromPath {
	public static void main(String[] args){
		String beginPath="D:\\Photo\\2009_01_17-01_49_47-203_b.jpeg";
		System.out.println("before:"+beginPath+"   after:"+getFileName(beginPath,"\\"));
	}
	
	
	private static String getFileName(String path, String delimeter){
		String returnValue=path;
		int position=path.lastIndexOf(delimeter);
		if(position>0){
			returnValue=path.substring(position+1);
		}
		return returnValue;
	}
}
