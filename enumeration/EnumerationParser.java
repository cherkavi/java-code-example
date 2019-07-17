package enumeration;

/** данный пример иллюстрирует возможность парсинга/перевода строковых значение в Enum значения, которые зарегестрированы  */
public class EnumerationParser {
	public static void main(String[] args){
		System.out.println("begin");
		// State state=State.valueOf("begin"); - throws Exception 
		// State state=State.valueOf("first"); - throws Exception 
		State state=State.valueOf("First"); // чувствителен к регистру, чувствителен к пробелам слева и справа 
		System.out.println("State:"+state);
		System.out.println("-end-");
	}
}

enum State{
    First, 
    Second, 
    Third;
    
}
