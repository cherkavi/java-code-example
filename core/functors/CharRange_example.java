package functors;

import org.apache.commons.lang.CharRange;

public class CharRange_example {
	public static void main(String[] args){
		System.out.println("begin");
		CharRange charRange=new CharRange('a','z');
		CharRange charSubRange=new CharRange('e','j');
		System.out.println("a..z contain d :"+charRange.contains('d'));
		System.out.println("is subrange in range:"+charRange.contains(charSubRange));
		System.out.println("-end-");
	}
}
