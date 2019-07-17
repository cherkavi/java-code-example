
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Enumeration;

public class FrameMain extends JFrame{

	public static void main(String[] args){
		
		new FrameMain();
	}
	
	public FrameMain(){
		super("example");
		
		this.getContentPane().add(initComponents());
		this.setSize(300,300);
		this.setVisible(true);
	}
	
	private JPanel initComponents(){
		// create component's
		UIManager.put("ScrollBar.width", new Integer(40));
		Enumeration keys=UIManager.getDefaults().keys();
		Object key;
		Object value;
		while(keys.hasMoreElements()){
			key=keys.nextElement();
			value=UIManager.getDefaults().get(key);
			System.out.println("Keys:  "+key);
			System.out.println("        Value:"+value);
		}
		
		JButton button_add=new JButton("add text");
		JTextArea area=new JTextArea();
		
		JScrollPane scroll_pane=new JScrollPane(area);
		JScrollBar bar=new JScrollBar(JScrollBar.VERTICAL);


		scroll_pane.setVerticalScrollBar(bar);
		scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// add listener's
		
		// placing component's
		JPanel panel_main=new JPanel();
		panel_main.setLayout(new BorderLayout());
		panel_main.add(button_add,BorderLayout.SOUTH);
		panel_main.add(scroll_pane,BorderLayout.CENTER);
		return panel_main;
	}
}
