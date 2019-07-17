
public class Base {
	{
		System.out.println("Base anonim area");
	}
	
	public Base(){
		System.out.println("Base constructor");
		this.initParameters();
	}
	
	protected void initParameters(){
		System.out.println("Base.initParameters");
	}
}
