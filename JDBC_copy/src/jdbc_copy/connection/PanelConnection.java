package jdbc_copy.connection;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

/** панель, которая отображает соединение с базой данных в виде JDBC URL, имени драйвера, имя драйвера */
public class PanelConnection extends JPanel implements IConnectionAware{
	private final static long serialVersionUID=1L;
	private JTextField editJdbcUrl;
	private JComboBox comboboxDriverName;
	private JButton buttonOpenJar;
	private JButton buttonClear;
	private JButton buttonConnect;
	private String captionButtonOpenJdbc="Открыть Jar файл";
	private String captionButtonClearJdbc="Отсоединиться";
	private File jarFile=new File("D:\\java_lib\\JDBC\\Firebird\\jaybird-full-2.1.1.jar");
	private JTextField editLogin;
	private JTextField editPassword;
	private String valueJdbcUrl="jdbc:firebirdsql://localhost:3050/D:/MESSENGER.GDB?sql_dialect=3";
	//private String valueDriver="org.firebirdsql.jdbc.FBDriver";
	private String valueLogin="SYSDBA";
	private String valuePassword="masterkey";
	private Connection connection;
	
	private static String[] driverNames=new String[]{"org.firebirdsql.jdbc.FBDriver","org.gjt.mm.mysql.Driver","oracle.jdbc.driver.OracleDriver"};
	/** панель, которая содержит все интерфейсы для соединения с базой данных */
	private JPanel panelConnectInterface;
	/** панель, которая отображает соединение с базой данных в виде JDBC URL, имени драйвера, имя драйвера 
	 * @param title - заголовок данной панели 
	 * */
	public PanelConnection(String title ){
		initComponents(title);
	}
	
