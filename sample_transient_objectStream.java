import java.io.*;
//import java.util.*;

class my_class implements Serializable{
	private String s1;
	private transient String s2;// тип переменной, которая не будет сохранена при Serialization
	my_class(String s1,String s2){
		this.s1=s1;
		this.s2=s2;
	}
	public String toString(){
		return "s1="+s1+"\n s2(transient)="+s2;
	}
}
class sample_ObjectStream{
	public void pack(my_class temp,String filename){
		try{
			FileOutputStream fos=new FileOutputStream(filename);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(temp);
			oos.flush();
			oos.close();
			fos.close();
		}
		catch(IOException e){
			System.out.println(" Error in save object ");
		}
	}
	public my_class unpack(String filename){
		my_class result=null;
		try{
			FileInputStream fis=new FileInputStream(filename);
			ObjectInputStream ois=new ObjectInputStream(fis);
			result=(my_class)ois.readObject();
			ois.close();
			fis.close();
		}
		catch(Exception e){
			System.out.println("Error in try read file");
		}
		return result;
	}
}
public class temp {
	public static void main(String args[]){
		my_class temp=new my_class("String one","String two");
		sample_ObjectStream sample=new sample_ObjectStream();
		System.out.println("Before:"+temp);
		sample.pack(temp, "c:\\object_file.tmp");
		temp=sample.unpack("c:\\object_file.tmp");
		System.out.println("After:"+temp);
	}
}
