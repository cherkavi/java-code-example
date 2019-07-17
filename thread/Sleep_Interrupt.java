import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** тестирование работы связки sleep-interrupt */
public class Test extends JFrame{
	private final static long serialVersionUID=1L;
	
	private Object sharedObject =new Object();
	private TestThread testThread=new TestThread(sharedObject);
	
	public static void main(String[] args){
		new Test();
	}

	
	public Test(){
		this.init();
		this.testThread.start();
	}
	
	private void init(){
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(2,1));
		JButton buttonInterrupt=new JButton("interrupt");
		buttonInterrupt.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				onButtonInterrupt();
			}
		});
		panel.add(buttonInterrupt);
		
		JButton buttonSynchronized=new JButton("synchronized");
		buttonSynchronized.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				onButtonSynchronized();
			}
		});
		panel.add(buttonSynchronized);
		
		this.getContentPane().add(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300,200);
		this.setVisible(true);
	}
	
	private void onButtonInterrupt(){
		System.out.println("прерывание потока ");
		this.testThread.interrupt();
	}
	
	private void onButtonSynchronized(){
		System.out.println("захват ресурса ");
		synchronized(this.sharedObject){
			try{
				Thread.sleep(5000);
			}catch(Exception ex){};
			System.out.println("захват ресурса.interrupt");
			testThread.interrupt();
			try{
				Thread.sleep(5000);
			}catch(Exception ex){};
 		}
	}
}

class TestThread extends Thread{
	private Object sharedObject;
	public TestThread(Object sharedObject){
		this.sharedObject=sharedObject;
	}
	
	@Override
	public void run(){
		while(true){
			System.out.println(">>> calculate_begin");
			long temp=0;
			for(long counter=0;counter<1000*1000*999;counter++){
				temp=temp+counter;
			}
			System.out.println("<<< calculate_end");
			System.out.println(">>> sleep_begin");
			try{
				Thread.sleep(3000);
			}catch(InterruptedException ex){
				System.err.println("Interrupted");
			};
			System.out.println("<<< sleep_end");
			
			System.out.println(">>> try synchronized");
			synchronized(sharedObject){};
			System.out.println("<<< end try synchronized");
		}
	}
}
/* 

>>> calculate_begin // кнопка не нажималась 
<<< calculate_end
>>> sleep_begin 
прерывание потока // кнопка нажалась в процессе  
Interrupted       // Thread.interrupt() - установился внутренний флаг в потоке и сработало InterruptException
<<< sleep_end	  // поток завершился не дождавшись 

>>> calculate_begin   // начало вычислений  
прерывание потока // Thread.interrupt() - нет реакции ( установился внутренний флаг потока, который сигнализирует об InterruptException )
прерывание потока // Thread.interrupt() - нет реакции
<<< calculate_end	  // вычисления закончены 
>>> sleep_beginInterrupted //попытка заснуть - в потоке установлен внутренний флаг Interrupt - вываливается с исключением InterruptException, не заснув - сразу же прерывается
<<< sleep_end

>>> calculate_begin 
<<< calculate_end
>>> sleep_begin // засыпает, т.к. флаг InterruptException был уже снят 
<<< sleep_end

на блоки synchronized не влияет выполнение interrupt()


*/