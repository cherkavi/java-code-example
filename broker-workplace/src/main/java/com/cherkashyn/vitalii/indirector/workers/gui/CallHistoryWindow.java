package com.cherkashyn.vitalii.indirector.workers.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.indirector.workers.domain.Phone;
import com.cherkashyn.vitalii.indirector.workers.domain.PhoneAttempt;
import com.cherkashyn.vitalii.indirector.workers.domain.PhoneDirty;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ModalPanel;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ModalResultListener;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.UIUtils;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.phone.PhoneFinder;
import com.cherkashyn.vitalii.indirector.workers.service.interf.phoneattempt.PhoneAttemptFinder;
import com.cherkashyn.vitalii.indirector.workers.service.interf.phonedirty.PhoneDirtyFinder;

@Component
@Scope("prototype")
public class CallHistoryWindow extends ModalPanel implements ModalResultListener{

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH=550;
	public static final int HEIGHT=200;
	
	
	@Autowired
	PhoneDirtyFinder finderPhoneDirty;
	
	@Autowired
	PhoneFinder finderPhone;
	
	@Autowired
	PhoneAttemptFinder finderPhoneAttempt;

	
	@Autowired
	ApplicationContext context;

	public CallHistoryWindow(){
	}
	
	@PostConstruct
	private void init(){
		this.setLayout(new BorderLayout());
		
		this.add(createPanelFind(), BorderLayout.NORTH);
		
		this.add(createPanelHistory(), BorderLayout.CENTER);
		
		// this.add(createPanelCall(), BorderLayout.SOUTH);
		
	}

/*	private JComponent createPanelCall() {
		JButton buttonCall=new JButton("Позвонить");
		this.add(buttonCall, BorderLayout.SOUTH);
		buttonCall.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonCall();
			}
		});
		return buttonCall;
	}
	*/

	private JComponent createPanelHistory() {
		this.listOfResolutions=new JList<PhoneAttempt>(new DefaultListModel<PhoneAttempt>());
		this.listOfResolutions.setBorder(BorderFactory.createTitledBorder("История звонков данного номера"));
		return this.listOfResolutions;
	}

	private JTextField fieldPhoneNumber;
	private JList<PhoneAttempt> listOfResolutions;
	
	private JPanel createPanelFind() {
		JPanel panel=new JPanel(new BorderLayout());
		
		JButton buttonClear=new JButton("Очистить");
		buttonClear.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonClear();
			}
		});
		panel.add(buttonClear, BorderLayout.WEST);
		
		JButton buttonFind=new JButton("Поиск повторов");
		buttonFind.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonFind();
			}
		});
		panel.add(buttonFind, BorderLayout.EAST);
		
		fieldPhoneNumber=new JTextField();
		fieldPhoneNumber.setBorder(BorderFactory.createTitledBorder("Телефонный номер"));
		panel.add(fieldPhoneNumber, BorderLayout.CENTER);
		fieldPhoneNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==10){
					onButtonFind();
				}
			}
		});
		return panel;
	}

	protected void onButtonClear() {
		this.fieldPhoneNumber.setText("");
		DefaultListModel<PhoneAttempt> model=(DefaultListModel<PhoneAttempt>)this.listOfResolutions.getModel();
		model.clear();
	}

	private final static String NEW_LINE="\n";
	
	protected void onButtonFind() {
		DefaultListModel<PhoneAttempt> model=(DefaultListModel<PhoneAttempt>)this.listOfResolutions.getModel();
		
		model.clear();
		
		List<Phone> existingPhone=null;
		try{
			existingPhone=this.findPhone(this.fieldPhoneNumber.getText());
			if(existingPhone!=null && existingPhone.size()>0){
				JOptionPane.showMessageDialog( this, "телефон уже есть в базе данных", "Информация", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
		}catch(ServiceException e){
			JOptionPane.showMessageDialog( this, "Данные не возвращены:"+e.getMessage(), "Ошибка ", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		
		List<PhoneDirty> phones=null;
		try {
			phones = this.findPhoneDirty(this.fieldPhoneNumber.getText());
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog( this, "Данные не возвращены:"+e.getMessage(), "Ошибка ", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// check for empty query 
		if(phones==null || phones.size()==0){
			onButtonCall(this.fieldPhoneNumber.getText().trim());
			return;
		}

		// check for multiply query 
		if(phones.size()>1){
			StringBuilder message=new StringBuilder();
			for(PhoneDirty eachPhone:phones){
				message.append(eachPhone.getPhone());
				message.append(NEW_LINE);
			}
			JOptionPane.showMessageDialog( this, "Уточните запрос:"+message.toString(), "Много данных", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		
		// show History for alone element - first element into list 
		List<PhoneAttempt> historyList=null;
		try {
			historyList = findByPhone(phones.get(0));
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog( this, "Данные не возвращены:"+e.getMessage(), "Ошибка ", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(historyList==null || historyList.isEmpty()){
			onButtonCall(phones.get(0));
			return;
		}
		for(PhoneAttempt eachResult: historyList){
			model.addElement(eachResult);
		}
		onButtonCall(phones.get(0));
	}
	
	private List<Phone> findPhone(String phoneNumber) throws ServiceException {
		return this.finderPhone.findLike(phoneNumber.trim().replaceAll("([^0-9])",""));
	}

	private List<PhoneAttempt> findByPhone(PhoneDirty phoneDirty) throws ServiceException{
		return finderPhoneAttempt.find(phoneDirty);
	}

	private List<PhoneDirty> findPhoneDirty(String value) throws ServiceException{
		return this.finderPhoneDirty.findLike(value.trim().replaceAll("([^0-9])",""));
	}

	private void onButtonCall(String number){
		CallResultWindow window=context.getBean(CallResultWindow.class);
		window.setPhone(number);
		UIUtils.showModal(this, window);
	}

	private void onButtonCall(PhoneDirty number){
		CallResultWindow window=context.getBean(CallResultWindow.class);
		window.setPhone(number);
		UIUtils.showModal(this, window);
	}
	
	@Override
	public void childWindowModalResult(Object value) {
		if(value==null || (!(value instanceof ModalResultListener.Result)) ){
			return;
		}
		ModalResultListener.Result result=(ModalResultListener.Result)value;
		if(result.equals(ModalResultListener.Result.Ok)){
			DefaultListModel<PhoneAttempt> model=(DefaultListModel<PhoneAttempt>)this.listOfResolutions.getModel();
			
			model.clear();
			
			try {
				for(PhoneAttempt eachResult: findByPhone(this.findPhoneDirty(this.fieldPhoneNumber.getText().trim()).get(0))){
					model.addElement(eachResult);
				}
			} catch (ServiceException e) {
				JOptionPane.showMessageDialog( this, "Данные не возвращены:"+e.getMessage(), "Ошибка ", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
	
}
