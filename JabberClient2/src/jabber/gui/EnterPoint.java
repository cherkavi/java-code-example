package jabber.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jabber.gui.panel_login.PanelLogin;
import jabber.gui.panel_recieve.PanelRecieve;
import jabber.gui.panel_send.PanelSend;
import jabber.wrap.IMessageListener;
import jabber.wrap.IPresenceListener;
import jabber.wrap.JabberWrap;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout.SequentialGroup;

public class EnterPoint extends JFrame implements IMessageListener, IPresenceListener{
	private final static long serialVersionUID=1L;
	
	public static void main(String[] args){
		new EnterPoint(args);
	}
	
	private PanelLogin panelLogin;
	private PanelRecieve panelRecieve;
	private PanelSend panelSend;
	/** proxy object for Jabber */
	private JabberWrap jabber;
	
	public EnterPoint(String[] args){
		super("Клиент Jabber");
		if(args.length==0){
			//                login                        password          recipient
			//args=new String[]{"technik7jobrobot@gmail.com","robottechnik7", "technik7job@gmail.com"};
			args=new String[]{"monitor_one@127.0.0.1","monitor_one", "monitor_one@127.0.0.1"};
		}
		initComponents(args);
		try{
			this.jabber=new JabberWrap("127.0.0.1", 5222, "technik");
		}catch(Exception ex){
			System.err.println(ex.getMessage());
			ex.printStackTrace(System.err);
			JOptionPane.showMessageDialog(this, "does not connected to server","Error",JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	
	/** инициализация компонентов */
	private void initComponents(String[] args){

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(350, 700);
		this.setVisible(true);
		
		panelLogin=new PanelLogin("Identification", new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonConnect();
			}
		},new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonDisconnect();
			}
		});
		panelRecieve=new PanelRecieve("recieve");
		panelSend=new PanelSend("Send",new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonSend();
			}
		});
		
		JPanel panel=new JPanel();
		GroupLayout groupLayout=new GroupLayout(panel);
		panel.setLayout(groupLayout);
		SequentialGroup horizontal=groupLayout.createSequentialGroup();
		SequentialGroup vertical=groupLayout.createSequentialGroup();
		groupLayout.setVerticalGroup(vertical);
		groupLayout.setHorizontalGroup(horizontal);
		horizontal.addGroup(groupLayout.createParallelGroup()
							.addComponent(panelLogin)
							.addComponent(panelSend)
							);
		vertical.addComponent(panelLogin);
		vertical.addComponent(panelSend);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panel, BorderLayout.NORTH);
		this.getContentPane().add(panelRecieve, BorderLayout.CENTER);
		
		if(args.length>0){
			this.panelLogin.setLogin(args[0]);
		}
		if(args.length>1){
			this.panelLogin.setPassword(args[1]);
		}
		if(args.length>2){
			this.panelSend.setRecipient(args[2]);
		}
		this.getRootPane().revalidate();
	}
	
	/** реакция на ввод логина и пароля */
	private void onButtonConnect(){
		try{
			this.jabber.connect(this.panelLogin.getLogin(), 
								this.panelLogin.getPassword(),
								this,
								this
								);
			this.panelLogin.showButtonDisconnect();
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "Login error, maybe login/password does not recognized", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void onButtonDisconnect(){
		this.jabber.disconnect();
		this.panelLogin.showButtonConnect();
	}
	
	/** реакция на нажатие кнопки отправки сообщения */
	private void onButtonSend(){
		this.jabber.sendMessage(panelSend.getRecipient(), panelSend.getText());
	}

	@Override
	public void messageNotify(String from, String text) {
		this.panelRecieve.addHistory(from, text);
	}

	@Override
	public void userEnter(String user) {
		System.out.println("User from contact list entered:"+user);
		
	}

	@Override
	public void userError(String user) {
		System.out.println("User from contact list send Error:"+user);
	}

	@Override
	public void userExit(String user) {
		System.out.println("User from contact list leave Jabber net"+user);
	}
}
