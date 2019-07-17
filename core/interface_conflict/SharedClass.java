package interface_conflict;

public class SharedClass implements IFirst, ISecond{

	@Override
	public void printSomething() {
		System.out.println(this.getClass().getName());
	}

	public static void main(String[] args){
		System.out.println("begin");
		IFirst first=new SharedClass();
		first.printSomething();
		ISecond second=(ISecond)first;
		second.printSomething();
		System.out.println("-end-");
	}
}
