import java.io.*;
import java.util.*;
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
class sample_Float{
	double d=10.25;
	Float f_object;
	float f;
	sample_Float(){
		try{
			f_object=new Float("1.5");
		}
		// error in convert String to Float
		catch(NumberFormatException e){
			System.out.println("Exception:"+e.getMessage());
		}
		System.out.println("Convert String to Float:"+f_object);
		if("Float".equalsIgnoreCase(f_object.TYPE.toString())){
			System.out.println(" data is float");
		}
		System.out.println("Float Type:"+f_object.TYPE);
	}
}
class sample_process{
	sample_process(){
		// получаем объект текущего времени исполнения
		Runtime r=Runtime.getRuntime();
		try{
			System.out.println("run program");
			// создать Process и запустить его на выполнение
			Process p=r.exec("calc");
			// разрушить процесс
			//p.destroy();
			// ожидание окончания процесса
			p.waitFor();
			System.out.println("end run program");
		}
		catch(Exception e){
			System.out.println("Error in run program \n"+e.getMessage());
		};
	}
}
class sample_runtime{
	sample_runtime(){
		String[] s={"calc","notepad"};
		try{
			// получить Process из Runtime.getRuntime() и запустить процесс на выполнение
			Process p=Runtime.getRuntime().exec(s);
		}
		catch(IOException e){
			System.out.println("Error \n"+e.getMessage());
		}
		int memory_before=(int)Runtime.getRuntime().freeMemory();
		System.out.println("Before freeMemory:"+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory());
		// выделить память для Integer[1000]
		Integer[] temp_array=new Integer[1000];
		for(int i=0;i<1000;i++){
			temp_array[i]=new Integer(i);
		}
		int memory_after=(int)Runtime.getRuntime().freeMemory();
		System.out.println("After Reserv Integer(1000) freeMemory:"+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory()+"  reserved:"+(memory_before-memory_after));
		// освободить память для Integer[1000]
		for(int i=0;i<1000;i++){
			temp_array[i]=null;
		}
		// активировать сборщик мусора
		System.gc();
		System.out.println("After System.gc() freeMemory:"+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory());
	}
}
class sample_system{
	sample_system(){
		// получить текущее время - количество милисекунд, прошедших с 1.01.1970
		long time_before=System.currentTimeMillis();
		// получить свободную память 
		long memory_before=Runtime.getRuntime().freeMemory();
		// выделить Integer[1000000]
		Integer[] array=new Integer[1000000];
		// инициировать Integer[1000000]
		for(int i=0;i<1000000;i++){
			array[i]=new Integer(i);
		}
		// получить текущее время
		long time_after=System.currentTimeMillis();
		// получить текущую память
		long memory_after=Runtime.getRuntime().freeMemory();
		// вывести прошедшее время
		System.out.println("Elapsed time:"+(time_after-time_before));
		System.out.println("Memory reserver: "+(memory_after-memory_before)+" \n Free memory:"+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory());
		
		time_before=System.currentTimeMillis();
		// получить свободную память 
		memory_before=Runtime.getRuntime().freeMemory();
		// выделить Integer[1000000]
		// осободить память 
		for(int i=0;i<1000000;i++){
			array[i]=null;
		}
		// вызвать сборщик мусора
		System.gc();
		// получить текущее время
		time_after=System.currentTimeMillis();
		// получить текущую память
		memory_after=Runtime.getRuntime().freeMemory();
		// вывести прошедшее время
		System.out.println("Elapsed time:"+(time_after-time_before));
		System.out.println("Memory reserver: "+(memory_after-memory_before)+" \n Free memory:"+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory());
		byte[] b1="abcdefg".getBytes();
		byte[] b2="1234567".getBytes();
		System.out.println("Before: s1="+new String(b1)+"  \ns2="+new String(b2));
		//System.arraycopy(b1, 1, b2, 1, b1.length-1);
		System.out.println("After: s1="+new String(b1)+"  \ns2="+new String(b2));
		for(int i=0;i<System.getProperties().size();i++){
			System.out.println(i+":"+System.getProperties().elements().toString());
		}
		
	} 
}
class simple_FileOutputStream{
	simple_FileOutputStream(String filename,String information){
		try {
			FileOutputStream fos=new FileOutputStream(filename,true);// append, if fileexists
			fos.write(information.getBytes());
			fos.flush();
			fos.close();
			System.out.println("OK");
		} 
		catch (Exception e) {
			System.out.println("Error \n"+e.getMessage());
		}
	}
}
class simple_FileInputStream{
	simple_FileInputStream(String filename){
		try{
			FileInputStream fis=new FileInputStream(filename);
			byte[] buffer=new byte[200];
			int read_counter=0;
			while((read_counter=fis.read(buffer))!=-1){
				String s=new String(buffer,0,read_counter);
				System.out.println(">>>"+s);
			}
		}
		catch(Exception e){
			System.out.println("Error");
		}
	}
}
class simple_ByteArrayInputStream{
	simple_ByteArrayInputStream(String s){
		ByteArrayInputStream bais=new ByteArrayInputStream(s.getBytes());
		int read_int=0;
		while((read_int=bais.read())!=-1){
			System.out.print((char)read_int);
		}
		System.out.println();
		bais.reset();
		while((read_int=bais.read())!=-1){
			System.out.print((char)read_int);
		}
	}
}
class simple_ByteArrayOutputStream{
	simple_ByteArrayOutputStream(String s,int capacity){
		ByteArrayOutputStream baos=null;
		if(capacity!=0){
			baos=new ByteArrayOutputStream(capacity);
		}
		else {
			baos=new ByteArrayOutputStream();
		}
		try{
			baos.write(s.getBytes());
			baos.writeTo(new FileOutputStream("c:\\2.txt",true));
		}
		catch(Exception e){
			System.out.println("Error \n"+e.getMessage());
		}
	}
}
class simple_BufferedInputStream{
	simple_BufferedInputStream(String filename){
		try{
			File f=new File(filename);
			FileInputStream fis=new FileInputStream(f);
			BufferedInputStream bis=new BufferedInputStream(fis);
			int read_count=0;
			byte[] buffer=new byte[100];
			while((read_count=bis.read(buffer))!=-1){
				String s=new String(buffer,0,read_count);
				System.out.println(">>>"+s);
			}
		}
		catch(Exception e){
			System.out.println("error \n"+e.getMessage());
		}
	}
}
class simple_BufferedOutputStream{
	simple_BufferedOutputStream(String filename,String information){
		try{
			FileOutputStream fos=new FileOutputStream(filename);
			BufferedOutputStream bos=new BufferedOutputStream(fos);
			bos.write(information.getBytes());
			bos.flush();
			bos.close();
			fos.close();
		}
		catch(Exception e){
			System.out.println("error \n"+e.getMessage());
		}
	}
}

