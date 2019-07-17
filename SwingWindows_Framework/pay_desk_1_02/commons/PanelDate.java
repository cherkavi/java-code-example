package pay_desk.commons;

import java.awt.GridLayout;
import java.util.Date;
import javax.swing.JPanel;

import com.toedter.calendar.JDateChooser;

/** панель с компонентом даты */
public class PanelDate extends JPanel{
	private final static long serialVersionUID=1L;
	private JDateChooser dateBirthDay=new JDateChooser();
	
	/** панель с компонентом даты */
	public PanelDate(String title){
		this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		this.setLayout(new GridLayout(1,1));
		this.add(dateBirthDay);
	}
	
	public Date getDate(){
		return this.dateBirthDay.getDate();
	}
	
	public void setDate(Date date){
		this.dateBirthDay.setDate(date);
	}
}
