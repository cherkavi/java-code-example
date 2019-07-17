import org.apache.commons.pool.BaseObjectPool;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;


/** демонстрация применения Pool-a объектов,
 Ключевой интерфейс:
  public interface PoolableObjectFactory {
    Object makeObject();
    void activateObject(Object obj);
    void passivateObject(Object obj);
    boolean validateObject(Object obj);
    void destroyObject(Object obj);
}

public interface KeyedPoolableObjectFactory {
    Object makeObject(Object key);
    void activateObject(Object key, Object obj);
    void passivateObject(Object key, Object obj);
    boolean validateObject(Object key, Object obj);
    void destroyObject(Object key, Object obj);
}

	Центальные классы для реализации:
	BaseObjectPool (implements PoolableObjectFactory)
		-базовые функции объекта, который будет ложиться в Pool
		 
	Пулы, которые будут генерировать объекты, реализующие интерфейс PoolableObjectFactory
	StackObjectPool
	StackKeyedObjectPool
	
	GenericObjectPool
	GenericKeyedObjectPool
	
	SoftReferenceObjectPool

 * */
public class ApachePool_Example {
	
	public static void main(String[] args){
		/** создать Pool из наростающих объектов */
		StackObjectPool pool=new StackObjectPool(new PoolFactory());
		for(int counter=0;counter<10;counter++){
			try{
				System.out.println(counter+" "+pool.borrowObject().toString());
			}catch(Exception ex){
				System.out.println("Borrow object Exception:"+ex.getMessage());
			}
		}
		
		/** создать Pool из набора, который должен быть строго ограничен кол-вом, временем ожидания, временем жизни... */
		GenericObjectPool genericPool=new GenericObjectPool(new PoolFactory(),5);
		ObjectInPool[] queue=new ObjectInPool[10];
		for(int counter=0;counter<10;counter++){
			try{
				/** "одолжить" объект из Pool-a*/
				queue[counter]=(ObjectInPool)genericPool.borrowObject();
				System.out.println(counter+" "+queue[counter].toString());
				/** если данный блок убрать, то на counter==4 поток повиснет в режиме ожидания возврата объектов  */
				if(counter==4){ // только что был взят последний доступный объект - 5-ый 
					System.out.println(">>> clean <<<"); 
					//genericPool.invalidateObject(queue[0]);// убрать объект из Pool
					//genericPool.invalidateObject(queue[1]);// убрать объект из Pool 
					genericPool.returnObject(queue[0]);// вернуть объект в Pool
					genericPool.returnObject(queue[1]);// вернуть объект в Pool 
				}
			}catch(Exception ex){
				System.out.println("Borrow object Exception:"+ex.getMessage());
			}
		}
	}
}

/** фабрика, которая будет управлять жизненным циклом объектов */
class PoolFactory extends BasePoolableObjectFactory{
	@Override
	public Object makeObject() throws Exception {
		System.out.println("get Object");
		return new ObjectInPool();
	}

	@Override
	public void passivateObject(Object object){
		try{
			((ObjectInPool)object).resetObject();
		}catch(Exception ex){
			System.out.println("PoolFactory#passivateObject Exception:"+ex.getMessage());
		}
	}
}


/** объекты, по которым будет создан Pool*/
class ObjectInPool{
	private static Integer value=new Integer(0);
	private Integer currentValue;
	public ObjectInPool(){
		currentValue=value++;
		System.out.println("create new object:Object in Pool");
	}
	
	@Override
	public String toString(){
		return "ObjectInPool ["+currentValue.toString()+"]";
	}
	
	public void resetObject(){
		System.out.println("resetObject:"+currentValue.toString());
	}
	
	@Override
	public void finalize(){
		System.out.println("finalizeObject:"+currentValue.toString());
	}
}
