package interface_access;

public class PrintValue implements IPrintable, IPrint{

	@Override
	public void print() {
		System.out.println(this);
	}

}
