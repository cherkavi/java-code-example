package gui.dovidka;

import gui.table_column_render.ColumnConst;
import gui.table_column_render.ColumnDate;
import gui.table_column_render.ColumnMultiCellValue;
import gui.table_column_render.ColumnSimple;
import gui.table_column_render.ColumnSimpleTrim;
import gui.table_column_render.ColumnTruncDouble;
import gui.table_column_render.ICellValue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import reporter.HtmlReplacer;
import reporter.MoneyToString;
import reporter.replacer.AddPrefixIsNotEmptyRaplacer;
import reporter.replacer.MultiReplacer;
import reporter.replacer.ReplaceValue;
import reporter.replacer.SingleReplacer;
import jxl.write.Number;

/** таблица, которая содержит результаты вычислений */
public class ResultTable extends JTable{
	private final static long serialVersionUID=1L;
	private ResultTableModel model;
	private String pathToPattern;
	private String pathToPatternAkt;
	/** имя таблицы, которая должна учавствовать в отборе */
	private String tableTnName="";
	/** имя таблицы, по которой можно получать информацию о долгах */
	private String tableOsName="";
	/** имя таблицы, по которой можно получить информацию о проплатах */
	private String tablePlName="";
	/** условие на жэки */
	private String whereJK="";
	/** данные для поля НАЗВА */
	private ArrayList<String> fieldName=new ArrayList<String>();
	/** данные для поля Од.Вим. */
	private ArrayList<String> fieldValue=new ArrayList<String>();
	/** ссылка на отделение*/
	private String viddil;
	/** нужно ли закачивать данные по почте, или же "привязываться к отделению"*/
	private boolean pochta=false;
	/** реквизиты Житлосервис 0..6 naim(0), adr(1), okp(2), mfo1(3), RASCH1(4), NOM_DPA(5), SWID_DPA (6)*/
	private ArrayList<String> rekvisit=new ArrayList<String>();	
	
	/** соединение с базой данных */
	private Connection connection;
	
	/** таблица, которая содержит все данные для печати 
	 * @param connection  - текущее соединение с базой данных
	 * @param pathToPattern - путь к файлу-шаблону 
	 * */
	public ResultTable(Connection connection,
					   String pathToPattern,
					   String pathToPatternAkt){
		super();
		//this.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
		this.connection=connection;
		this.pathToPattern=pathToPattern;
		this.pathToPatternAkt=pathToPatternAkt;
		this.model=new ResultTableModel(this.connection,
										new ICellValue[]{new ColumnMultiCellValue(new ColumnSimpleTrim(8),new ColumnConst(" "),new ColumnTruncDouble(11),new ColumnConst(" "),new ColumnSimpleTrim(13)),
														 new ColumnSimple(6),
														 new ColumnTruncDouble(11),
														 new ColumnDate(15),
														 new ColumnSimple(17),
														 new ColumnSimple(7)
				                                         });
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setModel(this.model);

		this.getColumn(this.getColumnName(0)).setPreferredWidth(150);
		this.getColumn(this.getColumnName(1)).setPreferredWidth(150);
		this.getColumn(this.getColumnName(2)).setPreferredWidth(30);
		this.getColumn(this.getColumnName(3)).setPreferredWidth(70);
		this.getColumn(this.getColumnName(4)).setPreferredWidth(50);
		this.getColumn(this.getColumnName(5)).setPreferredWidth(250);

		this.getColumn(this.getColumnName(0)).setHeaderValue("Адрес");
		this.getColumn(this.getColumnName(1)).setHeaderValue("Арендосъемщик");
		this.getColumn(this.getColumnName(2)).setHeaderValue("Номер дог.");
		this.getColumn(this.getColumnName(3)).setHeaderValue("Дата дог.");
		this.getColumn(this.getColumnName(4)).setHeaderValue("Площадь");
		this.getColumn(this.getColumnName(5)).setHeaderValue("Юр.адрес");
	}
	
	/** заполнить данными из файла ura_wp.dbf */
	private void fillUraWp(){
		for(int counter=0;counter<20;counter++){
			fieldName.add("");fieldValue.add("");
		}
		ResultSet rs=null;
		try{
			rs=this.connection.createStatement().executeQuery("SELECT * FROM URA_WP ");
			int position=0;
			System.out.println("URA_WP begin");
			while(rs.next()){
				//position=(new Float(rs.getFloat(1))).intValue();
				position=(new Float(rs.getFloat("WP1"))).intValue();
				System.out.println("WP: "+position+"   WP2:"+rs.getString("WP2")+"     WP7:"+rs.getString("WP7"));
				fieldName.set(position, rs.getString("WP2"));
				fieldValue.set(position,rs.getString("WP7"));
			}
			System.out.println("URA_WP end");
		}catch(Exception ex){
			System.err.println("fillUraWp:"+ex.getMessage());
		}finally{
			if(rs!=null){
				try{
					rs.close();
				}catch(Exception ex){};
			}
		}
		/*
		fieldName.add("Плата за прим.");fieldValue.add("грн.");//1
		fieldName.add("Експлуат. витрати");fieldValue.add("м2");//2
		fieldName.add("Черговi нагляд.");fieldValue.add("грн");//3
		fieldName.add("Технiчне обслуг.");fieldValue.add("грн");//4
		fieldName.add("Техобслуговування");fieldValue.add("грн");//5
		fieldName.add("Вiдшк.податку на зем");fieldValue.add("грн");//6
		fieldName.add("Холодна вода");fieldValue.add("м3");//7
		fieldName.add("Гаряча вода");fieldValue.add("ГКал");//8
		//fieldName.add("Спiльне вик.ел.мер.");fieldValue.add("");//9
		fieldName.add("Оплата за дог.");fieldValue.add("");//9
		fieldName.add("Опалення");fieldValue.add("ГКал");//10
		fieldName.add("4,0% вiд.ком.посл.");fieldValue.add("грн.");//11
		fieldName.add("За розмiщ.техн.обл.");fieldValue.add("грн.");//12
		fieldName.add("Посл.автос.паркiнг.");fieldValue.add("грн");//13
		fieldName.add("Iндекс. ор. пл.");fieldValue.add("грн");//14
		fieldName.add("ПДВ");fieldValue.add("грн");//15
		*/
	}
	
	/** очистить данные */
	public void clearData(){
		this.model.clearData();
	}
	
	/** получить в качестве ответа строку, содержащую целое число, либо же пустую строку, если =0*/
	private String getLongEmptyString(ResultSet rs, String columnName){
		String returnValue=null;
		try{
			long arn_okp=new Double(rs.getString(columnName)).longValue();
			if(arn_okp==0){
				// returnValue=null;
			}else{
				returnValue=Long.toString(arn_okp);
			}
		}catch(Exception ex){
			// String is empty
		}
		return (returnValue==null)?"":returnValue;
	}
	
	/** получить в качестве ответа строку, содержащую целое число, либо же пустую строку, если =0<br>
	 * и дополнить данную строку символами, до указанной длинны, если меньше, начиная с начала строки
	 * */
	private String getLongEmptyStringWithPrefix(ResultSet rs, 
											    String columnName,
											    int minLength,
											    char prefix){
		String returnValue=null;
		try{
			long arn_okp=new Double(rs.getString(columnName)).longValue();
			if(arn_okp==0){
				// returnValue=null;
			}else{
				returnValue=Long.toString(arn_okp);
			}
		}catch(Exception ex){
			// String is empty
		}
		if(returnValue==null){
			// строка пуста
		}else{
			// строка не пуста, проверить на кол-во знаков
			while(returnValue.length()<minLength){
				returnValue=prefix+returnValue;
			}
		}
		return (returnValue==null)?"":returnValue;
	}
	
