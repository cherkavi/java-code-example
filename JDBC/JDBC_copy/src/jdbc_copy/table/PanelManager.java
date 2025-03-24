package jdbc_copy.table;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import jdbc_copy.connection.PanelConnection;

/** панель для отображения управления существующими таблицами в базе-источнике и интерфейса их представления в базе-приемнике */
public class PanelManager extends JPanel{
	private final static long serialVersionUID=1L;
	private JButton buttonCreateTable;
	private JButton buttonCopyData;
	private PanelConnection source;
	private PanelConnection destination;
	private JButton buttonScan;
	/** модель, которая содержит необходимые таблицы в базе данных */
	private DatabaseTableModel rowModel;
	private JButton buttonClearHistory;
	private JTable table;
	
	/** панель для отображения управления существующими таблицами в базе-источнике и интерфейса их представления в базе-приемнике */
	public PanelManager(PanelConnection source, PanelConnection destination){
		this.setBorder(javax.swing.BorderFactory.createTitledBorder("Управление "));
		this.source=source;
		this.destination=destination;
		this.initComponents();
	}
	
	private void initComponents(){
		// create element's
		this.buttonScan=new JButton("Сканирование");
		this.buttonScan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonScan();
			}
		});
		
		this.buttonCreateTable=new JButton("Создание таблиц");
		this.buttonCreateTable.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				onButtonCreate();
			}
		});
		
		this.buttonCopyData=new JButton("Копирование данных");
		this.buttonCopyData.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonCopyData();
			}
		});
		
		this.buttonClearHistory=new JButton("Очистить логи");
		this.buttonClearHistory.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonClearHistory();
			}
		});

		this.rowModel=new DatabaseTableModel(this.source,this.destination);
		this.table=new JTable(this.rowModel);
		table.setRowHeight(150);
		table.setDefaultRenderer(Object.class, new DatabaseTableRenderer());
		table.setDefaultEditor(Object.class, new DatabaseTableEditor());
		
		// placing elements
		JPanel panelManager=new JPanel();
		panelManager.add(buttonScan);
		panelManager.add(buttonCreateTable);
		panelManager.add(buttonCopyData);
		panelManager.add(buttonClearHistory);
		
		this.setLayout(new BorderLayout());
		this.add(panelManager,BorderLayout.NORTH);
		this.add(new JScrollPane(table));
		
	}
	
	/** создать таблицы в базе приемнике по подобию базы источника */
	private void onButtonCreate(){
		if((this.source.getConnection()!=null)&&(this.destination.getConnection()!=null)){
			for(int counter=0;counter<this.rowModel.getRowCount();counter++){
				this.rowModel.getRow(counter).onButtonCreate();
				this.table.getSelectionModel().setSelectionInterval(counter, counter);
			}
		}
	}

	private void onButtonCopyData(){
		if((this.source.getConnection()!=null)&&(this.destination.getConnection()!=null)){
			for(int counter=0;counter<this.rowModel.getRowCount();counter++){
				this.rowModel.getRow(counter).onButtonCopy();
				this.table.getSelectionModel().setSelectionInterval(counter, counter);
			}
		}
	}
	
	private void onButtonClearHistory(){
		for(int counter=0;counter<this.rowModel.getRowCount();counter++){
			this.rowModel.getRow(counter).onButtonClearHistory();
			this.table.getSelectionModel().setSelectionInterval(counter, counter);
		}
	}
	
	private ArrayList<Table> tables=new ArrayList<Table>();
	
	/** предварительное сканирование данных */
	private void onButtonScan(){
		System.out.println("Scan");
		Connection connection=this.source.getConnection();
		if(connection!=null){
			try{
				DatabaseMetaData metaData=connection.getMetaData();
				ArrayList<String> tableNames=this.getTableNames(metaData);
				/*for(int counter=0;counter<tables.size();counter++){
					System.out.println(counter+" : "+tables.get(counter));
				}*/
				for(int counter=0;counter<this.tables.size();counter++){
					this.rowModel.removeRow(this.tables.get(counter));
				}
				this.tables.clear();
				for(int counter=0;counter<tableNames.size();counter++){
					this.tables.add(new Table(tableNames.get(counter),connection));
					this.rowModel.addRow(this.tables.get(counter));
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this,ex.getMessage(), "Ошибка при сканировании данных ",JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(this, "Откройте Источник", "Ошибка",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	/** получить имена таблицы */
	private ArrayList<String> getTableNames(DatabaseMetaData metaData) throws SQLException {
		ArrayList<String> returnValue=new ArrayList<String>();
		ResultSet rs=metaData.getTables(null, null, "%",new String[]{"TABLE"});
		/*int columnCount=rs.getMetaData().getColumnCount();
		for(int counter=0;counter<columnCount;counter++){
			System.out.print("   "+counter+" : "+rs.getMetaData().getColumnName(counter+1));
		}*/
		while (rs.next()){
			returnValue.add(rs.getString("TABLE_NAME"));
		}
		return returnValue;
	}
}	