	private void initComponents(String title){
		this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		// JDBC Url
		this.editJdbcUrl=new JTextField(valueJdbcUrl);
		JPanel panelJdbcUrl=new JPanel(new GridLayout(1,1));
		panelJdbcUrl.add(this.editJdbcUrl);
		panelJdbcUrl.setBorder(javax.swing.BorderFactory.createTitledBorder("JDBC URL"));
		// Driver Jar
		buttonOpenJar=new JButton(captionButtonOpenJdbc);
		buttonOpenJar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonOpenJar();
			}
		});
		// Driver Name
		this.comboboxDriverName=new JComboBox(driverNames);
		this.comboboxDriverName.setEditable(true);
		this.comboboxDriverName.setSelectedIndex(0);
		JPanel panelDriverName=new JPanel(new GridLayout(1,2));
		panelDriverName.add(this.comboboxDriverName);
		panelDriverName.setBorder(javax.swing.BorderFactory.createTitledBorder("Класс драйвера"));
		// Button Connect
		buttonConnect=new JButton("Connect");
		buttonConnect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonConnect();
			}
		});
		// Clear
		buttonClear=new JButton(this.captionButtonClearJdbc);
		buttonClear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonClear();
			}
		});
		
		this.editLogin=new JTextField(valueLogin);
		JPanel panelLogin=new JPanel(new GridLayout(1,1));
		panelLogin.add(this.editLogin);
		panelLogin.setBorder(javax.swing.BorderFactory.createTitledBorder("Логин"));

		this.editPassword=new JTextField(valuePassword);
		JPanel panelPassword=new JPanel(new GridLayout(1,1));
		panelPassword.add(this.editPassword);
		panelPassword.setBorder(javax.swing.BorderFactory.createTitledBorder("Пароль"));
		
		panelConnectInterface=new JPanel();
		GroupLayout groupLayout=new GroupLayout(panelConnectInterface);
		panelConnectInterface.setLayout(groupLayout);
		GroupLayout.SequentialGroup groupLayoutHorizontal=groupLayout.createSequentialGroup();
		GroupLayout.SequentialGroup groupLayoutVertical=groupLayout.createSequentialGroup();
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		groupLayout.setVerticalGroup(groupLayoutVertical);
		groupLayoutVertical.addGroup(
									  groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
									  .addGroup(groupLayout.createSequentialGroup()
											  	.addGroup(groupLayout.createParallelGroup()
											  			  .addComponent(panelJdbcUrl)
											  			  .addComponent(panelDriverName)
											  			  )
											  	.addComponent(this.buttonOpenJar,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
											    )
									  .addGroup(groupLayout.createSequentialGroup()
											    .addGroup(groupLayout.createParallelGroup()
											    		  .addComponent(panelLogin)
											    		  .addComponent(panelPassword)
											    		  )
											    .addComponent(buttonConnect,GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
											    )
									  );
		groupLayoutHorizontal.addGroup(groupLayout.createSequentialGroup()
									   .addGroup(groupLayout.createParallelGroup()
											   	 .addGroup(groupLayout.createSequentialGroup()
											   			   .addComponent(panelJdbcUrl)
											   			   .addComponent(panelDriverName)
											   			   )
											   	 .addComponent(buttonOpenJar,GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)		   
											     )
									   .addGroup(groupLayout.createParallelGroup()
											     .addGroup(groupLayout.createSequentialGroup()
											    		   .addComponent(panelLogin)
											    		   .addComponent(panelPassword)
											    		   )
											     .addComponent(buttonConnect,GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
											     )
				                       );
		this.setLayout(new GridLayout(1,1));
		this.add(panelConnectInterface);
	}
	
	/** нажатие на кнопку открыть Jar */
	private void onButtonOpenJar(){
		JFileChooser fileChooser=new JFileChooser(this.jarFile);
		fileChooser.setFileFilter(new FileFilter(){
			@Override
			public boolean accept(File f) {
				boolean returnValue=false;
				if(f.isFile()){
					String fileName=f.getName();
					int index=fileName.lastIndexOf('.');
					if(index>=0){
						if(fileName.substring(index+1).equalsIgnoreCase("JAR")){
							returnValue=true;
						}
					}
				}else{
					returnValue=true;
				}
				return returnValue;
			}

			@Override
			public String getDescription() {
				return "Jar драйвер JDBC";
			}
		});
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		if(fileChooser.showDialog(this, "Открыть Jar базы данных")==JFileChooser.APPROVE_OPTION){
			File selectedFile=fileChooser.getSelectedFile();
			if(selectedFile.exists()){
				this.jarFile=selectedFile;
				this.buttonOpenJar.setText(selectedFile.getName());
			}else{
				JOptionPane.showMessageDialog(this, "Файл не существует");
			}
		}
	
	}

	/** нажатие на кнопку Connect */
	private void onButtonConnect(){
		if(this.loadClass(this.jarFile.getAbsolutePath(), (String)this.comboboxDriverName.getSelectedItem())!=null){
			// Jar загружен, класс найден
			try{
	            URL url=new URL("file", null, this.jarFile.getAbsolutePath());
	            URLClassLoader ucl = new URLClassLoader(new URL[]{url},null);
	            System.out.println("---URLClassLoader:"+ucl);
	            Class<?> class_value=Class.forName((String)this.comboboxDriverName.getSelectedItem(),false,ucl);
	            System.out.println("---classValue:"+class_value);
	            Object d_object=class_value.newInstance();
	            System.out.println("---object Driver:"+d_object);
	            Driver driver = (Driver)class_value.newInstance();
	            System.out.println("---to Driver:"+driver);
	            DriverManager.registerDriver(driver);
	            System.out.println("---registered Driver");
	            Properties properties=new Properties();
	            properties.put("user",this.editLogin.getText());
	            properties.put("password",this.editPassword.getText());
	            this.connection=driver.connect(this.editJdbcUrl.getText(),properties);
				
				//System.setProperty("java.library.path", System.getProperty("java.library.path")+";"+this.jarFile.getAbsolutePath());
				//System.out.println(System.getProperty("java.library.path"));
				//Class.forName(this.editDriverName.getText(), true, new URLClassLoader(new URL[]{new URL("file", null, this.jarFile.getAbsolutePath())}));
				//System.loadLibrary(this.jarFile.getName());
				//Connection connection=java.sql.DriverManager.getConnection(this.editJdbcUrl.getText(),properties);
				//JOptionPane.showMessageDialog(this, "Connected");
				this.markConnect(true);
			}catch(Exception ex){
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(this, "Проверьте имя пользователя или пароль" ,"Ошибка соединения",JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(this,"Проверьте Jar файл, либо проверьте имя класса " ,"Класс не найден",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/** нажатие на кнопку Очистка */
	private void onButtonClear(){
		this.markConnect(false);
		try{
			this.connection.close();
			this.connection=null;
		}catch(Exception ex){};
	}

	/** установить визуальные компоненты в положение, которое соответствует подключению, или отключению */
	private void markConnect(boolean value){
		if(value){
			// соединение
			this.removeAll();
			buttonClear.setText("Отсоединиться: "+this.editJdbcUrl.getText());
			this.add(this.buttonClear);
			this.revalidate();
			System.out.println("connection");
		}else{
			// отсоединение
			this.removeAll();
			this.add(this.panelConnectInterface);
			this.revalidate();
		}
	}
	
	
    /** загрузка класса на основании пути к Jar и имени */
	private Class<?> loadClass(String path_to_jar, String class_name){
        Class<?> return_value=null;
        try{
            URLClassLoader urlLoader = new URLClassLoader(new URL[]{new URL("file", null, path_to_jar)});
            return_value=urlLoader.loadClass(class_name);
            System.out.println("Loaded class:"+return_value.getName());
        }catch(Throwable ex){
            System.out.println(" error in load class"+ex.getMessage());
        };
        return return_value;
    }

	/** получить соединение с базой данных
	 * @return null - если данное соединение не было 
	 * */
	@Override
	public Connection getConnection(){
		return this.connection;
	}
	
	public void setUrl(String url){
		this.editJdbcUrl.setText(url);
	}
	
	public String getUrl(){
		return this.editJdbcUrl.getText();
	}
	
	public void setDriverClass(String driverClass){
		this.comboboxDriverName.setSelectedItem(driverClass);
	}
	
	public String getDriverClass(){
		return (String)this.comboboxDriverName.getSelectedItem();
	}
	
	public void setLogin(String login){
		this.editLogin.setText(login);
	}
	
	public String getLogin(){
		return this.editLogin.getText();
	}
	
	public void setPassword(String password){
		this.editPassword.setText(password);
	}
	
	public String getPassword(){
		return this.editPassword.getText();
	}
	
}
