package calc;


import java.util.Date;

import message.AlarmMessage;

/** проверяющий для String значений */
public abstract class AStringChecker extends AbstractChecker{
	private final static long serialVersionUID=1L;
	
	/** значение с которым нужно проводить сравнение */
	private String controlValue;
	/** флаг, который говорит о необходимости игнорирования Case в строках  */
	private boolean ignoreCase;
	
	/** проверяющий для String значений 
	 * @param delayMs - время задержки для следующей итерации 
	 * @param message - сообщение, которое может быть выдано
	 * @param controlValue - контрольное значение, с которым необходимо проводить сравнение 
	 * @param ignoreCase - нужно ли игнорировать Case ("one"=="OnE")
	 */
	public AStringChecker(int delayMs, String message, String controlValue, boolean ignoreCase) {
		super(delayMs, message);
		this.controlValue=controlValue;
		this.ignoreCase=ignoreCase;
	}

	/** получить контрольное значение */
	protected String getControlValue(){
		return this.controlValue;
	}
	
	/** нужно ли игнорировать case в строках */
	protected boolean isIgnoreCase(){
		return this.ignoreCase;
	}
	
	/**
	 * @param value - значение, которое вернул датчик
	 * @return 
	 * */
	protected abstract boolean isAlarm(String value); 
	
	@Override
	protected AlarmMessage calculate(String value) {
		try{
			if(isAlarm(value)){
				this.setDateEvent();
				return new AlarmMessage(new Date(),this.alarmDescription,value); 
			}else{
				return null;
			}
		}catch(Exception ex){
			System.err.println("AStringChecker Exception: "+ex.getMessage());
			return null;
		}
	} 

	
	
}
