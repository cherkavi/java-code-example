package calc_implementation;

import calc.ADoubleChecker;

/** проверка на равенство величин */
public class EQDoubleChecker extends ADoubleChecker{
	private final static long serialVersionUID=1L;
	
	/**  проверка на равенство величин  
	 * @param delayMs - задержка перед следующей идентификацией события 
	 * @param message - сообщение, которое следует выдавать 
	 * @param controlValue - контрольное значение (значение для сравнения )
	 * @param digit - число знаков, на которые нужно делать округление  
	 */
	public EQDoubleChecker(int delayMs, String message, double controlValue, int digit) {
		super(delayMs, message,controlValue, digit);
	}

	@Override
	protected boolean isAlarm(double value) {
		try{
			if( this.round(value, this.getDigit())==this.round(this.getControlValue(),this.getDigit())){
				return true;
			}else {
				return false;
			}
		}catch(Exception ex){
			return false;
		}
	}

}
