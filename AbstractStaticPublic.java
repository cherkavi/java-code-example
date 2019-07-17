
public class AbstractStaticPublic {
	/** показать что есть возможность устанавливать значения у статических членов даже для абстрактных классов */
	public static void main(String[] args){
		// установить у абстрактного класса значение статического члена класса
		AbstractClass.value="changed value";
		System.out.println("this is changed value:"+AbstractClass.value);
	}
}

/** абстрактный класс, у которого объявлен один из членов - static String*/
abstract class AbstractClass{
	public static String value="default string";
	public abstract String getValue();
}