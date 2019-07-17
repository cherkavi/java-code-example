package thread_local;

public class KeyValue implements Cloneable{
private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String toString() {return this.name;}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		System.out.println("KeyValue: clone called");
		return super.clone();
	}
}
