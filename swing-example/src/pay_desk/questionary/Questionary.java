package pay_desk.questionary;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import swing_framework.AbstractInternalFrame;
import swing_framework.messages.IMessageSender;
import swing_framework.messages.WindowMessage;

public class Questionary extends AbstractInternalFrame{
	private final static long serialVersionUID=1L;

	public Questionary(IMessageSender sender,
						  int width, 
						  int height) {
		super("Анкеты",sender, width, height,false,false);
		initComponents();
	}
	
	private void initComponents(){

		JButton buttonAdd=new JButton("Добавить");
		buttonAdd.addActionListener(this.getActionListener(this, "onButtonAdd"));
		JButton buttonEdit=new JButton("Редактировать");
		buttonEdit.addActionListener(this.getActionListener(this, "onButtonEdit"));
		JButton buttonRemove=new JButton("Удалить");
		buttonRemove.addActionListener(this.getActionListener(this, "onButtonRemove"));
		JPanel panelManager=new JPanel(new BorderLayout());
		JPanel panelManagerEdit=new JPanel(new GridLayout(1,2));
		panelManagerEdit.add(buttonAdd);
		panelManagerEdit.add(buttonEdit);
		panelManager.add(panelManagerEdit,BorderLayout.CENTER);
		panelManager.add(buttonRemove,BorderLayout.EAST);
		

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panelManager,BorderLayout.SOUTH);
	}
	

	public void onButtonAdd(){
		System.out.println("buttonAdd");
		new QuestionaryCustomer("Добавить пользователя",this,false);
	}
	
	public void onButtonEdit(){
		System.out.println("buttonEdit");
	}

	public void onButtonRemove(){
		System.out.println("buttonRemove");
	}

	@Override
	protected void parentNotifier(WindowMessage message) {
		
		String additionalIdentifier=(String)message.getAdditionalIdentifier();
		String argument=(String)message.getArgument();
		System.out.println("AdditionalIdentifier: "+additionalIdentifier);
		System.out.println("AdditionalIdentifier: "+argument);
	}
	
}
