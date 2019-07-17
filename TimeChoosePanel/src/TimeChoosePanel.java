import javax.swing.*;
import java.awt.event.*;
import java.util.Date;

/** panel for show/edit time */
public class TimeChoosePanel extends JPanel{
	private static final long serialVersionUID=1L;
	private JSpinner fieldSpinnerHour;
	private JSpinner fieldSpinnerMinute;
	private JSpinner fieldSpinnerSecond;
	
	/** panel for show/edit time */
	public TimeChoosePanel(){
		super();
		initComponent();
	}
	
	/** set Time to panel  */
	public void setTime(){
		// TODO 
	}
	public Date getTime(){
		// TODO
		return new Date();
	}
	
	private void initComponent(){
		byte value_hour=0;
		byte value_minute=0;
		byte value_second=0;
		fieldSpinnerHour=new JSpinner(new SpinnerNumberModel(value_hour,0,23,1));
		fieldSpinnerMinute=new JSpinner(new SpinnerNumberModel(value_minute,0,59,1));
		fieldSpinnerSecond=new JSpinner(new SpinnerNumberModel(value_second,0,59,1));
	}
}
