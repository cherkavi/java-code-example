package jdbc_migration.container;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/** панель, которая содержит текст SQL запроса, кнопку удаление и кнопку экспорта данных*/
public class PanelElement extends JPanel{
	private final static long serialVersionUID=1L;
	private IPanelContainerManager manager;
	/** текст запроса  */
	private JTextArea query;
	/** имя таблицы  */
	private JTextField tableName;
	/** флаг,который говорит про удаление всех элементов из таблицы */
	private JCheckBox deleteFromTable;
	
	/** панель, которая содержит текст SQL запроса, кнопку удаление и кнопку экспорта данных*/
	public PanelElement(IPanelContainerManager containerManager){
		this.manager=containerManager;
		this.initComponents();
	}

	/** панель, которая содержит текст SQL запроса, кнопку удаление и кнопку экспорта данных
	 * @param containerManager - компонент, который владеет необходимыми данных для работы - соединением с базой данных и путём к файлу 
	 * @param query - SQL запрос, который нужно "положить" в визуальный компонент  
	 * @param isDeleteFromTable - установка флага предварительного удаления данных из таблицы 
	 */
	public PanelElement(IPanelContainerManager containerManager, String query, boolean isDeleteFromTable){
		this.manager=containerManager;
		this.initComponents();
		this.query.setText(query);
		this.deleteFromTable.setSelected(isDeleteFromTable);
	}

