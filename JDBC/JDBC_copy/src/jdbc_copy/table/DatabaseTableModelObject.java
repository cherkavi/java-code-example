package jdbc_copy.table;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import jdbc_copy.connection.IConnectionAware;

public class DatabaseTableModelObject extends JPanel{
	private final static long serialVersionUID=1L;
	private Table table;
	private Color colorFocused=new Color(178,178,178);
	private Color colorSelected=new Color(210,178,178);
	private Color colorDefault=new Color(178,178,178);
	private JCheckBox checkboxDropTable;
	private JCheckBox checkboxRemoveRecords;
	private JTextArea history;
	private IConnectionAware sourceConnection;
	private IConnectionAware destinationConnection;
	
	/** Объект для отображения на панели */
	public DatabaseTableModelObject(Table table, 
									IConnectionAware sourceConnection, 
									IConnectionAware destinationConnection){
		this.table=table;
		this.sourceConnection=sourceConnection;
		this.destinationConnection=destinationConnection;
		initComponents();
	}
	
	/** первоначальная инициализация компонентов */
	private void initComponents(){
		this.setBorder(javax.swing.BorderFactory.createTitledBorder(table.getName()));
		history=new JTextArea();
		JPanel panelHistory=new JPanel(new GridLayout(1,1));
		panelHistory.setBorder(javax.swing.BorderFactory.createTitledBorder("История"));
		panelHistory.add(new JScrollPane(history));
		
		JButton buttonCreate=new JButton("Создать");
		buttonCreate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonCreate();
			}
		});
		
		JButton buttonCopy=new JButton("Копировать");
		buttonCopy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonCopy();
			}
		});
		
		JButton buttonClearHistory=new JButton("Очистить историю");
		buttonClearHistory.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonClearHistory();
			}
		});
		
		checkboxDropTable=new JCheckBox("Предварительно удалить таблицу",false); 
		checkboxRemoveRecords=new JCheckBox("Предварительно удалить данные",false);
		
		GroupLayout groupLayout=new GroupLayout(this);
		this.setLayout(groupLayout);
		GroupLayout.SequentialGroup groupLayoutHorizontal=groupLayout.createSequentialGroup();
		GroupLayout.SequentialGroup groupLayoutVertical=groupLayout.createSequentialGroup();
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		groupLayout.setVerticalGroup(groupLayoutVertical);
		
		groupLayoutHorizontal.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
									   .addComponent(buttonCreate,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
									   .addComponent(checkboxDropTable,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
									   .addComponent(buttonCopy,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
									   .addComponent(checkboxRemoveRecords,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
									   .addComponent(buttonClearHistory,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
									   );
		groupLayoutHorizontal.addComponent(panelHistory);
		
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup()
									 	.addGroup(groupLayout.createSequentialGroup()
										     .addComponent(buttonCreate)
										     .addComponent(checkboxDropTable)
										     .addComponent(buttonCopy)
					     					 .addComponent(checkboxRemoveRecords)
					     					 .addComponent(buttonClearHistory)
									    )
									    .addComponent(panelHistory)
									);
		
	}
	
	public void onButtonCreate(){
		this.history.append("Create \n");
		Connection connection=this.destinationConnection.getConnection();
		try{
			if(this.checkboxDropTable.isSelected()){
				connection.createStatement().executeUpdate("DROP TABLE "+this.table.getName());
			}
			connection.createStatement().executeUpdate(this.table.getSqlCreate());
			this.history.append("Create ... OK \n");
		}catch(Exception ex){
			this.history.append("Exception: "+ex.getMessage()+"\n");
		}finally{
		}
		
	}
	
	public void onButtonCopy(){
		this.history.append("Copy "+this.table.getName()+"\n");
		Connection source=this.sourceConnection.getConnection();
		Connection destination=this.destinationConnection.getConnection();
		if(this.checkboxRemoveRecords.isSelected()){
			try{
				destination.createStatement().executeUpdate("DELETE FROM  "+this.table.getName());
			}catch(Exception ex){
				System.err.println("Exception:"+ex.getMessage());
				this.history.append("Copy Exception: "+ex.getMessage()+"\n");
			}
		}
		try{
			PreparedStatement insertQuery=destination.prepareStatement(this.table.getSqlPreparedInsert());
			ResultSet rs=source.createStatement().executeQuery("SELECT * FROM "+this.table.getName());
			while(rs.next()){
				insertQuery.clearParameters();
				System.out.println("fill parameter:"+rs.getString(1));
				this.table.fillParameters(insertQuery,rs);
				System.out.println("execute update:"+insertQuery);
				insertQuery.executeUpdate();
			}
			insertQuery.close();
			rs.getStatement().close();
			this.history.append("Copy is Done\n");
			System.out.println(table.getName()+" copy is Done");
		}catch(Exception ex){
			System.err.println("Copy Exception:"+ex.getMessage());
			this.history.append("Copy Exception: "+ex.getMessage()+"\n");
		}
	}
	
	public void onButtonClearHistory(){
		this.history.setText("");
	}
	
	/** установить данный объект как выделенный*/
	public void setSelected(boolean isSelected){
		if(isSelected){
			this.setBackground(colorSelected);
		}else{
			this.setBackground(colorDefault);
		}
	}

	public void setFocused(boolean hasFocused) {
		if(hasFocused){
			this.setBackground(colorFocused);
		}else{
			this.setBackground(colorDefault);
		}
	}
}
