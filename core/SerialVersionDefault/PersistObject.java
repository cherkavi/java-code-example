package SerialVersionDefault;

import java.io.Serializable;

public class PersistObject implements Serializable {
	private /* transient */ final static long serialVersion=1L;
	private int kod;
	private String name;
	
	public PersistObject(int kod, String name){
		this.kod=kod;
		this.name=name;
	}

	@Override
	public String toString() {
		return "PersistObject [kod=" + kod + ", name=" + name + "]";
	}

}
