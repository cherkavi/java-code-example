package calc;

import java.io.Serializable;
import java.util.Date;

import message.AlarmMessage;


/** абстрактный класс для вычислений */
public abstract class AbstractChecker implements Serializable{
	private final static long serialVersionUID=1L;
	
	/** время, после наступления которого поток проснется и начнет реагировать на запросы */
	private Date nextEnabledTime=null;
	/** время задержки в милисекундах */
	protected int delayMiliseconds=0;
	/** тревожное сообщение, которое стоит выдать */
	protected String alarmDescription;
	
	/** абстрактный класс для вычислений 
	 * @param delayMiliseconds - время задержки, после наступления события от датчика на которое "заснет" Checker и будет генерировать события только после окончания данного времени 
	 * @param alarmDescription - строка, которая будет выдана в {@link AlarmMessage#getDescription()}
	 */
	public AbstractChecker(int delayMiliseconds, String alarmDescription){
		this.delayMiliseconds=delayMiliseconds;
		this.alarmDescription=alarmDescription;
	}

	public AlarmMessage checkForAlarmMessage(String value){
		// проверка на установку таймера задержки для события 
		if(nextEnabledTime!=null){
			Date currentDate=new Date();
			if(currentDate.after(this.nextEnabledTime)){
				// сбросить время ожидания
				this.clearDateEvent();
				// провести очередную проверку 
				return calculate(value);
			}else{
				// время ожидания еще не вышло  
				return null;
			}
		}else{
			return calculate(value);
		}
	}
	
	/** на основании переданного параметра вернуть AlarmMessage, либо же вернуть null */
	protected abstract AlarmMessage calculate(String value);
	
	
	/** очистить таймер, который указывает на совершенное событие */
	protected void clearDateEvent(){
		this.nextEnabledTime=null;
	}

	/** установить следующую реакцию на событие после наступления времени, указанного в {@link #delayMiliseconds}*/
	protected void setDateEvent(){
		this.nextEnabledTime=new Date((new Date()).getTime()+delayMiliseconds);
	}
}
