package change_parameter_into_method;

/**
 Вывод: при передаче параметра копируется значение, в данном случае ссылка на объект, 
 при присваивании новой ссылке ( та которая создана внутри метода {@link #changeParameter(TempValue)} )
 будет создан новый объект в памяти и теперь две разные ссылки будут указывать на два разных объекта 
 
begin
Before: original value
After: original value
-end-
 */
public class ChangeParameterIntoMethod {
	public static void main(String[] args){
		System.out.println("begin");
		TempValue value=new TempValue("original value");
		System.out.println("Before: "+value);
		changeParameter(value);
		System.out.println("After: "+value);
		System.out.println("-end-");
	}
	
	private static void changeParameter(TempValue value){
		value=new TempValue("changed parameter");
	}
	
}

class TempValue{
	private String value;
	public TempValue(String value){
		this.value=value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
