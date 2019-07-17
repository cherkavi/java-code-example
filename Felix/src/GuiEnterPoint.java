import javax.swing.*;

import java.awt.event.*;

public class GuiEnterPoint extends JFrame{
	private final static long serialVersionUID=1L;
	
	public static void main(String[] args){
		new GuiEnterPoint();
	}
	
	public GuiEnterPoint(){
		super("this is example");
		this.setSize(100,100);
		initComponents();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	/** init visual Component's */
	private void initComponents(){
		// create element's
		JPanel panelMain=new JPanel();
		JLabel label=new JLabel("this is label");
		JButton buttonOk=new JButton("ok");
		// add listener's
		buttonOk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(GuiEnterPoint.this, "this is test");
			}
		});
		
		// placing components
		panelMain.add(label);
		panelMain.add(buttonOk);
		this.getContentPane().add(panelMain);
	}
}
