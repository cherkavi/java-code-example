package com.cherkashyn.vitalii.indirector.workers.gui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.MessageFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.indirector.workers.domain.Worker;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ListModel;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ModalPanel;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ModalResultListener;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.UIUtils;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.worker.WorkerFinder;
import com.cherkashyn.vitalii.indirector.workers.service.interf.worker.WorkerRepository;

@Component
public class WorkerListWindow extends ModalPanel implements ModalResultListener{

	public static final int WIDTH=850;
	public static final int HEIGHT=600;
	
	private static final long serialVersionUID = 1L;
	private static final int VISIBLE_WORKERS_COUNT=10;
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	WorkerFinder finder;

	@Autowired
	WorkerRepository repository;
	
	/**
	 * Create the application.
	 */
	public WorkerListWindow() {
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	@PostConstruct
	public void initialize() {
		
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.setLayout(new BorderLayout());
		this.add(createPanelSearch(), BorderLayout.NORTH);
		this.add(createPanelGrid(), BorderLayout.CENTER);
		this.add(createPanelGridManager(), BorderLayout.SOUTH);
		try {
			this.refreshList();
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog(this, "can't show records: "+e.getMessage());
		}
	}

	

	/**
	 * create panel with button Search
	 * @return
	 */
	private JPanel createPanelSearch() {
		final JTextField searchField=new JTextField();
		JButton buttonSearch=new JButton("search");
		buttonSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					refreshList(searchField.getText().trim());
				} catch (ServiceException ex) {
					JOptionPane.showMessageDialog(WorkerListWindow.this, "can't update list \n "+ex.getMessage());
				}
			}
		});
		
		JPanel panelSearch=new JPanel(new BorderLayout());
		panelSearch.add(new JLabel("Строка поиска:"), BorderLayout.WEST);
		panelSearch.add(searchField, BorderLayout.CENTER);
		panelSearch.add(buttonSearch, BorderLayout.EAST);
		
		return panelSearch;
	}


	private JPanel createPanelGridManager() {
		final ModalPanel panel=new ModalPanel();
		panel.setLayout(new BorderLayout());

		JButton buttonAdd = new JButton("Добавить");
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				WorkerWindow workerWindow=context.getBean(WorkerWindow.class);
				workerWindow.setModalResultListener(WorkerListWindow.this);
				UIUtils.showModal(panel, workerWindow, false);
			}
		});
		panel.add(buttonAdd, BorderLayout.CENTER);
		
		JButton buttonUpdate = new JButton("Редактировать");
		buttonUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				onButtonEditClick(event);
			}
		});
		panel.add(buttonUpdate, BorderLayout.WEST);
		
		JButton buttonRemove = new JButton("Удалить");
		buttonRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Worker forRemove=workerList.getSelectedValue();
				if(forRemove==null){
					return;
				}
				if(JOptionPane.showConfirmDialog(
							WorkerListWindow.this, 
							MessageFormat.format("Запись будет удалена: {0} {1} {2}",  forRemove.getSurname(), forRemove.getName(), forRemove.getFathername()),
							"Предупреждение",
							JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION){
					return;
				}
				try {
					repository.delete(forRemove);
				} catch (ServiceException e) {
					JOptionPane.showMessageDialog(WorkerListWindow.this, "Не могу обновить список: \n"+e.getMessage());
				}
				try {
					refreshList();
				} catch (ServiceException e) {
					JOptionPane.showMessageDialog(WorkerListWindow.this, "Запись была удалена, но не могу обновить список:\n"+e.getMessage());
				}
			}
		});
		panel.add(buttonRemove, BorderLayout.EAST);

		return panel;
	}


	protected void onButtonEditClick(AWTEvent event) {
		if(WorkerListWindow.this.workerList.getSelectedIndex()<0){
			return;
		}
		WorkerWindow workerWindow=context.getBean(WorkerWindow.class);
		try {
			workerWindow.setWorker(WorkerListWindow.this.workerList.getSelectedValue());
			workerWindow.setModalResultListener(WorkerListWindow.this);
			UIUtils.showModal(this, workerWindow, false);
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog(WorkerListWindow.this, "Не могу прочесть данные из источника:"+e.getMessage());
		}
		
	}


	JList<Worker> workerList=new JList<Worker>();
	{
		workerList.addMouseListener(new MouseAdapter(){
			 @Override
			public void mouseClicked(MouseEvent event) {
				if(event.getClickCount()>1){
					onButtonEditClick(event);
				}
			}
		});
	}

	private JPanel createPanelGrid() {
		JPanel panel=new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder("Last 10 workers"));
		
		workerList.setCellRenderer(new ListCellRenderer<Worker>() {
			@Override
			public java.awt.Component getListCellRendererComponent(JList<? extends Worker> list, 
																   Worker value, int index,
																   boolean isSelected, 
																   boolean cellHasFocus) {
				JLabel returnValue=new JLabel();
				returnValue.setText(value.getSurname()+"   "+value.getName());
				returnValue.setBackground(Color.WHITE);
				returnValue.setForeground(Color.BLACK);
				returnValue.setOpaque(true);
				
				if(cellHasFocus){
					returnValue.setBackground(Color.BLUE);
					returnValue.setForeground(Color.WHITE);
				}
				if(isSelected){
					returnValue.setBackground(Color.LIGHT_GRAY);
					returnValue.setForeground(Color.WHITE);
				}
				return returnValue;
			}
		});
		panel.add(workerList, BorderLayout.CENTER);
		
		return panel;
	}

	private void refreshList() throws ServiceException {
		refreshList(null);
	}
	
	private void refreshList(String searchValue) throws ServiceException {
		List<Worker> workers=null;
		if(searchValue==null){
			workers=this.finder.findLast(VISIBLE_WORKERS_COUNT);	
		}else{
			workers=this.finder.find(searchValue, VISIBLE_WORKERS_COUNT);
		}
		this.workerList.setModel(new ListModel<Worker>(workers));
	}


	@Override
	public void childWindowModalResult(Object value) {
		if(value instanceof ModalResultListener.Result){
			// check result 
			ModalResultListener.Result result=(ModalResultListener.Result)value;
			switch(result){
			case Ok:{
					try {
						refreshList();
					} catch (ServiceException e) {
						JOptionPane.showMessageDialog(this, "can't update list \n "+e.getMessage());
					}
					}; break;
			case Cancel:{
					System.out.println("Cancel");
					};break; 
			}
			
		}
		
	}

}
