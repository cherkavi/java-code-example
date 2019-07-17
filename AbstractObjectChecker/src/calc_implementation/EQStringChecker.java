package calc_implementation;

import calc.AStringChecker;

/** проверка на равенство строк */
public class EQStringChecker extends AStringChecker{
	private final static long serialVersionUID=1L;
	
	/** проверка на равенство строк 
	 * @param delayMiliseconds - задержка в милисекундах перед следующим опросом после совершения события
	 * @param alarmDescription - сообщение, которое нужно выдать в случае получения события
	 * @param controlValue - параметр, с которым нужно сравнивать значение 
	 * @param ignoreCase - игнорировать ли c 
	 */
	public EQStringChecker(int delayMiliseconds, String alarmDescription, String controlValue, boolean ignoreCase) {
		super(delayMiliseconds, alarmDescription, controlValue, ignoreCase);
	}

	@Override
	protected boolean isAlarm(String value) {
		try{
			if(this.isIgnoreCase()){
				return this.getControlValue().equalsIgnoreCase(value);
			}else{
				return this.getControlValue().equals(value);
			}
		}catch(Exception ex){
			System.err.println("EQStringChecker Exception:"+ex.getMessage());
			return false;
		}
	}


}
