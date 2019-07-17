package gui.View.TimePanel;

import javax.swing.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;
import java.awt.GridLayout;
import java.awt.BorderLayout;

/** panel for show/edit time */
public class TimeChoosePanel extends JPanel implements ITimeManager{
	
	// ------------       TEST     VALUE -----------------
	public static void main(String[] args){
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(init());
		frame.setSize(200,150);
		frame.setVisible(true);
		frame.pack();
	}
	
	private static JPanel init(){
		// create element's
		JPanel panel_main=new JPanel(new BorderLayout());
		
		final TimeChoosePanel timePanel=new TimeChoosePanel();
		timePanel.setTime(new Date());
		
		JButton buttonGetTime=new JButton("Get time");
		
		// add listener's
		buttonGetTime.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println(timePanel.getTime());
			}
		});
		
		panel_main.add(timePanel,BorderLayout.CENTER);
		panel_main.add(buttonGetTime,BorderLayout.SOUTH);

		return panel_main;
	}
	//-----------------------------------------------------------------------
	
	private static final long serialVersionUID=1L;
	private JSpinner fieldSpinnerHour;
	private JSpinner fieldSpinnerMinute;
	private JSpinner fieldSpinnerSecond;
	
	/** panel for show/edit time */
	public TimeChoosePanel(){
		super();
		initComponent();
	}
	
	@Override
	public void setTime(Date date){
		 Calendar calendar=Calendar.getInstance();
		 calendar.setTime(date);
		 fieldSpinnerHour.setValue(calendar.get(Calendar.HOUR));
		 fieldSpinnerMinute.setValue(calendar.get(Calendar.MINUTE));
		 fieldSpinnerSecond.setValue(calendar.get(Calendar.SECOND));
		 
	}
	
	@Override
	public Date getTime(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR, (Integer)(fieldSpinnerHour.getValue()));
		calendar.set(Calendar.MINUTE, (Integer)(fieldSpinnerMinute.getValue()));
		calendar.set(Calendar.SECOND, (Integer)(fieldSpinnerSecond.getValue()));
		return calendar.getTime();
	}
	
	/** инициализация компонентов */
	private void initComponent(){
		byte value_hour=0;
		byte value_minute=0;
		byte value_second=0;
		fieldSpinnerHour=new JSpinner(new SpinnerNumberModel(value_hour,0,23,1));
		fieldSpinnerMinute=new JSpinner(new SpinnerNumberModel(value_minute,0,59,1));
		fieldSpinnerSecond=new JSpinner(new SpinnerNumberModel(value_second,0,59,1));
		this.setLayout(new GridLayout(1,3));
		this.add(fieldSpinnerHour);
		this.add(fieldSpinnerMinute);
		this.add(fieldSpinnerSecond);
	}
}
