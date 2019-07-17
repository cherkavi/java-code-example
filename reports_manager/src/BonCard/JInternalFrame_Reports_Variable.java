package BonCard;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.cherkashin.vitaliy.frame.ModalClose;
import com.cherkashin.vitaliy.frame.Position;
import com.cherkashin.vitaliy.sql.TableSorter;
import com.cherkashin.vitaliy.sql.table_model;

/** класс, который отображает все отчеты, которые есть в базе данных */
public class JInternalFrame_Reports_Variable extends JInternalFrame implements InternalFrameListener{
	private static Logger field_logger=Logger.getLogger("JInternalFrame_Reports");
	static{
		field_logger.setLevel(Level.DEBUG);
	}
	private String[] field_table_title=new String[]{"<html><b>Номер отчета</b></html>", 
													"<html><b>Порядок</b></html>", 
													"<html><b>HTML имя</b></html>", 
													"<html><b>Имя параметра</b></html>", 
													"<html><b>Тип параметра</b></htmlj>",
													"<html><b>Обязательность</b></html>",
													"<html><b>Обязят.описание</b></html>",
													"<html><b>Описание параметра</b></html>",
													"<html><b>Значени по умолчанию</b></html>",
													"<html><b>Мульти выбор</b></html>",
													"<html><b>Мульти выбор описание</b></html>",
													"<html><b>Заголовок параметра</b></html>",
													"<html><b>Тип ввода</b></html>",
													"<html><b>Текст запроса</b></html>",
													"<html><b>Длинна в форме </b></html>",
													};
	private boolean[] field_fields_visible=new boolean[]{
													false,           
													true,       
													true,       
													true,        
													true,
													true,
													true,
													true,
													true,
													true,
													true,
													true,
													true,
													true,
													true,
													};
	private int[] field_fields_size=new int[]{       10,             
												    50,        
												    150,        
												    150,      
												    150,      
												    150,      
												    150,      
												    150,      
												    150,      
												    150,      
												    150,      
												    150,      
												    150,      
												    150,      
												    150,      
												    };
	/** соединение с базой данных*/
	private Connection field_connection;
	/** родительское окно, которому будет передано управление */
	private ModalClose field_parent;
	/** рабочий стол, на котором будут размещены внутренние фреймы */
	private JDesktopPane field_desktop; 
	/** номер текущего отчета */
	private String field_report_id;
	
