import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;
public class EnterPoint extends JFrame{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){
		new EnterPoint();
	}
	
	public EnterPoint(){
		super("Panel time choose example");
		initComponents();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200,150);
		pack();
		setVisible(true);
	}
	private void initComponents(){
		JPanel panel_main=new JPanel();
		panel_main.setLayout(new BorderLayout());
		JButton button_show_time=new JButton("Show time");
		button_show_time.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonShowTime();
			}
		});
		panel_main.add(button_show_time, BorderLayout.SOUTH);
		this.getContentPane().add(panel_main);
	}
	
	/** show time */
	private void onButtonShowTime(){
		
	}
}
