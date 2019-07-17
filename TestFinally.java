
/** отображение различных ситуаций, которые могут иметь место при использовании конструкции finally*/
public class TestFinally {
	
	/** демонстрация потерянного исключения */
	private static void lostException() throws Exception {
		try{
			System.out.println("try block");
			throw new Exception("try Exception ");
		}catch(Exception ex){
			System.out.println("catch block");
			// this exception was failed
			throw new Exception("catch Exception");
		}finally{
			System.out.println("finally block");
			throw new Exception("finally Exception");
		}
	}
	

	/** демонстрация потерянного исключения */
	private static void lostException2() throws Exception {
		try{
			System.out.println("try block");
			throw new Exception("try Exception ");
		}finally{
			System.out.println("finally block");
			throw new Exception("finally Exception ");
		}
	}
	
	/** демонстрация неудавшегося возврата функции */
	private static int lostReturn(){
		try{
			System.out.println("try");
			return 5;
		}finally{
			System.out.println("finally");
			// try remark finally return
			return 999;
		}
	}

	/** демонстрация неудавшегося возврата функции */
	private static int lostReturn2(boolean value){
		try{
			System.out.println("try");
			if(value==true){
				throw new Exception();
			}else{
				return 5;
			}
		}catch(Exception ex){
			System.out.println("catch");
			return 22;
		}finally{
			System.out.println("finally");
			// try remark finally return
			//return 999;
		}
		//return 7;
	}
	
	public static void main(String[] args){
		System.out.println("Demonstration lost exception   BEGIN:");
		try{
			lostException();
		}catch(Exception ex){
			System.out.println("Main:"+ex.getMessage());
		}
		System.out.println("Demonstration lost exception   END:");

		System.out.println("Demonstration lost exception  2  BEGIN:");
		try{
			lostException2();
		}catch(Exception ex){
			System.out.println("Main:"+ex.getMessage());
		}
		System.out.println("Demonstration lost exception  2  END:\n\n\n");
		
		System.out.println("Demonstration lost Return    BEGIN:");
		System.out.println(lostReturn());
		System.out.println("Demonstration lost Return     END: ");

		System.out.println("Demonstration lost Return  2  BEGIN:");
		System.out.println(lostReturn2(false));
		System.out.println("Demonstration lost Return  2   END: ");
		
	}
}
