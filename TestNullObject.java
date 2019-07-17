
public class TestNullObject {
	public static void main(String[] args){
		Child child=null;
		if(child instanceof Base){
			System.out.println("Base");
		}else{
			// null не имеет родителей и будет выведен указанный ниже текст 
			System.out.println("unknown");
		}
	}
}


class Base{
	
}

class Child extends Base{
	
}