package temp_package;

import java.util.*;
import java.io.*;
// смотрящий за объектом, который будет изменяться
// объект, который будет уведомлен об возможных изменениях

// класс который содержит интерфейс Observer(наблюдатель)
class Watcher implements Observer{
	String name="";
	Watcher(String name){
		this.name=name;
	}
	public void update(Observable arg0, Object arg1) {
		System.out.println(name+"/Watcher, вызван update:"+arg1);
	}
}
// класс который является расширением класса OBservable (наблюдаемый)
class Watched extends Observable{
	private String name="";
	Watched(String name){
		this.name=name;
	}
	void ring(String parameter){
		this.setChanged();// зафиксировать изменения в объекте
		this.notifyObservers(parameter);// вызвать всех Observers, которые зарегестрированы для данного объекта
	}
}
class my_class implements Serializable{
	String s;
	int i;
	float f;
	my_class(){
		this.s=new String("temp string");
		this.i=50;
		this.f=100;
	}
	my_class(String s,int i,float f){
		this.s=s;
		this.i=i;
		this.f=f;
	}
	public String toString(){
		String s="";
		s="i: "+i+" f:"+f+"  s:"+s;
		return s;
	}
}
class sample_Serialization{
	my_class temp_data1=new my_class();
	my_class temp_data2=new my_class();
	// упаковка объекта класса my_class implements Serializable в файл
	public boolean pack(my_class object_for_pack,String filename){
		try{
			// поток для записи в файл
			FileOutputStream fos=new FileOutputStream(filename);
			// обертка для записи объекта
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			// запись объекта
			oos.writeObject(object_for_pack);
			oos.flush();
			oos.close();
			fos.close();
			return true;
		}
		catch(Exception e){
			System.out.println("Error in save object to "+filename);
			return false;
		}
	}
	// распаковка объекта класса my_class implements Serializable из файла
	public my_class unpack(String filename){
		try{
			// поток для чтения из файла
			FileInputStream fis=new FileInputStream(filename);
			// обертка для чтения из файла
			ObjectInputStream ois=new ObjectInputStream(fis);
			// чтение объекта из файла
			return (my_class)ois.readObject();
		}
		catch(Exception e){
			System.out.println("Error in read object from "+filename);
			return null;
		}
	}
}
public class temp_class {
	public static void main(String args[]){
		Watcher master1=new Watcher("master1");
		Watcher master2=new Watcher("master2");
		Watched slave1=new Watched("slave1");
		Watched slave2=new Watched("slave2");
		slave1.addObserver(master1);// добавляем наблюдателя для slave1 - master1
		slave1.addObserver(master2);// добавляем наблюдателя для slave1 - master2
		
		slave2.addObserver(master2);// добавляем наблюдателя для slave2 - master2
		slave1.ring("RING SLAVE1");
		slave2.ring("ring slave2");
		//-------------------
	    sample_Serialization temp=new sample_Serialization();
	    my_class temp_my_class_1=new my_class();
	    temp.pack(temp_my_class_1, "c:\\pack");
	    System.out.println("original:"+temp_my_class_1);
	    System.out.println("readable:"+temp.unpack("c:\\pack"));
	}
}
