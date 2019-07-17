package calc_implementation;

import calc.ADoubleChecker;

/** проверка на больше либо равно */
public class GEDoubleChecker extends ADoubleChecker{
	private final static long serialVersionUID=1L;
	
	/**  проверка на больше или равно   
	 * @param delayMs - задержка перед следующей идентификацией события 
	 * @param message - сообщение, которое следует выдавать 
	 * @param controlValue - контрольное значение (значение для сравнения )
	 * @param digit - число знаков, на которые нужно делать округление  
	 */
	public GEDoubleChecker(int delayMs, String message, double controlValue, int digit) {
		super(delayMs, message,controlValue, digit);
	}

	@Override
	protected boolean isAlarm(double value) {
		try{
			if( this.round(value, this.getDigit())>=this.round(this.getControlValue(),this.getDigit())){
				return true;
			}else {
				return false;
			}
		}catch(Exception ex){
			return false;
		}
	}

}
