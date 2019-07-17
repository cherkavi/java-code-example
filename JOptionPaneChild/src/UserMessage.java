import java.awt.BorderLayout;

import javax.swing.*;


public class UserMessage extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserMessage(String message){
		super();
		this.setLayout(new BorderLayout());
		this.add(new JLabel(message),BorderLayout.NORTH);
		this.add(new JTextArea(),BorderLayout.CENTER);
		this.add(new JButton(),BorderLayout.SOUTH);
	}
}
