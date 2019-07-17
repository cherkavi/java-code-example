public class DoubleInterface implements One, Two{

	@Override
	public void display() {
		System.out.println("Hello");
	}
	
	public static void main(String[] args) throws OneException{
		One one=new DoubleInterface();
		one.display();
	}

}

class OneException extends Exception{
	
}
class TwoException extends Exception{
	
}

interface One{
	void display() throws OneException;
}
interface Two{
	void display() throws TwoException;
}
