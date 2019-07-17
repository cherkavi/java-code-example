import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
			workbook=Workbook.getWorkbook(new File("D:\\eclipse_workspace\\Astronomy3\\Information\\City_maps_koordinates\\city_koordinates.xls"));
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
	
	/** процесс работы с файлом Excel  */
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
	

	private static Float getFloat(Cell[] cells, int position){
		Float returnValue=null;
		try{
			returnValue= Integer.parseInt(cells[position].getContents())+ ((float)Integer.parseInt(cells[position+1].getContents()))/60f;
		}catch(Exception ex){
			System.err.println("getFloat Exception:"+ex.getMessage());
		}
		return returnValue;
	}
}