	/** инициализация компонентов */
	private void initComponents(){
		JButton buttonDelete=new JButton("Удалить");
		buttonDelete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				onButtonDelete();
			}
		});
		JButton buttonExport=new JButton("Экспортировать");
		buttonExport.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				onButtonExport();
			}
		});
		JButton buttonCheck=new JButton("Проверить");
		buttonCheck.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonCheck();
			}
		});
		this.tableName=new JTextField();
		this.tableName.setBorder(javax.swing.BorderFactory.createTitledBorder("Имя таблицы в приемнике"));
		this.tableName.addKeyListener(new KeyAdapter(){
			@Override
			public void keyReleased(KeyEvent e) {
				if(PanelElement.this.tableName.getText().length()>0){
					PanelElement.this.tableName.setBackground(Color.gray);
				}else{
					PanelElement.this.tableName.setBackground(Color.white);
				}
			}
		});
		
		JButton buttonDestination=new JButton("Залить в приёмник");
		buttonDestination.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonDestination();
			}
		});
		deleteFromTable=new JCheckBox("Предварительное удаление данных из таблицы");
		
		JPanel panelManager=new JPanel();
		GroupLayout groupLayout=new GroupLayout(panelManager);
		panelManager.setLayout(groupLayout);
		GroupLayout.SequentialGroup groupLayoutVertical=groupLayout.createSequentialGroup();
		GroupLayout.SequentialGroup groupLayoutHorizontal=groupLayout.createSequentialGroup();
		groupLayout.setVerticalGroup(groupLayoutVertical);
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		
		groupLayoutHorizontal.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
									 .addGroup(groupLayout.createSequentialGroup()
											   .addComponent(buttonCheck)
											   .addComponent(buttonExport)
											   .addComponent(tableName)
											   )
								     .addComponent(deleteFromTable)
								     .addComponent(buttonDestination)
									 );
		groupLayoutHorizontal.addComponent(buttonDelete);
		
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
									   .addGroup(groupLayout.createSequentialGroup()
											   	 .addGroup(groupLayout.createParallelGroup()
											   			 .addComponent(buttonCheck)
											   			 .addComponent(buttonExport)
											   			 .addComponent(tableName)
											   			   )
											   	 .addComponent(deleteFromTable)
											   	 .addComponent(buttonDestination)
											   	 )
									   .addComponent(buttonDelete)
									   );

		panelManager.add(buttonCheck);
		panelManager.add(buttonExport);
		panelManager.add(buttonDelete);
		panelManager.add(deleteFromTable);
		
		this.query=new JTextArea(5,20);
		
		this.setLayout(new BorderLayout());
		this.add(this.query,BorderLayout.CENTER);
		this.add(panelManager,BorderLayout.EAST);
		this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.black));
	}

	/** реакция на нажатие клавиши Delete */
	private void onButtonDelete(){
		this.manager.removeElement(this);
	}
	
	/** реакция на нажатие клавиши экспорт */
	private void onButtonExport(){
		String checkResult=this.check();
		if(checkResult==null){
			this.clearAlert();
			String returnValue=this.export();
			if(returnValue!=null){
				JOptionPane.showMessageDialog(this, returnValue,"Error",JOptionPane.ERROR_MESSAGE);
			}else{
				JOptionPane.showMessageDialog(this, "Export done");
			}
		}else{
			this.setAlert();
			JOptionPane.showMessageDialog(this, "Ошибка во время проверки данных/n"+checkResult,"Ошибка",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/** реакция на нажатие кнопки проверка */
	private void onButtonCheck(){
		String checkResult=this.check();
		if(checkResult!=null){
			this.setAlert();
			JOptionPane.showMessageDialog(this, checkResult, "Ошибка", JOptionPane.ERROR_MESSAGE);
		}else{
			this.clearAlert();
			JOptionPane.showMessageDialog(this, "OK");
		}
	}
	
	/** проверить введённый запрос
	 * @return
	 * <li> null - все прошло успешно </li>
	 * <li> not null - ошибка во время проверки, текст ошибки возвращается </li>
	 * */
	public String check(){
		String returnValue=null;
		Connection connection=this.manager.getImportConnection();
		ResultSet rs=null;
		try{
			rs=connection.createStatement().executeQuery(this.query.getText());
		}catch(NullPointerException nex){
			returnValue="Проверьте соединение с базой данных";
		}catch(Exception ex){
			returnValue="Error:"+ex.getMessage();
		}finally{
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	/** установить/обозначить данный элемент как ошибочный */
	public void setAlert(){
		this.query.setBackground(new Color(200,0,0));		
	}
	
	/** снять обозначение с данного элемента как с ошибочного */
	public void clearAlert(){
		this.query.setBackground(new Color(255,255,255));
	}
	
	private SimpleDateFormat formatTime=new SimpleDateFormat("HH:mm:ss");
	private SimpleDateFormat formatDate=new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat formatDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/** возвращает ошибку, или null в качестве положительного результата 
	 * @param addRemoveFromTable - нужно ли добавлять команду удаления всего содержимого таблицы 
	 * @return 
	 */
	public String export(){
		/** нужно ли добавлять строку-команду для предварительного удаления всех элементов из таблицы */
		boolean addRemoveFromTable=this.deleteFromTable.isSelected();
		String returnValue=null;
		/** флаг создания нового PrintWriter только для данного экспорта */
		boolean createNew=false;
		try{
			PrintWriter printWriter=this.manager.getExportWriter();
			if(printWriter==null){
				printWriter=this.manager.getExportWriterNew();
				createNew=true;
			}
			printWriter.write("\n");
			printWriter.write("/* ----- \n ");
			printWriter.write(this.query.getText());
			printWriter.write("*/ \n");
			if(addRemoveFromTable==true){
				printWriter.write("DELETE FROM '"+this.getTableNameFromQuery(this.query.getText())+"';");
				printWriter.write("\n");
			}
			printWriter.write("\n");
			/** отработать запрос, собрать */
			ResultSet rs=null;
			try{
				rs=this.manager.getImportConnection().createStatement().executeQuery(this.query.getText());
				ResultSetMetaData rsmd=rs.getMetaData();
				int columnCount=rsmd.getColumnCount();
				StringBuffer header=new StringBuffer();
				header.append("insert into '"+this.getTableNameFromQuery(this.query.getText())+"' (");
				for(int counter=1;counter<=columnCount;counter++){
					//header.append("'");
					header.append(rsmd.getColumnName(counter));
					//header.append("'");
					if(counter!=rsmd.getColumnCount()){
						header.append(", ");
					}
				}
				header.append(") values ");

				String sqlHeader=header.toString();
				while(rs.next()){
					printWriter.write(sqlHeader);
					
					printWriter.write("(");
					for(int counter=1;counter<=columnCount;counter++){
						Object object=rs.getObject(counter);
						if(object==null){
							printWriter.write("NULL");
						}else if(object instanceof Number){
							printWriter.write(object.toString());
						}else if(object instanceof String){
							printWriter.write("'");
							String tempString=object.toString().replaceAll("(['\"])", "\\$1");
							//printWriter.write(new String(tempString.getBytes("UTF-8")));
							printWriter.write(tempString);
							printWriter.write("'");
						}else if(object instanceof java.sql.Time){
							printWriter.write("'");
							printWriter.write(formatTime.format(new java.util.Date( ((java.sql.Time)object).getTime()) ));
							printWriter.write("'");
						}else if(object instanceof java.sql.Timestamp){
							printWriter.write("'");
							printWriter.write(formatDateTime.format(new java.util.Date( ((java.sql.Timestamp)object).getTime()) ));
							printWriter.write("'");
						
						}else if(object instanceof java.sql.Date){
							printWriter.write("'");
							printWriter.write(formatDate.format(new java.util.Date( ((java.sql.Date)object).getTime()) ));
							printWriter.write("'");
						}else{
							printWriter.write(object.toString());
						}
						if(counter!=columnCount){
							printWriter.write(", ");
						}
					}
					printWriter.write("); \n");
				}
			}catch(Exception ex){
				printWriter.write("-- ERROR\n");
				returnValue=ex.getMessage();
			}finally{
				try{
					rs.getStatement().close();
				}catch(Exception ex){};
			}
			
		}catch(NullPointerException nex){
			returnValue="Проверьте имя выходного файла, либо же наличия соединения с базой данных";
		}catch(Exception ex){
			System.err.println("PanelElement#export Exception:"+ex.getMessage());
			returnValue=ex.getMessage();
		}finally{
			if(createNew==true){
				this.manager.closeExportWriter();
			}
		}
		return returnValue;
	}
	
	/** получить имя таблицы на основании запроса - вынимаем следующее слово после FROM или же получением из поля имя таблицы, если не пустое */
	private String getTableNameFromQuery(String query){
		if((this.tableName.getText()!=null)&&(!this.tableName.getText().trim().equals(""))){
			return this.tableName.getText();
		}else{
			String returnValue="";
			StringTokenizer values=new StringTokenizer(query);
			while(values.hasMoreElements()){
				String currentElement=(String)values.nextElement();
				if(currentElement.trim().equalsIgnoreCase("FROM")){
					returnValue=(String)values.nextElement();
					break;
				}
			}
			return returnValue;
		}
	}
	
	public String getQuery(){
		return this.query.getText();
	}

	/** установлен ли флаг удаления данных перед их вставкой в таблицу */
	public boolean isDeleteBeforeInsert() {
		return this.deleteFromTable.isSelected();
	}
	
	private void onButtonDestination(){
		String result=destination();
		if(result!=null){
			JOptionPane.showMessageDialog(this, result,"Ошибка",JOptionPane.ERROR_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(this,"OK");
		}
	}
	
	/** выполнить Update запрос 
	 * @param connection - соединение с базой данных 
	 * @param query - Update запрос, который необходимо выполнить 
	 * @return null - если все прошло успешно, либо же текст ошибки 
	 */
	private String executeUpdate(Connection connection, String query){
		String returnValue=null;
		Statement statement=null;
		try{
			statement=connection.createStatement();
			statement.executeUpdate(query);
		}catch(Exception ex){
			System.err.println("PanelElement#executeUpdate Exception: "+ex.getMessage());
		}finally{
			try{
				statement.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	/** перелить данные из источника в приемник 
	 * @return not null if Error
	 * */
	public String destination(){
		/** нужно ли добавлять строку-команду для предварительного удаления всех элементов из таблицы */
		boolean addRemoveFromTable=this.deleteFromTable.isSelected();
		String returnValue=null;
		Connection sourceConnection=null;
		Connection destinationConnection=null;
		/** флаг создания нового PrintWriter только для данного экспорта */
		boolean createNew=false;
		try{
			sourceConnection=this.manager.getImportConnection();
			destinationConnection=this.manager.getExportConnection();

			if(addRemoveFromTable==true){
				String result=this.executeUpdate(destinationConnection, "DELETE FROM "+this.getTableNameFromQuery(this.query.getText())+"");
				if(result!=null){
					throw new Exception(result);
				}
				destinationConnection.commit();
			}
			/** отработать запрос к источнику */
			ResultSet rs=null;
			PreparedStatement insert=null;
			try{
				rs=sourceConnection.createStatement().executeQuery(this.query.getText());
				ResultSetMetaData rsmd=rs.getMetaData();
				int columnCount=rsmd.getColumnCount();
				StringBuffer header=new StringBuffer();
				StringBuffer tail=new StringBuffer();
				header.append("insert into "+this.getTableNameFromQuery(this.query.getText())+" (");
				for(int counter=1;counter<=columnCount;counter++){
					//header.append("'");
					header.append(rsmd.getColumnName(counter));
					//header.append("'");
					tail.append("?");
					if(counter!=rsmd.getColumnCount()){
						header.append(", ");
						tail.append(", ");
					}
				}
				header.append(") values \n");
				header.append("("+tail.toString()+")");
				
				System.out.println("Query: "+header.toString());
				
				insert=destinationConnection.prepareStatement(header.toString());
				
				while(rs.next()){
					insert.clearParameters();
					for(int counter=1;counter<=columnCount;counter++){
						insert.setObject(counter, rs.getObject(counter));
					}
					insert.executeUpdate();
					destinationConnection.commit();
				}
			}catch(Exception ex){
				returnValue=ex.getMessage();
			}finally{
				try{
					rs.getStatement().close();
				}catch(Exception ex){};
				try{
					insert.close();
				}catch(Exception ex){};
			}
		}catch(NullPointerException nex){
			returnValue="Проверьте имя выходного файла, либо же наличия соединения с базой данных";
		}catch(Exception ex){
			System.err.println("PanelElement#export Exception:"+ex.getMessage());
			returnValue=ex.getMessage();
		}finally{
			if(createNew==true){
				this.manager.closeExportWriter();
			}
		}
		return returnValue;
	}
	
}
