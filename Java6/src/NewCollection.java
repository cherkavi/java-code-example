import java.util.ArrayDeque;


public class NewCollection {
	
/**
 * Deque Ц развитие интерфейса Queue. Ќазвание расшифровываетс€ как Ђdouble ended queueї и произноситс€ как ƒ≈ .  ак вы наверно догадались основна€ иде€ в том, чтобы организовать очередь, котора€ работает с обоих концов Ц как FIFO (интерфейс Queue) и LIFO (интерфейс Stack)

BlockingDeque Ц интерфейс позвол€ет блокировать себ€ при ожидании поступлени€ в очередь элемента.

NavigableSet, NavigableMap Ц интерфейсы позвол€ют искать наиболее близкое значение в наборе.

ConcurrentNavigableMap Ц тоже, что и NavigableMap, но еще поддерживает рекурсию в подмэпах. Ќе смотрел более подробно Ц предоставл€ю это сделать вам самим.
 */
	private static void exampleDeque(){
		ArrayDeque<String> deque=new ArrayDeque<String>();
		
		deque.add("Middle object");
		
		deque.addFirst("First");
		deque.addLast("Last");
		
		for(String element:deque){
			System.out.println(element);
		}
	} 
	
	public static void main(String[] args){
		exampleDeque();
	}
	
}
