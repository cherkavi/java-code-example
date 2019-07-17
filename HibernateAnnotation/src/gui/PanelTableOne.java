package gui;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;

import javax.swing.*;

import database.firebird.TableOne;

public class PanelTableOne extends JPanel{
	private static String field_date_format="dd.MM.yyyy";
	private static String field_timestamp_format="dd.MM.yyyyy hh.mm.ss";
	private static SimpleDateFormat field_simple_date_format =new SimpleDateFormat(field_date_format);
	private static SimpleDateFormat field_simple_timestamp_format=new SimpleDateFormat(field_timestamp_format);
	/** */
	private JTextField field_string;
	/** */
	private JTextField field_double;
	/** */
	private JTextField field_date;
	/** */
	private JTextField field_timestamp;

	private static final long serialVersionUID = 1L;
	
	public PanelTableOne(){
		super();
		initComponents();
	}
	
	private JPanel getTitledPanel(JComponent component,String text_border){
		JPanel return_value=new JPanel(new GridLayout(1,1));
		return_value.add(component);
		return_value.setBorder(javax.swing.BorderFactory.createTitledBorder(text_border));
		return return_value;
	}
	
	private void initComponents(){
		// create element's
		field_string=new JTextField(20);
		JPanel panel_field_string=getTitledPanel(field_string,"FIELD_STRING");
		
		field_double=new JTextField(20);
		JPanel panel_field_double=getTitledPanel(field_double,"FIELD_DOUBLE");
		
		field_date=new JTextField(20);
		JPanel panel_field_date=getTitledPanel(field_date,"FIELD_DATE");
		
		field_timestamp=new JTextField(20);
		JPanel panel_field_timestamp=getTitledPanel(field_timestamp,"FIELD_TIMESTAMP");
		// add listener's
		
		// placing component's
		JPanel panel_main=new JPanel(new GridLayout(4,1));
		panel_main.add(panel_field_string);
		panel_main.add(panel_field_double);
		panel_main.add(panel_field_date);
		panel_main.add(panel_field_timestamp);
		this.add(panel_main);
	}
	
	/** вернуть объект из визуальных компонентов */
	public TableOne getTableOne(){
		TableOne return_value=new TableOne();
		return_value.setField_string(this.field_string.getText());
		try{
			return_value.setField_double(new Double(this.field_double.getText()));
		}catch(Exception ex){};
		
		try{
			return_value.setField_date(field_simple_date_format.parse(this.field_date.getText()));
		}catch(Exception ex){};

		try{
			return_value.setField_timestamp(field_simple_date_format.parse(this.field_timestamp.getText()));
		}catch(Exception ex){};
		return return_value;
	
	}
	
	/** установить из объекта в визуальные элементы */
	public void setTableOne(TableOne value){
		this.field_string.setText(value.getField_string());
		this.field_double.setText(new Double(value.getField_double()).toString());
		this.field_date.setText(field_simple_date_format.format(value.getField_date()));
		this.field_timestamp.setText(field_simple_timestamp_format.format(value.getField_timestamp()));
	}
}
