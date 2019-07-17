package install.show_password;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import install.EnterPoint;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.commons.beanutils.BeanUtils;

/** ввод логина и парол€  */
public class Password extends JInternalFrame{
	private final static long serialVersionUID=1L;
	
	private EnterPoint manager;
	
	public Password(EnterPoint manager, String tempDirectory){
		this.manager=manager;
		this.setTitle("¬вод инсталл€ционного логина и парол€ ");
		initComponents();
		this.setSize(350, 120);
		this.setVisible(true);
	}
	
	JTextField fieldLogin;
	JPasswordField fieldPassword;
	
	private void initComponents(){
		this.getContentPane().setLayout(new GridLayout(3,1));

		JPanel panelLogin=new JPanel();
		this.getContentPane().add(panelLogin);
		panelLogin.setLayout(new GridLayout(1,2));
		JLabel labelLogin=new JLabel("Ћогин:");
		labelLogin.setHorizontalAlignment(SwingConstants.RIGHT);
		panelLogin.add(labelLogin);
		fieldLogin=new JTextField();
		panelLogin.add(fieldLogin);
		
		JPanel panelPassword=new JPanel();
		this.getContentPane().add(panelPassword);
		panelPassword.setLayout(new GridLayout(1,2));
		JLabel labelPassword=new JLabel("ѕароль:");
		labelPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		panelPassword.add(labelPassword);
		fieldPassword=new JPasswordField();
		panelPassword.add(fieldPassword);
		
		JPanel panelManager=new JPanel(new GridLayout(1,2));
		this.getContentPane().add(panelManager);
		
		JButton buttonOk=new JButton("»нстал€ци€");
		panelManager.add(buttonOk);
		buttonOk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				onButtonEnter();
			}
		});
		
		JButton buttonCancel=new JButton("ќтмена");
		panelManager.add(buttonCancel);
		buttonCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonCancel();
			}
		});
	}
	
	private void onButtonCancel(){
		this.manager.showTempDirectory();
	}
	
	@SuppressWarnings("deprecation")
	private void onButtonEnter(){
		if((this.fieldLogin.getText()==null)||(this.fieldLogin.getText().trim().equals(""))){
			JOptionPane.showInternalMessageDialog(this, "введите Ћогин");
			return;
		}
		if((this.fieldPassword.getText()==null)||(this.fieldPassword.getText().trim().equals(""))){
			JOptionPane.showInternalMessageDialog(this, "введите ѕароль");
			return;
		}
		BeanUtils.createCache();
		JOptionPane.showInternalMessageDialog(this, "Ћогин и/или пароль не опознаны");
	}
}
