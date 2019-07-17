package com.cherkashyn.vitalii.indirector.workers.gui.dictionary;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.indirector.workers.domain.Category;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ListModel;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ModalPanel;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ModalResultListener;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.UIUtils;
import com.cherkashyn.vitalii.indirector.workers.service.exception.RepeatInsertException;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.category.CategoryFinder;
import com.cherkashyn.vitalii.indirector.workers.service.interf.category.CategoryRepository;


@Component
@Scope("prototype")
public class CategoryWindow extends ModalPanel implements ModalResultListener{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH=400;
	public static final int HEIGHT=300;
	
	JList<Category> list;
	{
		list=new JList<Category>();
		list.setCellRenderer(new DefaultListCellRenderer());
	}
	
	@Autowired
	CategoryFinder finder;
	
	@Autowired
	CategoryRepository repository;
	
	public CategoryWindow(){
	}
	
	@PostConstruct
	public void init() {
		this.setLayout(new BorderLayout());
		
		this.refreshList();
		this.add(list, BorderLayout.CENTER);
		
		JButton buttonAdd=new JButton("Add");
		buttonAdd.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIUtils.showModal(CategoryWindow.this, new PanelEdit());
			}
		});
		
		JButton buttonRemove=new JButton("Remove");
		buttonRemove.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(CategoryWindow.this.list.getSelectedIndex()<0){
					return ;
				}
				Category forRemove=CategoryWindow.this.list.getModel().getElementAt(CategoryWindow.this.list.getSelectedIndex());
				if(forRemove==null){
					return;
				}
				if(JOptionPane.showConfirmDialog(CategoryWindow.this, "Are you sure for remove: "+forRemove, "Remove records", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					try {
						CategoryWindow.this.repository.delete(forRemove);
						refreshList();
					} catch (ServiceException ex) {
						JOptionPane.showMessageDialog(CategoryWindow.this, "Can't remove record: "+ex.getMessage());
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

	private void refreshList(){
		List<Category> data=null;
		try {
			data = finder.findAll();
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog(this, "Service Error: "+e.getMessage());
			return;
		}
		list.setModel(new ListModel<Category>(data));
	}
	
	@Override
	public void childWindowModalResult(Object value) {
		if(value!=null && (value instanceof Category) ){
			try {
				repository.create((Category)value);
			} catch (RepeatInsertException e) {
				JOptionPane.showMessageDialog(this, "Check inserted value: "+value);
			} catch (ServiceException e) {
				JOptionPane.showMessageDialog(this, "service exception: "+e.getMessage());
			}
		}
		refreshList();
	}

	
	private static class PanelEdit extends ModalPanel{
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unused")
		public static final int WIDTH=200;
		@SuppressWarnings("unused")
		public static final int HEIGHT=150;

		public PanelEdit(){
			super();
			init();
		}
		
		JTextField field;
		
		private void init(){
			this.setLayout(new GridLayout(2,1));
			field=new JTextField();
			field.setBorder(BorderFactory.createTitledBorder("Новая категория навыков"));
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
					Category Category=new Category();
					Category.setName(valueFromField);
					PanelEdit.this.closeModal(Category);
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
