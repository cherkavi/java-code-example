package calc_implementation;

import calc.ADoubleChecker;

/** проверка на больше */
public class GTDoubleChecker extends ADoubleChecker{
	private final static long serialVersionUID=1L;
	
	/**  проверка на больше 
	 * @param delayMs - задержка перед следующей идентификацией события 
	 * @param message - сообщение, которое следует выдавать 
	 * @param controlValue - контрольное значение (значение для сравнения )
	 * @param digit - число знаков, на которые нужно делать округление  
	 */
	public GTDoubleChecker(int delayMs, String message, double controlValue, int digit) {
		super(delayMs, message,controlValue, digit);
	}

	@Override
	protected boolean isAlarm(double value) {
		try{
			if( this.round(value, this.getDigit())>this.round(this.getControlValue(),this.getDigit())){
				return true;
			}else {
				return false;
			}
		}catch(Exception ex){
			return false;
		}
	}

}
