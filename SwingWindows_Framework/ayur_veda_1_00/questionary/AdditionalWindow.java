package ayur_veda.questionary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import swing_framework.AbstractInternalFrame;
import swing_framework.messages.IMessageSender;

public class AdditionalWindow extends AbstractInternalFrame{
	private final static long serialVersionUID=1L;
	
	public AdditionalWindow( IMessageSender messageSender) {
		super("Дополнительное окно ", messageSender, 100, 75,true,false);
		this.getContentPane().add(new JLabel("this is temp"));
		JButton button=new JButton("button");
		this.getContentPane().add(button);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonClick();
			}
		});
	}

	private void onButtonClick(){
		System.out.println("On Button Click");
	}
}
