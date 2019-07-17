package interface_class;

/**
 * проверка на возможность определения классов и/или перечислений внутри интерфейса 
 */
public class InterfaceClass {
	public static void main(String[] args){
		// пример реализации внутреннего класса, который не является статическим
		// то есть создается на основании созданного объекта класса
		Temp.TempClassInner tempInner=new Temp("").new TempClassInner();
		
		// пример внутреннего статического класса, 
		// который создается на основании именно класса, а не объекта 
		ITemp.TempInner value=new ITemp.TempInner("temp");
		System.out.println(value);
		
		ITemp temp=new Temp("Текст для описания");
		System.out.println(temp.getDescription());
	}
}

interface ITemp{
	public enum EValues{
		one, two, three;
	}
	
	public static class TempInner{
		private String text;
		public TempInner(String text){
			this.text=text;
		}
		@Override
		public String toString() {
			return text;
		}
	}
	
	public String getDescription();
}

class Temp implements ITemp{
	private String description;
	
	public Temp(String description){
		this.description=description;
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}

	public class TempClassInner{
		public TempClassInner(){
			
		}
	}
}
