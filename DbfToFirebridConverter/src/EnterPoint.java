import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.io.File;
import java.sql.Connection;

import javax.swing.filechooser.FileFilter;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import dbfConverter.Converter;
import dbfConverter.JdbcConnection;

public class EnterPoint extends JFrame{
	private final static long serialVersionUID=1L;
	
	public static void main(String[] args){
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.DEBUG);
		new EnterPoint();
	}
	
	/** главная форма, отображающая все действия */
	public EnterPoint(){
		super();
		this.setSize(400,250);
		initComponents();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.pack();
	}
	
	private JTextField textFieldDirectory;
	private JTextField textFieldDatabase;
	private JTextField textFieldLog;
	
	
	/** инициализация компонентов */
	private void initComponents(){
		// create element's
		JPanel panelMain=new JPanel();
		panelMain.setLayout(new GridLayout(4,1));
		JButton buttonAction=new JButton("Action");
		
		JButton buttonOpenDirectory=new JButton("...");
		String path="D:\\eclipse_workspace\\GitloService_Report\\Information\\"; 
		textFieldDirectory=new JTextField(path);
		
		JButton buttonOpenDatabase=new JButton("...");
		textFieldDatabase=new JTextField(path+"temp.gdb");
		
		JButton buttonOpenLog=new JButton("...");
		textFieldLog=new JTextField("c:\\log_dbf_converter.txt");

		// add action listener
		buttonOpenDirectory.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonOpenDirectory();
			}
		});
		buttonOpenDatabase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonOpenFirebird();
			}
		});
		buttonOpenLog.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonOpenLogFile();
			}
		});
		buttonAction.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonAction();
			}
		});
		
		// placing 
		panelMain.add(getBorderPanel("DBF directory path",textFieldDirectory,buttonOpenDirectory));
		panelMain.add(getBorderPanel("Database file",textFieldDatabase,buttonOpenDatabase));
		panelMain.add(getBorderPanel("Log file",textFieldLog,buttonOpenLog));
		panelMain.add(buttonAction);
		this.getContentPane().add(panelMain);
	}
	
	private JPanel getBorderPanel(String caption, JTextField field, JButton button){
		JPanel panel=new JPanel(new BorderLayout());
		panel.add(field,BorderLayout.CENTER);
		panel.add(button,BorderLayout.EAST);
		panel.setBorder(javax.swing.BorderFactory.createTitledBorder(caption));
		return panel;
	}
	
	
	
	/** запуск программы на выполнение */
	private void onButtonAction(){
		try{
			// check directory exists
			File directory=new File(this.textFieldDirectory.getText());
			if(directory.exists()==false){
				throw new Exception("directory is not Exists");
			}
			// check is directory
			if(directory.isDirectory()==false){
				throw new Exception("directory is not Directory");
			}
			// check file DBF is exists
			File database=new File(this.textFieldDatabase.getText());
			if(database.exists()==false){
				throw new Exception("Database file is not found");
			}
			// check DataBase file
			if(database.isFile()==false){
				throw new Exception("DataBase is not File");
			}
			// create log file
			File logFile=new File(this.textFieldLog.getText()); 
			// INFO Action 
			JdbcConnection jdbc=new JdbcConnection(database);
			Connection connection=jdbc.getConnection();
			connection.setAutoCommit(false);
			if(connection!=null){
				Converter converter=new Converter(this,directory,connection,logFile);
				if(converter.convert()==false){
					JOptionPane.showMessageDialog(this,"probably was mistakes","Warnings",JOptionPane.WARNING_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(this, "OK","done",JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				throw new Exception("Connection is not getting");
			}
			connection.commit();
			connection.close();
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/** указать путь к директории */
	private void onButtonOpenDirectory(){
		JFileChooser chooser=new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result=chooser.showOpenDialog(this);
		if(result==JFileChooser.APPROVE_OPTION){
			File selectedFile=chooser.getSelectedFile();
			this.textFieldDirectory.setText(selectedFile.getAbsolutePath());
		}else{
			// user cancel choice
		}
	}
	
	/** указать путь к GDB файлу */
	private void onButtonOpenFirebird(){
		JFileChooser chooser=new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		ExtensionFileFilter filter=new ExtensionFileFilter("gdb");
		chooser.setFileFilter(filter);
		int result=chooser.showOpenDialog(this);
		if(result==JFileChooser.APPROVE_OPTION){
			File selectedFile=chooser.getSelectedFile();
			this.textFieldDirectory.setText(selectedFile.getAbsolutePath());
		}else{
			// user cancel choice
		}
	}
	
	/** указать путь к LOG файлу */
	private void onButtonOpenLogFile(){
		JFileChooser chooser=new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		ExtensionFileFilter filter=new ExtensionFileFilter("txt");
		chooser.setFileFilter(filter);
		int result=chooser.showOpenDialog(this);
		if(result==JFileChooser.APPROVE_OPTION){
			File selectedFile=chooser.getSelectedFile();
			this.textFieldDirectory.setText(selectedFile.getAbsolutePath());
		}else{
			// user cancel choice
		}
	}
}


class ExtensionFileFilter extends FileFilter{
	private String[] ext;
	
	public ExtensionFileFilter(String ... ext){
		if(ext==null){
			this.ext=new String[]{"txt"};
		}
		this.ext=ext;
	}

	@Override
	public boolean accept(File arg0) {
		return isFilter(getExtension(arg0));
	}
	
	/** вернуть True если данное расширение есть в массиве  */
	private boolean isFilter(String value){
		boolean returnValue=false;
		for(int counter=0;counter<ext.length;counter++){
			if(ext[counter].equalsIgnoreCase(value)){
				returnValue=true;
				break;
			}
		}
		return returnValue;
	}
	
	/** получить расширение файла */
	private String getExtension(File file){
		String fileName=file.getName();
		int dotPosition=fileName.lastIndexOf(".");
		if(dotPosition>0){
			return fileName.substring(dotPosition+1);
		}else{
			return "";
		}
	}

	@Override
	public String getDescription() {
		StringBuffer buffer=new StringBuffer();
		for(int counter=0;counter<this.ext.length;counter++){
			buffer.append(ext[counter]);
			if(counter!=(this.ext.length-1)){
				buffer.append(", ");
			}
		}
		return buffer.toString();
	}
	
	
}