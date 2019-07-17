import jade.gui.TimeChooser;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;

public class EnterPoint extends JFrame{
	private static final long serialVersionUID=1L;
	private TimeChooser time_chooser=new TimeChooser(new Date()); 
	
	public static void main(String[] args){
		new EnterPoint();
	}

	/** */
	public EnterPoint(){
		super("JADE Example");
		initComponents();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(100,100);
		this.getContentPane().add(initComponents());
		this.setVisible(true);
		this.pack();
	}
	
	/** инициализация визуальных компонентов */
	private JPanel initComponents(){
		JPanel return_value=new JPanel();
		return_value.setLayout(new BorderLayout());
		JButton buttonShowTime=new JButton("show time");
		return_value.add(buttonShowTime,BorderLayout.NORTH);
		buttonShowTime.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				time_chooser.showEditTimeDlg(EnterPoint.this);
			}
		});
		return return_value;
	}
}
