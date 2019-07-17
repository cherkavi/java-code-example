package jtree_example;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import database.Connector;

public class JTreeExample extends JFrame{
	private final static long serialVersionUID=1L;
	private JTreeView treeView;
	private JTreeModel treeModel;
	
	public static void main(String[] args){
		new JTreeExample();
	}
	
	public JTreeExample(){
		super("title");
		initComponents();
		this.setSize(300, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void initComponents(){
		try{
			// создать объекты 
			Connection connection=Connector.getConnection();
			this.treeModel=new JTreeModel();
			this.treeView=new JTreeView(this.treeModel, connection);
			this.treeView.updateModel();
			JButton buttonUp=new JButton("UP");
			JButton buttonDown=new JButton("DOWN");
			buttonUp.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					onButtonUp();
				}
			});
			buttonDown.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					onButtonDown();
				}
			});
			JPanel panelManager=new JPanel(new GridLayout(1,2));
			panelManager.add(buttonUp);
			panelManager.add(buttonDown);
			// прочитать дерево в объект 
			JPanel panel=new JPanel(new BorderLayout());
			panel.add(new JScrollPane(this.treeView));
			panel.add(panelManager, BorderLayout.SOUTH);
			this.getContentPane().add(panel);
			connection.close();
		}catch(Exception ex){
			System.err.println("JTreeExample#initComponents: "+ex.getMessage());
		}
	}
	
	private void onButtonUp(){
		this.treeView.selectionMoveUp();
	}
	
	private void onButtonDown(){
		this.treeView.selectionMoveDown();
	}
}
