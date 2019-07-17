package calc_implementation;

import calc.AIntegerChecker;

/**  проверка на НЕ равенство величин */
public class NEIntegerChecker extends AIntegerChecker{
	private final static long serialVersionUID=1L;

	/**  проверка на НЕ равенство величин  
	 * @param delayMs - задержка перед следующей идентификацией события 
	 * @param message - сообщение, которое следует выдавать 
	 * @param controlValue - контрольное значение (значение для сравнения )
	 */
	public NEIntegerChecker(int delayMs, String message, int controlValue) {
		super(delayMs, message,controlValue);
	}

	@Override
	protected boolean isAlarm(int value) {
		if(controlValue!=value){
			return true;
		}else return false;
	}

}
