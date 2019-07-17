package com.cherkashyn.vitalii.indirector.workers.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.indirector.workers.domain.PhoneAttempt;
import com.cherkashyn.vitalii.indirector.workers.domain.PhoneAttemptResult;
import com.cherkashyn.vitalii.indirector.workers.domain.PhoneDirty;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ModalPanel;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ModalResultListener;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.phoneattempt.PhoneAttemptRepository;
import com.cherkashyn.vitalii.indirector.workers.service.interf.phoneattemptresult.PhoneAttemptResultFinder;
import com.cherkashyn.vitalii.indirector.workers.service.interf.phoneattemptresult.PhoneAttemptResultRepository;
import com.cherkashyn.vitalii.indirector.workers.service.interf.phonedirty.PhoneDirtyRepository;


@Component
@Scope("prototype")
public class CallResultWindow extends ModalPanel{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH=400;
	public static final int HEIGHT=200;
	
	JComboBox<PhoneAttemptResult> list;
	
	JTextField fieldDescription;

	
	@Autowired
	PhoneAttemptResultFinder finderPhoneAttemptResult;
	
	@Autowired
	PhoneAttemptResultRepository repositoryPhoneAttemptResult;

	@Autowired
	PhoneAttemptRepository repositoryPhoneAttempt;

	@Autowired
	PhoneDirtyRepository repositoryDirty;

	/** new phone, need to create PhoneDirty record  */
	private String phoneNew;

	/** PhoneDirty record exists, need to create result only */
	private PhoneDirty phoneExists;

	public CallResultWindow(){
	}
	
	public void setPhone(PhoneDirty phoneDirty){
		this.phoneExists=phoneDirty;
		this.setBorder(BorderFactory.createTitledBorder("Очередная попытка для номера:"+phoneDirty));		
	}
	
	public void setPhone(String phone){
		this.phoneNew=phone;
		this.setBorder(BorderFactory.createTitledBorder("новый номер:"+phone));
	}
	
	@PostConstruct
	public void init() {
		this.setLayout(new BorderLayout());
		
		this.add(createPanelValues(), BorderLayout.CENTER);
		
		this.add(createPanelManager(), BorderLayout.SOUTH);
	}
	
	
	private JPanel createPanelValues(){
		list=new JComboBox<PhoneAttemptResult>();
		list.setRenderer(new DefaultListCellRenderer());
		
		fieldDescription=new JTextField();
		fieldDescription.setBorder(BorderFactory.createTitledBorder("Дополнительная информация "));
		
		JPanel panel=new JPanel(new GridLayout(2, 1));
		panel.add(list);
		panel.add(fieldDescription);

		this.refreshList();
		
		return panel;
	}
	
	private JPanel createPanelManager(){
		JButton buttonAdd=new JButton("Сохранить");
		buttonAdd.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonSave();
			}
		});
		
		JButton buttonRemove=new JButton("Отменить");
		buttonRemove.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				onButtonCancel();
			}
		});
		
		JPanel panelManager=new JPanel();
		panelManager.setLayout(new BorderLayout());
		panelManager.add(buttonAdd, BorderLayout.CENTER);
		panelManager.add(buttonRemove, BorderLayout.EAST);
		
		return panelManager;
	}

	
	
	protected void onButtonCancel() {
		this.closeModal(ModalResultListener.Result.Cancel);
	}

	private final static String PHONE_DEFAULT_PREFIX="044";
	private final static int PHONE_DEFAULT_LEN=7;
	
	protected void onButtonSave() {
		if(phoneExists==null){
			this.phoneExists=new PhoneDirty();
			String phone=this.phoneNew.trim().replaceAll("([^0-9])","");
			if(phone.length()==PHONE_DEFAULT_LEN){
				phone=PHONE_DEFAULT_PREFIX+phone;
			}
			this.phoneExists.setPhone(phone);
			try {
				repositoryDirty.create(this.phoneExists);
			} catch (ServiceException e) {
				JOptionPane.showMessageDialog( this, "Не могу сохранить:"+e.getMessage(), "Ошибка ", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		PhoneAttempt attempt=new PhoneAttempt();
		attempt.setDescription(this.fieldDescription.getText());
		attempt.setPhoneDirty(phoneExists);
		attempt.setResult((PhoneAttemptResult)this.list.getSelectedItem());
		
		try {
			repositoryPhoneAttempt.create(attempt);
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog( this, "Не могу сохранить:"+e.getMessage(), "Ошибка ", JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.closeModal(ModalResultListener.Result.Ok);
	}

	private void refreshList(){
		List<PhoneAttemptResult> data=null;
		try {
			data = finderPhoneAttemptResult.findAll();
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog(this, "Service Error: "+e.getMessage());
			return;
		}
		
		DefaultComboBoxModel<PhoneAttemptResult> model=new DefaultComboBoxModel<PhoneAttemptResult>();
		list.setModel(model);
		for(PhoneAttemptResult eachElement:data){
			model.addElement(eachElement);
		}
		list.setSelectedIndex(0);
	}

}
