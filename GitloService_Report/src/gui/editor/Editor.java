package gui.editor;
import gui.Position;
import gui.editor.database.HibernateConnection;
import gui.editor.database.gui.JTableWrap;
import gui.editor.database.wrap.PayKind;
import gui.editor.database.wrap.Tariff;
import gui.editor.database.wrap.Tn;
import gui.editor.tariff.TariffBrowser;
import gui.editor.tn.TnBrowser;
import gui.table_column_render.*;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.swing.*;

import org.apache.log4j.Logger;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import database.Connector;
import database.FirebirdConnection;
import database.Utility;
import dbfConverter.Converter;

import xml_ini.Loader;

public class Editor extends JPanel{
	private final static long serialVersionUID=1L;
	/** объект, который содержит все необходимые параметры для отображения данных */
	private Logger logger=Logger.getLogger("Editor");
	private Loader settings;
	private JDateChooser dateReport;
	private JMonthChooser monthChooser;
	private JYearChooser yearChooser;
	private JComboBox viddil;
	private Connector connector;
	private HibernateConnection hibernateConnection;
	/** таблица, которая содержит список договоров */
	private JTableWrap table;
	/** parent dialog */
	private JDialog parent;
	/** запрос к базе для отбора уникальных номеров */
	private String queryUniqueId="select kod_pom from npom inner join viddil_js on viddil_js.ul1=npom.ul1 and viddil_js.dm1=npom.dm1 where jk1=2 and npom.do_ref>=? and viddil_js.viddil=?"; 
	/** запрос к базе для получения кол-ва */
	private String queryCount="select count(kod_pom) from npom inner join viddil_js on viddil_js.ul1=npom.ul1 and viddil_js.dm1=npom.dm1 where jk1=2 and npom.do_ref>=? and viddil_js.viddil=?";
	/** запрос к базе для получения текущего значения */
	private String queryRow="select ul.naiu ul_naiu, npom.dm1 npom_dm1, npom.lt1 npom_lt1, npom.lt1_kw npom_lt1_kw, arn.adr,arn.naim, npom.nd,npom.dd, npom.oplp from npom inner join arn on arn.akod=npom.aren inner join viddil_js on viddil_js.ul1=npom.ul1 and viddil_js.dm1=npom.dm1 inner join ul on ul.ul=npom.ul1 where jk1=2 and npom.do_ref>=? and viddil_js.viddil=? and kod_pom=?";
	
		
	
	public Editor(JDialog parent, Loader settings){
		super();
		this.parent=parent;
		this.settings=settings;
		connector=new FirebirdConnection(settings.getValue("//SETTINGS/URL_DATABASE"));
		try{
			connector.getConnection().setAutoCommit(false);
			hibernateConnection=new HibernateConnection(connector.getConnection(),"org.hibernate.dialect.FirebirdDialect",Tariff.class,PayKind.class,Tn.class);
		}catch(Exception ex){
			logger.error("connection setAutoCommit:"+ex.getMessage());
		}
		this.initComponents();
	}
	
	public void finalize(){
		if(this.connector.getConnection()!=null){
			try{
				this.connector.getConnection().close();
			}catch(Exception ex){};
		}
	}
	
	/** получить панель с внедренным в нее компонентом и заголовком*/
	public static JPanel getTitledPanel(JComponent component, String title){
		JPanel returnValue=new JPanel(new GridLayout(1,1));
		returnValue.add(component);
		returnValue.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		return returnValue;
	}
	/** получить панель с внедренным в нее компонентами и размерами для этих компонетов, а так же заголовком 
	 * @param components - компоненты 
	 * @param width - значения ширины для компонентов ( если 0 - PREFERRED_SIZE)
	 * @param title - заголовок для таблицы
	 * @return возвращает панель с расставленными компонентами 
	 */
	public static JPanel getTitledPanel(JComponent[] components, int[] width, String title){
		JPanel returnValue=new JPanel();
		GroupLayout groupLayout=new GroupLayout(returnValue);
		returnValue.setLayout(groupLayout);
		GroupLayout.SequentialGroup groupLayoutHorizontal=groupLayout.createSequentialGroup();
		GroupLayout.SequentialGroup groupLayoutVertical=groupLayout.createSequentialGroup();
		
		GroupLayout.ParallelGroup verticalGroup=groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
		for(int counter=0;counter<components.length;counter++){
			groupLayoutHorizontal.addComponent(components[counter],
											   width[counter],
											   width[counter],
											   width[counter]);
			verticalGroup.addComponent(components[counter],GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE);
		}
		groupLayoutVertical.addGroup(verticalGroup);
		groupLayout.setVerticalGroup(groupLayoutVertical);
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		returnValue.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		return returnValue;
	}
	
