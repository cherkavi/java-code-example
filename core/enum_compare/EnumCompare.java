package enum_compare;

public class EnumCompare {
	public static enum Count{
		one, two, three;
	}
	
	public static void main(String[] args){
		System.out.println(Count.one.compareTo(Count.three));
		System.out.println(Count.one==Count.three);
		
	}
}
