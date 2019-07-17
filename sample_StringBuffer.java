class sample_StringBuffer{
	sample_StringBuffer(){
		StringBuffer s=new StringBuffer("hello");
		StringBuffer s2=new StringBuffer("HELLO");
		// метод length
		System.out.println(" length():"+s.length());
		// метод capacity - length+16 - сколько зарезервировано символов
		System.out.println(" capacity():"+s.capacity());
		// метод ensureCapacity - установить размер буфера
		s.ensureCapacity(s.capacity()+5);
		System.out.println(" ensureCapacity():"+s.capacity());
		// метод setLength
		s.setLength(s.length()+2);
		System.out.println(" setLength():"+s);
		// перевод StringBuffer в String, а потом опять в StringBuffer
		s=new StringBuffer(s.toString().trim());
		System.out.println("convert to string, trim, convert to StringBuffer"+s);
		// метод charAt();
		for(int i=0;i<s.length();i++){
			System.out.print(i+" - "+s.charAt(i)+";  ");
		}
		System.out.println();
		// метод setCharAt();
		s.setCharAt(s.indexOf("l"), 'L');
		s.setCharAt(s.indexOf("l"), 'L');
		System.out.println(" setCharAt():"+s);
		// method getChars
		char[] chars=new char[s.length()];
		s.getChars(0, s.length(), chars, 0);
		String s3=new String(chars);
		System.out.println("Print chars:"+s3);
		// method append()
		s2.append(s3).append(s2);
		System.out.println("Append():"+s2);
		// method insert()
		s2.insert(0,s3);
		System.out.println("insert():"+s2);
		// method revers()
		s2.reverse();
		System.out.println("revers():"+s2);
		// method delete() and deleteCharAt()
		s2.delete(0, s2.indexOf("h"));
		s2.delete(1, s2.indexOf("E"));
		s2.deleteCharAt(2);
		s2.deleteCharAt(2);
		s2.delete(s2.indexOf("e"), s2.indexOf("o"));
		s2.delete(s2.lastIndexOf("L"), s2.length());
		s2.deleteCharAt(s2.length()-1);
		System.out.println("delete():"+s2);
		// method replace()
		s2.replace(2, 4, "ll");
		System.out.println("replace():"+s2);
		// method substring()
		System.out.println("substring():"+s2.substring(s2.indexOf("E")));
		System.out.println("substring():"+s2.substring(s2.indexOf("E")+1,s2.indexOf("o")));
	}
}
public class temp {
	public static void main(String args[]){
		sample_StringBuffer temp=new sample_StringBuffer();
	}
}
