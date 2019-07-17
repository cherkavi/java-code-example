import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class EnterPoint extends JFrame {
	private final static long serialVersionUID=1L;
	private PanelConnect panelConnect;
	private PanelRecipient panelRecipient;
	
	public EnterPoint(){
		super("client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(200,600);
		this.initComponents();
		this.setVisible(true);
	}
	
	private void initComponents(){
		JPanel panelConnect=new JPanel();
		
		history=new JTextArea();
		
		JPanel panelSend=new JPanel(new GridLayout(2,1));
		textSend=new JTextField();
		panelSend.add(textSend);
		JButton buttonSend=new JButton("Send");
		buttonSend.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonSend();
			}
		});
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panelConnect, BorderLayout.NORTH);
		this.getContentPane().add(history, BorderLayout.CENTER);
		this.getContentPane().add(panelSend, BorderLayout.SOUTH);
	}
	
	private JTextField textSend;
	private JTextArea history;
	private void onButtonSend(){
		
	}
}
