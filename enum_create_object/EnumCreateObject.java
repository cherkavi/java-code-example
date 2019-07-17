package enum_create_object;

/**
 * Цель проекта - понять поведение enum при обращении к элементам.
 * Вывод: перед статическим анонимным блоком ( в статическом конструкторе ) создаются все элементы,
 * которые затем предоставляются пользователю. То есть не создаются каждый раз, а только однажды и затем предоставляются пользователю
 */
public class EnumCreateObject {
	public static void main(String[] args){
		System.out.println(Values.one);
		System.out.println(Values.one);
		System.out.println(Values.one.getValue());
		System.out.println(Values.two.getValue());
		System.out.println(Values.two.getValue());
		System.out.println(Values.two.hashCode());
		System.out.println(Values.two.hashCode());
	}
	
}

enum Values{
	one(1),two(2),three(3),four(4);
	static{
		System.out.println("after static constructor");
	}
	private int intValue=0;
	
	Values(int value){
		System.out.println("Enum constructor: "+value);
		this.intValue=value;
	}
	
	/** получить значение переменной  */
	public int getValue(){
		System.out.println("Values#getValue: "+this);
		return this.intValue;
	}
}