package calc;


import java.util.Date;

import message.AlarmMessage;

/** проверяющий для Integer значений */
public abstract class AIntegerChecker extends AbstractChecker{
	private final static long serialVersionUID=1L;
	protected int controlValue;
	
	/** проверяющий для Integer значений 
	 * @param delayMs - время задержки для следующей итерации 
	 * @param message - сообщение, которое может быть выдано 
	 */
	public AIntegerChecker(int delayMs, String message, int controlValue) {
		super(delayMs, message);
		this.controlValue=controlValue;
	}

	/**
	 * @param value - значение, которое вернул датчик
	 * @return 
	 * */
	protected abstract boolean isAlarm(int value); 
	
	@Override
	protected AlarmMessage calculate(String value) {
		try{
			Integer intValue=Integer.parseInt(value.trim());
			if(isAlarm(intValue)){
				this.setDateEvent();
				return new AlarmMessage(new Date(),this.alarmDescription,value); 
			}else{
				return null;
			}
		}catch(Exception ex){
			System.err.println("AInteger Exception: "+ex.getMessage());
			return null;
		}
	} 

	
	
}
