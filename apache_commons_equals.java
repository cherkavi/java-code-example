import org.apache.commons.lang.builder.EqualsBuilder;


public class apache_commons_equals {
	
	public static void main(String[] args) throws Exception {
		System.out.println("Begin");
		TestEquals test=new TestEquals("first", 1);
		TestEquals test2=(TestEquals)test.clone();
		TestEquals test3=new TestEquals("first", 1);
		TestEquals test4=new TestEquals("second", 2);
		System.out.println("test==test2 ? :"+EqualsBuilder.reflectionEquals(test, test2));
		System.out.println("test==test3 ? :"+EqualsBuilder.reflectionEquals(test, test3));
		System.out.println("test==test4 ? :"+EqualsBuilder.reflectionEquals(test, test4));
		
		EqualsBuilder builder=new EqualsBuilder();
		builder.append(test, test4);
		// builder.append(test, test3);
		System.out.println("Multiply: "+builder.isEquals());
		System.out.println("-end-");
		/*
			Begin
			test==test2 ? :true // equals with Clone
			test==test3 ? :true // equals with another object with some values 
			test==test4 ? :false // not equals with another object with different values 
			Multiply: false 	// not equals with another object with different values
			-end-
		 */
	}
}


class TestEquals implements Cloneable{
	private String stringValue;
	private int intValue;

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public TestEquals(String s, int i){
		this.stringValue=s;
		this.intValue=i;
	}

	@Override
	public String toString() {
		return "TestEquals [intValue=" + intValue + ", stringValue="
				+ stringValue + "]";
	}
	
}