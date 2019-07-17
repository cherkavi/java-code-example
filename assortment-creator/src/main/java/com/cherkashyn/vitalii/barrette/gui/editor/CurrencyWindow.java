package com.cherkashyn.vitalii.barrette.gui.editor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.accounting.domain.Currency;
import com.cherkashyn.vitalii.accounting.domain.RowStatus;
import com.cherkashyn.vitalii.accounting.exception.GeneralServiceException;
import com.cherkashyn.vitalii.accounting.service.finder.CurrencyFinder;
import com.cherkashyn.vitalii.accounting.service.repository.CurrencyRepository;
import com.cherkashyn.vitalii.barrette.gui.swing_utility.ModalPanel;

@Component
@Scope("prototype")
public class CurrencyWindow extends ModalPanel{

	public static final int WIDTH=250;
	public static final int HEIGHT=150;

	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	CurrencyFinder finder;
	
	@Autowired
	CurrencyRepository repository;
	
	/** current edit value */
	private Currency value;
	
	public CurrencyWindow(){
		super();
	}
	
	@PostConstruct
	public void init(){
		this.setLayout(new BorderLayout());
		try {
			this.value=finder.findActive();
		} catch (GeneralServiceException e) {
		}
		this.add(createMainPanel(this.value));
	}

	private JPanel createMainPanel(Currency currency) {
		final ModalPanel panel=new ModalPanel();
		panel.setLayout(new GridLayout(2,1));
		
		final JTextField value=new JTextField();
		if(currency!=null && currency.getDollar()!=null){
			value.setText(Float.toString(currency.getDollar()));
		}
		value.setBorder(BorderFactory.createTitledBorder("Валюта"));
		panel.add(value);
		
		JPanel panelManager=new JPanel(new GridLayout(1,2));
		panel.add(panelManager);

		JButton buttonSave=new JButton("установить");			

		panelManager.add(buttonSave);
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onButtonSave(value);
			}
		});
		
		JButton buttonCancel=new JButton("отменить");
		panelManager.add(buttonCancel);
		buttonCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				onButtonCancel();
			}
		});
		
		return panel;
	}
	
	private void onButtonSave(JTextField field){
		String textCurrencyNew=StringUtils.trimToNull(field.getText());
		if(textCurrencyNew==null){
			JOptionPane.showMessageDialog(this, "value is not set");
			return;
		}
		
		Float floatCurrencyNew=null;
		try{
			floatCurrencyNew=Float.parseFloat(textCurrencyNew);
		}catch(NumberFormatException ex){
			floatCurrencyNew=null;
		}
		if(floatCurrencyNew==null){
			JOptionPane.showMessageDialog(this, "can't parse value: "+textCurrencyNew);
			return;
		}
		
		if(this.value!=null){
			this.value.setRowstatus(RowStatus.INACTIVE.getValue());
		}
		try {
			if(this.value!=null){
				repository.update(this.value);
			}
			repository.create(new Currency(floatCurrencyNew));
		} catch (GeneralServiceException e) {
			JOptionPane.showMessageDialog(this, "can't save data: "+e.getMessage());
			return;
		}
		this.closeModal();
	}

	
	private void onButtonCancel(){
		this.closeModal();
	}

	
}
