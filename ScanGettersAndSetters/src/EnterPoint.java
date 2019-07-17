
public class EnterPoint {
	public static void main(String[] args){
		Source source=new Source();
		source.setId(1);source.setName("temp name");
		System.out.println("Source: "+source);
		Destination destination=new Destination();
		System.out.println("Destination before: "+destination);
		
		GetterSetterAnalisator analisator=new GetterSetterAnalisator();
		analisator.copy(source, destination);
		System.out.println("Destination after: "+destination);
	}
}
