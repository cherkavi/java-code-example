package temp_package;

import java.util.*;

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
	
	}
}
