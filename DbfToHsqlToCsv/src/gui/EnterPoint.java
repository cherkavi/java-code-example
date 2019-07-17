package gui;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import table.FieldTableModel;
import table.FieldTablePanel;

import com.linuxense.javadbf.DBFReader;
import common.Field;
import database.Connector;
import database.HsqldbConnection;

import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.csvreader.CsvWriter;

public class EnterPoint extends JFrame {
	private final static long serialVersionUID = 1L;

	public static void main(String[] args) {
		new EnterPoint();
	}

	private JTextField pathToDbf;
	private JTextField pathToCsv;
	/** поля из файла DBF */
	private Field[] fields;
	/** компонент отображения полей DBF в виде списка */
	private FieldTablePanel table;
	private JButton buttonLoad;
	private JButton buttonConvert;
	private JCheckBox checkBoxUnique;

	/** главная форма, которая отображает интерфейс пользователя */
	public EnterPoint() {
		super("DBF to CSV converter");
		initComponents();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 500);
		this.setVisible(true);
	}

	private void initComponents() {
		// создать комопоненты
		pathToDbf = new JTextField("D:\\temp\\viddil_js.dbf ");
		pathToCsv = new JTextField();
		JButton buttonPathToDbf = new JButton("...");
		JButton buttonPathToCsv = new JButton("...");
		buttonLoad = new JButton("Load");
		buttonConvert = new JButton("Convert");
		checkBoxUnique=new JCheckBox("Distinct",false);
		JPanel panelDbf = getBorderPanelWithTitle("Путь к DBF для получения данных", buttonPathToDbf, null, null,null, pathToDbf);
		JPanel panelCsv = getBorderPanelWithTitle("Путь к CSV для вывода результата ", buttonPathToCsv, null,null, null, pathToCsv);
		this.table = new FieldTablePanel();
		// назначить слушателей
		buttonPathToDbf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onPathToDbf();
			}
		});
		buttonPathToCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onPathToCsv();
			}
		});
		buttonLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onButtonLoad();
			}
		});
		buttonConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onButtonConvert();
			}
		});
		// расставить компоненты
		JPanel panelMain = new JPanel();
		GroupLayout groupLayout = new GroupLayout(panelMain);
		panelMain.setLayout(groupLayout);
		GroupLayout.SequentialGroup groupLayoutHorizontal = groupLayout
				.createSequentialGroup();
		GroupLayout.SequentialGroup groupLayoutVertical = groupLayout
				.createSequentialGroup();
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		groupLayout.setVerticalGroup(groupLayoutVertical);

		groupLayoutHorizontal.addGroup(groupLayout.createParallelGroup()
									   .addComponent(panelDbf).addComponent(panelCsv).addComponent(buttonLoad, GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
									   .addComponent(this.table, GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
									   .addGroup(groupLayout.createSequentialGroup()
											   	 .addComponent(buttonConvert, GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
											   	 .addComponent(checkBoxUnique)
											     )
									   );
		
		groupLayoutVertical.addComponent(panelDbf, GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
		groupLayoutVertical.addComponent(panelCsv, GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
		groupLayoutVertical.addComponent(buttonLoad);
		groupLayoutVertical.addComponent(this.table,GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE);
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup()
									 .addComponent(buttonConvert)
									 .addComponent(checkBoxUnique)
									 );

		this.getContentPane().add(panelMain);
	}

	private void onPathToDbf() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new ExtendFileFilter("DBF"));
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		if (fileChooser.showDialog(this, "DBF для чтения") == JFileChooser.APPROVE_OPTION) {
			this.pathToDbf.setText(fileChooser.getSelectedFile()
					.getAbsolutePath());
		} else {
			// user click CancelButton
		}
	}

	private void onPathToCsv() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new ExtendFileFilter("CSV"));
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
			if(fileChooser.getSelectedFile().getAbsolutePath().toUpperCase().indexOf(".CSV")<0){
				this.pathToCsv.setText(fileChooser.getSelectedFile()
						.getAbsolutePath()+".csv");
			}else{
				this.pathToCsv.setText(fileChooser.getSelectedFile()
						.getAbsolutePath());
			}
		} else {
			// user click CancelButton
		}
	}

	/** reaction on Striking ButtonConvert */
	private void onButtonConvert(){
		Connection connection=null;
		try{
			// загрузить данные из файла в базу данных HSQL
			System.out.println("get Connection");
			Connector connector=new HsqldbConnection("temp_data");
			connection=connector.getConnection();
			String tableName=(new File(this.pathToDbf.getText())).getName();
			tableName=tableName.substring(0,tableName.indexOf('.'));
			
			System.out.println("remove table by DBF filename");
			try{
				connection.createStatement().executeUpdate("DROP TABLE "+tableName);
				connection.commit();
				System.out.println("table was dropped");
			}catch(Exception ex){
				System.out.println("DropTable "+tableName+"Exception:"+ex.getMessage());
			}
			
			System.out.println("create table by fieldNames and DBF filename");
			String sql=null;
			try{
				sql=Field.getSqlCreateTable(tableName, fields);
				connection.createStatement().executeUpdate(sql.toString());
				connection.commit();
			}catch(Exception ex){
				// SQL запрос не выполнен - причина - ex.getMessage()
				System.out.println("createTable - query is not execute:"+ex.getMessage()+"\n"+sql);
			}
			System.out.println("put data into Connection");
			PreparedStatement statement=Field.getPreparedStatement(connection, tableName, fields);
			DBFReader reader=new DBFReader(new FileInputStream(this.pathToDbf.getText()));
			Object[] row;
			while( (row=reader.nextRecord())!=null){
				statement.clearParameters();
				for(int counter=0;counter<fields.length;counter++){
					if(row[counter] instanceof String){
						//logger.debug(counter+" : "+Field.convertDosString((String)fields[counter].getObjectForSql(row[counter])));
						statement.setObject(counter+1, Field.convertDosString((String)fields[counter].getObjectForSql(row[counter])));
					}else{	
						if(row[counter]!=null){
							//logger.debug(counter+":"+row[counter].getClass().getName());
							statement.setObject(counter+1, fields[counter].getObjectForSql(row[counter]));
						}else{
							statement.setObject(counter+1, fields[counter].getObjectForSql(row[counter]));
						}
					}
				}
				try{
					statement.executeUpdate();
					connection.commit();
				}catch(Exception ex){
					System.err.println("Execute Exception: "+ex.getMessage());
				}
			}
			System.out.println("Execute query by Order ");
			String queryMain=this.table.getSqlQueryByFieldsPosition(tableName,null,this.checkBoxUnique.isSelected());
			
			System.out.print("Query:");System.out.println(queryMain);
			ResultSet rs=connection.createStatement().executeQuery(queryMain);
			CsvWriter writer=new CsvWriter(this.pathToCsv.getText(),',',Charset.forName("WINDOWS-1251"));

			int columnSelectedCount=this.table.getFieldCount();
			String[] headers=new String[columnSelectedCount];
			for(int counter=0;counter<headers.length;counter++){
				headers[counter]=this.table.getSelectedFieldName(counter);
			}
			writer.writeRecord(headers);
			while(rs.next()){
				writer.writeRecord(getArrayOfStringByResultSet(rs,headers));
			}
			writer.close();
			JOptionPane.showMessageDialog(this, "OK");
			this.pathToDbf.setEnabled(true);
			this.pathToCsv.setEnabled(true);
			this.buttonLoad.setEnabled(true);
			this.buttonConvert.setEnabled(true);
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}finally{
			try{
				connection.close();
			}catch(Exception ex){};
		}
	}

	private String[] getArrayOfStringByResultSet(ResultSet rs, String[] columns){
		String[] returnValue=new String[columns.length];
		try{
			for(int counter=0;counter<columns.length;counter++){
				returnValue[counter]=rs.getString(columns[counter]);
			}
		}catch(Exception ex){
			System.err.println("getArrayOfStringByResultSet:"+ex.getMessage());
		};
		return returnValue;
	}

	/** reaction on Striking ButtonLoad */
	private void onButtonLoad() {
		try {
			// загрузить все поля из файла DBF в визуальный компонент
			DBFReader reader = new DBFReader(new FileInputStream(this.pathToDbf.getText()));
			fields = new Field[reader.getFieldCount()];
			for (int counter = 0; counter < fields.length; counter++) {
				fields[counter] = new Field(reader.getField(counter), counter);
			}
			this.table.setTableModel(new FieldTableModel(fields));
			this.pathToDbf.setEnabled(false);
			this.buttonLoad.setEnabled(false);
			this.buttonConvert.setEnabled(true);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Load Error:" + ex.getMessage());
		}
	}

	/**
	 * получить панель с компонентами на борту, которая обрамлена текстом и
	 * добавить компоненты в эту панель EAST, SOUTH, WEST, NORTH, CENTRE
	 */
	private JPanel getBorderPanelWithTitle(String title,
			JComponent... components) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		String[] position = new String[] { BorderLayout.EAST,
				BorderLayout.SOUTH, BorderLayout.WEST, BorderLayout.NORTH,
				BorderLayout.CENTER };
		for (int counter = 0; counter < position.length; counter++) {
			if (components.length > counter) {
				if (components[counter] != null) {
					panel.add(components[counter], position[counter]);
				}
			}
		}
		return panel;
	}
}

class ExtendFileFilter extends FileFilter {
	String[] fileExt;

	public ExtendFileFilter(String... ext) {
		this.fileExt = ext;
	}

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		} else {
			String name = f.getName();
			int dotPosition = name.lastIndexOf('.');
			String ext = name.substring(dotPosition + 1);
			return elementInArray(ext);
		}
	}

	private boolean elementInArray(String value) {
		boolean returnValue = false;
		for (int counter = 0; counter < this.fileExt.length; counter++) {
			if (this.fileExt[counter].equalsIgnoreCase(value)) {
				returnValue = true;
				break;
			}
		}
		return returnValue;
	}

	@Override
	public String getDescription() {
		StringBuffer description = new StringBuffer();
		if (fileExt != null) {
			for (int counter = 0; counter < fileExt.length; counter++) {
				description.append(this.fileExt[counter]);
				if (counter != (fileExt.length - 1)) {
					description.append(", ");
				}
			}
		}
		return description.toString();
	}

}