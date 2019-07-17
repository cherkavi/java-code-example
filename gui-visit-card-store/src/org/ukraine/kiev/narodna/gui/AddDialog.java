package org.ukraine.kiev.narodna.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.ukraine.kiev.narodna.domain.ListItemModel;

public class AddDialog extends JDialog {
	private static final long	serialVersionUID	= 1L;

	public AddDialog(JFrame parent) {

		super(parent, "Add new element", true);
		if (parent != null) {
			Dimension parentSize = parent.getSize();
			Point p = parent.getLocation();
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}
		
		JPanel mainPanel = new JPanel(new GridLayout(4,1));
		getContentPane().add(mainPanel);
		
		// edit elements
		mainPanel.add(this.createTitledPanel("Surname", this.surname));
		mainPanel.add(this.createTitledPanel("Name", this.name));
		mainPanel.add(this.createTitledPanel("Fathername", this.fathername));
		mainPanel.add(this.createTitledPanel("Phone", this.phone));
		
		// manage elements
		JButton buttonSave=new JButton("Save");
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onButtonSave();
			}
		});
		JButton buttonCancel=new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onButtonCancel();
			}
		});
		
		JPanel panelManager=new JPanel(new GridLayout(1,2));
		mainPanel.add(panelManager, BorderLayout.SOUTH);
		panelManager.add(buttonSave);
		panelManager.add(buttonCancel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}

	private ListItemModel model=null;
	private JTextField name=new JTextField();
	private JTextField surname=new JTextField();
	private JTextField fathername=new JTextField();
	private JTextField phone=new JTextField();
	
	private void onButtonSave(){
		model=new ListItemModel(0, name.getText().trim(), surname.getText().trim(), fathername.getText().trim(), phone.getText().trim());
		String warningMessage=checkModel(model);
		if(warningMessage==null){
			close();
		}else{
			model=null;
			JOptionPane.showMessageDialog(this, warningMessage);
		}
	}
	
	public ListItemModel getModel(){
		return this.model;
	}
	
	private String checkModel(ListItemModel modelForCheck) {
		if(modelForCheck.getName()==null || modelForCheck.getName().length()==0){
			return "Input NAME";
		}
		if(modelForCheck.getSurname()==null || modelForCheck.getSurname().length()==0){
			return "Input SURNAME";
		}
		if(modelForCheck.getFathername()==null || modelForCheck.getFathername().length()==0){
			return "Input FATHERNAME";
		}
		if(modelForCheck.getPhone()==null || modelForCheck.getPhone().length()==0){
			return "Input Phone";
		}else{
			String phone=modelForCheck.getPhone().replaceAll("[^0-9]", "");
			if(phone.length()<10){
				return "input PHONE NUMBER, example: 050 100 20 30 ";
			}
		}
		return null;
	}

	private void onButtonCancel(){
		model=null;
		close();
	}
	
	private void close() {
		setVisible(false);
		dispose();
	}
	
	private JPanel createTitledPanel(String title, JTextField textField){
		JPanel panel=new JPanel(new GridLayout(1,1));
		panel.add(textField);
		panel.setBorder(BorderFactory.createTitledBorder(title));
		return panel;
	}

}