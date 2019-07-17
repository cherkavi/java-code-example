package finalize_throw;


/** тестирование бросания исключения в Finalize  
 * <br />
 * Вывод:
 * 	бросание исключения не влияет на другие объекты ( Finalize для других так же выполняется )
 * */
public class FinalizeThrow {
	
	public static void main(String[] args) throws Exception {
		System.out.println("begin");
		{
			for(int counter=0;counter<10;counter++)new Child(counter);
		}
		System.gc();
		Thread.sleep(5000);
		System.out.println("-END-");
	}
}

class Parent {
	private int index;
	public Parent(int index){
		this.index=index;
		System.out.println("Parent constructor:"+index);
	}
	
	protected int getIndex(){
		return this.index;
	}
	
	@Override
	protected void finalize() throws Throwable {
		System.out.println("Parent finalize:"+index);
		super.finalize();
	}
}

class Child extends Parent{
	private boolean flag=false;
	
	public Child(int index){
		this(index, false);
		
	}
	
	public Child(int index, boolean flag){
		super(index);
		this.flag=flag;
		System.out.println("Child constructor:"+this.getIndex());
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("Child finalize:"+this.getIndex());
		super.finalize();
		if(flag==false){
			System.out.println("Child finalize throw Exception:"+this.getIndex());
			throw new Exception();
		}
		// super.finalize();
	}
}

