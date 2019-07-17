package pay_desk.questionary;

import java.awt.BorderLayout;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pay_desk.commons.PanelTextField;

import swing_framework.AbstractInternalFrame;
import swing_framework.messages.IMessageSender;
import swing_framework.messages.WindowMessage;
import swing_framework.messages.WindowMessageType;

public class Address extends AbstractInternalFrame{
	private final static long serialVersionUID=1L;
	private PanelTextField panelPostKod;
	private PanelTextField panelCityName;
	private PanelTextField panelStreetName;
	private PanelTextField panelHomeNumber;
	private PanelTextField panelHomeNumberAdd;
	private PanelTextField panelAppartmentNumber;
	private String returnId;

	public Address(String caption, 
				   IMessageSender messageSender,
				   String returnId) {
		super(caption, messageSender, 200, 300, false, true);
		this.returnId=returnId;
		initComponents();
	}

	private void initComponents(){
		JPanel panelManager=new JPanel(new GridLayout(1,2));
		JButton buttonOk=new JButton("Сохранить");
		buttonOk.addActionListener(this.getActionListener(this, "onButtonSave"));
		JButton buttonCancel=new JButton("Отменить");
		buttonCancel.addActionListener(this.getActionListener(this, "onButtonCancel"));
		panelManager.add(buttonOk);
		panelManager.add(buttonCancel);
		
		JPanel panelMain=new JPanel();
		panelMain.setLayout(new GridLayout(6,1));
		panelPostKod=new PanelTextField("Индекс");
		panelCityName=new PanelTextField("Город");
		panelStreetName=new PanelTextField("Улица");
		panelHomeNumber=new PanelTextField("Номер дома");
		panelHomeNumberAdd=new PanelTextField("Корпус");
		panelAppartmentNumber=new PanelTextField("квартира");

		panelMain.add(panelPostKod);
		panelMain.add(panelCityName);
		panelMain.add(panelStreetName);
		panelMain.add(panelHomeNumber);
		panelMain.add(panelHomeNumberAdd);
		panelMain.add(panelAppartmentNumber);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panelManager,BorderLayout.SOUTH);
		this.getContentPane().add(new JScrollPane(panelMain),BorderLayout.CENTER);
	}
	@Override
	protected void parentNotifier(WindowMessage message) {
	}

	
	public void onButtonSave(){
		this.sendMessage(this, new WindowMessage(WindowMessageType.PARENT_NOTIFIER,this.returnId,this.panelCityName.getText()));
		this.close();
	}
	
	public void onButtonCancel(){
		this.sendMessage(this, new WindowMessage(WindowMessageType.PARENT_NOTIFIER,this.returnId,null));
		this.close();
	}
}
