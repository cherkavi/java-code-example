package generic_constructor;

public class TestGenericEntity {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException{
		System.out.println(" --- begin ---");
		GenericEntity value=new GenericEntity(new Integer(5));
		System.out.println(" ---  end  ---");
		Void voidValue=value.setValue();
		System.out.println(voidValue);
	}
}
