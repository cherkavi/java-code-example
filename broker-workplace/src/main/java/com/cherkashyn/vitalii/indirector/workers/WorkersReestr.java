package com.cherkashyn.vitalii.indirector.workers;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cherkashyn.vitalii.indirector.workers.gui.CallHistoryWindow;
import com.cherkashyn.vitalii.indirector.workers.gui.DictionariesWindow;
import com.cherkashyn.vitalii.indirector.workers.gui.WorkerListWindow;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ModalPanel;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.UIUtils;

public class WorkersReestr extends JFrame{
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
		
		new WorkersReestr(context).setVisible(true);
	}
 	
	
	private final static int WIDTH=200;
	private final static int HEIGHT=400;
	
	private ApplicationContext context;
	
	private ModalPanel mainPanel;
	
	public WorkersReestr(ApplicationContext context){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.context=context;
		initUI();
		UIUtils.setToCenterOfScreen(this);
	}

	private void initUI() {
		this.setSize(WIDTH, HEIGHT);
		
		mainPanel=new ModalPanel();
		this.add(mainPanel);
		mainPanel.setLayout(new GridLayout(3,1));
		
		JButton buttonWorkerList=new JButton("Реестр работников");
		buttonWorkerList.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonWorkersList();
			}
		});
		mainPanel.add(buttonWorkerList);
		
		JButton buttonCallToCandidate=new JButton("Звонок кандидату");
		buttonCallToCandidate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonCallHistory();
			}
		});
		mainPanel.add(buttonCallToCandidate);

		JButton buttonDictionaries=new JButton("Словари");
		buttonDictionaries.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonDictionaries();
			}
		});
		mainPanel.add(buttonDictionaries);

	}
	
	protected void onButtonCallHistory() {
		UIUtils.showModal(mainPanel, context.getBean(CallHistoryWindow.class));
	}

	protected void onButtonDictionaries() {
		UIUtils.showModal(mainPanel, context.getBean(DictionariesWindow.class));
	}

	private void onButtonWorkersList(){
		
		UIUtils.showModal(mainPanel, context.getBean(WorkerListWindow.class));
	}
	
}
