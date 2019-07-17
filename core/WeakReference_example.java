import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.WeakHashMap;


public class WeakReference_example {
	
	/**
	 * Вывод
	 * WeakReference - обертка для объекта, который будет очищен после запуска GC
	 * HashWeakReference - контейнер для объектов-ключей и объектов-значений 
	 * ( так же будет очищен после запуска GC )
	 */
	public static void main(String[] args){
		System.out.println("begin");
		
		WeakReference<Value> ref=new WeakReference<Value>(new Value("first"));
		
		WeakHashMap<Key, Value> map=new WeakHashMap<Key,Value>();
		map.put(new Key("1"), new Value("first"));
		map.put(new Key("2"), new Value("second"));
		map.put(new Key("3"), new Value("third"));
		map.put(new Key("4"), new Value("fourth"));
		
		// System.gc();
	
		System.out.println("Value:"+ref.get());
		Iterator<Key> iterator=map.keySet().iterator();
		while(iterator.hasNext()){
			Key key=iterator.next();
			System.out.println(key+"   "+key.hashCode()+"    "+map.get(key));
		}
		System.out.println("-end-");
	}
}

class Key{
	private String key;
	public Key(String key){
		this.key=key;
	}
	@Override
	public String toString() {
		return key;
	}
}

class Value{
	private String value;
	public Value(String value){
		this.value=value;
	}
	@Override
	public String toString() {
		return this.value;
	}
}
