package elements.digital_in_out;

import java.util.Arrays;

import elements.base.ElementState;

public class DigitalInOutState extends ElementState{
	private byte[] state;
	
	/** состояние модулей цифрового ввода-вывода 
	 * @param pinCount - кол-во вводов-выводов
	 * */
	public DigitalInOutState(int pinCount){
		this.state=new byte[pinCount];
	}

	/** получить массив состояний объекта */
	public byte[] getState(){
		return this.state;
	}
	
	/** установить массив состояний объкта 
	 * @param value - значения, которые нужно установить 
	 * */
	public void setState(byte[] value){
		try{
			Arrays.fill(this.state, (byte)0);
			for(int counter=0;counter<value.length;counter++){
				state[counter]=value[counter];
			}
		}catch(Exception ex){
		}
	}
	
	/** получить состояние одного из PIN-ов*/
	public byte getPinState(int pinNumber){
		try{
			return this.state[pinNumber];
		}catch(Exception ex){
			return 0;
		}
	}
	
	/** установить состояние одного из PIN-ов */
	public void setPinState(int pinNumber, boolean isOn){
		try{
			if(isOn){
				this.state[pinNumber]=1;
			}else{
				this.state[pinNumber]=0;
			}
			
		}catch(Exception ex){};
	}
	
	@Override
	public byte[] getByteData() {
		return this.state;
	}

}
