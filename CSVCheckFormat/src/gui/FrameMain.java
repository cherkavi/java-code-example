package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import Utility.CsvChecker;

public class FrameMain extends JFrame{
	/** */
	private static final long serialVersionUID = 1L;

	JTextField field_path_to_csv;
	
	JTextField field_path_to_xml;
	
	public FrameMain(){
		super("Проверка CSV");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponent();
		this.setSize(300,200);
		this.setVisible(true);
	}

	
	private void initComponent(){
		// create component's
		field_path_to_csv=new JTextField();
		field_path_to_xml=new JTextField();
		JButton button_open_csv_dialog=new JButton("...");
		JButton button_open_xml_dialog=new JButton("...");
		JButton button_action=new JButton("check");
		// add listener's
		button_open_csv_dialog.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_csv_dialog_open();
			}
		});
		button_open_xml_dialog.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_xml_dialog_open();
			}
		});
		button_action.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_action();
			}
		});
		
		// placing component's
		JPanel panel_csv=new JPanel(new BorderLayout());
		panel_csv.add(this.field_path_to_csv,BorderLayout.CENTER);
		panel_csv.add(button_open_csv_dialog,BorderLayout.EAST);
		panel_csv.setBorder(javax.swing.BorderFactory.createTitledBorder("CSV"));
		
		JPanel panel_xml=new JPanel(new BorderLayout());
		panel_xml.add(this.field_path_to_xml,BorderLayout.CENTER);
		panel_xml.add(button_open_xml_dialog,BorderLayout.EAST);
		panel_xml.setBorder(javax.swing.BorderFactory.createTitledBorder("XML"));
		
		
		JPanel panel_main=new JPanel(new GridLayout(3,1));
		panel_main.add(panel_csv);
		panel_main.add(panel_xml);
		panel_main.add(button_action);
		this.getContentPane().add(panel_main);
	}
	
	private void on_button_csv_dialog_open(){
		JFileChooser file_chooser=new JFileChooser("d:\\eclipse_workspace");
		file_chooser.setFileFilter(new FileNameExtensionFilter("CSV file","csv","CSV"));
		if(file_chooser.showDialog(this, "file for check")==JFileChooser.APPROVE_OPTION){
			this.field_path_to_csv.setText(file_chooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	private void on_button_xml_dialog_open(){
		JFileChooser file_chooser=new JFileChooser("d:\\eclipse_workspace");
		file_chooser.setFileFilter(new FileNameExtensionFilter("XML file","xml","XML"));
		if(file_chooser.showDialog(this, "XML pattern")==JFileChooser.APPROVE_OPTION){
			this.field_path_to_xml.setText(file_chooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	private void on_button_action(){
		try{
			CsvChecker checker=new CsvChecker(this.field_path_to_xml.getText(),this.field_path_to_csv.getText());
			String check_result=checker.checkCSV();
			if(check_result.isEmpty()){
				JOptionPane.showMessageDialog(this, "OK");
			}else{
				JOptionPane.showMessageDialog(this,  check_result,"Error",JOptionPane.ERROR_MESSAGE);
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
