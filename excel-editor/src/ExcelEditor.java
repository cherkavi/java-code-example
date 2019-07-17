import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


public class ExcelEditor {
	public static void main(String[] args) throws Exception{
		System.out.println("begin");
		Workbook workbook=null;
		Connection connection=null;
		try{
			connection=connector.get_connection_to_firebird("","D:/eclipse_workspace/Astronomy3/database/ASTRONOMY.GDB",null,"SYSDBA","masterkey");
			// открыть файл Excel
			workbook=Workbook.getWorkbook(new File("D:\\eclipse_workspace\\Astronomy3\\Information\\Task\\a_planet_double.xls"));
			// произвести манипул€ции
			process(workbook,connection);
		}finally{
			try{
				// закрыть файл Excel
				workbook.close();
			}catch(Exception ex){};
			
			try{
				connection.close();
			}catch(Exception ex){};
		}
		System.out.println("-end-");
	}
	
	
	//  процесс работы с файлом Excel  
	private static void process(Workbook workbook, Connection connection){
		Sheet sheet=workbook.getSheet(0);
		int rowCount=sheet.getRows();
		for(int counter=1;counter<rowCount;counter++){
			int id=getInteger(sheet.getCell(0,counter));
			String maxPeriod=sheet.getCell(4,counter).getContents();
			String stepCalculation=sheet.getCell(5,counter).getContents();
			saveToDatabase(connection, id, maxPeriod, stepCalculation);
		}
	}
	
	// сохранить данные  из указанной строки 
	private static void saveToDatabase(Connection connection, int id, String maxPeriod, String stepCalculation){
		try{
			ResultSet rs=connection.createStatement().executeQuery("select gen_id(gen_a_time_value_id, 1) from rdb$database");
			rs.next();
			int maxPeriodId=rs.getInt(1);
			ATimeValue timeValue=ATimeValue.getInstance(maxPeriod);
			PreparedStatement ps=connection.prepareStatement("insert into a_time_value(id, year_value, month_value, day_value, hour_value, minute_value, second_value) values(?,?,?,?,?,?,?)");
			ps.setInt(1, maxPeriodId);
			ps.setInt(2, timeValue.getYear());
			ps.setInt(3, timeValue.getMonth());
			ps.setInt(4, timeValue.getDay());
			ps.setInt(5, timeValue.getHour());
			ps.setInt(6, timeValue.getMinute());
			ps.setInt(7, timeValue.getSecond());
			ps.executeUpdate();
			
			rs=connection.createStatement().executeQuery("select gen_id(gen_a_time_value_id, 1) from rdb$database");
			rs.next();
			int stepCalcId=rs.getInt(1);
			timeValue=ATimeValue.getInstance(stepCalculation);
			ps.setInt(1, stepCalcId);
			ps.setInt(2, timeValue.getYear());
			ps.setInt(3, timeValue.getMonth());
			ps.setInt(4, timeValue.getDay());
			ps.setInt(5, timeValue.getHour());
			ps.setInt(6, timeValue.getMinute());
			ps.setInt(7, timeValue.getSecond());
			ps.executeUpdate();

			connection.createStatement().executeUpdate("update a_planet_double set max_period="+maxPeriodId+", step_calculation="+stepCalcId+" where id="+id);
			ps.getConnection().commit();
		}catch(Exception ex){
			try{
				connection.rollback();
			}catch(Exception ex2){};
			System.err.println("#saveToDatabase: "+ex.getMessage());
		}
	}
	
	static class ATimeValue{
		private int year;
		private int month;
		private int day;
		private int hour;
		private int minite;
		private int second;
		
		public static ATimeValue getInstance(String value){
			ATimeValue returnValue=new ATimeValue();
			if(value.indexOf("дн")>0||value.indexOf("день")>0){
				returnValue.day=getIntValue(value);
			}else{
				if(value.indexOf("час")>0){
					returnValue.hour=getIntValue(value);
				}else{
					if(value.indexOf("мес€ц")>0){
						returnValue.month=getIntValue(value);
					}else{
						if((value.indexOf("год")>0)||(value.indexOf("лет")>0)){
							returnValue.year=getIntValue(value);
						}
					}
				}
			}
			return returnValue;
		}
		
		private static int getIntValue(String value){
			if((value!=null)&&(!value.trim().equals(""))){
				int spaceIndex=value.indexOf(" ");
				try{
					if(spaceIndex>0){
						return Integer.parseInt(value.substring(0,spaceIndex).trim());
					}else{
						return Integer.parseInt(value);
					}
				}catch(Exception ex){
					System.err.println("getIntValue Exception: "+ex.getMessage());
					return 0;
				}
			}else{
				return 0;
			}
		}
		
		public int getYear() {
			return year;
		}
		public int getMonth() {
			return month;
		}
		public int getDay() {
			return day;
		}
		public int getHour() {
			return hour;
		}
		public int getMinute() {
			return minite;
		}
		public int getSecond() {
			return second;
		}
	}
	
	/*
	private static void process(Workbook workbook, Connection connection){
		// Sheet sheet=workbook.getSheet(0);
		// Cell[] columnOne=sheet.getColumn(0);
		// System.out.println(sheet.getCell(columnCounter,rowCounter).getContents());
		Sheet sheet=workbook.getSheet(0);
		int rowCount=sheet.getRows();
		
		for(int rowCounter=1;rowCounter<rowCount;rowCounter++){
			if(sheet.getRow(rowCounter).length==0)continue;
			String name=sheet.getCell(0,rowCounter).getContents();
			if((name!=null)&&(!name.trim().equals(""))){
				Float latitude=getFloat(sheet.getRow(rowCounter), 1); 
				Float longitude=getFloat(sheet.getRow(rowCounter), 3);
				if((latitude!=null)&&(longitude!=null)){
					System.out.println("City: "+name+"  Latitude:"+latitude+" Longitude:"+longitude);
					saveToDatabase(connection, name, longitude, latitude);
				}else{
					System.err.println("Row does not recognized:"+rowCounter);
				}
			}else{
				// this row is empty
			}
		}
	}
	
	private static void saveToDatabase(Connection connection, String city, Float longitude, Float latitude){
		PreparedStatement ps=null;
		try{
			ps=connection.prepareStatement("insert into a_city(name, longitude, latitude) values(?,?,?)");
			ps.setString(1, city);
			ps.setFloat(2, longitude);
			ps.setFloat(3, latitude);
			ps.executeUpdate();
			ps.getConnection().commit();
		}catch(Exception ex){
			System.err.println("saveToDatabase Exception:"+ex.getMessage());
		}finally{
			try{
				ps.close();
			}catch(Exception ex){};
		}
	}
	*/

	private static Float getFloat(Cell[] cells, int position){
		Float returnValue=null;
		try{
			returnValue= Integer.parseInt(cells[position].getContents())+ ((float)Integer.parseInt(cells[position+1].getContents()))/60f;
		}catch(Exception ex){
			System.err.println("getFloat Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	
	/** получить Integer из Cell */
	private static int getInteger(Cell cell){
		if(cell!=null){
			try{
				return Integer.parseInt(cell.getContents());
			}catch(Exception ex){
				return 0;
			}
		}else{
			return 0;
		}
	}
	
	/** получить Float из Cell */
	private static Float getFloat(Cell cell){
		if(cell!=null){
			try{
				return Float.parseFloat(cell.getContents());
			}catch(Exception ex){
				return 0f;
			}
		}else{
			return 0f;
		}
	}
}
