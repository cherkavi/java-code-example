package elements.base;

import java.util.Date;

import net_exchange.Exchanger;

/** класс, который является базовым для всех подклассов, 
 * которые будут являтся заместителями или прокси-объектами удаленных через сеть устройств на центральном устройстве */
public abstract class BaseElement {
	private Exchanger exchanger;
	public final static byte SEND_OK=0x1;
	public final static byte SEND_CANCEL=0x2;
	public final static byte SEND_ERROR=0x3;
	
	//public final static byte FLAG_BEGIN_PACKAGE=0x0a;
	//public final static byte FLAG_END_PACKAGE=0x0c;
	/** номер функции опроса датчиков на наличие в сети */
	public static byte FUNCTION_SOFT_LOCATOR=0x01;
	/** установка настроек на устройство */
	public static byte FUNCTION_SET_SETTINGS=0x02;
	/** получение настроек с устройства */
	public static byte FUNCTION_GET_SETTINGS=0x03;
	/** установка состояния на устройство */
	public static byte FUNCTION_SET_STATE=0x04;
	/** получение состояния с устройства */
	public static byte FUNCTION_GET_STATE=0x05;
	
	
	/** настройки элемента */
	protected ElementSettings settings;
	/** время установки настроек */
	private Date timeSettings;
	/** состояние элемента */
	protected ElementState state;
	/** время получения состояния элемента */
	private Date timeState;
	/** уникальный номер устройства в сети */
	private byte address;
	/** уникальный номер данной функции */
	private Integer uniqueNumber;
	
	/** создать элемент
	 * @param exchanger - объект-транспорт, который шлет данные и принимает ответы 
	 * @param address - адрес устройства в сети
	 * @param data (nullable) - несет смысловую нагрузку необходимости передачи в конструктор возможных параметров для определения ПодТипа модуля  
	 * */
	public BaseElement(Integer uniqueNumber, Exchanger exchanger,Byte address, byte[] data ){
		this.exchanger=exchanger;
		this.address=address;
		this.uniqueNumber=uniqueNumber;
	}
	
	/** получить уникальный тип устройства */
	public Integer getUniqueType(){
		return uniqueNumber;
	}
	/** получить описание данного устройства */
	public String getDescription(){
		return "";
	}
	
	/** установить настройки на устройство - 
	 * <b>данный метод не возвращает управление до окончания обмена с устройством</b> 
	 * @param settings - настройки, которые следует послать на устройство 
	 * */
	public byte setSettings(ElementSettings settings ){
		byte returnValue=SEND_ERROR;
		this.settings=null;
		this.timeSettings=null;
		// послать на устройство двоичные данные и получить ответ в виде двоичной последовательности
		try{
			byte[] answer=this.exchanger.sendData(this.createPackage(FUNCTION_SET_SETTINGS,settings.getByteData()));
			if(answer!=null){
				// данные приняты успешно принимающей стороной - понять услышан ли ответ 
				if(decodeSetSettingsAnswer(answer)==true){
					// данные успешно установлены
					this.settings=settings;
					this.timeSettings=new Date();
				}else{
					// данные не установлены
					this.settings=null;
				}
			}else{
				// данные не услышаны на принимающей стороне
				returnValue=SEND_ERROR;
			}
		}catch(Exception ex){
			// данные не услышаны на принимающей стороне
			returnValue=SEND_ERROR;
		}
		return returnValue;
	}
	
	/** установить настройки на устройство - 
	 * <b>данный метод не возвращает управление до окончания обмена с устройством</b> 
	 * @param settings - настройки, которые следует послать на устройство 
	 * */
	public byte setState(ElementState state ){
		byte returnValue=SEND_ERROR;
		this.state=null;
		this.timeState=null;
		// послать на устройство двоичные данные и получить ответ в виде двоичной последовательности
		try{
			byte[] answer=this.exchanger.sendData(this.createPackage(FUNCTION_SET_STATE,state.getByteData()));
			if(answer!=null){
				// данные приняты успешно принимающей стороной - понять услышан ли ответ 
				if(decodeSetStateAnswer(answer)==true){
					// данные успешно установлены
					this.state=state;
					this.timeState=new Date();
				}else{
					// данные не установлены
					this.state=null;
				}
			}else{
				// данные не услышаны на принимающей стороне
				returnValue=SEND_ERROR;
			}
		}catch(Exception ex){
			// данные не услышаны на принимающей стороне
			returnValue=SEND_ERROR;
		}
		return returnValue;
	}

