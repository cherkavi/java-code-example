import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

/** форма, которая содержит пути к базе данных, путь к каталогу вывода, и кнопку запуска*/
public class Main extends JFrame{
	private final static long serialVersionUID=1L;
	
	public static void main(String[] args){
		new Main();
	}

	private JTextField fieldDataBase;
	private JTextField fieldDirectory;
	
	public Main(){
		super("Convert Database Firebird to Hibernate Java classes");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,200);
		this.initComponents();
		this.setVisible(true);
	}

	private void initComponents() {
		// create component's
		JPanel panelMain=new JPanel(new GridLayout(3,1));
		JButton buttonExecute=new JButton("Execute");
		
		JButton buttonPathToDataBase=new JButton("...");
		JButton buttonPathToDirectory=new JButton("...");
		fieldDataBase=new JTextField();
		fieldDirectory=new JTextField();
		
		// add listener's
		buttonExecute.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonExecute();
			}
		});
		buttonPathToDataBase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onPathToDataBase();
			}
		});
		buttonPathToDirectory.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onPathToDirectory();
			}
		});
		// placting 
		JPanel panelDataBase=new JPanel(new BorderLayout());
		panelDataBase.setBorder(javax.swing.BorderFactory.createTitledBorder("Path to DataBase"));
		panelDataBase.add(fieldDataBase);
		panelDataBase.add(buttonPathToDataBase,BorderLayout.EAST);
		
		JPanel panelDirectory=new JPanel(new BorderLayout());
		panelDirectory.setBorder(javax.swing.BorderFactory.createTitledBorder("Path to Output Directory"));
		panelDirectory.add(fieldDirectory);
		panelDirectory.add(buttonPathToDirectory,BorderLayout.EAST);
		
		panelMain.add(panelDataBase);
		panelMain.add(panelDirectory);
		panelMain.add(buttonExecute);
		
		this.getContentPane().add(panelMain);
	}
	
	private void onButtonExecute(){
		System.out.println("GO!");
		DatabaseToHibernate converter=null;
		if(!this.fieldDirectory.getText().trim().endsWith(System.getProperty("file.separator"))){
			converter=new DatabaseToHibernate(this.getConnection(),this.fieldDirectory.getText().trim()+System.getProperty("file.separator"));
		}else{
			converter=new DatabaseToHibernate(this.getConnection(),this.fieldDirectory.getText());
		}
		try{
			Properties sqlTypeToJavaType=new Properties();
			sqlTypeToJavaType.setProperty("SMALLINT", "Integer");
			sqlTypeToJavaType.setProperty("INTEGER", "Integer");
			sqlTypeToJavaType.setProperty("BIGINT", "Integer");
			sqlTypeToJavaType.setProperty("SMALLINT", "Integer");

			sqlTypeToJavaType.setProperty("FLOAT", "Float");
			sqlTypeToJavaType.setProperty("DOUBLE PRECISION", "Float");
			sqlTypeToJavaType.setProperty("NUMERIC", "Float");
			sqlTypeToJavaType.setProperty("DECIMAL", "Float");
			
			sqlTypeToJavaType.setProperty("DATE", "Date");
			sqlTypeToJavaType.setProperty("TIME", "Date");
			sqlTypeToJavaType.setProperty("TIMESTAMP", "Date");

			sqlTypeToJavaType.setProperty("CHAR", "String");
			sqlTypeToJavaType.setProperty("VARCHAR", "String");
			
			if(converter.convert(sqlTypeToJavaType)==true){
				JOptionPane.showMessageDialog(this, "OK","",JOptionPane.INFORMATION_MESSAGE);
			}else{
				JOptionPane.showMessageDialog(this, "Ошибка","",JOptionPane.ERROR_MESSAGE);
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private Connection getConnection(){
		Connection connection=null;
		try{
			//System.out.println(this.fieldDataBase.getText());
			String url = "jdbc:firebirdsql://localhost:3050/"+this.fieldDataBase.getText()+"?sql_dialect=3";
			System.out.println("URL:"+url);
			Class.forName("org.firebirdsql.jdbc.FBDriver");
		    connection = DriverManager.getConnection(url, "SYSDBA", "masterkey");			
		}catch(Exception ex){
			System.err.println("Main#getConnection Exception: "+ex.getMessage());
		}
		return connection;
	}
	
	private void onPathToDataBase(){
		JFileChooser chooser=new JFileChooser(new File("c:\\"));
		chooser.setFileFilter(null);
		FileFilter gdbFilter=new FileFilter(){
			@Override
			public boolean accept(File pathname) {
				return pathname.getAbsolutePath().toLowerCase().endsWith(".gdb")||(pathname.isDirectory());
			}
			@Override
			public String getDescription() {
				return "Firebird Database";
			}
		};
		chooser.setFileFilter(gdbFilter);
		int returnValue=chooser.showDialog(this, "Select Database");
		if(returnValue==JFileChooser.APPROVE_OPTION){
			this.fieldDataBase.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	private void onPathToDirectory(){
		JFileChooser chooser=new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue=chooser.showDialog(this, "Select OutputDirectory");
		if(returnValue==JFileChooser.APPROVE_OPTION){
			this.fieldDirectory.setText(chooser.getSelectedFile().getAbsolutePath()+System.getProperty("file.separator"));
		}
	}
}
