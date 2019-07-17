package gui;
import gui.table.ImageTableModel;
import gui.table.ImageTableRenderer;
import gui.table.Repaint;
import gui.table.RowElement;

import javax.swing.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import java.awt.event.*;
import java.awt.GridLayout;

import java.awt.BorderLayout;

public class EnterPoint extends JFrame implements Repaint{
	private static final long serialVersionUID = 1L;
	private Logger field_logger=Logger.getLogger(this.getClass().getName());
	{
		field_logger.setLevel(Level.DEBUG);
		if(!field_logger.getAllAppenders().hasMoreElements()){
			BasicConfigurator.configure();
		}
	}
	
	public static void main(String[] args){
		new EnterPoint();
	}
	
	
	private JTable field_table;
	private JScrollPane field_table_pane;
	
	public EnterPoint(){
		super("Table with image example ");
		field_logger.debug("EnterPoint:begin");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,600);
		this.getContentPane().add(initComponents());
		this.setVisible(true);
		field_logger.debug("EnterPoint:end");
	}
	
	private ImageTableModel field_table_model;
	
	private JPanel initComponents(){
		field_logger.debug("EnterPoint:begin");
		JPanel return_value=new JPanel();
		return_value.setLayout(new BorderLayout());

		field_logger.debug("table create:begin");
		{
			
			field_table=new JTable();
			field_table_pane=new JScrollPane(field_table);
			field_table_model=new ImageTableModel((Repaint)this);
			field_table.setModel(field_table_model);
			this.field_table.removeColumn(this.field_table.getColumn(this.field_table.getColumnName(this.field_table.convertColumnIndexToView(0))));
			field_table.setSelectionMode(JTable.AUTO_RESIZE_OFF);
			field_table.setRowSelectionAllowed(true);
			field_table.setRowHeight(150);
			ImageTableRenderer renderer=new ImageTableRenderer();
			field_table.setDefaultRenderer(RowElement.class, 
										   renderer
										   );
			field_table.setTableHeader(null);
			
/*			field_table.setDefaultRenderer(String.class, 
					   renderer
					   );
*/			
			field_logger.debug("table create:end");
		}
		
		JButton button_add=new JButton("add");
		JButton button_remove=new JButton("remove");
		
		button_add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_add();
			}
		});
		button_remove.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_remove();
			}
		});
		
		JPanel panel_manager=new JPanel(new GridLayout(1,2));
		panel_manager.add(button_add);
		panel_manager.add(button_remove);
		
		return_value.add(field_table_pane,BorderLayout.CENTER);
		return_value.add(panel_manager,BorderLayout.SOUTH);
		field_logger.debug("EnterPoint:end");
		return return_value;
	}
	
	private void on_button_add(){
		field_logger.debug("button_add:begin");
		this.field_table_model.addElement("d:\\temp_image\\first.bmp","d:\\temp_image\\second.bmp","d:\\temp_image\\third.bmp");
		//System.gc();
		field_logger.debug("button_add:end");
	}
	private void on_button_remove(){
		field_logger.debug("button_remove:begin");
		Runtime.getRuntime().gc();
		field_logger.debug("button_remove:end");
	}

	@Override
	public void repaintVisualElements() {
		field_logger.debug("repaint:begin");
		this.field_table.updateUI();
		
		/*
		this.field_table.repaint();
		this.field_table_pane.repaint();
		for(int counter=0;counter<this.getContentPane().getComponentCount();counter++){
			this.getComponent(counter).repaint();
		}
		this.repaint();
		*/
		field_logger.debug("repaint:end");
	}
}
