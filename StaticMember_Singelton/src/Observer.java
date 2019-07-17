import javax.swing.*;
import java.awt.event.*;
/** класс-наблюдатель за поведением Propositus*/
public class Observer extends JFrame{
	/** */
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args){
		new Observer();
	}
	
	public Observer(){
		super("Observer");
		this.getContentPane().add(getPanelMain());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(100,250);
		this.setVisible(true);
	}
	
	private JPanel getPanelMain(){
		JPanel panel=new JPanel();
		JButton button_create=new JButton("create");
		JButton button_get=new JButton("getInstance");
		JButton button_get_string=new JButton("getString");
		JButton button_clear=new JButton("clear");
		JButton button_gc=new JButton("GarbageCollector");
		

		button_gc.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_gc();
			}
		});

		button_create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_create();
			}
		});

		button_get.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_get();
			}
		});
		button_get_string.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_get_string();
			}
		});
		
		button_clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_clear();
			}
		});
		
		panel.add(button_create);
		panel.add(button_get);
		panel.add(button_get_string);
		panel.add(button_clear);
		panel.add(button_gc);
		return panel;
	}
	
	public void on_button_create(){
		Propositus.create();
	}
	public void on_button_clear(){
		Propositus.clear();
	}
	public void on_button_get(){
		System.out.println("Get Propositus:"+Propositus.getInstance());
	}
	public void on_button_get_string(){
		System.out.println("Get Propositus.String:"+Propositus.getContent());
	}
	
	public void on_button_gc(){
		System.gc();
	}
}
