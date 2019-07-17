
/** попытка запуска приложения и/или вызова системных функций (copy, delete, remove)*/
public class TestRuntime {
	
	public static void main(String[] args){
		System.out.println("BEGIN");
		try{
			// copy c:\*.ini d:\*.ini
			// move c:\two.ini c:\three.ini
			Runtime.getRuntime().exec(new String[]{"cmd.exe","/c","copy","c:\\one.ini","c:\\two.ini"});
		}catch(Exception ex){
			System.out.println("Exception:"+ex.getMessage());
		}
		System.out.println("-END-");
	}
}
