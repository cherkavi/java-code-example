package exception_in_finalize;

/**
 * Отслеживание работы метода Finalize
 * а именно: при создании объекта Parent, локально создается объект Child, 
 * при сборке мусора сначала уничтожается объект Child, затем объект Parent.
 * При уничтожении объекта Child в методе Finalize, который вызывает сборщик мусора,
 * выбрасывает исключение
 * Результат - исключение в Child никак не влияет на вызов методов finalize у других объектов в очереди
 *  
 */
public class ExceptionInFinalize {
	public static void main(String[] args){
		System.out.println("begin");
		System.out.println("-end-");
		new Parent();
		System.gc();
	}
}

class Child{
	public Child(){
		System.out.println("Child create:"+this.toString());
	}
	
	protected void finalize() throws Throwable {
		System.out.println("Child finalize:"+this.toString());
		throw new RuntimeException("");
	};
}


class Parent{
	public Parent(){
		System.out.println("Parent create:"+this.toString());
		new Child();
	}
	
	protected void finalize() throws Throwable {
		System.out.println("Parent finalize:"+this.toString());
		super.finalize();
	};
}
