package ayur_veda.questionary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import swing_framework.AbstractInternalFrame;
import swing_framework.messages.IMessageSender;

public class AddQuestionary extends AbstractInternalFrame{
	private final static long serialVersionUID=1L;

	public AddQuestionary(IMessageSender sender,
						  int width, 
						  int height) {
		super("Добавить анкету",sender, width, height,false,false);
		initComponents();
	}
	
	private void initComponents(){
		JButton buttonShowModal=new JButton("Show modal");
		buttonShowModal.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonShowModal();
			}
		});
		this.getContentPane().add(buttonShowModal);
	}
	
	private void onButtonShowModal(){
		new AdditionalWindow(this);
		//frame.setModal(true);
	}

}
