
/** проект, который демонстрирует использование Enum */
public class EnterPoint {
	private static void debug(Object information){
		System.out.println(information);
	}
	
	
	
	public static void main(String[] args){
		debug("-- begin --");
		debug("    simple Example ");
		Variables v1=Variables.V_1;
		Variables v2=Variables.V_2;
		
		debug("Value(1):"+v1.name());
		debug("Value(2):"+v2.name());
		debug("String to Variable:"+Variables.valueOf("V_3").name());
		
		debug("Value(1) order:"+v1.ordinal());
		debug("Value(2) order:"+v2.ordinal());
		
		debug("   complex example ");
		Widths w1=Widths.W_1;
		Widths w2=Widths.W_3;
		debug("Widths: "+w1.getValue()+"  ("+w1.getValue()+")");
		debug("Widths: "+w2.getValue()+"  ("+w2.getValue()+")");
		debug("--  end  --");
	}
}

/** declaration of enumeration type */
enum Variables {V_1, V_2, V_3};

/** declaration of enumeration type with parameters */
enum Widths{
	/** width of first */
	W_1(5),
	/** width of second */
	W_2(7),
	/** width of third */
	W_3(12),
	/** width of fourth */
	W_4;
	
	private int value;
	
	Widths(int value){
		System.out.println("constructor:"+value);
		this.value=value;
	}
	
	Widths(){
		System.out.println("constructor");
		this.value=-1;
	}
	
	public int getValue(){
		return this.value;
	}
}
