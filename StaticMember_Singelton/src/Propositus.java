import java.io.Serializable;
/** класс, который наблюдается другим классом */
public class Propositus implements Serializable{
	private final static long serialVersionUID=1L;
	
	private static Propositus field_instance;
	private static String field_content;

	public static void create(){
		new Propositus();
		field_content=(new Propositus()).toString();
	}
	
	public static Propositus getInstance(){
		return field_instance;
	}
	
	public static void clear(){
		field_instance=null;
	}
	
	public Propositus(){
		System.out.println("constructor create");
	}
	
	public static String getContent(){
		return field_content;
	}
	
	public void finalize(){
		System.out.println(">>>   Propositus finalize");
	}
	
}
