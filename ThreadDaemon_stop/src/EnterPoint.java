import javax.swing.*;
import java.awt.event.*;

public class EnterPoint extends JFrame {
	public static void main(String[] args){
		new EnterPoint();
	}

	
	private Daemon daemon;
	
	public EnterPoint(){
		super("Daemon thread try stopping");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(150,100);
		this.init();
		this.setVisible(true);
	}
	
	public void init(){
		JPanel panel_main=new JPanel();
		JButton button_start=new JButton("start");
		button_start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_start_click();
			}
		});
		JButton button_stop=new JButton("stop");
		button_stop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_stop_click();
			}
		});
		panel_main.add(button_start);
		panel_main.add(button_stop);
		this.getContentPane().add(panel_main);
	}
	
	private void on_button_start_click(){
		daemon=new Daemon("Simple Daemon ",1000);
	}
	private void on_button_stop_click(){
		daemon.stop();
	}
}
