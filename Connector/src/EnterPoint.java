import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import bc.connection.Connector;


public class EnterPoint extends JFrame {
	private final static long serialVersionUID=1L;
	
	private String user="bc_reports";
	private String password="bc_reports";
	
	/*private String user="bc_reports";
	private String password="bc_reports";
	*/
	
	private void debug(Object information){
		System.out.print("EnterPoint");
		System.out.print(" DEBUG ");
		StackTraceElement[] ste = (new Throwable()).getStackTrace();
		System.out.print(ste[1].getMethodName()+": ");
		System.out.println(information);
	}

	private void error(Object information){
		System.out.print("EnterPoint");
		System.out.print(" ERROR ");
		StackTraceElement[] ste = (new Throwable()).getStackTrace();
		System.out.print(ste[1].getMethodName()+": ");
		System.out.println(information);
	}
	
	public static void main(String[] args){
		new EnterPoint();
	}
	
	public EnterPoint(){
		super("JFrame");
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(org.apache.log4j.Level.WARN);
		initComponents();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,400);
		this.setVisible(true);
		this.pack();
	}
	
	private JTextField fieldLogin;
	private JTextField fieldPassword;
	
	/** получить панель с обернутым в нее визуальным компонентом 
	 * @param component - компонент, который нужно обернуть
	 * @param caption - заголовок, который нужно назначить компоненту 
	 * */
	private JPanel getTitlePanelWrap(JComponent component, String caption){
		JPanel returnValue=new JPanel(new GridLayout(1,1));
		returnValue.add(component);
		returnValue.setBorder(javax.swing.BorderFactory.createTitledBorder(caption));
		return returnValue;
	}
	
	private void initComponents(){
		// create element's
		JButton buttonGetConnection=new JButton("Get Connection (U,P) and Close It");
		JButton buttonGetConnection2=new JButton("Get Connection (U,P) without Close");
		JButton buttonGetConnection5=new JButton("Get Connection (session) and Close It");
		JButton buttonGetConnection7=new JButton("Remove session by \"DBA\" ");

		JButton buttonGetConnection3=new JButton("User Connection (U,P) and Close It");
		JButton buttonGetConnection4=new JButton("User Connection (U,P) without Close");
		JButton buttonGetConnection6=new JButton("User Connection (session) and Close It");
		JButton buttonDropUser=new JButton("Drop User <First>");
		JButton buttonPrintAllConnection=new JButton("print ALL connection ");
		
		fieldLogin=new JTextField();
		fieldPassword=new JTextField();
		JButton buttonConnectCustom=new JButton("Connect with any");
		JPanel panelCustom=new JPanel(new GridLayout(1,2));
		panelCustom.add(getTitlePanelWrap(fieldLogin, "login"));
		panelCustom.add(getTitlePanelWrap(fieldPassword, "password"));
		
		JPanel panelChangePassword=new JPanel();
		panelChangePassword.setBorder(javax.swing.BorderFactory.createTitledBorder("Смена пароля для пользователя"));
		GroupLayout groupLayout=new GroupLayout(panelChangePassword);
		panelChangePassword.setLayout(groupLayout);
		GroupLayout.SequentialGroup groupLayoutHorizontal=groupLayout.createSequentialGroup();
		GroupLayout.SequentialGroup groupLayoutVertical=groupLayout.createSequentialGroup();
		groupLayout.setVerticalGroup(groupLayoutVertical);
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		
		final JTextField fieldUserName=new JTextField();
		final JTextField fieldPasswordBefore=new JTextField();
		final JTextField fieldPasswordAfter=new JTextField();
		JPanel panelUserName=getTitlePanelWrap(fieldUserName, "Имя пользователя");
		JPanel panelPasswordBefore=getTitlePanelWrap(fieldPasswordBefore, "Пароль до");
		JPanel panelPasswordAfter=getTitlePanelWrap(fieldPasswordAfter, "Пароль после");
		JButton buttonChange=new JButton("Сменить пароль");
		
		
		groupLayoutHorizontal.addGroup(groupLayout.createParallelGroup()
									   .addComponent(panelUserName)
									   .addGroup(groupLayout.createSequentialGroup()
											   .addComponent(panelPasswordBefore)
											   .addComponent(panelPasswordAfter)
									   			)
									   .addComponent(buttonChange,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
									   );
		
		groupLayoutVertical.addComponent(panelUserName);
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup()
				   					 .addComponent(panelPasswordBefore)
				   					 .addComponent(panelPasswordAfter)
				   					 );
		groupLayoutVertical.addComponent(buttonChange);
		
		// set listener's
		buttonChange.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				EnterPoint.this.changePassword(fieldUserName.getText(), fieldPasswordBefore.getText(), fieldPasswordAfter.getText());
			}
		});
		
		buttonConnectCustom.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonConnectCustom();
			}
		});
		
		buttonGetConnection.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonGetConnection();
			}
		});

		buttonGetConnection2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonGetConnection2();
			}
		});

		buttonGetConnection3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonGetConnection3();
			}
		});
		
		buttonGetConnection4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonGetConnection4();
			}
		});
		
		buttonGetConnection5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonGetConnection5();
			}
		});

		buttonGetConnection6.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonGetConnection6();
			}
		});

		buttonGetConnection7.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonGetConnection7();
			}
		});

		buttonDropUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonDropUser();
			}
		});
		
		buttonPrintAllConnection.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonPrintAllConnection();
			}
		});
		// placing component's
		JPanel panelMain=new JPanel();
		groupLayout=new GroupLayout(panelMain);
		panelMain.setLayout(groupLayout);
		groupLayoutHorizontal=groupLayout.createSequentialGroup();
		groupLayoutVertical=groupLayout.createSequentialGroup();
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		groupLayout.setVerticalGroup(groupLayoutVertical);
		
		groupLayoutHorizontal.addGroup(groupLayout.createParallelGroup()
									   .addGroup(
												groupLayout.createSequentialGroup()
												   .addGroup(groupLayout.createParallelGroup()
														   .addComponent(buttonGetConnection)
														   .addComponent(buttonGetConnection2)
														   .addComponent(buttonGetConnection5)
														   .addComponent(buttonGetConnection7)
												   			)
												   	.addGroup(groupLayout.createParallelGroup()
												   			.addComponent(buttonGetConnection3)
												   			.addComponent(buttonGetConnection4)
												   			.addComponent(buttonGetConnection6)
												   			.addComponent(buttonDropUser)
												   			)
											   )
										.addComponent(buttonPrintAllConnection,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
										.addComponent(panelCustom)
										.addComponent(buttonConnectCustom,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
										.addComponent(panelChangePassword)
										);
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup()
									 .addGroup(groupLayout.createSequentialGroup()
											   .addComponent(buttonGetConnection)
											   .addComponent(buttonGetConnection2)
											   .addComponent(buttonGetConnection5)
											   .addComponent(buttonGetConnection7)
											   )
									 .addGroup(groupLayout.createSequentialGroup()
									   			.addComponent(buttonGetConnection3)
									   			.addComponent(buttonGetConnection4)
									   			.addComponent(buttonGetConnection6)
									   			.addComponent(buttonDropUser)
									 		   )	
									 );
	    groupLayoutVertical.addGap(20);
		groupLayoutVertical.addComponent(buttonPrintAllConnection);
		groupLayoutVertical.addGap(10);
		groupLayoutVertical.addComponent(panelCustom);
		groupLayoutVertical.addComponent(buttonConnectCustom);
		groupLayoutVertical.addGap(20);
		groupLayoutVertical.addComponent(panelChangePassword);

		this.getContentPane().add(panelMain);
	}
	
	private void onButtonConnectCustom() {
		Connection connection=Connector.getConnection(this.fieldLogin.getText(),this.fieldPassword.getText(),"222");
		try{
			debug("new Custom Connection:"+connection+"   USERNAME:"+connection.getMetaData().getUserName());
		}catch(Exception ex){
		}
		try{
			connection.close();
			debug("connection is closed ");
		}catch(Exception ex){
			error("Exception: "+ex.getMessage());
		}
	}

	private void onButtonGetConnection(){
		Connection connection=Connector.getConnection(user, password, "id1");
		try{
			debug("new Connection1:"+connection+"   USERNAME:"+connection.getMetaData().getUserName());
		}catch(Exception ex){
		}
		try{
			connection.close();
			debug("connection is closed ");
		}catch(Exception ex){
			error("Exception: "+ex.getMessage());
		}
	}
	
	private void onButtonGetConnection2(){
		Connection connection=Connector.getConnection(user, password, "id1");
		try{
			debug("new Connection1:"+connection+"   USERNAME:"+connection.getMetaData().getUserName());
		}catch(Exception ex){
		}
	}

	private void onButtonGetConnection5(){
		Connection connection=Connector.getConnection("id1");
		try{
			debug("new Connection1:"+connection+"   USERNAME:"+connection.getMetaData().getUserName());
			connection.close();
		}catch(Exception ex){
		}
	}
	
	private void onButtonGetConnection3(){
		Connection connection=Connector.getConnection("first", "first", "id1");
		try{
			debug("new Connection1:"+connection+"   USERNAME:"+connection.getMetaData().getUserName());
		}catch(Exception ex){
		}
		try{
			connection.close();
			debug("connection is closed ");
		}catch(Exception ex){
			error("Exception: "+ex.getMessage());
		}
	}
	
	private void onButtonGetConnection4(){
		Connection connection=Connector.getConnection("first", "first", "id1");
		try{
			debug("new Connection1:"+connection+"   USERNAME:"+connection.getMetaData().getUserName());
		}catch(Exception ex){
		}
	}


	private void onButtonGetConnection6(){
		Connection connection=Connector.getConnection("id1");
		try{
			debug("new Connection1:"+connection+"   USERNAME:"+connection.getMetaData().getUserName());
			connection.close();
		}catch(Exception ex){
		}
	}

	private void onButtonGetConnection7(){
		Connector.removeSessionId("id1");
/*		try{
			Connection connection=Connector.getConnection("id1");
			debug("new Connection1:"+connection+"   USERNAME:"+connection.getMetaData().getUserName());
			connection.close();
		}catch(Exception ex){
		}
*/		
	}
	
	private void onButtonDropUser(){
		System.out.println("drop User name:"+Connector.dropUserConnection("first"));
	}
	
	private void onButtonPrintAllConnection(){
		Connector.printAllConnectionCount(System.out);
	}
	
	/** функция, которая производит измненения в пароле*/
	private void changePassword(String userName, String passwordBefore, String passwordAfter){
		// TODO 
		Connector.dropUserConnection(userName);
		
	}
}
