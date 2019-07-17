package org.ukraine.kiev.narodna.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import org.ukraine.kiev.narodna.domain.ListItemModel;
import org.ukraine.kiev.narodna.service.ObjectStorage;
import org.ukraine.kiev.narodna.service.ServiceException;
import org.ukraine.kiev.narodna.service.Storage;

public class EditList extends JFrame{
	private static final long	serialVersionUID	= 1L;
	private Storage storage; 

	public static void main(String[] args){
		if(args.length>0){
			new EditList(args[0]);
		}else{
			new EditList("user_data.dat");
		}
		
	}
	
	public EditList(String pathToStorage) {
		this.storage=new ObjectStorage(pathToStorage);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 600);
		initComponents();
		this.updateModel(storage.getAll());
		this.setVisible(true);
	}

	private DefaultListModel<ListItemModel> listModel=new DefaultListModel<ListItemModel>();
	JList<ListItemModel> list=new JList<ListItemModel>(listModel);
	
	private void initComponents() {
		this.setLayout(new BorderLayout());
		// list
		list.setCellRenderer(new ListCellRenderer<ListItemModel>() {

			@Override
			public Component getListCellRendererComponent(
					JList<? extends ListItemModel> list, ListItemModel value,
					int index, boolean isSelected, boolean cellHasFocus) {
				Color color=null;
				if(isSelected){
					color=new Color(0,191,255);
				}
				if(cellHasFocus){
					color=new Color(0,255,255);
				}
				if(color==null){
					color=new Color(0,206,209);
				}
				JLabel label=new JLabel(value.toString());
				label.setBackground(color);
				label.setOpaque(true);
				return label;
			}
		});
		
		this.add(new JScrollPane(list));
		
		// manager
		JPanel panelManager=new JPanel(new GridLayout(1,2));
		JButton buttonAdd=new JButton("Add");
		buttonAdd.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					onButtonAdd();
				}catch(ServiceException ex){
					JOptionPane.showMessageDialog(EditList.this,  ex.getMessage(), "can't save Element", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		panelManager.add(buttonAdd);
		
		JButton buttonEdit=new JButton("Print");
		buttonEdit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				onButtonPrint();
			}
		});
		panelManager.add(buttonEdit);
		
		this.add(panelManager, BorderLayout.NORTH);
	}
	
	private void updateModel(List<ListItemModel> items){
		this.listModel.removeAllElements();
		for(ListItemModel eachElement:items){
			this.listModel.addElement(eachElement);
		}
	}

	private void onButtonAdd() throws ServiceException{
		AddDialog dialog=new AddDialog(this);
		if(dialog.getModel()!=null){
			ListItemModel model=dialog.getModel();
			this.storage.save(model);
			this.updateModel(this.storage.getAll());
			System.out.println("add new: "+model);
		}else{
			// System.out.println("cancel add new" );
		}
	}
	
	private void onButtonPrint(){
		ListItemModel model=this.list.getSelectedValue();
		if(model!=null){
			if( JOptionPane.showConfirmDialog(this, model.toString(), "Are you sure to print ?", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				// TODO Print 
				System.out.println(model);
			}
		}
	}
	
	
}
