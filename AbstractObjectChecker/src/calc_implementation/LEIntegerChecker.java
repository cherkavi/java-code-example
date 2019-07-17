package calc_implementation;

import calc.AIntegerChecker;

/** проверка на меньше либо равно */
public class LEIntegerChecker extends AIntegerChecker{
	private final static long serialVersionUID=1L;
	
	/**  проверка на меньше либо равно   
	 * @param delayMs - задержка перед следующей идентификацией события 
	 * @param message - сообщение, которое следует выдавать 
	 * @param controlValue - контрольное значение (значение для сравнения )
	 */
	public LEIntegerChecker(int delayMs, String message, int controlValue) {
		super(delayMs, message,controlValue);
	}

	@Override
	protected boolean isAlarm(int value) {
		if(value<=controlValue){
			return true;
		}else return false;
	}

}
