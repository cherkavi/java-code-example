import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** панель соединения с Jabber сервером */
public class PanelConnect extends JPanel{
	private final static long serialVersionUID=1L;
	public JTextField fieldLogin=new JTextField();
	public JTextField fieldPassword=new JTextField();

	public PanelConnect(String title, ActionListener loginListener){
		this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		
		JPanel panelLogin=new JPanel(new GridLayout(1,1));
		panelLogin.setBorder(javax.swing.BorderFactory.createTitledBorder("Логин"));
		panelLogin.add(fieldLogin);
		
		JPanel panelPassword=new JPanel(new GridLayout(1,1));
		panelPassword.setBorder(javax.swing.BorderFactory.createTitledBorder("Пароль"));
		panelPassword.add(fieldPassword);
		
		JPanel panelInput=new JPanel(new GridLayout(2,1));
		panelInput.add(panelLogin);
		panelInput.add(panelPassword);
		
		JButton button=new JButton("Connect");
		button.addActionListener(loginListener);
		this.setLayout(new BorderLayout());
	}
	
}
