package elements.digital_in_out;

import java.util.Arrays;

import elements.base.ElementSettings;

public class DigitalInOutSettings extends ElementSettings{
	private byte[] settings;
	
	/** Настройки для модуля цифрового ввода-вывода 
	 * @param pinCount - кол-во управляемых pin-ов
	 * */
	public DigitalInOutSettings(int pinCount){
		this.settings=new byte[pinCount];
	}
	
	/** получить значения установок для модуля */
	public byte[] getSettings(){
		return this.getSettings();
	}
	
	/** установить значения цифровых установок для модуля */
	public void setSettings(byte[] value){
		Arrays.fill(this.settings, (byte)0);
		try{
			for(int counter=0;counter<this.settings.length;counter++){
				this.settings[counter]=value[counter];
			}
		}catch(Exception ex){}
	}
	
	/** установить значение настройки одного из Pin-ов */
	public void setPinSettings(int pinNumber, boolean isOn){
		try{
			if(isOn){
				this.settings[pinNumber]=1;
			}else{
				this.settings[pinNumber]=0;
			}
		}catch(Exception ex){};
	}
	
	
	/** получить значение одного из Pin-ов */
	public byte getPinSettings(int pinNumber){
		try{
			return this.settings[pinNumber];
		}catch(Exception ex){
			return 0;
		}
	}
	
	
	@Override
	public byte[] getByteData() {
		return this.settings;
	}

}
