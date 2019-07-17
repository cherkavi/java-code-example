package com.cherkashyn.vitalii.barrette.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cherkashyn.vitalii.barrette.gui.editor.AssortmentWindow;
import com.cherkashyn.vitalii.barrette.gui.editor.CurrencyWindow;
import com.cherkashyn.vitalii.barrette.gui.swing_utility.ModalPanel;
import com.cherkashyn.vitalii.barrette.gui.swing_utility.UIUtils;


public class MainForm extends JFrame{
	private final static long serialVersionUID=1L;
	
	private final static String SPRING_CONTEXT_PATH="applicationContext.xml";
	
	/** Enter point for application */
	public static void main(String[] args){
		ApplicationContext context=null;
		if(args.length>0){
			context=new FileSystemXmlApplicationContext(args[0]);
		}else{
			context=new ClassPathXmlApplicationContext(SPRING_CONTEXT_PATH);
		}
		
		new MainForm(context).setVisible(true);
	}
 	
	
	private final static int WIDTH=550;
	private final static int HEIGHT=100;
	
	private ApplicationContext context;
	
	private ModalPanel mainPanel;
	
	public MainForm(ApplicationContext context){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.context=context;
		initUI();
		UIUtils.setToCenterOfScreen(this);
	}

	
	private void initUI() {
		this.setSize(WIDTH, HEIGHT);
		
		mainPanel=new ModalPanel();
		this.add(mainPanel);
		mainPanel.setLayout(new GridLayout(2,1));
		
		JButton buttonAssortment=new JButton("Создать новый ассортимент");
		mainPanel.add(buttonAssortment);
		buttonAssortment.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonAssortment();
			}
		});
		
		JButton buttonAssortmentList=new JButton("Ассортимент список");
		mainPanel.add(buttonAssortmentList);
		buttonAssortmentList.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonAssortmentList();
			}
		});

		JButton buttonCurrency=new JButton("Курс валюты");
		mainPanel.add(buttonCurrency);
		buttonCurrency.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonCurrency();
			}
		});
		
		JButton buttonStore=new JButton("Склад");
		mainPanel.add(buttonStore);
		buttonStore.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonStore();
			}
		});

	}
	
	private void onButtonCurrency() {
		UIUtils.showModal(mainPanel, context.getBean(CurrencyWindow.class));
	}

	private void onButtonStore() {
		// UIUtils.showModal(mainPanel, context.getBean(DictionariesWindow.class));
	}

	private void onButtonAssortmentList(){
	}
	
	private void onButtonAssortment(){
		UIUtils.showModal(mainPanel, context.getBean(AssortmentWindow.class));
	}
}
