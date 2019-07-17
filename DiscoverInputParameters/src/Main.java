import java.util.StringTokenizer;


public class Main {
	public static void main(String[] args){
		// args=new String[]{"this is temp","and temp again", "and","not","run"};
		System.out.println("begin:");
		int counter=0;
		for(String current_value:args){
			counter++;
			StringTokenizer stringTokenizer=new StringTokenizer(current_value);
			System.out.println(counter+" : "+current_value+" count:"+stringTokenizer.countTokens());
		}
		System.out.println("end:");
	}
}
