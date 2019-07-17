import javax.swing.*;
import java.awt.BorderLayout;

public class EnterPoint extends JFrame{
	/** */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){
		new EnterPoint();
	}
	
	private JTable field_table;
	
	public EnterPoint(){
		super("Table with image example ");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,600);
		this.getContentPane().add(initComponents());
		this.setVisible(true);
	}
	
	private JPanel initComponents(){
		JPanel return_value=new JPanel();
		return_value.setLayout(new BorderLayout());
		field_table=new JTable();
		
		return_value.add(field_table,BorderLayout.CENTER);
		return return_value;
	}
}
