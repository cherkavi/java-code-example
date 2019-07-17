
/** демонстрация поведения статического метода в многопоточной среде */
public class ThreadForStatic {
	public static void main(String[] args){
		System.out.println("begin");
		(new CommonThread()).start();
		try{Thread.sleep(5000);}catch(Exception ex){};
		(new CommonThread()).start();
		System.out.println("end");
	}
}

class CommonThread extends Thread{
	public void run(){
		for(int counter=0;counter<5;counter++){
			//Common.staticBeat();
			//Common.beat();
			// в данном случае создается уникальная динамическая переменная в виде строкового значения 
			Common.beat(Integer.toString(counter));
			try{
				Thread.sleep(1000);
			}catch(Exception ex){};
		}
	}
}

/** Общий класс для получения доступа к статическому методу */
class Common{
	private static int counter=0;
	public static void staticBeat(){
		// статическая переменная будет одна для всех потоков 
		for(counter=0;counter<10;counter++){
			try{
				System.out.println(counter+" : "+Thread.currentThread().getName());Thread.sleep(500);
			}catch(Exception ex){};
		}
	}

	public static void beat(){
		// Динамически создаваемая переменная будет уникальна для каждого потока 
		for(int counter=0;counter<10;counter++){
			try{
				System.out.println(counter+" : "+Thread.currentThread().getName());Thread.sleep(500);
			}catch(Exception ex){};
		}
	}

	public static void beat(String information){
		// Динамически создаваемая переменная будет уникальна для каждого потока 
		for(int counter=0;counter<10;counter++){
			try{
				System.out.println(counter+" : "+information+"  "+Thread.currentThread().getName());Thread.sleep(500);
			}catch(Exception ex){};
		}
	}
	
}