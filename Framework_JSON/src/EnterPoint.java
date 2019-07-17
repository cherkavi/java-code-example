import com.google.gson.Gson;


/** пример использования JSON - прослойки между различными языками */
public class EnterPoint {
	
	private static void out(Object value){
		System.out.println(value);
	}
	
	public static void main(String[] args){
		out("Serialization");
		Gson gson=new Gson();
		out("simple int(autopack): "+gson.toJson(1));
		out("string: "+gson.toJson("abcd"));       
		out("long: "+gson.toJson(new Long(10))); 
		int[] values = { 1 };
		out("array: "+gson.toJson(values));
		
		out("Deserialization");
		int one = gson.fromJson("1", int.class);
		out("one:"+one);
		Integer intOne = gson.fromJson("1", Integer.class);
		out("intOne:"+intOne);
		Long longOne = gson.fromJson("1", Long.class);
		out("longOne:"+longOne);
		Boolean boolValue = gson.fromJson("false", Boolean.class);
		out("boolValue:"+boolValue);
		String str = gson.fromJson("\"abc\"", String.class);
		out("str:"+str);
		String anotherStr = gson.fromJson("[\"abc\"]", String.class);
		out("anotherStr: "+anotherStr);
	}
	
}
