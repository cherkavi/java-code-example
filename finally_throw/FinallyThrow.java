package finally_throw;

public class FinallyThrow {

	public static void main(String[] args){
		System.out.println("begin");
		try{System.out.println("Get Int:"+getInt(2));
		}catch(Exception ex){System.err.println("#main: "+ex.getMessage());}
		try{System.out.println("Get Int:"+getInt2(1));
		}catch(Exception ex){System.err.println("#main: "+ex.getMessage());}
		System.out.println("-END-");
	}
	
	private static int getInt(int value) throws Exception {
		try{
			if(value%2 == 0){
				throw new Exception();
			}else{
				return value;
			}
		}finally{
			// данный метод не работает
			// try{}catch(Exception ex){System.err.println("Detected Exception:"+ex.getMessage());}
			// метод ВСЕГДА возвращает -1, исключение "теряется"
			System.out.println("getInt finally: ");
			return -1;
		}
	}
	
	private static int getInt2(int value) throws Exception {
		try{
			if(value%2 == 0){
				throw new Exception();
			}else{
				return value;
			}
		}finally{
			System.out.println("getInt2 finally:");
		}
	}
	
}
