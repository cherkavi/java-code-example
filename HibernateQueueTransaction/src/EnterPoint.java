import javax.swing.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

import database.Connector;
import database.Event;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * Данный проект предназначен для отображения работы Hibernate с Transaction,
 * при чем подтверждает тот факт, что для Session в Hibernate работает только одна транзакция,
 * то есть нельзя сделать одновременно много транзакций ( одну над другой) а потом в 
 * случайном порядке их закрывать<br>
 *  при нажатии на кнопку Add стартует транзакция и добавляется в pool, добавляется значение в таблицу, 
 *  при нажатии на кнопку Return - rollback по последней транзакции ( которая отменяет все транзакции, а не только себя)
 *  ( то есть при старте многих транзакций, замыкание хотя-бы одной черевато замыканием всех, равно как и RollBack) 
 * @author First
 */

public class EnterPoint extends JFrame{
	private final static long serialVersionUID=1L;
	private Session session;
	private DataTableModel tableModel;
	//private LinkedList<Transaction> transactions=new LinkedList<Transaction>();
	private LinkedList<Savepoint> transactions=new LinkedList<Savepoint>();
	private int transactionLength=4;
	public static void main(String[] args){
		new EnterPoint();
	}
	
	public EnterPoint(){
		super("Hibernate Queue Test");
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		session=Connector.getSession();
		initComponents();
		setVisible(true);
	}
	
	private void initComponents(){
		JButton buttonAdd=new JButton("Add");
		JButton buttonReturn=new JButton("Return");

		JButton buttonCommit=new JButton("Commit");
		JButton buttonRollback=new JButton("RollbackAll");
		
		// add listener's
		buttonAdd.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonAdd();
			}
		});
		buttonReturn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonReturn();
			}
		});
		buttonCommit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonCommit();
			}
		});
		buttonRollback.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonRollback();
			}
		});
		
		// placing components
		JPanel panelManager=new JPanel(new GridLayout(4,1));
		panelManager.add(buttonAdd);
		panelManager.add(buttonReturn);
		panelManager.add(buttonCommit);
		panelManager.add(buttonRollback);
		
		
		JPanel panelInformation=new JPanel(new GridLayout(1,1));
		
		this.tableModel=new DataTableModel(session);
		panelInformation.add(new JScrollPane(new JTable(this.tableModel)));
		
		JPanel panelMain=new JPanel(new BorderLayout());
		panelMain.add(panelManager,BorderLayout.NORTH);
		panelMain.add(panelInformation,BorderLayout.CENTER);
		
		this.getContentPane().add(panelMain);
	}
	
	
	private void onButtonAdd(){
		this.addNewRecordWithStartTransaction();
		this.refresh();
	}
	
	
	private void onButtonReturn(){
		rollbackToLast();
		this.refresh();
	}

	
	private void onButtonCommit(){
		commitAll();
		this.refresh();
	}
	
	
	private void onButtonRollback(){
		rollbackAll();
		this.refresh();
	}
	
	private void refresh(){
		this.tableModel.refresh(session);
	}
	
	private void debug(Object information){
		StackTraceElement element=(new Throwable()).getStackTrace()[1];
		System.out.print(element.getClassName()+" # "+element.getMethodName());
		System.out.print(": DEBUG ");
		System.out.println(information);
	}
	
	
	/** 
	 * add record to table
	 */
	private String addNewRecordWithStartTransaction(){
		try{
			Connection connection=this.session.connection();
			Statement statement=connection.createStatement();
			ResultSet rs=statement.executeQuery("select gen_id(gen_event_id,1) from rdb$database");
			rs.next();
			String uniqueId=rs.getString(1);
			this.transactions.addFirst(connection.setSavepoint(uniqueId));
			statement.execute("insert into Event(id, filename) values("+uniqueId+",'"+uniqueId+"')");
			return uniqueId;
		}catch(Exception ex){
			debug("Exception: "+ex.getMessage());
			return null;
		}
		
		/** HIBERNATE */
		/*
		Event newEvent=new Event();
		newEvent.setFilename("this is file");
		session.save(newEvent);
		
		Transaction transaction=this.session.beginTransaction();
		if(this.transactions.size()>=this.transactionLength){
			while(this.transactions.size()>=this.transactionLength){
				Transaction transactionLast=this.transactions.removeLast();
				debug("Commit last: "+transactionLast);
				try{
					transactionLast.commit();
				}catch(Exception ex){
					debug("ERROR "+ex.getMessage());
				}
			}
		}
		this.transactions.addFirst(transaction);
		*/
	}
	
	private void rollbackAll(){
		try{
			this.session.connection().rollback();
			this.transactions.clear();
		}catch(Exception ex){
			debug("Exception: "+ex.getMessage());
		}
		// HIBERNATE
/*		while(this.transactions.size()>0){
			Transaction transaction=this.transactions.removeLast();
			debug(transaction);
			transaction.rollback();
		}
*/
	}

	private void commitAll(){
		try{
			this.session.connection().commit();
			this.transactions.clear();
		}catch(Exception ex){
			debug("Exception: "+ex.getMessage());
		}
		
		// HIBERNATE
		/*
		while(this.transactions.size()>0){
			Transaction transaction=this.transactions.removeLast();
			debug(transaction);
			transaction.commit();
		}
		*/
	}

	private void rollbackToLast(){
		if(transactions.size()>0){
			Savepoint id=this.transactions.removeFirst();
			try{
				this.session.connection().rollback(id);
			}catch(Exception ex){
				debug("Exception: "+ex.getMessage());
			}
		}
		
		
		// HIBERNATE
		/*
		if(transactions.size()>0){
			Transaction transaction=this.transactions.removeFirst();
			debug(transaction);
			transaction.rollback();
		}
		*/
	}
	
}