	/** первоначальная инициализация компонентов */
	private void initComponents(){
		// create components 
		dateReport=new JDateChooser(new Date());
		monthChooser=new JMonthChooser();
		yearChooser=new JYearChooser();
		viddil=new JComboBox(new String[]{"1","2","3","4","5"});
		JButton buttonLoadData=new JButton("Загрузить данные");
		JButton buttonRefreshData=new JButton("<html><b>Обновление данных в базе</b></html>");
		JButton buttonPrice=new JButton("Цены для начисления");
		JButton buttonEdit=new JButton("Начисление по услугам");
		JButton buttonPrintFact=new JButton("Счет-фактура");
		try{
			table=new JTableWrap(this.hibernateConnection.getConnection(),
					 this.queryUniqueId,
					 this.queryRow,
					 this.queryCount,
					 new String[]{"Адрес","Название","Номер дог.","Дата дог.","Площадь"},
					 new int[]{250,250,80,80,50},
					 new ICellValue[]{new ColumnMultiCellValue(new ColumnSimpleTrim("UL_NAIU"),
							 								   new ColumnConst(" "),
							 								   new ColumnTruncDouble("NPOM_DM1"),
							 								   new ColumnConst(" "),
							 								   new ColumnSimpleTrim("NPOM_LT1"),
							 						  		   new ColumnSimpleTrim("NPOM_LT1_KW")),
							 		  new ColumnSimple("NAIM"),
							 		  new ColumnSimple("ND"),
							 		  new ColumnDate("DD"),
							 		  new ColumnSimple("OPLP")}
					);
			table.refreshData(Utility.getSqlDate(1, this.monthChooser.getMonth()+1, this.yearChooser.getYear()),new Integer(this.viddil.getSelectedIndex()+1));
		}catch(Exception ex){
			System.err.println("Editor#initComponents Exception:"+ex.getMessage());
		}
		// change listener
		ActionListener visualChange=new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onChangeVisualCriterion();
			}
		};
		viddil.addActionListener(visualChange);
		PropertyChangeListener propertyChangeListener=new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if((evt.getPropertyName().equalsIgnoreCase("year"))||(evt.getPropertyName().equalsIgnoreCase("month"))){
					onChangeVisualCriterion();
				}
			}
        };
		monthChooser.addPropertyChangeListener(propertyChangeListener);
		yearChooser.addPropertyChangeListener(propertyChangeListener);
		
		JScrollPane tableScroll=new JScrollPane(table);
		// add listener's
		buttonLoadData.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonLoadData();
			}
		});
		buttonRefreshData.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonRefreshData();
			}
		});
		buttonPrice.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonPriceData();
			}
		});
		buttonEdit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonEdit();
			}
		});
		buttonPrintFact.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonPrintFact();
			}
		});
		JPanel managerPay=new JPanel(new BorderLayout());
		managerPay.add(buttonEdit,BorderLayout.CENTER);
		managerPay.add(buttonPrintFact,BorderLayout.EAST);
		// placing component's
		JPanel panelDate=Editor.getTitledPanel(new JComponent[]{Editor.getTitledPanel(dateReport, "Дата отчета"),
															  Editor.getTitledPanel(monthChooser, "Месяц"),
															  Editor.getTitledPanel(yearChooser, "Год"),
															  Editor.getTitledPanel(viddil, "Отдел")
															  }, 
										     new int[]{110,110,70,50}, 
										     "Критерии отбора");
		GroupLayout groupLayout=new GroupLayout(this);
		this.setLayout(groupLayout);
		GroupLayout.SequentialGroup groupLayoutHorizontal=groupLayout.createSequentialGroup();
		GroupLayout.SequentialGroup groupLayoutVertical=groupLayout.createSequentialGroup();
		groupLayout.setVerticalGroup(groupLayoutVertical);
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);

	  	groupLayoutHorizontal.addGroup(groupLayout.createParallelGroup()
	  								   .addGroup(groupLayout.createSequentialGroup()
	  										  	.addGroup(groupLayout.createParallelGroup()
	  				  								   .addComponent(panelDate)
	  				  								   .addComponent(buttonLoadData,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
	  				  								   )
	  								   			.addGroup(groupLayout.createParallelGroup()
	  				  								   .addComponent(buttonRefreshData,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
	  				  								   .addComponent(buttonPrice,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
	  				  								   )
	  										     )
	  								   .addComponent(tableScroll)
	  								   .addComponent(managerPay,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
	  								   );
	  	groupLayoutVertical.addGroup(groupLayout.createParallelGroup()
	  								 .addGroup(groupLayout.createSequentialGroup()
	  										   .addComponent(panelDate)
	  										   .addComponent(buttonLoadData)
	  										   )
	  								 .addGroup(groupLayout.createSequentialGroup()
	  										   .addGap(20)
	  										   .addComponent(buttonRefreshData)
	  										   .addGap(15)
	  										   .addComponent(buttonPrice)
	  										 )
	  								 );
	  	groupLayoutVertical.addComponent(tableScroll);
		groupLayoutVertical.addComponent(managerPay);
	}
	
	/** загрузить данные */
	private void onButtonLoadData(){
		Cursor cursor=this.getCursor();
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.table.clearData();
		this.table.refreshData(Utility.getSqlDate(1, this.monthChooser.getMonth()+1, this.yearChooser.getYear()),new Integer(this.viddil.getSelectedIndex()+1)); 
		this.setCursor(cursor);
	}
	
	
	private String getPathToDbf(){
		try{
			return this.settings.getValue("//SETTINGS/DBF_DIRECTORY");
		}catch(Exception ex){
			return "\\";
		}
		//return "D:\\eclipse_workspace\\GitloService_Report\\Information\\";
	}

	private String getPathToSourceDBF(){
		try{
			return this.settings.getValue("//SETTINGS/DBF_DIRECTORY_SOURCE");
		}catch(Exception ex){
			return "\\";
		}
		//return "D:\\eclipse_workspace\\GitloService_Report\\Information\\";
	}

	private boolean copyFile(String source, String destination) throws Exception{
        boolean return_value=false;
        File file_source = new File(source);
        File file_destination = new File(destination);
        InputStream in = new FileInputStream(file_source);

      //For Append the file.
//      OutputStream out = new FileOutputStream(f2,true);
      //For Overwrite the file.
      OutputStream out = new FileOutputStream(file_destination);

        // buffer size
        byte[] buf = new byte[4096];
        int len;
        while ((len = in.read(buf)) > 0){
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
        return_value=true;
        return return_value;
    }
	
	/** обновление данных в базе */
	private void onButtonRefreshData(){
		Cursor cursor=this.getCursor();
		try{
			String pathToDbf=this.getPathToDbf();
			String pathToSourceDbf=this.getPathToSourceDBF();
			String currentFileName=null;
			// копирование из директории-источника в директорию приемник
			try{
				currentFileName="arn.dbf";
				if(copyFile(pathToSourceDbf+currentFileName, pathToDbf+currentFileName)==false){
					throw new Exception("copy ERROR");
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "Ошибка копирования файла:\n"+pathToSourceDbf+currentFileName+"\n в каталог:"+pathToDbf+"\n"+ex.getMessage());
			}
			try{
				currentFileName="npom.dbf";
				if(copyFile(pathToSourceDbf+currentFileName, pathToDbf+currentFileName)==false){
					throw new Exception("copy ERROR");
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "Ошибка копирования файла:\n"+pathToSourceDbf+currentFileName+"\n в каталог:"+pathToDbf+"\n"+ex.getMessage());
			}
			try{
				currentFileName="ul.dbf";
				if(copyFile(pathToSourceDbf+currentFileName, pathToDbf+currentFileName)==false){
					throw new Exception("copy ERROR");
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "Ошибка копирования файла:\n"+pathToSourceDbf+currentFileName+"\n в каталог:"+pathToDbf+"\n"+ex.getMessage());
			}
			try{
				currentFileName="viddil_js.dbf";
				if(copyFile(pathToSourceDbf+currentFileName, pathToDbf+currentFileName)==false){
					throw new Exception("copy ERROR");
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "Ошибка копирования файла:\n"+pathToSourceDbf+currentFileName+"\n в каталог:"+pathToDbf+"\n"+ex.getMessage());
			}
			// INFO место загрузки всех файлов из DBF
			File fileArn=new File(getPathToDbf()+"arn.dbf ");
			File fileNpom=new File(getPathToDbf()+"npom.dbf ");
			File fileUl=new File(getPathToDbf()+"ul.dbf");
			File fileViddilJs=new File(getPathToDbf()+"viddil_js.dbf");
			
			if(fileArn.exists()==false){
				throw new Exception("Не найден файл  ARN.DBF");
			}
			if(fileNpom.exists()==false){
				throw new Exception("Не найден файл  Npom.DBF");
			}
			if(fileUl.exists()==false){
				throw new Exception("Не найден файл  ul.DBF");
			}
			if(fileViddilJs.exists()==false){
				throw new Exception("Не найден файл  viddil_js.DBF");
			}
			//File logFile=new File("c:\\logger_gitloservice.txt");
			Converter converter=new Converter(new File[]{fileArn, fileNpom, fileUl,fileViddilJs},
											  connector.getConnection(),
											  null);
			//Converter converter=new Converter(new File[]{fileTn},connector.getConnection(),logFile);
			this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			if(converter.convert()==false){
				this.setCursor(cursor);
				JOptionPane.showMessageDialog(this, "Не удалось загрузить данные", "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				this.setCursor(cursor);
				JOptionPane.showMessageDialog(this,"Данные успешно обновлены");
			}
		}catch(Exception ex){
			this.setCursor(cursor);
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("EnterPoint exception: "+ex.getMessage());
		}
	}
	
	/** цены для начисления */
	private void onButtonPriceData(){
		JDialog modal=new JDialog(this.parent,"Таблица тарифов",Dialog.ModalityType.APPLICATION_MODAL);
		modal.add(new TariffBrowser(modal, this.hibernateConnection,this.monthChooser.getMonth()+1,this.yearChooser.getYear()));
		Position.setDialogToCenterBySize(modal, 450, 370);
		//modal.setSize(450,370);
		modal.setVisible(true);
	}
	
	/** рекция на нажатие клавиши печать счет-фактуры */
	private void onButtonPrintFact(){
		// TODO 
		if(this.table.getSelectedObject()!=null){
			// отработать запрос
			// проверка на наличие данных
			// отобразить запрос 
		}else{
			JOptionPane.showMessageDialog(this, "Необходимо выделить объект");
		}
	}
	
	/** редактор начислений по услугам */
	private void onButtonEdit(){
		if(this.table.getSelectedObject()!=null){
			JDialog modal=new JDialog(this.parent,"Редактирование TN",Dialog.ModalityType.APPLICATION_MODAL);
			modal.add(new TnBrowser(modal, 
									this.hibernateConnection,
									this.monthChooser.getMonth()+1,
									this.yearChooser.getYear(),
									this.table.getSelectedObject()
									)
					  );
			//modal.setSize(450,370);
			Position.setDialogToCenterBySize(modal, 450, 370);
			modal.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(this, "Сделайте свой выбор");
		}
	}
	
	private void onChangeVisualCriterion(){
		this.table.clearData();
	}
}
