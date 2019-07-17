package install.temp_directory;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import install.EnterPoint;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** отобразить путь ко временному каталогу  */
public class TempDirectory extends JInternalFrame{
	private final static long serialVersionUID=1L;
	private EnterPoint manager;
	
	/** отобразить путь ко временному каталогу  */
	public TempDirectory(EnterPoint manager){
		this.manager=manager;
		this.initComponents();
		this.setSize(500,150);
		this.setVisible(true);
	}
	
	private JTextField pathToDirectory;
	
	private void initComponents(){
		this.getContentPane().setLayout(new GridLayout(3,1));
		this.getContentPane().add(new JLabel("Путь к каталогу c временными файлами"));
		this.setClosable(false);
		
		pathToDirectory=new JTextField(getTempSystemDirectory());
		pathToDirectory.setToolTipText("полный путь к каталогу для хранения временных файлов во время инсталляции ");
		JPanel panel=new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(pathToDirectory,BorderLayout.CENTER);
		JButton buttonChoiceDirectory=new JButton("...");
		buttonChoiceDirectory.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonChoiceCatalog();
			}
		});
		panel.add(buttonChoiceDirectory,BorderLayout.EAST);
		this.getContentPane().add(panel);
		
		JPanel panelManager=new JPanel();
		panelManager.setLayout(new GridLayout(1,2));
		JButton buttonOk=new JButton("Установить");
		panelManager.add(buttonOk);
		buttonOk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evet){
				onButtonOk();
			}
		});
		
		buttonOk.setToolTipText("Установить текущий каталог как каталог для загрузки и распаковки временных файлов");
		JButton buttonCancel=new JButton("Отменить");
		buttonCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				onButtonCancel();
			}
		});
		panelManager.add(buttonCancel);
		
		this.getContentPane().add(panelManager);
	}
	
	private String getTempSystemDirectory(){
		try{
			String returnValue=System.getProperty("java.io.tmpdir");
			return (returnValue==null)?"":returnValue;
		}catch(Exception ex){
			return "";
		}
	}
	
	private String getPathDelimeter(){
		return System.getProperty("file.separator");
	}
	
	private void onButtonChoiceCatalog(){
		JFileChooser dialog=new JFileChooser();
		dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(dialog.showDialog(this, "Каталог для загрузки")==JFileChooser.APPROVE_OPTION){
			String tempDirectory=dialog.getSelectedFile().getPath();
			if(tempDirectory!=null){
				tempDirectory=tempDirectory.trim();
				if(tempDirectory.endsWith(this.getPathDelimeter())==false){
					tempDirectory=tempDirectory+this.getPathDelimeter();
				}
			}
			if(this.isDirectoryExists(tempDirectory)){
				this.pathToDirectory.setText(tempDirectory);
			}else{
				JOptionPane.showInternalMessageDialog(this, "Каталог не существует");
			}
			
		}
	}
	
	/** существует ли каталог ?  */
	private boolean isDirectoryExists(String path){
		boolean returnValue=false;
		try{
			File file=new File(path);
			returnValue=file.exists();
		}catch(Exception ex){
			returnValue=false;
		}
		return returnValue;
	}
	
	private void onButtonOk(){
		String tempDirectory=this.pathToDirectory.getText();
		if(tempDirectory!=null){
			tempDirectory=tempDirectory.trim();
			if(tempDirectory.endsWith(this.getPathDelimeter())==false){
				tempDirectory=tempDirectory+this.getPathDelimeter();
			}
		}
		if(this.isDirectoryExists(tempDirectory)){
			this.pathToDirectory.setText(tempDirectory);
			this.manager.showPassword(tempDirectory);
		}else{
			JOptionPane.showInternalMessageDialog(this, "Выбранный каталог не существует");
		}
		
	}
	
	private void onButtonCancel(){
		System.exit(0);
	}
}
