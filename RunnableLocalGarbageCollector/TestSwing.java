import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class TestSwing extends JFrame{
	public static void main(String[] args){
		new TestSwing();
	}

	private final static long serialVersionUID=1L;
	
	public TestSwing(){
		super("TestSwing");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		this.setSize(300,200);
		this.setVisible(true);
	}
	
	/** первоначальная инициализация компонентов */
	private void initComponents(){
		// create component's
		JButton buttonRun=new JButton("Run thread");
		JButton buttonGarbage=new JButton("Garbage");
		JButton buttonStop=new JButton("Stop thread");
		// add listener's
		buttonRun.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonRun();
			}
		});
		buttonGarbage.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonGarbage();
			}
		});
		buttonStop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonStop();
			}
		});
		
		// placing component's
		JPanel panelMain=new JPanel(new BorderLayout());
		JPanel panelManager=new JPanel(new GridLayout(3,1));
		panelManager.add(buttonRun);
		panelManager.add(buttonGarbage);
		panelManager.add(buttonStop);
		
		panelMain.add(panelManager);
		this.getContentPane().add(panelMain);
	}
	
	private void onButtonRun(){
		TestThread thread=new TestThread();
	}
	private void onButtonGarbage(){
		Runtime.getRuntime().gc();
	}
	private void onButtonStop(){
		
	}
}
/** класс, который запускает поток, при чем создается поток с помощью интерфейса Runnable,
 * но ссылка на Thread(this) локальна в конструкторе, то есть не привязана к классу, 
 * а теоретически должна быть убрана конструктором через некоторое время после создание данного класса */
class TestThread implements Runnable{
	private boolean flagRun=false;
	
	public TestThread(){
		Thread thread=new Thread(this);
		this.flagRun=true;
		thread.start();
	}
	
	public void stop(){
		this.flagRun=false;
	}
	
	public void run(){
		int counter=0;
		while(flagRun==true){
			System.out.println("TestThread:"+(counter++));
			try{
				Thread.sleep(250);
			}catch(InterruptedException ex){
				System.out.println("InterrruptedException: "+ex.getMessage());
			}catch(Exception ex){
				System.out.println("Exception "+ex.getMessage());
			}
		}
	}
}
