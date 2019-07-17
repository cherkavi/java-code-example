
class my_exception extends Exception{
	my_exception(){
		super();
	}
	my_exception(String s){
		super(s);
	}
}


public class temp_class {
	public static int divide(int arg1,int arg2) throws my_exception{
		if(arg2!=0){
			return arg1/arg2;
		}
		else {
			throw new my_exception("divide by zero");
		}
	}
	public static void main(String args[]){
		try{
			System.out.println(divide(10,20));
			System.out.println(divide(10,0));
		}
		catch(my_exception e){
			System.out.println(e.getMessage());
		}
	}
}
