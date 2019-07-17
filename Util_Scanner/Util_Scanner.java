import java.io.StringReader;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Util_Scanner {
	
	public static void main(String[] args){
		System.out.println("begin");
		new Util_Scanner();
		System.out.println("- end -");
	}
	
	
	private Scanner scanner;
	public Util_Scanner(){
		// getWords();
		getNumbers();
	}

	private void getWords(){
		scanner=new Scanner(new StringReader("this is string for 10 scan "));
		int counter=0;
		while(scanner.hasNext()){
			counter++;
			System.out.println(counter+":"+scanner.next());
		}
	}
	
	private void getNumbers(){
		scanner=new Scanner(new StringReader("21 this 12  is 23stri44ng for 10 scan 33"));
		int counter=0;
		while(scanner.hasNextInt()){
			counter++;
			System.out.println(counter+":"+scanner.nextInt());
		}
	}
}
