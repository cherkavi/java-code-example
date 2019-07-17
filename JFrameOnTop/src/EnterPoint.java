import javax.swing.*;

import com.sun.cnpi.rss.elements.Channel;
import com.sun.cnpi.rss.elements.ComplexElement;
import com.sun.cnpi.rss.elements.Guid;
import com.sun.cnpi.rss.elements.Item;
import com.sun.cnpi.rss.elements.Rss;
import com.sun.cnpi.rss.parser.RssParser;
import com.sun.cnpi.rss.parser.RssParserFactory;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

public class EnterPoint extends JFrame{
	private final static long serialVersionUID=1L;
	private JButton buttonToTop;
	
	public static void main(String[] args){
		new EnterPoint();
	}
	
	public EnterPoint(){
		super("on Top Example ");
		initComponents();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(100,50);
		this.setVisible(true);
	}
	
	private void initComponents(){
		JPanel panelMain=new JPanel(new BorderLayout());
		buttonToTop=new JButton("place to Top");
		buttonToTop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonToTop();
			}
		});
		panelMain.add(buttonToTop,BorderLayout.SOUTH);
		this.getContentPane().add(panelMain);
	}
	
	/** reaction on striking Button to Top */
	private void onButtonToTop(){
		
		// лента новостей, описание: http://htmlweb.ru/other/rss.php
		// источник новостей: http://news.rambler.ru/rss/
		try{
			RssParser parser = RssParserFactory.createDefault();
	        Rss rss = parser.parse(new URL("http://news.rambler.ru/rss/Kazakhstan/"));
	        Channel channel=rss.getChannel();
	        Collection<Object> collection=channel.getItems();
	        Iterator<Object> iterator=collection.iterator();
	        int counter=0;
	        while(iterator.hasNext()){
	        	counter++;
	        	Object element=iterator.next();
	        	if(element instanceof Item){
	        		Item item=(Item)element;
	        		Guid guid=item.getGuid();//
	        		if(guid!=null){
	        			System.out.println(counter+" "+guid.getText()+" :"+((Item)element).getTitle());
	        		}else{
	        			System.out.println(counter+" "+"Title:"+((Item)element).getTitle());
	        		}
	        		
	        	}else{
	        		System.out.println("Another element:"+element.getClass());
	        	}
	        }
		}catch(Exception ex){
			System.err.println("Rss Exception: "+ex.getMessage());
		}
	}
	
}

class ButtonCounter extends Thread{
	private JFrame parentFrame;
	private JButton buttonForChange;
	private int delayTime;
	
	public ButtonCounter(JFrame parentFrame, JButton buttonForChange,int delayTime){
		super("buttonCounter");
		this.parentFrame=parentFrame;
		this.buttonForChange=buttonForChange;
		this.delayTime=delayTime;
		
		this.buttonForChange.setEnabled(false);
		this.delayTime=delayTime;
		this.parentFrame=parentFrame;
		this.buttonForChange=buttonForChange;
		this.start();
	}
	
	
	
	public void run(){
		while(this.delayTime>0){
			try{
				Thread.sleep(1000);
				this.delayTime--;
				this.buttonForChange.setLabel(Integer.toString(this.delayTime));
			}catch(Exception ex){};
		}
		this.buttonForChange.setEnabled(true);	
		
		int counter=10;
		while(counter>0){
			try{
				Thread.sleep(1000);
				this.parentFrame.toFront();
			}catch(Exception ex){};
			counter--;
		}
		// this.parentFrame.setAlwaysOnTop(true);
	}
}
