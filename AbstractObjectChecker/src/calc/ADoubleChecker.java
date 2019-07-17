package calc;


import java.math.BigDecimal;
import java.util.Date;

import message.AlarmMessage;

/** проверяющий для Double значений */
public abstract class ADoubleChecker extends AbstractChecker{
	private final static long serialVersionUID=1L;
	/** контрольное значение */
	private double controlValue;
	/** знак, до которого следует округлять */
	private int digitForRound;
	
	/** проверяющий для Double значений 
	 * @param delayMs - время задержки для следующей итерации 
	 * @param message - сообщение, которое может быть выдано
	 * @param controlValue - контрольное значение для сравнения
	 * @param digit - разряд, до которого следует округлять значение (-1 - округление не требуется)
	 * <li><b>-1</b>округление не требуется </li>
	 * <li><b>>=0</b>округлить до указанного знака </li>
	 */
	public ADoubleChecker(int delayMs, String message,double controlValue, int digit) {
		super(delayMs, message);
		this.controlValue=controlValue;
		this.digitForRound=digit;
	}

	/** получить контрольное значение, с которым нужно сравнивать величины */
	protected double getControlValue(){
		return this.controlValue;
	}
	
	/** получить кол-во знаков, на которые нужно округлять значения */
	protected int getDigit(){
		return this.digitForRound;
	}
	
	/** округлить значение до указанного знака  */
	protected double round(double d, int decimalPlace){
		if(decimalPlace<0){
			return d;
		}else{
		    // see the Javadoc about why we use a String in the constructor
		    // http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
		    BigDecimal bd = new BigDecimal(Double.toString(d));
		    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
		    return bd.doubleValue();
		}
	  }	/**
	 * @param value - значение, которое вернул датчик
	 * @return 
	 * */
	protected abstract boolean isAlarm(double value); 
	
	@Override
	protected AlarmMessage calculate(String value) {
		try{
			double intValue=Double.parseDouble(value.trim());
			if(isAlarm(intValue)){
				this.setDateEvent();
				return new AlarmMessage(new Date(),this.alarmDescription,value); 
			}else{
				return null;
			}
		}catch(Exception ex){
			System.err.println("ADoubleChecker Exception: "+ex.getMessage());
			return null;
		}
	} 

	
	
}
