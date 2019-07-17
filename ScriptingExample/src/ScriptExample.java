import java.util.List;

import javax.script.*;

/** данный класс показывает возможность выполнения скриптового языка */
public class ScriptExample {

	private static void getAllLanguage(){
		ScriptEngineManager factory=new ScriptEngineManager();
		List<ScriptEngineFactory> list=factory.getEngineFactories();
		for(ScriptEngineFactory element:list){
			System.out.println("--- Begin ---");
			System.out.println("EngineName: "+element.getEngineName());
			System.out.println("EngineVersion: "+element.getEngineVersion());
			System.out.println("LanguageName: "+element.getLanguageName());
			System.out.println("LanguageVersion: "+element.getLanguageVersion());
			System.out.println("---  End  ---");
		}
	}
	

	/** пример работы с двигателем процедуры */
	private static void example(){
		 // create a script engine manager
        ScriptEngineManager factory = new ScriptEngineManager();
        // create a JavaScript engine
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        try{
        	// evaluate JavaScript code from String
        	engine.eval("print('Hello, World')");
        }catch(Exception ex){
        	System.out.println("Engine:"+ex.getMessage());
        }
	}
	
	/** доступ к объектам Java из скриптовых языков */
	private static void exampleAccess(){
		 // create a script engine manager
        ScriptEngineManager factory = new ScriptEngineManager();
        // create a JavaScript engine
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        
        // объект, который будет вызываться в коде 
        Wrap wrap=new Wrap("Key","Value");
        engine.put("java_object", wrap);
        try{
            engine.eval("print(java_object.getName())");
            engine.eval("print(java_object.getValue())");
        }catch(Exception ex){
        	System.out.println("Eval Exception:"+ex.getMessage());
        }
	}
	
	
	public static void main(String[] args){
		System.out.println("Получить поддерживаемые скриптовые языки ");
		getAllLanguage();
		System.out.println("Пример работы со скриптовыми языками ");
		example();
		System.out.println("Пример работы в скриптовом языке с объектами Java");
		exampleAccess();
	}
}


/** данный класс будем вызывать из кода доступа */
class Wrap{
	String name;
	String value;
	
	public Wrap(String name, String value){
		this.name=name;
		this.value=value;
	}

	public String getName() {
		System.out.println("Get Name:");
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		System.out.println("Get Value:");
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}


/*
	выполнение скрипта из файла:
        // evaluate JavaScript code from given file - specified by first argument
        engine.eval(new java.io.FileReader(args[0]));


	способ добраться до выполнения объектов Java в скриптовом языке:
      File f = new File("test.txt");
        // expose File object as variable to script
        engine.put("file", f);

        // evaluate a script string. The script accesses "file" 
        // variable and calls method on it
        engine.eval("print(file.getAbsolutePath())");
	


 */ 
