package ayur_veda.questionary;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.openswing.swing.client.OptionPane;
import org.openswing.swing.mdi.client.InternalFrame;
import org.openswing.swing.mdi.client.MDIFrame;

public class AddQuestionary extends InternalFrame{
	private final static long serialVersionUID=1L;
	
	public AddQuestionary(){
	    this.setResizable(false);
	    this.setIconifiable(false);
	    this.setMaximizable(false);
	    this.setSize(300,200);
	    this.setTitle("Добавление анкеты");
	    this.initComponents();
	}
	
	private void initComponents(){
		JPanel panelMain=new JPanel(new FlowLayout(FlowLayout.LEFT));

		JButton buttonAdd=new JButton("Add");
		buttonAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonAdd();
			}
		});
		panelMain.add(buttonAdd);

		JButton buttonDialog=new JButton("Add");
		buttonDialog.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonDialog();
			}
		});
		panelMain.add(buttonDialog);
		
		this.getContentPane().add(panelMain);
	}
	
	private void onButtonAdd(){
		System.out.println("ButtonAdd");
	}
	
	private void onButtonDialog(){
		System.out.println("ButtonDialog:begin");
	}
}
