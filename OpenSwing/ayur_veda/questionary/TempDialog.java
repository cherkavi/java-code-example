package ayur_veda.questionary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.openswing.swing.form.client.Form;
import org.openswing.swing.form.client.FormController;
import org.openswing.swing.mdi.client.InternalFrame;
import org.openswing.swing.mdi.client.MDIFrame;

public class TempDialog extends InternalFrame{
	private final static long serialVersionUID=1L;
	private boolean ok=false;
	private Form mainPanel=new Form();
	
	public TempDialog(FormController formController){
		super();
	    this.setResizable(false);
	    this.setIconifiable(false);
	    this.setMaximizable(false);
	    this.setSize(150,100);
	    this.setTitle("Предупреждение");
	    //this.setModal(true);
	    this.initComponents();
	    mainPanel.setFormController(formController);
	}
	
	private void initComponents(){
		JPanel panelMain=new JPanel();
		JButton buttonOk=new JButton("OK");
		buttonOk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonOk();
			}
		});
		panelMain.add(buttonOk);
		
		JButton buttonCancel=new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonCancel();
			}
		});
		panelMain.add(buttonCancel);
		this.getContentPane().add(panelMain);
	}
	
	private void onButtonOk(){
		this.ok=true;
		try{
			this.closeFrame();
		}catch(Exception ex){};
	}
	
	private void onButtonCancel(){
		this.ok=false;
		try{
			this.closeFrame();
		}catch(Exception ex){};
	}
	
	public boolean isOk(){
		return this.ok;
	}
}
