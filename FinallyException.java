
public class FinallyException {
	public static void main(String[] args){
		try{
			System.out.println("begin");
			newException();
			System.out.println("end");
		}catch(Exception ex){
			System.out.println("main Exception:"+ex.getMessage());
		}
	}
	
	private static void newException() throws Exception{
		try{
			throw new Exception("this is exception");
		}finally{
			System.out.println("finally block:");
			throw new Exception("this is new Exception");
		}
	}
}