class temp_Enumeration implements Enumeration{
	private Enumeration files=null;
	temp_Enumeration(Vector v){
		this.files=v.elements();
	}
	public boolean hasMoreElements() {
		// TODO Auto-generated method stub
		return files.hasMoreElements();
	}
	public Object nextElement() {
		// TODO Auto-generated method stub
		try{
			return new FileInputStream(files.nextElement().toString());
		}
		catch(IOException e){
			return null;
		}
	}
	
}
class simple_SequenceInputStream{
	simple_SequenceInputStream(String filename1,String filename2, String filename3){
		Vector v=new Vector();
		v.clear();
		v.add(filename1);
		v.add(filename2);
		v.add(filename3);
		temp_Enumeration temp_enumeration=new temp_Enumeration(v);
		InputStream is=new SequenceInputStream(temp_enumeration);
		int read_count=0;
		byte[] buffer=new byte[1024];
		try{
			while((read_count=is.read(buffer))!=-1){
				String s=new String(buffer,0,read_count);
				System.out.println(s);
			}
		}
		catch(IOException e){
			System.out.println("error");
		}
	}
}
public class temp {
	public static void main(String args[]){
		//simple_FileOutputStream temp=new simple_FileOutputStream("c:\\1.txt","information for output\n into file");
		//simple_FileInputStream temp=new simple_FileInputStream("c:\\1.txt");
		//simple_ByteArrayInputStream temp=new simple_ByteArrayInputStream("hello");
		//simple_ByteArrayOutputStream temp=new simple_ByteArrayOutputStream("this is simple for ByteArrayOutputStream",0);
		//simple_BufferedInputStream temp=new simple_BufferedInputStream("c:\\1.txt");
		//simple_BufferedOutputStream temp=new simple_BufferedOutputStream("c:\\3.txt","temp information ");
		new simple_SequenceInputStream("c:\\1.txt","c:\\1.txt","c:\\1.txt");
	}
}
