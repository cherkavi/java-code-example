
public class SharedObject {
	
	public static synchronized void printPerHalfSecond(int count){
		for(int counter=0;counter<count;counter++){
			try{
				Thread.sleep(500);
			}catch(Exception ex){};
			System.out.println("printPerHalfSecond: "+(counter+1)+"/"+count);
		}
	}
	
	public static synchronized void printPerSecond(int count){
		for(int counter=0;counter<count;counter++){
			try{
				Thread.sleep(1000);
			}catch(Exception ex){};
			System.out.println("printPerSecond: "+(counter+1)+"/"+count);
		}
	}
}
