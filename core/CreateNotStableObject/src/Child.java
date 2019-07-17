
public class Child extends Base{
	// Цель данного проекта - посмотреть значения объектов до их инициализации, путем обращения к ним из конструктора базового класса
	public static void main(String[] args){
		new Child();
	}
	
	public Child(){
		System.out.println("Child constructor");
		System.out.println("Object:"+valueObject);
		System.out.println("IntValue:"+valueInt);
	}

	private Object valueObject=new Object();
	private int valueInt=5;
	
	{
		System.out.println("Child anonim area");
	}
	
	@Override
	protected void initParameters() {
		System.out.println("Object:"+valueObject);
		System.out.println("IntValue:"+valueInt);
	}
}