	private String getEmptyStringWithPrefix(ResultSet rs, 
		    							    String columnName,
		    							    String prefix){
		String returnValue = null;
		try{
			returnValue=rs.getString(columnName).trim();
			if(returnValue.length()>0){
				returnValue=prefix+returnValue;
			}else{
				returnValue="";
			}
		}catch(Exception ex){
			System.err.println("getEmptyString WithPrefix Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	
	private String getEmptyString(ResultSet rs, String columnName){
		String returnValue="";
		try{
			if(rs.getString(columnName)!=null){
				returnValue=rs.getString(columnName);
			}
		}catch(Exception ex){
			System.err.println("getEmptyString Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	
	private SimpleDateFormat dateFormat=new SimpleDateFormat("dd.MM.yyyy");
	
	/** получить дату в формате "dd.MM.yyyy" из поля TimeStamp */
	private String getFormatDateFromTimeStamp(ResultSet rs, String column){
		String returnValue=null;
		try{
			returnValue=dateFormat.format(rs.getDate(column));
		}catch(Exception ex){
			// returnValue=null;
		}
		return (returnValue==null)?"":returnValue;
	}
	
	/** получить число Float в формате #,##*/
	private String getFormatCurrencyByString(ResultSet rs, String column){
		String returnValue=null;
		try{
			returnValue=MessageFormat.format("{0,number,#0.00}",new Object[]{rs.getFloat(column)});
		}catch(Exception ex){
			// returnValue=null;
		}
		return (returnValue==null)?"0.00":returnValue;
	}
	
	/** получить число Float в формате #,## */
	private String getFormatCurrencyByFloat(Float value){
		return MessageFormat.format("{0,number,#0.00}",new Object[]{value});
	};

	/** получить число Float в формате #,##, просуммировав два числа */
	@SuppressWarnings("unused")
	private String getFormatCurrencyByString(ResultSet rs, String column, String column2){
		String returnValue=null;
		try{
			returnValue=MessageFormat.format("{0,number,#0.00}",new Object[]{rs.getFloat(column)+rs.getFloat(column2)});
		}catch(Exception ex){
			// returnValue=null;
		}
		return (returnValue==null)?"0.00":returnValue;
	}
	
	/** возвращает положительное значение, если Float значение в ResultSet отличное от 0*/
	private Float getFloatFromResultSet(ResultSet rs, String column){
		float returnValue=0;
		try{
			returnValue=rs.getFloat(column);
		}catch(Exception ex){
			// returnValue=0;
		}
		return returnValue;
	}

	/** получить из набора данных указанную колонку со значением, и так же вывести в виде числа с 4 знаками после запятой */
	private String getFloatFromResultSetFour(ResultSet rs, String column){
		String returnValue=null;
		try{
			returnValue=MessageFormat.format("{0,number,#0.0000}",new Object[]{rs.getFloat(column)});
		}catch(Exception ex){
			// returnValue=null;
		}
		return (returnValue==null)?"0.0000":returnValue;
	}
	/** получить из набора данных указанную колонку со значением, и так же вывести в виде числа с 5 знаками после запятой */
	private String getFloatFromResultSetFive(ResultSet rs, String column){
		String returnValue=null;
		try{
			returnValue=MessageFormat.format("{0,number,#0.00000}",new Object[]{rs.getFloat(column)});
		}catch(Exception ex){
			// returnValue=null;
		}
		return (returnValue==null)?"0.0000":returnValue;
	}
	
	/** вывести в Excel справку по "Площадям" 
	 * @param month - месяц вывода данных
	 * @param year - год вывода данных
	 * @param jk - жэк
	 * @param viddil - отделение 
	 * @param date - дата вывода 
	 * @param pathToExcelFile - путь к файлу Excel
	 * @throws Exception - исключение, которое выбросило 
	 */
	public void printSquareToExcel(String month, 
								   String year, 
								   String jk,
								   String viddil,
								   Calendar date,
								   String pathToExcelFile) throws Exception{
		// создать файл Excel
		// settings for workbook
		WorkbookSettings settings=new WorkbookSettings();
		settings.setEncoding("WINDOWS-1251");
		WritableWorkbook workbook=Workbook.createWorkbook(new File(pathToExcelFile),settings);
		// create sheet into workbook
		WritableSheet sheet=workbook.createSheet(month+" "+year, 0);
		// вывести шапку
		CellView cellView=new CellView();
		WritableCellFormat center=new WritableCellFormat();
		WritableCellFormat right=new WritableCellFormat();
		WritableCellFormat left=new WritableCellFormat();
		WritableCellFormat rightFloat=new WritableCellFormat(new NumberFormat("#.00"));
		try{
			left.setAlignment(Alignment.LEFT);
			center.setAlignment(Alignment.CENTRE);
			right.setAlignment(Alignment.RIGHT);
			rightFloat.setAlignment(Alignment.RIGHT);
		}catch(Exception ex){
		}
		//улица+номер дома+номер помещения (квартиры) - 
		cellView.setSize(7000);
		sheet.setColumnView(0, cellView);
		cellView.setFormat(left);
		//назва организации орендаря из arn
		cellView.setSize(10000);
		cellView.setFormat(left);
		sheet.setColumnView(1, cellView);
		//npom->nd номер договра
		cellView.setSize(2500);
		cellView.setFormat(center);
		sheet.setColumnView(2, cellView);
		//npom->dd дата договора
		cellView.setSize(3000);
		cellView.setFormat(center);
		sheet.setColumnView(3, cellView);
		//npom->oplp орендована площа
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(4, cellView);
		//юр.адрес
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(4, cellView);
		// вывести заголовок
		sheet.addCell(new Label(2,0,"Мiсяць:"+month+"  Рiк:"+year+"  ЖЕК:"+jk+"  Вiддiлення:"+viddil));
		float amount=0;
		float amountHome=0;
		String homeName="";
		String homeNumber="";
		String tempNumber;
		// вывести данные
		int row=2;
		for(int counter=0;counter<this.model.getRowCount();counter++){
			ResultSet rs = this.model.getResultSetByIndex(counter);
			rs.next();
			try {
				//counter<(this.model.getRowCount()-1)
				tempNumber=this.getLongEmptyString(rs,"NPOM_DM1")+" "+rs.getString("NPOM_LT1").trim();
				if((!homeName.equals(rs.getString("UL_NAIU")))||(!homeNumber.equals(tempNumber))){
					if(counter!=0){
						addAmountHome(++row, homeName, homeNumber, amountHome, sheet);
						row++;
					}
					homeName=rs.getString("UL_NAIU");
					homeNumber=tempNumber;
					amountHome=rs.getFloat("NPOM_OPLP");
				}else{
					amountHome+=rs.getFloat("NPOM_OPLP");
				}
				amount+=rs.getFloat("NPOM_OPLP");
				addRowToExcel(++row,rs,sheet);
			} catch (Exception ex) {
				System.out.println("printSelection ERROR:" + ex.getMessage());
			}
			rs.close();
		}
		addAmountHome(++row, homeName, homeNumber, amountHome, sheet);
		row+=2;
		addAmoutToEnd(row,amount,sheet);
		// закрыть файл Excel
		workbook.write();
		workbook.close();
		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pathToExcelFile);		
	}
	
	private String getDoubleSymbolFromInteger(int value){
		if(value<10){
			return "0"+value;
		}else{
			return Integer.toString(value);
		}
	}
	
	/** получить номер месяца и год, исходя из названия таблицы PL */
	private String getMonthAndYear(){
		this.tableTnName.substring(4, 6);
		this.tableTnName.substring(6, 8);
		return this.tableTnName.substring(6,8)+"."+this.tableTnName.substring(4, 6);
	}
	/** получить следующий номер месяца и год, исходя из названия таблицы PL*/
	private String getNextMonthAndYear(){
		this.tableTnName.substring(4, 6);
		this.tableTnName.substring(6, 8);
		if(Integer.parseInt(this.tableTnName.substring(6, 8))!=12){
			int value=Integer.parseInt(this.tableTnName.substring(6,8));
			return getDoubleSymbolFromInteger(value+1)
				   +"."
			       +this.tableTnName.substring(4, 6);
		}else{
			return "01."+this.getDoubleSymbolFromInteger(Integer.parseInt(this.tableTnName.substring(6,8)));
		}
	}
	
	/** вывести в Excel оборотную ведомость для ЖЭК №1 
	 * @param month - месяц вывода данных
	 * @param year - год вывода данных
	 * @param jk - жэк ( по умолчанию всегда должен быть первый - регулируется уровнем выше)
	 * @param viddil - отделение 
	 * @param date - дата вывода 
	 * @param pathToExcelFile - путь к файлу Excel
	 * @throws Exception - исключение, которое выбросило 
	 */
	public void printOborotkaToExcel(String month, 
								   String year, 
								   String jk,
								   String viddil,
								   Calendar date,
								   String pathToExcelFile) throws Exception{
		// создать файл Excel
		// settings for workbook
		WorkbookSettings settings=new WorkbookSettings();
		settings.setEncoding("WINDOWS-1251");
		WritableWorkbook workbook=Workbook.createWorkbook(new File(pathToExcelFile),settings);
		// create sheet into workbook
		WritableSheet sheet=workbook.createSheet(month+" "+year, 0);
		// вывести шапку
		CellView cellView=new CellView();
		WritableCellFormat center=new WritableCellFormat();
		WritableCellFormat right=new WritableCellFormat();
		WritableCellFormat left=new WritableCellFormat();
		WritableCellFormat rightFloat=new WritableCellFormat(new NumberFormat("#0.00"));
		try{
			left.setAlignment(Alignment.LEFT);
			center.setAlignment(Alignment.CENTRE);
			right.setAlignment(Alignment.RIGHT);
			rightFloat.setAlignment(Alignment.RIGHT);
		}catch(Exception ex){
			System.err.println("ResultTable create Alignment Error:"+ex.getMessage());
		}
			// Font
		WritableFont times14ptBoldUnderline = new WritableFont(WritableFont.TIMES,
				   14,
				   WritableFont.BOLD,
				   false,
				   UnderlineStyle.SINGLE);
		WritableFont times10ptBold = new WritableFont(WritableFont.TIMES,
				   10,
				   WritableFont.BOLD,
				   false,
				   UnderlineStyle.NO_UNDERLINE);
		WritableFont times10ptItalic = new WritableFont(WritableFont.ARIAL,
				   10,
				   WritableFont.NO_BOLD,
				   true,
				   UnderlineStyle.NO_UNDERLINE);
		
			// Format
		WritableCellFormat timesBoldUnderline = new WritableCellFormat(times14ptBoldUnderline);
		WritableCellFormat timesBold = new WritableCellFormat(times10ptBold);
		WritableCellFormat timesAmount = new WritableCellFormat(times10ptItalic,new NumberFormat("#0.00"));
		
		timesBold.setVerticalAlignment(VerticalAlignment.CENTRE);
		timesBold.setAlignment(Alignment.CENTRE);
		timesBold.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
		timesBold.setWrap(true);
		timesBoldUnderline.setAlignment(Alignment.CENTRE);
		// вывести заголовок для таблицы
	    sheet.mergeCells(0, 0, 13, 0);
		sheet.addCell(new Label(0,0,
								"Оборотна вiдомiсть(ЖЕК 1) за :"+month+"  Рiк:"+year+"  Вiддiлення:"+viddil,
								timesBoldUnderline
								)
					 );
		// (A) порядковый номер позиции:
		cellView.setSize(1000);
		cellView.setFormat(center);
		sheet.setColumnView(0, cellView);
		sheet.mergeCells(0, 2, 0, 4);
		sheet.addCell(new Label(0,2,"№",timesBold));
		// (B) Найменування орендарів та власників
		cellView.setSize(7000);
		cellView.setFormat(left);
		sheet.setColumnView(1, cellView);
		sheet.mergeCells(1, 2, 1, 4);
		sheet.addCell(new Label(1,2,"Найменування орендарів та власників",timesBold));
		// (C) Адреса
		cellView.setSize(10000);
		cellView.setFormat(left);
		sheet.setColumnView(2, cellView);
		sheet.mergeCells(2, 2, 2, 4);
		sheet.addCell(new Label(2,2,"Адреса",timesBold));
		// (D) С-до на 01.02.09:Д-т	
			sheet.mergeCells(3, 2, 4, 3);	
			sheet.addCell(new Label(3,2,"С-до на 01."+this.getMonthAndYear(),timesBold));
		cellView.setSize(2500);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(3, cellView);
		sheet.addCell(new Label(3,4,"Д-т",timesBold));
		// (E) С-до на 01.02.09:К-т
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(4, cellView);
		sheet.addCell(new Label(4,4,"К-т",timesBold));
		// (F) Плата за примiщення
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(5, cellView);
		sheet.mergeCells(5, 2, 5, 4);
		sheet.addCell(new Label(5,2,"Плата за примiщення",timesBold));
		// (G) Експлуатацiйнi витрати
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(6, cellView);
		sheet.mergeCells(6, 2, 6, 4);
		sheet.addCell(new Label(6,2,"Експлуатацiйнi витрати",timesBold));
		// (H) Iншi нарахування
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(7, cellView);
		sheet.mergeCells(7, 2, 7, 4);
		sheet.addCell(new Label(7,2,"Iншi нарахування",timesBold));
		// (I) Разом
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(8, cellView);
		sheet.mergeCells(8, 2, 8, 4);
		sheet.addCell(new Label(8,2,"Разом",timesBold));
		// (J) ПДВ      643
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(9, cellView);
		sheet.mergeCells(9, 2, 9, 4);
		sheet.addCell(new Label(9,2,"ПДВ 643",timesBold));
		// (K) Всього нараховано
		cellView.setSize(3500);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(10, cellView);
		sheet.mergeCells(10, 2, 10, 4);
		sheet.addCell(new Label(10,2,"Всього нараховано",timesBold));
		// (Q) Всього надходжень
		cellView.setSize(3500);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(11, cellView);
		sheet.mergeCells(11, 2, 11, 4);
		sheet.addCell(new Label(11,2,"Всього надходжень",timesBold));
		// (S) С-до на 01.03.09: Д-т
			sheet.mergeCells(12, 2, 13, 3);	
			sheet.addCell(new Label(12,2,"С-до на 01."+this.getNextMonthAndYear(),timesBold));
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(12, cellView);
		sheet.addCell(new Label(12,4,"Д-т",timesBold));
		// (T) С-до на 01.03.09: К-т
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(13, cellView);
		sheet.addCell(new Label(13,4,"К-т",timesBold));
		
		sheet.addCell(new Label(15,2,"TN1"));
		sheet.addCell(new Label(16,2,"TNA"));
		// вывести данные
		int row=4;
		PreparedStatement columnQ=connection.prepareStatement("select sum(wpl) from "+this.tablePlName+" where "+this.tablePlName+".tna=? and "+this.tablePlName+".tn1=?");
		float g=0;
		float d_e=0;
		float h=0;
		float q=0;
		float s=0;
		float k=0;
		float f=0;
		String tempNumber="";
		String homeNumber="";
		String homeName="";
		int homeCounter=1;
		float[] amount=new float[11];
		
		for(int counter=0;counter<this.model.getRowCount();counter++){
			ResultSet rs = this.model.getResultSetByIndex(counter);
			rs.next();
			row++;
			try {
				// (C) Адреса
				tempNumber=this.getLongEmptyString(rs,"NPOM_DM1")+" "+rs.getString("NPOM_LT1").trim();
				if((!homeName.equals(rs.getString("UL_NAIU")))||(!homeNumber.equals(tempNumber))){
					homeCounter=0;
					homeName=rs.getString("UL_NAIU");
					homeNumber=tempNumber;
					//amountHome=rs.getFloat("NPOM_OPLP");
					if(counter>0){
						// output amount by building
						for(int index=0;index<amount.length;index++){
							sheet.addCell(new Number(3+index,row,amount[index],timesAmount));
						}
						row++;row++;
						for(int index=0;index<amount.length;index++)amount[index]=0;
					}
				}else{
					homeCounter++;
					}
				sheet.addCell(new Label(2,
										row,
										rs.getString("UL_NAIU").trim()+" "+this.getLongEmptyString(rs, "npom_dm1")+" "+rs.getString("NPOM_LT1").trim()+" "+rs.getString("NPOM_LT1_KW").trim()
										)
							  );
				
				// контрольные значения
				sheet.addCell(new Number(15,row,rs.getInt("tn1")));
				sheet.addCell(new Number(16,row,rs.getInt("tna")));
				// (A) порядковый номер позиции:
				sheet.addCell(new Label(0,row,Integer.toString(homeCounter+1)));
				// (B) Найменування орендарів та власників
				if((rs.getString("NPOM_ND")!=null)&&(!rs.getString("NPOM_ND").equals(""))){
					sheet.addCell(new Label(1,row,this.getEmptyString(rs, "arn_naim").trim()+"  ( "+this.getEmptyStringWithPrefix(rs, "NPOM_ND", "№")+" вiд "+this.getFormatDateFromTimeStamp(rs, "NPOM_DD")+" )"));
				}else{
					sheet.addCell(new Label(1,row,this.getEmptyString(rs, "arn_naim")));
				}

				// С-до на 01.02.09
				if(rs.getFloat("os_pl")>0){
					// (D) :Д-т
					sheet.addCell(new Number(3,row,rs.getFloat("os_pl")));
					// (E) :К-т
					sheet.addCell(new Number(4,row,0));
					d_e=rs.getFloat("os_pl");
					amount[0]+=d_e;
				}else{
					// (D) :Д-т
					sheet.addCell(new Number(3,row,0));
					// (E) :К-т
					sheet.addCell(new Number(4,row,Math.abs(rs.getFloat("os_pl"))));
					d_e=Math.abs(rs.getFloat("os_pl"));
					amount[1]+=d_e;
				}
				// (F) Плата за примiщення
				f=rs.getFloat("pl1")+rs.getFloat("pl14");
				sheet.addCell(new Number(5,row,f));
				amount[2]+=f;
				// (G) Експлуатацiйнi витрати
				g=rs.getFloat("pl2");
				sheet.addCell(new Number(6,row,g));
				amount[3]+=g;
				// (H) Iншi нарахування
				h=rs.getFloat("pl3")+rs.getFloat("pl4")+rs.getFloat("pl5")+rs.getFloat("pl6")+
				  rs.getFloat("pl7")+rs.getFloat("pl8")+rs.getFloat("pl9")+rs.getFloat("pl10")+
				  rs.getFloat("pl11")+rs.getFloat("pl12")+rs.getFloat("pl13");
				sheet.addCell(new Number(7,row,h));
				amount[4]+=h;
				// (I) Разом
				sheet.addCell(new Number(8,row,f+g+h));
				amount[5]+=g+h;
				// (J) ПДВ      643
				sheet.addCell(new Number(9,row,rs.getFloat("pl15")));
				amount[6]+=rs.getFloat("pl15");
				// (K) Всього нараховано
				k=g+h+rs.getFloat("pl15");
				sheet.addCell(new Number(10,row,k));
				amount[7]+=g+h+rs.getFloat("pl15");
				// (Q) Всього надходжень
				try{
					q=0;
					columnQ.setFloat(1, rs.getFloat("tna"));
					columnQ.setFloat(2, rs.getFloat("tn1"));
					ResultSet rsQ=columnQ.executeQuery();
					if(rsQ.next()){
						q=rsQ.getFloat(1);
					}
					rsQ.close();
				}catch(Exception ex){
					System.err.println("Q column Exception:"+ex.getMessage());
				};
				sheet.addCell(new Number(11,row,q));
				amount[8]+=q;
				// С-до на 01.03.09:
				s=d_e // D-E
				 +k // +K
				 -q;// -Q
				if(s>0){
					// (S)  Д-т
					sheet.addCell(new Number(12,row,s));
					// (T)  К-т
					sheet.addCell(new Number(13,row,(float)0));
					amount[9]+=s;
				}else{
					// (S)  Д-т					
					sheet.addCell(new Number(12,row,(float)0));
					// (T)  К-т
					sheet.addCell(new Number(13,row,Math.abs(s)));
					amount[10]+=Math.abs(s);
				}

			} catch (Exception ex) {
				System.out.println("printSelection ERROR:" + ex.getMessage());
			}
			rs.close();
		}
		row++;
		// output amount by building
		for(int index=0;index<amount.length;index++){
			sheet.addCell(new Number(3+index,row,amount[index]));
		}
		
		row+=2;
		//addAmoutToEnd(row,amount,sheet);
		// закрыть файл Excel
		workbook.write();
		workbook.close();
		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pathToExcelFile);		
	}
	
	
	/** вывести в Excel оборотную ведомость для ЖЭК №2 
	 * @param month - месяц вывода данных
	 * @param year - год вывода данных
	 * @param jk - жэк ( по умолчанию всегда должен быть первый - регулируется уровнем выше)
	 * @param viddil - отделение 
	 * @param date - дата вывода 
	 * @param pathToExcelFile - путь к файлу Excel
	 * @throws Exception - исключение, которое выбросило 
	 */
	public void printOborotkaToExcel2(String month, 
								   String year, 
								   String jk,
								   String viddil,
								   Calendar date,
								   String pathToExcelFile) throws Exception{
		// создать файл Excel
		// settings for workbook
		WorkbookSettings settings=new WorkbookSettings();
		settings.setEncoding("WINDOWS-1251");
		WritableWorkbook workbook=Workbook.createWorkbook(new File(pathToExcelFile),settings);
		// create sheet into workbook
		WritableSheet sheet=workbook.createSheet(month+" "+year, 0);
		// вывести шапку
		CellView cellView=new CellView();
		WritableCellFormat center=new WritableCellFormat();
		WritableCellFormat right=new WritableCellFormat();
		WritableCellFormat left=new WritableCellFormat();
		WritableCellFormat rightFloat=new WritableCellFormat(new NumberFormat("#0.00"));
		try{
			left.setAlignment(Alignment.LEFT);
			center.setAlignment(Alignment.CENTRE);
			right.setAlignment(Alignment.RIGHT);
			rightFloat.setAlignment(Alignment.RIGHT);
		}catch(Exception ex){
			System.err.println("ResultTable create Alignment Error:"+ex.getMessage());
		}
			// Font
		WritableFont times14ptBoldUnderline = new WritableFont(WritableFont.TIMES,
				   14,
				   WritableFont.BOLD,
				   false,
				   UnderlineStyle.SINGLE);
		WritableFont times10ptBold = new WritableFont(WritableFont.TIMES,
				   10,
				   WritableFont.BOLD,
				   false,
				   UnderlineStyle.NO_UNDERLINE);
		/*
		WritableFont times10ptItalic = new WritableFont(WritableFont.ARIAL,
				   10,
				   WritableFont.NO_BOLD,
				   true,
				   UnderlineStyle.NO_UNDERLINE);
		*/
			// Format
		WritableCellFormat timesBoldUnderline = new WritableCellFormat(times14ptBoldUnderline);
		WritableCellFormat timesBold = new WritableCellFormat(times10ptBold);
		//WritableCellFormat timesAmount = new WritableCellFormat(times10ptItalic,new NumberFormat("#0.00"));
		
		timesBold.setVerticalAlignment(VerticalAlignment.CENTRE);
		timesBold.setAlignment(Alignment.CENTRE);
		timesBold.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
		timesBold.setWrap(true);
		timesBoldUnderline.setAlignment(Alignment.CENTRE);
		// вывести заголовок для таблицы
	    sheet.mergeCells(0, 0, 13, 0);
		sheet.addCell(new Label(0,0,
								"Оборотна вiдомiсть(ЖЕК 2) за :"+month+"  Рiк:"+year+"  Вiддiлення:"+viddil,
								timesBoldUnderline
								)
					 );
		// (A) порядковый номер позиции:
		cellView.setSize(1000);
		cellView.setFormat(center);
		sheet.setColumnView(0, cellView);
		sheet.mergeCells(0, 2, 0, 4);
		sheet.addCell(new Label(0,2,"№",timesBold));
		// (B) Найменування орендарів та власників
		cellView.setSize(7000);
		cellView.setFormat(left);
		sheet.setColumnView(1, cellView);
		sheet.mergeCells(1, 2, 1, 4);

		sheet.addCell(new Label(1,2,"Найменування орендарів та власників",timesBold));
		// (C) Адреса
		cellView.setSize(10000);
		cellView.setFormat(left);
		sheet.setColumnView(2, cellView);
		sheet.mergeCells(2, 2, 2, 4);
		sheet.addCell(new Label(2,2,"Адреса",timesBold));
		// (D) С-до на 01.02.09:Д-т	
		sheet.mergeCells(3, 2, 4, 3);	
		sheet.addCell(new Label(3,2,"С-до на 01."+this.getMonthAndYear(),timesBold));
			cellView.setSize(2500);
			cellView.setFormat(rightFloat);
			sheet.setColumnView(3, cellView);
			sheet.addCell(new Label(3,4,"Д-т",timesBold));
			// (E) С-до на 01.02.09:К-т
			cellView.setSize(3000);
			cellView.setFormat(rightFloat);
			sheet.setColumnView(4, cellView);
			sheet.addCell(new Label(4,4,"К-т",timesBold));
		// (F) Холодне водопостач.
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(5, cellView);
		sheet.mergeCells(5, 2, 5, 4);
		sheet.addCell(new Label(5,2,"Холодне водопостач.",timesBold));
		// (G) Гаряче водопостач.
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(6, cellView);
		sheet.mergeCells(6, 2, 6, 4);
		sheet.addCell(new Label(6,2,"Гаряче водопостач.",timesBold));
		// (H) Опалення
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(7, cellView);
		sheet.mergeCells(7, 2, 7, 4);
		sheet.addCell(new Label(7,2,"Опалення",timesBold));
		// (I) Ел. постачання
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(8, cellView);
		sheet.mergeCells(8, 2, 8, 4);
		sheet.addCell(new Label(8,2,"Ел. постачання",timesBold));
		// (J) 4 % вiдш
		cellView.setSize(3000);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(9, cellView);
		sheet.mergeCells(9, 2, 9, 4);
		sheet.addCell(new Label(9,2,"4 % вiдш",timesBold));
		// (K) Разом
		cellView.setSize(3500);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(10, cellView);
		sheet.mergeCells(10, 2, 10, 4);
		sheet.addCell(new Label(10,2,"Разом",timesBold));

		// (L) ПДВ 643
		cellView.setSize(3500);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(11, cellView);
		sheet.mergeCells(11, 2, 11, 4);
		sheet.addCell(new Label(11,2,"ПДВ 643",timesBold));

		// (M) Всього нараховано
		cellView.setSize(3500);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(12, cellView);
		sheet.mergeCells(12, 2, 12, 4);
		sheet.addCell(new Label(12,2,"Всього нараховано",timesBold));

		// (S) Всього надходжень
		cellView.setSize(3500);
		cellView.setFormat(rightFloat);
		sheet.setColumnView(13, cellView);
		sheet.mergeCells(13, 2, 13, 4);
		sheet.addCell(new Label(13,2,"Всього надходжень",timesBold));
		
		// (U) С-до на 01.03.09: Д-т
		sheet.mergeCells(14, 2, 15, 3);	
		sheet.addCell(new Label(14,2,"С-до на 01."+this.getNextMonthAndYear(),timesBold));
			cellView.setSize(3000);
			cellView.setFormat(rightFloat);
			sheet.setColumnView(14, cellView);
			sheet.addCell(new Label(14,4,"Д-т",timesBold));
			// (V) С-до на 01.03.09: К-т
			cellView.setSize(3000);
			cellView.setFormat(rightFloat);
			sheet.setColumnView(15, cellView);
			sheet.addCell(new Label(15,4,"К-т",timesBold));
		
		sheet.addCell(new Label(16,2,"TN1"));
		sheet.addCell(new Label(17,2,"TNA"));
		// вывести данные
		int row=4;
		PreparedStatement columnK=connection.prepareStatement("select sum(wpl) from "+this.tablePlName+" where "+this.tablePlName+".tna=? and "+this.tablePlName+".tn1=?");
		float f=0;
		float g=0;
		float h=0;
		float i=0;
		float j=0;
		float k=0;
		float l=0;
		float d=0;
		float e=0;
		float m=0;
		float s=0;
		float u=0;
		String groupAddress=null;
		int groupCounter=0;
		float[] amount=new float[13];
		for(int counter=0;counter<this.model.getRowCount();counter++){
			ResultSet rs = this.model.getResultSetByIndex(counter);
			rs.next();
			row++;
			try {
				String currentAddress=rs.getString("UL_NAIU").trim()+this.getLongEmptyString(rs,"NPOM_DM1")+" "+rs.getString("NPOM_LT1").trim();
				if(currentAddress!=groupAddress){
					// new Address
					groupCounter=0;
					groupAddress=currentAddress;
					if(groupAddress!=null){
						// output Amount by Address
						for(int index=0;counter<amount.length;index++){
							sheet.addCell(new Number(index+3,row,amount[index]));
						}
						row++;
						row++;
					}else{
						// it is first line into output 
					}
				}
				groupCounter++;
				
				// (C) Адреса
				sheet.addCell(new Label(2,
						row,
						rs.getString("UL_NAIU").trim()+" "+this.getLongEmptyString(rs, "npom_dm1")+" "+rs.getString("NPOM_LT1").trim()+" "+rs.getString("NPOM_LT1_KW").trim()
						)
				);
				// контрольные значения
				sheet.addCell(new Number(16,row,rs.getInt("tn1")));
				sheet.addCell(new Number(17,row,rs.getInt("tna")));
				// (A) порядковый номер позиции:
				sheet.addCell(new Label(0,row,Integer.toString(groupCounter)));
				// (B) Найменування орендарів та власників
				if((rs.getString("NPOM_ND")!=null)&&(!rs.getString("NPOM_ND").equals(""))){
					sheet.addCell(new Label(1,row,this.getEmptyString(rs, "arn_naim").trim()+"  ( "+this.getEmptyStringWithPrefix(rs, "NPOM_ND", "№")+" вiд "+this.getFormatDateFromTimeStamp(rs, "NPOM_DD")+" )"));
				}else{
					sheet.addCell(new Label(1,row,this.getEmptyString(rs, "arn_naim")));
				}

				// С-до на 01.02.09
				if(rs.getFloat("os_pl")>0){
					// (D) :Д-т
					d=rs.getFloat("os_pl");
					sheet.addCell(new Number(3,row,d));
					amount[0]+=d;
					// (E) :К-т
					e=0;
					sheet.addCell(new Number(4,row,e));
				}else{
					// (D) :Д-т
					d=0;
					sheet.addCell(new Number(3,row,d));
					// (E) :К-т
					e=Math.abs(rs.getFloat("os_pl"));
					sheet.addCell(new Number(4,row,e));
					amount[1]+=e;
				}
				// (F) Холодне водопостач.
				f=rs.getFloat("pl7");
				sheet.addCell(new Number(5,row,f));
				amount[2]+=f;

				// (G) Гаряче водопостач.
				g=rs.getFloat("pl8");
				sheet.addCell(new Number(6,row,g));
				amount[3]+=g;

				// (H) Опалення
				h=rs.getFloat("pl10");
				sheet.addCell(new Number(7,row,h));
				amount[4]+=h;

				// (I) Ел. постачання
				i=rs.getFloat("pl9");
				sheet.addCell(new Number(8,row,i));
				amount[5]+=i;

				// (J) 4 % вiдш
				j=rs.getFloat("pl11");
				sheet.addCell(new Number(9,row,j));
				amount[6]+=j;

				// (K) Разом
				k=f+g+h+i+j;
				sheet.addCell(new Number(10,row,k));
				amount[7]+=k;

				// (L) ПДВ 643
				l=rs.getFloat("pl15");
				sheet.addCell(new Number(11,row,l));
				amount[8]+=l;

				// (M) Всього нараховано
				m=k+l;
				sheet.addCell(new Number(12,row,m));
				amount[9]+=m;

				// (S) Всього надходжень
				//k=g+h+rs.getFloat("pl15");
				try{
					s=0;
					columnK.setFloat(1, rs.getFloat("tna"));
					columnK.setFloat(2, rs.getFloat("tn1"));
					ResultSet rsK=columnK.executeQuery();
					if(rsK.next()){
						s=rsK.getFloat(1);
					}
					rsK.close();
				}catch(Exception ex){
					System.err.println("K column Exception:"+ex.getMessage());
				};
				sheet.addCell(new Number(13,row,s));
				amount[10]+=s;
				// С-до на 01.03.09:
				u=d-e+m-s;
				if(u>0){
					// (U)  Д-т
					sheet.addCell(new Number(14,row,u));
					amount[11]+=u;
					// (V)  К-т
					sheet.addCell(new Number(15,row,(float)0));
				}else{
					// (U)  Д-т					
					sheet.addCell(new Number(14,row,(float)0));
					// (V)  К-т
					sheet.addCell(new Number(15,row,Math.abs(u)));
					amount[12]+=Math.abs(u);
				}

			} catch (Exception ex) {
				System.out.println("printSelection ERROR:" + ex.getMessage());
			}
			rs.close();
		}
		//addAmoutToEnd(row,amount,sheet);
		// закрыть файл Excel
		workbook.write();
		workbook.close();
		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pathToExcelFile);		
	}

	
	/** добавить запись в лист 
	 * @param row - строка, в которую следует записать данные из ResultSet
	 * @param rs - набор данных 
	 * @param sheet - лист, в который следует записать данные 
	 */
	private void addRowToExcel(int row, ResultSet rs, WritableSheet sheet){
		try{
			sheet.addCell(new Label(0,row,rs.getString("UL_NAIU").trim()
										 +" "
										 +this.getLongEmptyString(rs,"NPOM_DM1")
										 +getEmptyStringWithPrefix(rs,"NPOM_LT1",", ")
										 +getEmptyStringWithPrefix(rs,"NPOM_LT1_KW",", ")
										 ));// улица
			sheet.addCell(new Label(1,row,rs.getString("ARN_NAIM")));// название организации
			sheet.addCell(new Label(2,row,rs.getString("NPOM_ND")));// номер договора
			sheet.addCell(new Label(3,row,this.getFormatDateFromTimeStamp(rs, "NPOM_DD")));// дата договора
			sheet.addCell(new Number(4,row,rs.getFloat("NPOM_OPLP")));// арендованная площадь
			sheet.addCell(new Label(5,row,rs.getString("ARN_ADR")));// юр.адрес
		}catch(Exception ex){
			System.err.println("addRowToExcel Exception: "+ex.getMessage());
		}
	}
	/** добавить сумму в конец листа */
	private void addAmoutToEnd(int row, float amount, WritableSheet sheet){
		try{
			sheet.addCell(new Label(3,row,"Всього:"));
			sheet.addCell(new Number(4,row,amount));
		}catch(Exception ex){
			System.err.println("addAmount ToExcel:"+ex.getMessage());
		}
	}
	
	/** добавить в лист запись о сумме по выделенному дому 
	 * @param row - строка
	 * @param homeName - имя дома
	 * @param numberName - номер дома
	 * @param amount - сумма
	 * @param sheet - лист
	 */
	private void addAmountHome(int row, String homeName, String numberName, float amount, WritableSheet sheet){
		try{
			sheet.addCell(new Label(0,row,homeName.trim()+" "+numberName));
			sheet.addCell(new Label(3,row,"Сума:"));
			sheet.addCell(new Number(4,row,amount));
		}catch(Exception ex){
			System.err.println("addAmount ToExcel:"+ex.getMessage());
		}
	}
	
	
	/** Напечатать выделенную запись в виде Счет-фактуры 
	 * @param monthName - имя месяца, по которому создается отчет
	 * @param year - год, по которому создается отчет 
	 * @param now - дата, по которой создается отчет
	 * @param isJk1 - если нужно выводить на печать Жэк1
	 * @param showBord - отображать ли задолженности по указанным критериям
	 * @param showProplata - включить в отображение Проплаты
	 * @param pathToOutFile - путь к файлу для вывода данных
	 * */
	public void printSelection(String monthName, 
			   				   String year, 
			   				   Calendar now,
			   				   boolean isJk1,
			   				   boolean showBorg,
			   				   boolean showProplata,
			   				   String pathToOutFile){
		ResultSet rs=this.model.getResultSetByIndex(this.convertRowIndexToModel(this.getSelectedRow()));
		try{
			FileOutputStream fos=new FileOutputStream(pathToOutFile);
			BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(fos));
			this.printResultSet(monthName, year, now, pathToOutFile, rs,isJk1,showBorg,showProplata, writer,true,true);
			writer.close();
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+pathToOutFile);
			rs.close();
		}catch(Exception ex){
			System.out.println("printSelection ERROR:"+ex.getMessage());
		}
	}
	/** Напечатать выделенную запись в виде Акта 
	 * @param monthName - имя месяца, по которому создается отчет
	 * @param year - год, по которому создается отчет 
	 * @param now - дата, по которой создается отчет
	 * */
	public void printSelectionAkt(String monthName, 
			   				   String year, 
			   				   Calendar now,
			   				   String pathToOutFile){
		try{
			FileOutputStream fos=new FileOutputStream(pathToOutFile);
			BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(fos));
			ResultSet rs=this.model.getResultSetByIndex(this.convertRowIndexToModel(this.getSelectedRow()));
			this.printResultSetAsAkt(monthName, year, now, rs,writer,true,false,false);
			rs.previous();
			this.printResultSetAsAkt(monthName, year, now, rs,writer,false,true,true);
			writer.close();
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+pathToOutFile);
			rs.close();
		}catch(Exception ex){
			System.out.println("printSelectionAkt ERROR:"+ex.getMessage());
		}
	}

	public void printAll(String monthName, 
						 String year, 
						 Calendar now,
						 boolean isJk1,
						 boolean showBorg,
						 boolean showProplata,
						 String pathToOutFile) {
		try{
			FileOutputStream fos=new FileOutputStream(pathToOutFile);
			BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(fos));
			for(int counter=0;counter<this.model.getRowCount();counter++){
				ResultSet rs = this.model.getResultSetByIndex(counter);
				try {
					if(counter==0){
						this.printResultSet(monthName, year, now, pathToOutFile, rs,isJk1,showBorg, showProplata, writer,true,false);
					}else{
						if(counter<(this.model.getRowCount()-1)){
							this.printResultSet(monthName, year, now, pathToOutFile, rs,isJk1,showBorg, showProplata, writer,false,false);
						}else{
							this.printResultSet(monthName, year, now, pathToOutFile, rs,isJk1,showBorg,  showProplata,writer,false,true);
						}
					}
				rs.close();	
				} catch (Exception ex) {
					System.out.println("printSelection ERROR:" + ex.getMessage());
				}
			}
			writer.close();
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pathToOutFile);
		}catch(Exception ex){
			System.out.println("printAll ERROR:" + ex.getMessage());
		}
	}
	
	public void printAllAkt(String monthName, 
			 String year, 
			 Calendar now,
			 String pathToOutFile) {
		try {
			FileOutputStream fos = new FileOutputStream(pathToOutFile);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					fos));
			for (int counter = 0; counter < this.model.getRowCount(); counter++) {
				ResultSet rs = this.model.getResultSetByIndex(counter);
				try {
					if (counter == 0) {
						this.printResultSetAsAkt(monthName, year, now,rs, writer, true, false,false);
						rs.previous();
						this.printResultSetAsAkt(monthName, year, now,rs, writer, false, false,true);
					} else {
						if (counter < (this.model.getRowCount() - 1)) {
							this.printResultSetAsAkt(monthName, year, now,rs, writer, false, false,false);
							rs.previous();
							this.printResultSetAsAkt(monthName, year, now,rs, writer, false, false,true);
						} else {
							this.printResultSetAsAkt(monthName, year, now,rs, writer, false, false,false);
							rs.previous();
							this.printResultSetAsAkt(monthName, year, now,rs, writer, false, true,(counter&1)==1);
						}
					}
					rs.close();
				} catch (Exception ex) {
					System.out.println("printSelection ERROR:"
							+ ex.getMessage());
				}
			}
			writer.close();
			Runtime.getRuntime().exec(
					"rundll32 url.dll,FileProtocolHandler " + pathToOutFile);
		} catch (Exception ex) {
			System.out.println("printAllAkt ERROR:" + ex.getMessage());
		}
	}
	

	/** печать ResultSet как акта 
	 * @param monthName - месяц
	 * @param year - год
	 * @param now - время вывода
	 * @param pathToOutFile - путь к файлу, в который нужно выводить данные
	 * @param rs - набор данных для вывода файла 
	 * @param printWriter - 
	 * @param printHeader
	 * @param printFooter
	 * @param setPageDelimeter
	 */
	public void printResultSetAsAkt(String monthName, 
							   		String year, 
							   		Calendar now,
							   		ResultSet rs,
							   		BufferedWriter printWriter,
							   		boolean printHeader,
							   		boolean printFooter,
							   		boolean setPageDelimeter){
		MultiReplacer multi=new MultiReplacer("<tr><td width=100></td><td align=\"left\" width=300 >{0}</td><td align=\"right\" width=150> {1} грн.</td><td width=50></td></tr>",
											  "<tr><td width=100></td><td align=\"left\" width=300 ></td><td align=\"right\" width=150> </td><td width=50></td></tr>");
		try{
			if(rs.next()){
				// data for output
				ArrayList<ReplaceValue> values=new ArrayList<ReplaceValue>();
				multi.clearObjects();
				try{
					values.add(new SingleReplacer(rs.getString("NPOM_ND")));//1 - номер договора по акту
					values.add(new SingleReplacer(dateFormat.format(now.getTime())));//2 - дата проведения договора 
					values.add(new SingleReplacer(rs.getString("ARN_NAIM")));// 3 - полное имя 
					values.add(new SingleReplacer(rs.getString("UL_NAIU")+" &nbsp "
		  				      					  +this.getLongEmptyString(rs, "npom_dm1")+"&nbsp "
		  				      					  +rs.getString("NPOM_LT1").trim()+"&nbsp "
		  				      					  +rs.getString("NPOM_LT1_KW").trim()
		  				      					  +", "+MessageFormat.format("{0,number,#0.00}", new Object[]{getFloatFromResultSet(rs,"NPOM_OPLP")})+" м2."
		  				      					  )
							  );// 4 - полный адрес ( + площадь )
					values.add(new SingleReplacer(monthName+" "+year));//5 - месяц и год
					Float currentFloat;
					float amount=0;
					currentFloat=getFloatFromResultSet(rs,"PL1");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(1),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL2");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(2),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL3");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(3),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL4");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(4),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL5");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(5),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL6");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(6),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL7");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(7),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL8");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(8),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL9");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(9),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL10");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(10),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL11");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(11),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL12");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(12),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL13");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(13),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					currentFloat=getFloatFromResultSet(rs,"PL14");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(14),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					values.add(multi);
					values.add(new SingleReplacer(getFormatCurrencyByString(rs,"PL15")));// 7 -ПДВ
					values.add(new SingleReplacer(getFormatCurrencyByString(rs,"PL")));// 8 - Разом

					values.add(new SingleReplacer(this.rekvisit.get(0)));
					values.add(new SingleReplacer(this.rekvisit.get(4)));
					values.add(new SingleReplacer(this.rekvisit.get(3)));
					values.add(new SingleReplacer(this.rekvisit.get(2)));
					
					values.add(new SingleReplacer(rs.getString("ARN_NAIM")));// 9 - полное имя 
					values.add(new SingleReplacer(getLongEmptyString(rs,"ARN_RASCH1")));// 10 - расчетный счет
					values.add(new SingleReplacer(rs.getString("MFO_NAME"))); // 11 - банк
					values.add(new SingleReplacer(getLongEmptyString(rs,"ARN_MFO1")));//12 - мфо
					values.add(new SingleReplacer(getLongEmptyString(rs,"ARN_OKP")));//13 - код банка
					if(setPageDelimeter==true){
						values.add(new SingleReplacer("<p style=\"page-break-before: always\">"));// page delimeter
					}else{
						values.add(new SingleReplacer(""));
					}
				}catch(Exception ex){
					System.out.println("Exception: "+ex.getMessage());
				}
				
				HtmlReplacer replacer=new HtmlReplacer(this.pathToPatternAkt,
													   "WINDOWS-1251",
													   "value",
													   values);
				// INFO место сохранения созданного шаблона
				replacer.printResultToOutputStream(printWriter,printHeader,"Акт",printFooter);
				System.out.println("printSelection: OK");
			}else{
				JOptionPane.showMessageDialog(this, "Data not found");
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "Exception:"+ex.getMessage());
		}
		
	}
	
	/** печать ResultSet как счет-фактуры 
	 * @param monthName - имя месяца, по которому создается отчет
	 * @param year - год, по которому создается отчет
	 * @param pathToOutFile - путь к файлу для вывода данных
	 * @param rs - набор данных 
	 * @param showBord - отображать ли задолженности по указанным критериям
	 * @param showProplata - включить в расчет проведенные проплаты 
	 * @param printWriter - объект для вывода данных
	 * @param printHeader - печать заголовка
	 * @param printFooter - print footer
	 * */
	private void printResultSet(String monthName, 
							   String year, 
							   Calendar now,
							   String pathToOutFile,
							   ResultSet rs,
							   boolean isJk1,
							   boolean showBorg,
							   boolean showProplata,
							   BufferedWriter printWriter,
							   boolean printHeader,
							   boolean printFooter){
		MultiReplacer multi=new MultiReplacer("<tr><td width=200 align=\"center\">{0}</td><td width=80 align=\"center\">{1}</td><td width=80 align=\"right\">{2}</td><td width=110 align=\"right\">{3}</td><td width=80 align=\"right\">{4}</td><td ></td></tr>",
											  "<tr><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
		try{
			if(rs.next()){
				ResultSet rsProplata=null;
				if(showProplata==true){
					rsProplata=this.model.getResultSetForProplata(rs.getFloat("TNA"), rs.getFloat("TN1"));
				}
				// data for output
				ArrayList<ReplaceValue> values=new ArrayList<ReplaceValue>();
				multi.clearObjects();
				try{
					values.add(new SingleReplacer(getLongEmptyString(rs,"NPOM_PLAT")));
					values.add(new SingleReplacer(MessageFormat.format("{0,number,#0}", new Object[]{new Float(rs.getFloat("npom_jk1"))})));
					values.add(new SingleReplacer(dateFormat.format(now.getTime())));
					for(int counter=0;counter<7;counter++){
						values.add(new SingleReplacer(this.rekvisit.get(counter)));
					}
					values.add(new SingleReplacer(rs.getString("ARN_NAIM")));
					values.add(new SingleReplacer(rs.getString("ARN_ADR")));
					values.add(new SingleReplacer(getLongEmptyStringWithPrefix(rs,"ARN_OKP",8,'0')));// платник.код
					values.add(new SingleReplacer(getLongEmptyString(rs,"ARN_MFO1")));
					values.add(new SingleReplacer(getLongEmptyString(rs,"ARN_RASCH1")));
					values.add(new AddPrefixIsNotEmptyRaplacer(",&nbsp",rs.getString("MFO_NAME")));
					if(isJk1==true){
						// isJk1
						values.add(new SingleReplacer(""));
					}else{
						// isJk2
						values.add(new SingleReplacer("комунальних "));
					}
					values.add(new SingleReplacer(monthName+" "+year));
					values.add(new SingleReplacer(rs.getString("UL_NAIU")+" &nbsp "
							  				      //+rs.getString("NPOM_LT1_KW")+"&nbsp "
							  				      +this.getLongEmptyString(rs, "npom_dm1")+"&nbsp "
							  				      +rs.getString("NPOM_LT1")+"&nbsp "
							  				      +rs.getString("NPOM_LT1_KW")+",&nbsp "
												  +" &nbsp&nbsp ( дог № &nbsp "
												  +rs.getString("NPOM_ND")+" &nbsp вiд &nbsp "
												  +getFormatDateFromTimeStamp(rs,"NPOM_DD")
												  +")")
												  );
					
					Float currentFloat;
					float amount=0;
					String prefixBorg="Борг ";
					// ----------------------- 1 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL1");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL1");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(1),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// Борг is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL1");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(1),
								  this.fieldValue.get(1),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}",
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 2 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL2");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL2");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(2),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// Борг is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL2");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(2),
								  this.fieldValue.get(2),
								  getFloatFromResultSetFour(rs,"NPOM_OPLP"),
								  getFloatFromResultSetFive(rs,"ura_pp_stpl2"),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 3 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL3");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL3");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(3),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// Борг is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL3");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(3),
								  this.fieldValue.get(3),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 4 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL4");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL4");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(4),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// Борг is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL4");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(4),
								  this.fieldValue.get(4),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 5 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL5");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL5");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(5),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// Борг is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL5");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(5),
								  this.fieldValue.get(5),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 6 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL6");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL6");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(6),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// Борг is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL6");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(6),
								  this.fieldValue.get(6),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 7 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL7");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL7");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(7),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// Борг is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL7");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(7),
								  this.fieldValue.get(7),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 8 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL8");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL8");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(8),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// Борг is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL8");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(8),
								  this.fieldValue.get(8),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 9 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL9");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL9");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(9),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// Борг is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL9");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(9),
								  this.fieldValue.get(9),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 10 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL10");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL10");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(10),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// Борг is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL10");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(10),
								  this.fieldValue.get(10),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 11 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL11");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL11");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(11),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// Борг is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL11");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(11),
								  this.fieldValue.get(11),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 12 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL12");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL12");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(12),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// Борг is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL12");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(12),
								  this.fieldValue.get(12),
								  "1,000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 13 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL13");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL13");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(13),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// Борг is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL13");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(13),
								  this.fieldValue.get(13),
								  "1,0000",
								  MessageFormat.format("{0,number,#.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					// ----------------------- 14 ----------------------------------
					if((showBorg)||(showProplata)){
						currentFloat=0f;
						if(showBorg){
							currentFloat+=getFloatFromResultSet(rs,"OS_PL14");
						}
						if(showProplata){
							rsProplata.beforeFirst();
							while(rsProplata.next()){
								currentFloat-=getFloatFromResultSet(rsProplata,"PL14");
							}
						}
						amount+=currentFloat;
						if(currentFloat!=0){
							multi.add(prefixBorg+this.fieldName.get(14),
									  "",
									  "",
									  "",
									  MessageFormat.format("{0,number,#0.00}", 
											  			   new Object[]{currentFloat}
									  					   )
									  );
						}else{
							// Борг is empty
						}
					}
					currentFloat=getFloatFromResultSet(rs,"PL14");
					amount+=currentFloat;
					if(currentFloat>0){
						multi.add(this.fieldName.get(14),
								  this.fieldValue.get(14),
								  "1,0000",
								  MessageFormat.format("{0,number,#0.00000}", 
							  			   new Object[]{(Double)((double)Math.round(currentFloat*100)/100)}
					  					   ),
								  MessageFormat.format("{0,number,#0.00}", 
										  			   new Object[]{currentFloat}
								  					   )
								  );
					}
					values.add(multi);
					values.add(new SingleReplacer(MessageFormat.format("{0,number,#0.00}", new Object[]{new Float(amount)})));// Всього
					float amountPl15=0; // ПДВ
					float amountPl=0; // Разом

					if(showBorg){
						amountPl15+=rs.getFloat("OS_PL15");
						amountPl+=rs.getFloat("OS_PL");
					}
					if(showProplata){
						rsProplata.beforeFirst();
						while(rsProplata.next()){
							amountPl15-=rsProplata.getFloat("PL15");
							amountPl-=rsProplata.getFloat("PL");
						}
					}
					amountPl15+=rs.getFloat("PL15");
					amountPl+=rs.getFloat("PL");
					values.add(new SingleReplacer(getFormatCurrencyByFloat(amountPl15)));
					values.add(new SingleReplacer(getFormatCurrencyByFloat(amountPl)));
					values.add(new SingleReplacer(moneyToString.moneytostr(new Double(amountPl))));
					values.add(new SingleReplacer("<p style=\"page-break-before: always\">"));// page delimeter
				}catch(Exception ex){
					System.out.println("Exception: "+ex.getMessage());
				}
				if(rsProplata!=null){
					try{
						rsProplata.close();
					}catch(Exception ex){}
				}
				HtmlReplacer replacer=new HtmlReplacer(this.pathToPattern,
													   "WINDOWS-1251",
													   "value",
													   values);
				// INFO место сохранения созданного шаблона
				replacer.printResultToOutputStream(printWriter,printHeader,"Счет-фактура",printFooter);
				System.out.println("printSelection: OK");
			}else{
				JOptionPane.showMessageDialog(this, "Data not found");
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "Exception:"+ex.getMessage());
		}
	}
	private MoneyToString moneyToString=new MoneyToString();
	
	/** "освежить" данные 
	 * @param currentDate - текущая дата, по которой происходит выборка
	 * @param month - месяц(0..11), по которому необходимо загрузить файл 
	 * @param year - год
	 * @param jk1 - жек 1
	 * @param jk2 - жек 2
	 * @param borg - нужно ли печатать Задолженности
	 * @param proplata - нужно ли печетать проплату
	 * @param viddil - номер отдела
	 * @param plataAll - ЖЭК №1 - все данные  
	 * @param plataYes - ЖЭК №1 - только если есть плата за помещение  
	 * @param plataNo - ЖЭК №1 - только если нет платы за помещение  
	 * @param pochta - нужно отбирать по данным из файла Pochta.dbf, а не из отделов
	 */
	public void refreshData(Date currentDate,
							int month, 
							int year, 
							boolean jk1, 
							boolean jk2,
							String viddil,
							boolean plataAll,
							boolean plataYes,
							boolean plataNo,
							boolean pochta){
		fillUraWp();
		this.tableTnName=MessageFormat.format("tn{0,number,0000}{1,number,00}", new Object[]{new Integer(year),new Integer(month+1)});
		this.tableOsName=MessageFormat.format("os{0,number,0000}{1,number,00}", new Object[]{new Integer(year),new Integer(month+1)});
		this.tablePlName=MessageFormat.format("pl{0,number,0000}{1,number,00}", new Object[]{new Integer(year),new Integer(month+1)});
		if(jk1==true){
			this.whereJK=" npom.JK1=1 ";
			if(plataAll==false){
				if(plataYes==true){
					this.whereJK+="AND "+this.tableTnName+".pl1<>0 ";
				}
				if(plataNo==true){
					this.whereJK+="AND "+this.tableTnName+".pl1=0 ";
				}
			}
		}
		if(jk2==true){
			this.whereJK=" npom.JK1=2 ";
		}
		this.viddil=viddil;
		this.pochta=pochta;
		this.model.refresh(this.getSqlText(currentDate),
						   this.getSqlWhereId(), 
						   this.getSqlOrderBy(),
						   this.getSqlProplata()
						   );
		// загрузка в переменные реквизитов Житлосервис
		ResultSet rs=null;
		try{
			rs=this.connection.createStatement().executeQuery("SELECT NAIM, ADR, OKP, MFO1, RASCH1, NOM_DPA, SWID_DPA FROM ARN WHERE ARN.AKOD=22");
			rekvisit.clear();
			rs.next();
			// naim
			rekvisit.add(rs.getString(1));
			// adr
			rekvisit.add(rs.getString(2));
			// okp
			rekvisit.add((new Long((new Float(rs.getFloat(3))).longValue())).toString());
			// mfo1
			rekvisit.add((new Long((new Float(rs.getFloat(4))).longValue())).toString());
			// RASCH1
			rekvisit.add(this.getBeforeComma(rs.getString(5)));
			// NOM_DPA
			rekvisit.add(this.getBeforeComma(rs.getString(6)));
			// SWID_DPA
			rekvisit.add(this.getBeforeComma(rs.getString(7)));
		}catch(Exception ex){
			
		}finally{
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
		}
	}

	/** если в строке есть запятая - получить символы до знака "запятая" */
	private String getBeforeComma(String value){
		int indexOfComma=value.indexOf(',');
		if(indexOfComma>0){
			return getBeforeDot(value.substring(0,indexOfComma).trim());
		}else{
			return getBeforeDot(value);
		}
	}
	/** если в строке есть точка - получить символы до знака "точка" */
	private String getBeforeDot(String value){
		int indexOfComma=value.indexOf('.');
		if(indexOfComma>0){
			return value.substring(0,indexOfComma).trim();
		}else{
			return value;
		}
	}
	
	/** получить SQL query for PreparedStatement query */
	private String getSqlProplata(){
		StringBuffer returnValue=new StringBuffer();
		returnValue.append("select * from "+this.tablePlName+" where tna=? and tn1=?");
		return returnValue.toString();
	}
	
	/** получить запрос SQL 
	 * @param currentDate - текущая дата, по которой получаем данные записи
	 * @param borg - нужно ли печатать Задолженности
	 * */
	private String getSqlText(Date currentDate){
		boolean borg=true;
		StringBuffer result=new StringBuffer();
		result.append("	select distinct "+this.tableTnName+".tn1,\n");
		result.append("	        "+this.tableTnName+".tna,\n");
		result.append("	        npom.kod_pom,	\n");
		result.append("	        npom.aren,	\n");
		result.append("	        '-',	\n");
		result.append("	        arn.naim arn_naim,	\n");// 6
		result.append("	        arn.adr arn_adr,	\n");
		result.append("	        ul.naiu ul_naiu,	\n");//8
		result.append("	        mfo.mfo1 mfo_number, \n");
		result.append("	        mfo.mfo2 mfo_name, \n"); //10
		result.append("	        npom.dm1 npom_dm1,	\n");//11
		result.append("	        npom.nd npom_nd,	\n");//12
		result.append("	        npom.lt1 npom_lt1,	\n");//13
		result.append("	        npom.lt1_kw npom_lt1_kw,	\n");
		result.append("	        npom.dd npom_dd,	\n");//15
		result.append("	        npom.plat npom_plat,	\n");
		result.append("	        npom.oplp npom_oplp,	\n");
		result.append("	        arn.okp arn_okp,	\n");
		result.append("	        arn.mfo1 arn_mfo1,	\n");
		result.append("	        npom.JK1 npom_jk1,	\n"); //20
		result.append("	        arn.rasch1 arn_rasch1,	\n");
		result.append("         ura_pp.stpl2 ura_pp_stpl2, \n");
		result.append("	        "+this.tableTnName+".pl1 pl1,	\n");
		result.append("	        "+this.tableTnName+".pl2 pl2,	\n");
		result.append("	        "+this.tableTnName+".pl3 pl3,	\n");
		result.append("	        "+this.tableTnName+".pl4 pl4,	\n");
		result.append("	        "+this.tableTnName+".pl5 pl5,	\n");
		result.append("	        "+this.tableTnName+".pl6 pl6,	\n");
		result.append("	        "+this.tableTnName+".pl7 pl7,	\n");
		result.append("	        "+this.tableTnName+".pl8 pl8,	\n");
		result.append("	        "+this.tableTnName+".pl9 pl9,	\n");
		result.append("	        "+this.tableTnName+".pl10 pl10,	\n");
		result.append("	        "+this.tableTnName+".pl11 pl11,	\n");
		result.append("	        "+this.tableTnName+".pl12 pl12,	\n");
		result.append("	        "+this.tableTnName+".pl13 pl13,	\n");
		result.append("	        "+this.tableTnName+".pl14 pl14,	\n");
		result.append("	        "+this.tableTnName+".pl15 pl15,	\n");
		/*os200902.tna=tn200902.tna and 
		os200902.tn1=tn200902.tn1
		потом выбираем os200902.pl2..pl14
		*/
		if(borg){
			result.append("	        "+this.tableOsName+".pl os_pl,	\n");
			result.append("	        "+this.tableOsName+".pl1 os_pl1,	\n");
			result.append("	        "+this.tableOsName+".pl2 os_pl2,	\n");
			result.append("	        "+this.tableOsName+".pl3 os_pl3,	\n");
			result.append("	        "+this.tableOsName+".pl4 os_pl4,	\n");
			result.append("	        "+this.tableOsName+".pl5 os_pl5,	\n");
			result.append("	        "+this.tableOsName+".pl6 os_pl6,	\n");
			result.append("	        "+this.tableOsName+".pl7 os_pl7,	\n");
			result.append("	        "+this.tableOsName+".pl8 os_pl8,	\n");
			result.append("	        "+this.tableOsName+".pl9 os_pl9,	\n");
			result.append("	        "+this.tableOsName+".pl10 os_pl10,	\n");
			result.append("	        "+this.tableOsName+".pl11 os_pl11,	\n");
			result.append("	        "+this.tableOsName+".pl12 os_pl12,	\n");
			result.append("	        "+this.tableOsName+".pl13 os_pl13,	\n");
			result.append("	        "+this.tableOsName+".pl14 os_pl14,	\n");
			result.append("	        "+this.tableOsName+".pl15 os_pl15,	\n");
		}
		result.append("	        "+this.tableTnName+".pl pl	\n");
		result.append("	from "+this.tableTnName+"	\n");
		result.append("	inner join npom on npom.aren="+this.tableTnName+".tna and npom.kod_pom="+this.tableTnName+".tn1	\n");
		result.append("	inner join arn on arn.akod="+this.tableTnName+".tna	\n");
		result.append("	left join ul on ul.ul=npom.ul1	\n");
		result.append("	left join mfo on mfo.mfo1=arn.mfo1 \n");
		result.append("	left join ura_pp on ura_pp.tpa="+this.tableTnName+".tna and ura_pp.tpn="+this.tableTnName+".tn1	\n");
		if(borg){
			result.append("inner join "+this.tableOsName+" on "+this.tableOsName+".tna="+this.tableTnName+".tna and "+this.tableOsName+".tn1="+this.tableTnName+".tn1 \n");
		}
		if(this.pochta){
			result.append("inner join pochta on pochta.akod=arn.akod \n");
		}else{
			result.append("left join viddil_js on (viddil_js.ul1=npom.ul1 and viddil_js.dm1=npom.dm1 \n");
			result.append("and trim(both from viddil_js.lt1)=trim(both from npom.lt1)) \n");
		}
		result.append("where "+this.whereJK+" AND "+this.tableTnName+".pl<>0 \n");
		
		if(this.pochta){
			// pochta
		}else{
			if(this.viddil.length()>1){
				// viddil is not number
				result.append("and viddil_js.viddil is null\n");
			}else{
				// viddil is number 
				result.append("and viddil_js.viddil="+this.viddil+"\n");
			}
		}
		//result.append("AND convert(npom.do_ref,DATE)> convert('"+this.dateSql.format(currentDate)+"',DATE) \n");
		//result.append("AND npom.do_ref> '"+this.dateSql.format(currentDate)+"' \n");
		return result.toString();
	}
	
	/** INFO особенность HSQLDb TIMESTAMP */
	@SuppressWarnings("unused")
	private SimpleDateFormat dateSql=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/** вернуть часть запроса, которая отвечает за OrderBy*/
	private String getSqlOrderBy(){
		return "	order by ul.naiu,npom.dm1 ";
	}
	
	/** вернуть часть запроса, которая отвечает за получение уникальной строки, для добавления в нее номера*/
	private String getSqlWhereId(){
		return "AND "+this.tableTnName+".tn1=";
	}
}

