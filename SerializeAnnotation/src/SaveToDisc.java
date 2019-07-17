
public class SaveToDisc {
	public static void main(String[] args){
		System.out.println("Begin");
		Value value=new Value("SaveToDisc");
		Utility.writeObject_to_file("c:\\object.file", value);
		System.out.println("End");
	}
}
