package com.cherkashyn.vitalii.indirector.workers.gui.dictionary;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import com.cherkashyn.vitalii.indirector.workers.domain.Skill;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ListModel;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ModalPanel;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ModalResultListener;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.UIUtils;
import com.cherkashyn.vitalii.indirector.workers.service.exception.RepeatInsertException;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.category.CategoryFinder;
import com.cherkashyn.vitalii.indirector.workers.service.interf.skill.SkillFinder;
import com.cherkashyn.vitalii.indirector.workers.service.interf.skill.SkillRepository;


@Component
@Scope("prototype")
public class SkillWindow extends ModalPanel implements ModalResultListener{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH=400;
	public static final int HEIGHT=300;
	
	JComboBox<Category> comboboxCategory;
	{
		comboboxCategory=new JComboBox<Category>();
		comboboxCategory.setRenderer(new DefaultListCellRenderer());
		comboboxCategory.setBorder(BorderFactory.createTitledBorder(" Категория навыков/умений "));
		comboboxCategory.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SkillWindow.this.refreshList();
			}
		});
	}
	
	JList<Skill> list=new JList<Skill>();
	{
		list.setCellRenderer(new DefaultListCellRenderer());
	}
	
	@Autowired
	CategoryFinder finderCategory;
	
	@Autowired
	SkillFinder finderSkill;
	
	@Autowired
	SkillRepository repositorySkill;
	
	public SkillWindow(){
	}
	
	@PostConstruct
	public void init() {
		this.setLayout(new BorderLayout());
		this.refreshCategory();
		this.add(comboboxCategory, BorderLayout.NORTH);
		this.refreshList();
		this.add(list, BorderLayout.CENTER);
		
		JButton buttonAdd=new JButton("Add");
		buttonAdd.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIUtils.showModal(SkillWindow.this, new PanelEdit((Category)SkillWindow.this.comboboxCategory.getSelectedItem()));
			}
		});
		
		JButton buttonRemove=new JButton("Remove");
		buttonRemove.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(SkillWindow.this.list.getSelectedIndex()<0){
					return ;
				}
				Skill forRemove=SkillWindow.this.list.getModel().getElementAt(SkillWindow.this.list.getSelectedIndex());
				if(forRemove==null){
					return;
				}
				if(JOptionPane.showConfirmDialog(SkillWindow.this, "Are you sure for remove: "+forRemove, "Remove records", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					try {
						SkillWindow.this.repositorySkill.delete(forRemove);
						refreshList();
					} catch (ServiceException ex) {
						JOptionPane.showMessageDialog(SkillWindow.this, "Can't remove record: "+ex.getMessage());
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

	private void refreshCategory() {
		try {
			// read data and set to model
			this.comboboxCategory.setModel(new DefaultComboBoxModel<Category>(this.finderCategory.findAll().toArray(new Category[]{})));
			// set default value 
			this.comboboxCategory.getModel().setSelectedItem(this.comboboxCategory.getModel().getElementAt(0));
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog(this, "can't read data:"+e.getMessage());
		}
	}

	private void refreshList(){
		List<Skill> skills=null;
		try {
			skills = finderSkill.findAll((Category)this.comboboxCategory.getSelectedItem());
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog(this, "Service Error: "+e.getMessage());
			return;
		}
		list.setModel(new ListModel<Skill>(skills));
	}
	
	@Override
	public void childWindowModalResult(Object value) {
		if(value!=null && (value instanceof Skill) ){
			try {
				Skill forSave=(Skill)value;
				checkRepeat(forSave);
				repositorySkill.create(forSave);
			} catch (RepeatInsertException e) {
				JOptionPane.showMessageDialog(this, "Check inserted value: "+value);
			} catch (ServiceException e) {
				JOptionPane.showMessageDialog(this, "service exception: "+e.getMessage());
			}
		}
		refreshList();
	}

	
	private void checkRepeat(Skill forSave) throws RepeatInsertException{
		for(int index=0; index<this.list.getModel().getSize();index++){
			if(this.list.getModel().getElementAt(index).getName().equalsIgnoreCase(forSave.getName())){
				throw new RepeatInsertException("value is repeated");
			}
		}
		
	}


	private static class PanelEdit extends ModalPanel{
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unused")
		public static final int WIDTH=200;
		@SuppressWarnings("unused")
		public static final int HEIGHT=150;
		private Category category;

		public PanelEdit(Category category){
			super();
			this.category=category;
			init();
			
		}
		
		JTextField field;
		
		private void init(){
			this.setLayout(new GridLayout(2,1));
			field=new JTextField();
			field.setBorder(BorderFactory.createTitledBorder("Новый навык"));
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
					Skill skill=new Skill();
					skill.setCategory(PanelEdit.this.category);
					skill.setName(valueFromField);
					PanelEdit.this.closeModal(skill);
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
