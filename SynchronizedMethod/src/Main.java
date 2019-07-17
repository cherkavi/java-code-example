import java.util.concurrent.TimeUnit;

/**
 * Данный проект демонстрирует Synchronized доступ к методам объекта,
 * подтверждение того, что "монитор" связан с объектом, а не с методом;
 * другими словами если метод синхронизирован, это равноценно установке на объект замка (syncronized(object))
 * и обращение к методу этого объекта в "мониторе"  
 *
 */

public class Main {
	public static void main(String[] args){
		Core core=new Core();
		new Watcher(core, true);
		new Watcher(core, false);
		while (core.getValue()<1000){
			try{
				TimeUnit.SECONDS.sleep(5);
			}catch(Exception ex){};
		}
	}
}
