package parametrize_method;

public class StaticParametrizeMethod {
	
	public static <T> T getSelf(T object){
		return object;
	}
	
	private <T> T getAnother(T object){
		return object;
	}
}
