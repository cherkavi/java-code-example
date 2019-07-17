//import java.io.*;
//import java.util.*;

class my_class{
	public String s1;
	// это внутрення директива для компилятора, никак не проявляется, 
	// служит для постоянного отображения реального значения, 
	// а не как константа между одним присваивание и вторым, когда компилятор видит ее как константу
	// и если не стоит volatile, то изменения в это время другим потоком не дадут результата
	// boolean volatile value=true;
	// while(value){System.out.println(++i)};
	// если обратиться как this.value=false выйдет из цикла, если убрать volatile - вычный цикл 

	public volatile String s2;// переменная, которая может быть изменена в любое время другим потоком
	my_class(String s1,String s2){
		this.s1=s1;
		this.s2=s2;
	}
}
class my_thread implements Runnable{
	private my_class temp=null;
	private String name=null;
	my_thread(my_class temp,String thread_name){
		this.temp=temp;
		this.name=thread_name;
		(new Thread(this)).start();
	}
	public String toString(){
		return name+"  s1:"+temp.s1+"  s2(volatile):"+temp.s2;
	}
	public void run() {
		System.out.println(this);
		this.temp.s2=this.name;
		System.out.println(this);
	}
	
}
public class temp {
	public static void main(String args[]){
		my_class temp=new my_class("string one","string two");
		my_thread one=new my_thread(temp,"one");
		my_thread two=new my_thread(temp,"two");
	}
}
