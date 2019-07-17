/** заменить в строке один символ на другой, используя стандартный вариант */
public class TestStringReplacer {
	public static void main(String[] args){
		System.out.println("begin");
		String testString="c:\\out_from_computer\\sub_folder\\filename.ext";
		System.out.println("Original String:"+testString);
		// for replace symbol \ for /
		System.out.println("Changed String:"+testString.replaceAll("\\\\", "/"));
		System.out.println("end");
	}
}
