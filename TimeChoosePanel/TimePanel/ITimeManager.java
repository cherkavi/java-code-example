package gui.View.TimePanel;
import java.util.Date;

/** интерфейс, который содержит методы по установке и получению времени */
public interface ITimeManager {
	/** set Time (as Date)*/
	public void setTime(Date date);
	/** get Time (as Date)*/
	public Date getTime();
}
