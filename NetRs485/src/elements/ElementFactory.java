package elements;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import net_exchange.Exchanger;
import elements.base.BaseElement;

/** класс, который сканирует сеть на наличие устройств, и выдает в виде классов все устройства, которые были распознаны */
public class ElementFactory {
	/** объект, который служит для отправки данных в сеть и получения данных из сети */
	private Exchanger exchanger;
	
	/** объект, который сканирует сеть, и выдает все */
	public ElementFactory(Exchanger exchanger){
		this.exchanger=exchanger;
	}
	
	/** просканировать сеть на наличие в ней элементов */
	public ArrayList<BaseElement> scanNet(){
		return this.scanNet(0x10,0xff);
	}
	
	/** просканировать сеть на наличие в ней элементов в заданном диапазоне адресов<br>
	 * <b> [ begin .. end ] </b> 
	 * @param begin - [0x10..0xff] начало диапазона сканирования (включительно )
	 * @param end - [0x10..0xff] конец диапазона сканирования ( включительно ) 
	 * */
	public ArrayList<BaseElement> scanNet(int begin, int end){
		ArrayList<BaseElement> elements=new ArrayList<BaseElement>();
		for(int counter=begin;counter<=end;counter++){
			try{
				byte[] response=this.exchanger.sendData(this.getPackageSoftLocator((byte)counter));
				BaseElement newElement=getElementByResponse(response);
				if(newElement!=null){
					out(counter+" Detect Element: "+newElement.getDescription());
					elements.add(newElement);
				}else{
					out(counter+"  Element is not recognized: ");
					printArray(response);
				}
			}catch(Exception ex){
				// данные не получены, либо получены с ошибкой - устройство не найдено
				out(counter+"  Answer Exception:"+ex.getMessage());
			}
		}
		return elements;
	}
	
	/** получить пакет для отправки в сеть для определения наличия элемента в сети */
	private byte[] getPackageSoftLocator(byte address){
		return new byte[]{address,BaseElement.FUNCTION_SOFT_LOCATOR};
	}
	
	/** вывести отладочную информацию */
	private void out(Object information){
		System.out.print("ElementFactory");
		System.out.print(" DEBUG ");
		System.out.println(information);
	}
	
	private void printArray(byte[] array){
		for(int counter=0;counter<array.length;counter++){
			if(counter==0){
				System.out.print(" ");				
			}else{
				System.out.print(", ");
			}

			if(array[counter]<0){
				System.out.print(array[counter]+256);
			}else{
				System.out.print(array[counter]);
			}
		}
		System.out.println();
	}


	
	/** на основании ответа от локатора создать Объект-заместитель для устройства 
	 * @param data - данные полученные в ответ на запрос устройства о его наличии в сети - {@link BaseElement#FUNCTION_SOFT_LOCATOR} 
	 * при чем данные не содержат маркера начала последовательности, и маркера окончания последовательности
	 * @return вернуть созданный на основании данных элемент, который является потомком BaseElement
	 * */
	@SuppressWarnings("unchecked")
	private BaseElement getElementByResponse(byte[] data){
		BaseElement returnValue=null;
		byte address=data[0]; // адрес устройства 
		int elementType=data[1];// тип устройства
		byte[] elementSubType=Arrays.copyOfRange(data, 2, data.length);//data[2..data.length] - остальные данные; подтип устройства 
		try{
			String className=elementClassName.get(elementType);
			Constructor<BaseElement> constructor=(Constructor<BaseElement>)Class.forName(className).getConstructor(Integer.class,Exchanger.class,Byte.class,(new byte[]{}).getClass());
			returnValue=constructor.newInstance(elementType, this.exchanger,address,elementSubType);
		}catch(Exception ex){
			System.err.println(address+"  Create Element Exception: "+ex.getMessage());
		}
		return returnValue;
	}
	
	/** объект, который содержит список классов, допустимых для создания на основании типа-индекса<br>
	 * другими словами - берем тип-индекс и получаем имя создаваемого класса
	 * */
	static private HashMap<Integer,String> elementClassName=new HashMap<Integer,String>();
	static{
		elementClassName.put(new Integer(0x01), "elements.digital_in_out.DigitalInOutElement");
	}

	public static void main(String[] args){
		ElementFactory factory=new ElementFactory(null);
		System.out.println("Created Object:"+factory.getElementByResponse(new byte[]{0x02,0x01,0x33,0x35,0x37}));
	}
}
