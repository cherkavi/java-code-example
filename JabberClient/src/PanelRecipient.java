import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

/** панель содержащая получателя адресованных сообщений */
public class PanelRecipient extends JPanel{
	private final static long serialVersionUID=1L;
	public JTextField fieldRecipient=new JTextField(); 
	
	/** панель содержащая получателя адресованных сообщений */
	public PanelRecipient(String title){
		this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		this.setLayout(new GridLayout(1,1));
		this.add(fieldRecipient);
	}
}
