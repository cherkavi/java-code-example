import java.awt.BorderLayout;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.junit.Test;

import message.AlarmMessage;

import calc.AbstractChecker;
import calc_implementation.*;

import junit.framework.*;

public class EnterPoint extends JFrame{
	private final static long serialVersionUID=1L;
	private JTextField textValue=new JTextField();
	private JTextArea textArea=new JTextArea();
	
	public EnterPoint(){
		super("Test");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton button=new JButton("generate event");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButton();
			}
		});
		this.textValue.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
			@Override
			public void keyReleased(KeyEvent event) {
				if(event.getKeyCode()==KeyEvent.VK_ENTER){
					onButton();
				}
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
		JPanel panelManager=new JPanel();
		panelManager.setLayout(new GridLayout(2,1));
		panelManager.add(textValue);
		panelManager.add(button);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panelManager,BorderLayout.NORTH);
		this.getContentPane().add(textArea,BorderLayout.CENTER);
		this.setSize(300,600);
		this.setVisible(true);
	}

	private AbstractChecker checker=this.getChecker();
	private SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
	
	private void onButton(){
		System.out.println("CheckValue ");
		AlarmMessage message=checker.checkForAlarmMessage(this.textValue.getText());
		if(message!=null){
			this.textArea.append(message.getDescription()+"   : "+message.getValue());
		}else{
			this.textArea.append("null");
		}
		this.textArea.append("  "+sdf.format(new Date())+"\n");
	}

	/** получить Checker */
	private AbstractChecker getChecker(){
		// EQIntegerChecker.java
		// GEIntegerChecker.java
		// GTIntegerChecker.java
		// LEIntegerChecker.java
		// LTIntegerChecker.java
		// NEIntegerChecker.java

		// EQStringChecker.java
		// NEStringChecker.java
		
		// EQDoubleChecker.java
		// GEDoubleChecker.java
		// GTDoubleChecker.java
		// LEDoubleChecker.java
		// LTDoubleChecker.java
		// NEDoubleChecker.java
		return new NEDoubleChecker(5000,"Alarm is Active", 1.2345,3);

		// return (AbstractChecker) getObjectFromFile("c:\\out.bin");
	}
	
	
	private Object getObjectFromFile(String pathToFile){
		Object returnValue=null;
		try{
			ObjectInputStream input=new ObjectInputStream(new FileInputStream(pathToFile));
			returnValue=input.readObject();
			input.close();
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
		}
		return returnValue;
	}
	
	@Test
	public void testSimple(){
		System.out.println("Execute test");
	}
	
	
	
	
	public static void main(String[] args){
		new EnterPoint();
		System.out.println("Start OK");
	}
	
	
}
