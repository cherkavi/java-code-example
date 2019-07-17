package calc_implementation;

import calc.AIntegerChecker;

/** проверка на больше либо равно */
public class GEIntegerChecker extends AIntegerChecker{
	private final static long serialVersionUID=1L;
	
	/**  проверка на больше либо равно   
	 * @param delayMs - задержка перед следующей идентификацией события 
	 * @param message - сообщение, которое следует выдавать 
	 * @param controlValue - контрольное значение (значение для сравнения )
	 */
	public GEIntegerChecker(int delayMs, String message, int controlValue) {
		super(delayMs, message,controlValue);
	}

	@Override
	protected boolean isAlarm(int value) {
		if(value>=controlValue){
			return true;
		}else return false;
	}

}
