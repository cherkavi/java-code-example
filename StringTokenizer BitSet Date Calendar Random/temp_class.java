package temp_package;

import java.util.*;

class sample_StringTokenizer{
	sample_StringTokenizer(String text){
		// создать объект в котором будут храниться token's 
		StringTokenizer st=new StringTokenizer(text," ;,",false);//true - будут возвращены и разделители как элементы 
		System.out.println(" Всего элементов: "+st.countTokens());
		while(st.hasMoreTokens()){
			System.out.println(st.nextToken());
		}
	}
}
class sample_BitSet{
	BitSet bs=null;
	BitSet bs2=null;
	void set_begin(){
		bs.clear();
		bs2.clear();
		for(int i=0;i<16;i++){
			if(i%4==0){
				bs.set(i);
			}
			if(i%3==0){
				bs2.set(i);
			}
		}
	}
	sample_BitSet(){
		int len=16;
		bs=new BitSet(len);
		bs2=new BitSet(len);

		this.set_begin();
		System.out.println("Исходный образ:"+this.bs+" length:"+bs.length());
		System.out.println("Исходный образ:"+bs2+" length:"+bs2.length());
		bs.and(bs2);
		System.out.println("AND:"+bs);

		this.set_begin();
		bs.andNot(bs2);
		System.out.println("AndNot:"+bs);

		this.set_begin();
		bs.or(bs2);
		System.out.println("OR:"+bs);
		
		this.set_begin();
		bs.xor(bs2);
		System.out.println("XOR"+bs);
	}
}
class sample_Date{
	sample_Date(){
		Date d=new Date();
		int date=d.getDate();
		int year=d.getYear();
		int month=d.getMonth();
		int day=d.getDay();
		int hours=d.getHours();
		int minutes=d.getMinutes();
		int seconds=d.getSeconds();
		long miliseconds=d.getTime();
		try{
			Thread.sleep(1000);// если не ставить задержку (d=d2): after - false; before - false;
		}
		catch(Exception e){
			
		}
		Date d2=new Date();
		System.out.println("Year:"+year);
		System.out.println("Month:"+month);
		System.out.println("Date:"+date);
		System.out.println("Day:"+day);
		System.out.println("Hours:"+hours);
		System.out.println("Minutes:"+minutes);
		System.out.println("Seconds:"+seconds);
		System.out.println("Miliseconds:"+miliseconds);
		
		System.out.println("Now:"+(new Date()));
		System.out.println("Date1:"+d+"   "+d.getTime());
		System.out.println("Date2:"+d2+"   "+d2.getTime());
		System.out.println("Date1 after Date2:"+d.after(d2));
		System.out.println("Date1 before Date2:"+d.before(d2));
	}
}
class sample_Calendar{
	sample_Calendar(){
		Calendar calendar=Calendar.getInstance();// получить текущее значение времени для текущих данных(языковая среда и пояс)
		int date=calendar.get(Calendar.DATE);
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH);
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		int hours=calendar.get(Calendar.HOUR);
		int minutes=calendar.get(Calendar.MINUTE);
		int seconds=calendar.get(Calendar.SECOND);
		long miliseconds=calendar.get(Calendar.MILLISECOND);

		System.out.println("Year:"+year);
		System.out.println("Month:"+month);
		System.out.println("Date:"+date);
		System.out.println("Day:"+day);
		System.out.println("Hours:"+hours);
		System.out.println("Minutes:"+minutes);
		System.out.println("Seconds:"+seconds);
		System.out.println("Miliseconds:"+miliseconds);
	}
}
class sample_Random{
	sample_Random(){
		Random r=new Random();// отправная точка по умолчанию
		Random r2=new Random((new Date()).getTime());// отправная точка - текущее время
		System.out.println(r.nextBoolean());
		System.out.println(r.nextDouble());
		System.out.println(r.nextFloat());
		System.out.println(r.nextGaussian());
		System.out.println(r.nextInt());
		System.out.println(r.nextLong());
		System.out.println(r.nextInt(10));//вывод случайного числа [0..10]
		r.setSeed((new Date()).getTime());
	}
}

public class temp_class {
	public static void main(String args[]){
		new sample_StringTokenizer("Hello this is my computer,and Win OS");
		new sample_BitSet();
		new sample_Date();
		new sample_Calendar();
		new sample_Random();
	}
}
