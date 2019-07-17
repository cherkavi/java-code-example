package pay_desk.commons.combobox;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class PanelCombobox extends JPanel{
	private final static long serialVersionUID=1L;
	private JComboBox combobox;
	AbstractComboboxModel model;
	
	public PanelCombobox(String title,AbstractComboboxModel model){
		this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		this.model=model;
		combobox=new JComboBox(model.getAllElements());
		combobox.setSelectedItem(model.getSelectdElement());
		combobox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onChange();
			}
		});
		this.setLayout(new GridLayout(1,1));
		this.add(combobox);
	}

	private void onChange(){
		this.model.setSelectedElement(this.combobox.getSelectedItem());
	}
}
