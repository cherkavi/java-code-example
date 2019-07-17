import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


public class FXDemo {
	public static void main(String[] args){
		try{
			ScriptEngineManager manager = new ScriptEngineManager(null);
		    final ScriptEngine engine = manager.getEngineByName("FX");

		    engine.put("msg:java.lang.String", "JavaFX Script");

		    Runnable r = new Runnable() {
		      public void run() {
		        try {
		          System.out.println("EDT running: " + EventQueue.isDispatchThread());
		          engine.eval(new BufferedReader(new FileReader("script.fx")));
		        } catch (Exception e) {
		          e.printStackTrace();
		        }
		      }
		    };
		    EventQueue.invokeLater(r);		
		}catch(Exception ex){
			System.out.println("Exception: "+ex.getMessage());
		}
	}
	
}
