package elements.digital_in_out;

import net_exchange.Exchanger;
import elements.base.BaseElement;
import elements.base.ElementSettings;
import elements.base.ElementState;

public class DigitalInOutElement extends BaseElement{
	/** кол-во элементов ввода-вывода */
	private int pinCount=0;
	
	public int getPinCount(){
		return pinCount; 
	}
	
	/** заместитель для модуля Цифрового ввода-вывода 
	 * данный модуль имеет конечное кол-во выводов, которые могут быть сконфигурированы на вход(0), или на выход(1),<br>
	 * а так же получены данные из этих устройств ( 0 или 1) <br>
	 * или же могут быть эти данные записаны (0 или 1)<br>
	 * @param exchanger - объект, который обменивается информацией с сетью 
	 * @param address - адрес данного объекта 
	 * @param data - данные о кол-ве элементов ввода-вывода 
	 */
	public DigitalInOutElement(Integer uniqueNumber, Exchanger exchanger, byte address, byte[] data) {
		super(uniqueNumber, exchanger, address,data);
		// проанализировать подтип данного объекта
		pinCount=data[0];
		this.settings=new DigitalInOutSettings(this.pinCount);
		this.state=new DigitalInOutState(this.pinCount);
	}
	
	@Override
	public String getDescription(){
		return "DigitalInOutElement";
	}

	@Override
	protected ElementSettings decodeElementSettingsFromResponse(byte[] response) {
		// response[0] - address
		// response[1] - function number
		DigitalInOutSettings tempSettings=new DigitalInOutSettings(this.pinCount);
		try{
			for(int counter=0;counter<pinCount;counter++){
				tempSettings.setPinSettings(counter, response[2+counter]>0);
			};
		}catch(Exception ex){};
		return tempSettings;
	}

	@Override
	protected ElementState decodeElementStateFromResponse(byte[] response) {
		DigitalInOutState tempState=new DigitalInOutState(this.pinCount);
		try{
			// response[0] - address
			// response[1] - function number
			for(int counter=0;counter<pinCount;counter++){
				tempState.setPinState(counter,response[2+counter]>0);
			}
		}catch(Exception ex){
		}
		return tempState;
	}

	@Override
	protected boolean decodeSetSettingsAnswer(byte[] response) {
		try{
			if(response[2]==BaseElement.SEND_OK){
				return true;
			}else{
				return false;
			}
		}catch(Exception ex){
			return false;
		}
	}

	@Override
	public boolean decodeSetStateAnswer(byte[] data) {
		try{
			if(data[2]==BaseElement.SEND_OK){
				return true;
			}else{
				return false;
			}
		}catch(Exception ex){
			return false;
		}
	}

}