	/** прочесть двоичный ответ на установку состояния 
	 * @data - данные полученные от элемента
	 * @return 
	 * <li>true - данные успешно распознаны </li>
	 * <li>false - данные не распознаны или повреждены</li>
	 * */
	public abstract boolean decodeSetStateAnswer(byte[] data);
	
	
	/**
	 * получить настройки с устройства
	 * <b>данный метод не возвращает управление до окончания обмена с утройством</b>  
	 */
	public ElementSettings getSettingsFromDevice(){
		this.settings=null;
		// собрать функцию-запрос на получение настроек
		byte[] request=new byte[]{this.address, FUNCTION_GET_SETTINGS}; 
		// послать функцию на устройство,
		try{
			byte[] response=this.exchanger.sendData(request);
			if(response!=null){
				this.settings=decodeElementSettingsFromResponse(response);
			}else{
				// элемент не ответил на установку параметров  
			}
		}catch(Exception ex){
			// ошибка обмена с устройством 
		}
		return this.settings;
	}

	/**
	 * получить настройки с устройства
	 * <b>данный метод не возвращает управление до окончания обмена с утройством</b>  
	 */
	public ElementState getStateFromDevice(){
		this.state=null;
		// собрать функцию-запрос на получение настроек
		byte[] request=new byte[]{this.address, FUNCTION_GET_STATE}; 
		// послать функцию на устройство,
		try{
			byte[] response=this.exchanger.sendData(request);
			if(response!=null){
				this.state=decodeElementStateFromResponse(response);
			}else{
				// элемент не ответил на запрос получения параметров   
			}
		}catch(Exception ex){
			// ошибка обмена с устройством 
		}
		return this.state;
	}
	
	/** получить установки с устройства 
	 * @return прочитанное когда-то сосотояние устройства  
	 * */
	public ElementState getState(){
		return this.state;
	}
	
	/** получить время получения состояния элемента */
	public Date getTimeState(){
		return this.timeState;
	}
	
	/** получить время установки настроек для элемента */
	public Date getTimeSettings(){
		return this.timeSettings;
	}
	
	/** декодировать полученные от Элемента данные в ElementState
	 * @param response - данные, полученные от элемента
	 * */
	protected abstract ElementState decodeElementStateFromResponse(byte[] response);
	
	/** декодировать полученные от Элемента данные в ElementSettings 
	 * @param response - данные, полученные от элемента
	 * */
	protected abstract ElementSettings decodeElementSettingsFromResponse(byte[] response);
	
	
	/** получить настройки, который когда-то были прочитаны с устройства */
	public ElementSettings getSettings(){
		return this.settings;
	}
	
	/** ответил ли Элемент утвердительно на установку настроек  
	 * @param response - данные, которые передало в ответ на устновку настроек устройство 
	 * */
	protected abstract boolean decodeSetSettingsAnswer(byte[] response);
	
	/** запрос на получение настроек */
	
	
	/** запрос на установку настроек */
	/** запрос на получение состояния */
	/** запрос на установку состояния */
	
	/* послать функцию на удаленное устройство 
	 * @param data - данные, которые посылаются на устройство
	 * @return 
	 * <li> byte[] - данные успешно переданы и получен ответ </li>
	 * <li> null - данные не переданы, ответ не получен </li>
	protected byte[] sendFunction(byte[] data){
		byte[] returnValue=null;
		// послать поток данные в сеть (на порт и получить ответ в течении установленного времени, или вернуть ошибку)
		System.out.println("BaseElement#sendFunction:");printArray(data);
		try{
			returnValue= exchanger.sendData(data);
		}catch(Exception ex){
			returnValue=null;
		}
		
		return returnValue;
	}*/
	

	/** создать пакет для отправки на Элемент
	 * @param functionNumber - номер функции для отправки 
	 * @param data - данные, которые должны быть переданы в функции 
	 * */
	private byte[] createPackage(byte functionNumber, byte[] data){
		if(data==null){
			data=new byte[]{};
		}
		byte[] returnValue=new byte[data.length+2];
		returnValue[0]=this.address;
		returnValue[1]=functionNumber;
		for(int counter=0;counter<data.length;counter++){
			returnValue[2+counter]=data[counter];
		}
		return returnValue;
	}

	/** получить из массивов байт один для отправки */
	@SuppressWarnings("unused")
	private byte[] createByteArrayPackage(byte[] ... data){
		int length=0;
		for(int counter=0;counter<data.length;counter++){
			length+=data[counter].length;
		}
		byte[] returnValue=new byte[length];
		int currentPosition=0;
		for(int counter=0;counter<data.length;counter++){
			for(int index=0;index<data[counter].length;index++){
				returnValue[currentPosition]=data[counter][index];
				currentPosition++;
			}
		}
		return returnValue;
	}
}
