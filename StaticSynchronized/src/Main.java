
public class Main {
	public static void main(String[] args){
		System.out.println("begin:");
		Runner r1=new Runner("runner1:",500);
		Runner r2=new Runner("RUN2:",200);
/*		try{
			r1.join();
			r2.join();
		}catch(Exception ex){
			System.err.println("Main Exception:"+ex.getMessage());
		}
*/		
		System.out.println("end");
	}
}
