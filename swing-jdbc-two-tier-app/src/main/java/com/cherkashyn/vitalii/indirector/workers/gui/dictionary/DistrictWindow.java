package com.cherkashyn.vitalii.indirector.workers.gui.dictionary;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.indirector.workers.domain.District;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ModalPanel;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ModalResultListener;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.UIUtils;
import com.cherkashyn.vitalii.indirector.workers.service.exception.RepeatInsertException;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.district.DistrictFinder;
import com.cherkashyn.vitalii.indirector.workers.service.interf.district.DistrictRepository;


@Component
@Scope("prototype")
public class DistrictWindow extends ModalPanel implements ModalResultListener{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH=400;
	public static final int HEIGHT=300;
	
	private JTree tree=new JTree();
	{
		tree.setCellRenderer(new DefaultTreeCellRenderer());
	}
	
	@Autowired
	DistrictFinder finder;
	
	@Autowired
	DistrictRepository repository;
	
	public DistrictWindow(){
	}
	
	@PostConstruct
	public void init() {
		
		this.setLayout(new BorderLayout());
		
		this.refreshList();
		this.add(new JScrollPane(tree), BorderLayout.CENTER);
		
		JButton buttonAdd=new JButton("Add Child");
		buttonAdd.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TreePath path=tree.getSelectionPath();
				if(path==null || path.getPath()==null || path.getPath().length==0){
					return;
				}
				
				DefaultMutableTreeNode lastElement=(DefaultMutableTreeNode)path.getLastPathComponent();
				UIUtils.showModal(DistrictWindow.this, new PanelEdit((District)lastElement.getUserObject()));
			}
		});
		
		JButton buttonRemove=new JButton("Remove");
		buttonRemove.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				TreePath path=tree.getSelectionPath();
				if(path==null || path.getPath()==null || path.getPath().length==0){
					return;
				}
				
				DefaultMutableTreeNode lastElement=(DefaultMutableTreeNode)path.getLastPathComponent();
				District forRemove=(District)lastElement.getUserObject();
				
				if(JOptionPane.showConfirmDialog(DistrictWindow.this, "Are you sure for remove: "+forRemove, "Remove records", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					try {
						DistrictWindow.this.repository.delete(forRemove);
						refreshList();
					} catch (ServiceException ex) {
						JOptionPane.showMessageDialog(DistrictWindow.this, "Can't remove record: "+ex.getMessage());
					}
				}
			}
		});
		
		JPanel panelManager=new JPanel();
		panelManager.setLayout(new BorderLayout());
		panelManager.add(buttonAdd, BorderLayout.CENTER);
		panelManager.add(buttonRemove, BorderLayout.EAST);
		this.add(panelManager, BorderLayout.SOUTH);
	}

	
	private void fillNode(DefaultMutableTreeNode parentNode, District parentElement) throws ServiceException{
		List<District> rootList=finder.findAll(parentElement);
		if(rootList==null || rootList.size()==0){
			return;
		}
		
		for(District childElement:rootList){
			DefaultMutableTreeNode childNode=new DefaultMutableTreeNode(childElement);
			parentNode.add(childNode);
			fillNode(childNode, childElement);
		}
	}
	
	private void refreshList(){
		DefaultMutableTreeNode treeNode=new DefaultMutableTreeNode();
		
		try {
			fillNode(treeNode, null);
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog(this, "Service Error: "+e.getMessage());
			return;
		}
		
		this.tree.setModel(new DefaultTreeModel(treeNode));
		
	}
	
	@Override
	public void childWindowModalResult(Object value) {
		if(value!=null && (value instanceof District) ){
			try {
				District valueForSave=(District)value;
				checkForUnique(valueForSave);
				repository.create(valueForSave);
			} catch (RepeatInsertException e) {
				JOptionPane.showMessageDialog(this, "Check inserted value: "+value);
			} catch (ServiceException e) {
				JOptionPane.showMessageDialog(this, "service exception: "+e.getMessage());
			}
		}
		refreshList();
	}

	
	private void checkForUnique(District valueForSave) throws ServiceException{
		List<District> brothers=this.finder.findAll(valueForSave.getParent());
		if(brothers==null || brothers.size()==0){
			return;
		}
		for(District eachElement:brothers){
			if(eachElement.getName().equalsIgnoreCase(valueForSave.getName())){
				throw new RepeatInsertException();
			}
		}
	}


	private static class PanelEdit extends ModalPanel{
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unused")
		public static final int WIDTH=200;
		@SuppressWarnings("unused")
		public static final int HEIGHT=150;

		private District parent;
		
		public PanelEdit(District parentDistrict){
			super();
			this.parent=parentDistrict;
			init();
		}
		
		JTextField field;
		
		private void init(){
			this.setLayout(new GridLayout(2,1));
			field=new JTextField();
			field.setBorder(BorderFactory.createTitledBorder("Новый район"));
			this.add(field);
			
			JPanel panelManager=new JPanel();
			this.add(panelManager);
			panelManager.setLayout(new GridLayout(1,2));
			
			JButton buttonAdd=new JButton("Save");
			buttonAdd.setBorder(new EmptyBorder(5, 5, 5, 5));
			buttonAdd.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String valueFromField=StringUtils.trimToNull(field.getText());
					if(valueFromField==null){
						PanelEdit.this.closeModal();
					}
					District newElement=new District();
					newElement.setName(valueFromField);
					newElement.setParent(parent);
					PanelEdit.this.closeModal(newElement);
				}
			});
			panelManager.add(buttonAdd);
			
			JButton buttonRemove=new JButton("Cancel");
			buttonRemove.setBorder(new EmptyBorder(5, 5, 5, 5));
			buttonRemove.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					PanelEdit.this.closeModal(null);
				}
			});
			panelManager.add(buttonRemove);
		}
	}
}
