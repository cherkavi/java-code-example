package com.cherkashyn.vitalii.indirector.workers.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.indirector.workers.gui.dictionary.AssetWindow;
import com.cherkashyn.vitalii.indirector.workers.gui.dictionary.CategoryWindow;
import com.cherkashyn.vitalii.indirector.workers.gui.dictionary.DistrictWindow;
import com.cherkashyn.vitalii.indirector.workers.gui.dictionary.PhoneAttemptResultWindow;
import com.cherkashyn.vitalii.indirector.workers.gui.dictionary.SkillWindow;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ModalPanel;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.UIUtils;


@Component
public class DictionariesWindow extends ModalPanel{

	public static final int WIDTH=250;
	public static final int HEIGHT=150;

	private static final long serialVersionUID = 1L;
	
	@Autowired
	ApplicationContext context;
	
	
	public DictionariesWindow(){
		super();
	}

	@PostConstruct
	private void init() {
		this.setLayout(new BorderLayout());
		this.add(createPanelDictionaries());
	}

	private JPanel createPanelDictionaries() {
		final ModalPanel panel=new ModalPanel();
		panel.setLayout(new GridLayout(5,1));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JButton btnAssets = new JButton("Доп.возможности");
		btnAssets.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				UIUtils.showModal(panel, context.getBean(AssetWindow.class), false);
			}
		});
		panel.add(btnAssets);
		
		JButton btnDistrict = new JButton("Район");
		btnDistrict.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				UIUtils.showModal(panel, context.getBean(DistrictWindow.class), false);
			}
		});
		panel.add(btnDistrict);
		
		
		JButton btnCategory = new JButton("Категории навыков");
		btnCategory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				UIUtils.showModal(panel, context.getBean(CategoryWindow.class), false);
			}
		});
		panel.add(btnCategory);

		JButton btnSkills = new JButton("Умения/навыки");
		btnSkills.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				UIUtils.showModal(panel, context.getBean(SkillWindow.class), false);
			}
		});
		panel.add(btnSkills);

		JButton btnResolution = new JButton("Результат тел. звонка");
		btnResolution.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				UIUtils.showModal(panel, context.getBean(PhoneAttemptResultWindow.class), false);
			}
		});
		panel.add(btnResolution);

		return panel;
	}

	
}