	private JButton field_button_add;
	private JButton field_button_edit;
	private JButton field_button_delete;
	private JTable field_table;
	private StringBuffer fieldQueryForReport;
	private JButton field_button_show_report;
	/** 
	 * Отображение всех отчетов, с возможностью их редактирования
	 * @param connection - текущее соединение с базой данных
	 * @param desktop - рабочий стол, на котором проводится отображение
	 * @param  parent - родительское окно, которому будет передано управление
	 * @param width - ширина окна
	 * @param height - высота окна
	 * @param report_id - текущий отчет, по которому отображаются переменные
	 */
	public JInternalFrame_Reports_Variable(Connection connection, 
								  JDesktopPane desktop, 
								  ModalClose parent,
								  int width,
								  int height,
								  String report_id){
		super("Переменные по отчету: #"+report_id,true,true,true,true);
		field_logger.debug("конструктор:begin");
		this.field_connection=connection;
		this.field_desktop=desktop;
		this.field_parent=parent;
		this.field_report_id=report_id;
		initComponents();
		this.addInternalFrameListener(this);
		Position.set_frame_by_dimension(this, field_desktop, width, height);
		this.field_report_id=report_id;
		this.setVisible(true);
		this.field_desktop.add(this);
		try{
			this.setMaximum(true);
		}catch(Exception ex){
			
		}
		field_logger.debug("конструктор:end");
	}
	/** инициализация и расстановка компонентов на форме 
	 * 
	 */
	private void initComponents(){
		field_logger.debug("initComponents:begin");
		field_logger.debug("создание компонентов");
		field_button_add=new JButton(this.getButtonAddCaption());
		field_button_edit=new JButton(this.getButtonEditCaption());
		field_button_delete=new JButton(this.getButtonDeleteCaption());
		field_button_show_report = new JButton(this.getButtonShowCaption());
        field_table=new JTable();
		fillTable();
		
		field_logger.debug("размещение компонентов");
		/** панель для размещения всех компонентов */
		JPanel panel_main=new JPanel(new BorderLayout());
		
		/** панель для управления - размещения кнопок*/
		JPanel panel_manager=new JPanel(new BorderLayout());
		JPanel panel_manager_edit=new JPanel(new FlowLayout());
		panel_manager_edit.add(this.field_button_edit);
		panel_manager_edit.add(this.field_button_add);
		panel_manager_edit.add(this.field_button_delete);
		JPanel panel_manager_show=new JPanel(new FlowLayout());
		
		panel_manager_show.add(this.field_button_show_report);
		panel_manager.add(panel_manager_show,BorderLayout.WEST);
		panel_manager.add(panel_manager_edit,BorderLayout.EAST);
		
		panel_main.add(new JScrollPane(this.field_table),BorderLayout.CENTER);
		panel_main.add(panel_manager,BorderLayout.SOUTH);
		this.getContentPane().add(panel_main);
		
		field_logger.debug("назначение слушателей для компонентов");
		this.field_button_add.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				onButtonAddClick();
			}
		});
		this.field_button_edit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				onButtonEditClick();
			}
		});
		this.field_button_delete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				onButtonDeleteClick();
			}
		});
		this.field_button_show_report.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonShowReportClick();
			}
		});
		field_logger.debug("initComponents:end");
	}

	private String getButtonShowCaption() {
		return "Отобразить переменные";
	}
	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		this.setVisible(false);
		this.field_parent.close(0);
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
	}
	
	private String getButtonAddCaption(){
		return "Добавить";
	}
	private String getButtonEditCaption(){
		return "Редактировать";
	}
	private String getButtonDeleteCaption(){
		return "Удалить отчет";
	}
	
	private void onButtonAddClick(){
		field_logger.debug("reaction on striking button ADD");
	}
	private void onButtonEditClick(){
		field_logger.debug("reaction on striking button EDIT");
	}
	private void onButtonDeleteClick(){
		field_logger.debug("reaction on striking button DELETE");
	}
	/** наполнение данными таблицы */
	private void fillTable(){
		field_logger.debug("Fill resultset");
		try{
			ResultSet resultset=this.field_connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
																	  ResultSet.CONCUR_READ_ONLY).executeQuery(this.getQueryForShowReports()
																	  );
			this.field_table.setModel(new table_model(resultset,this.field_table_title));
			this.setTableSettings(this.field_table);
		}catch(SQLException ex){
			field_logger.error("fillTable SQLException:"+ex.getMessage());
		}catch(Exception e){
			field_logger.error("fillTable Exception:"+e.getMessage());
		}
	}
	/** 
	 * @return запрос для отображения всех отчетов, которые есть в базе данных
	 */
	private String getQueryForShowReports(){
		if(this.fieldQueryForReport==null){
			this.fieldQueryForReport=new StringBuffer();
			   this.fieldQueryForReport.append("	select ID_REPORT,	 ");
			   this.fieldQueryForReport.append("	       ORDER_NUMBER,	 ");
			   this.fieldQueryForReport.append("	       CD_PARAM,	 ");
			   this.fieldQueryForReport.append("	       NAME_PARAM,	 ");
			   this.fieldQueryForReport.append("	       TYPE_PARAM,	 ");
			   this.fieldQueryForReport.append("	       REQUIRED,	 ");
			   this.fieldQueryForReport.append("	       REQUIRED_TSL,	 ");
			   this.fieldQueryForReport.append("	       DESC_PARAM,	 ");
			   this.fieldQueryForReport.append("	       DEFAULT_VALUE,	 ");
			   this.fieldQueryForReport.append("	       MYLTI_CHOISE,	 ");
			   this.fieldQueryForReport.append("	       MYLTI_CHOISE_TSL,	 ");
			   this.fieldQueryForReport.append("	       TEXT_IN_REPORT,	 ");
			   this.fieldQueryForReport.append("	       ENTER_TYPE,	 ");
			   this.fieldQueryForReport.append("	       SQL_TEXT,	 ");
			   this.fieldQueryForReport.append("	       LENGTH_IN_FORM	 ");
			   this.fieldQueryForReport.append("	       	 ");
			   this.fieldQueryForReport.append("	from VC_USER_REPORTS_PARAM_ALL	 ");
			   this.fieldQueryForReport.append("	where VC_USER_REPORTS_PARAM_ALL.id_report="+this.field_report_id);
			   this.fieldQueryForReport.append("	order by order_number	 ");
		}
		return this.fieldQueryForReport.toString(); 
		
	}
	/** получить наименование столбцов таблицы */
	private String[] getTableTitle(){
		return this.field_table_title;
	}
	/** установить значения для таблицы*/
	private void setTableSettings(JTable table){
        table.setModel(new TableSorter(table.getModel(),
        										   table.getTableHeader())
        						   );
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        com.cherkashin.vitaliy.sql.table_model.setColumnWidth(table,this.field_fields_size);
        for(int counter=this.field_fields_visible.length;counter>=0;counter--){
            if(counter<table.getModel().getColumnCount()){
                if(this.field_fields_visible[counter]==false){
                    //table.removeColumn(table.getColumn(table.getColumnName(table.convertColumnIndexToView(counter))));
                    table.getColumn(table.getColumnName(table.convertColumnIndexToView(counter))).setMinWidth(0);
                    table.getColumn(table.getColumnName(table.convertColumnIndexToView(counter))).setMaxWidth(0);
                    table.getColumn(table.getColumnName(table.convertColumnIndexToView(counter))).setPreferredWidth(0);
                }
            }
        }
	}
	
	/** получить Report_ID для выделенного отчета 
	 * @return null, если нет выделений, 
	 * @return (String)ReportID
	 * */
	private String getReportID(){
		if(this.field_table.getSelectedRow()>=0){
			return (String)this.field_table.getModel().getValueAt(this.field_table.getSelectedRow(),
																  this.field_table.convertColumnIndexToView(0));
		}else{
			return null;
		}
	}
	
	/** 
	 * reaction on striking button "Show report" 
	 */
	private void onButtonShowReportClick(){
		String current_report_id=this.getReportID();
		field_logger.debug("onButtonShowReportClick:"+current_report_id);
		if(current_report_id!=null){

			
		}else{
			JOptionPane.showMessageDialog(this, "Сделайте свой выбор");
		}
	}
}
