package excel_to_hsql;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jxl.common.Logger;

import org.apache.log4j.BasicConfigurator;

import excel_to_hsql.reader.Column;
import excel_to_hsql.reader.ExcelReader;

/**
 * manager for read from Excel and put to SQL 
 */
public class ExcelSqlConverter {
	
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		Logger logger=Logger.getLogger(ExcelSqlConverter.class);
		
		logger.info("begin");
		HsqlDbWriter writer=new HsqlDbWriter(HsqlDbWriter.generateUrlFile("/tmp/temp/temp"));
		
		copyFromExcelToHsqlDb(writer,
							  "rata_province",
							  "/tmp/temp/RataNet_Dict_province_region_city_compare.xls", 
							  "province", 
							  new ArrayList<Column>(){
									private static final long serialVersionUID = 1L;
									{
										this.add(new Column("ID",0));
										this.add(new Column("NAME",1));
										this.add(new Column("AXA_KEY",2)); // !!! ADD it after first step
									}
							   }, 
							  1);
		copyFromExcelToHsqlDb(writer,
							  "rata_region",
							  "/tmp/temp/RataNet_Dict_province_region_city_compare.xls", 
							  "region", 
							  new ArrayList<Column>(){
									private static final long serialVersionUID = 1L;
									{
										this.add(new Column("ID",0));
										this.add(new Column("NAME",1));
										this.add(new Column("PROVINCE_ID",2));
										this.add(new Column("AXA_KEY",3)); // !!! ADD it after second step
									}
							   }, 
							  1);
		copyFromExcelToHsqlDb(writer,
							  "rata_city",
							  "/tmp/temp/RataNet_Dict_province_region_city_compare.xls", 
							  "city", 
							  new ArrayList<Column>(){
									private static final long serialVersionUID = 1L;
									{
										this.add(new Column("ID",0));
										this.add(new Column("NAME",1));
										this.add(new Column("PROVINCE_ID",2));
										this.add(new Column("REGION_ID",3));
									}
							   }, 
							  1);
		copyFromExcelToHsqlDb(writer,
				  "axioma",
				  "/tmp/temp/CASKO_town_region_district.xls", 
				  "Export Worksheet", 
				  new ArrayList<Column>(){
						private static final long serialVersionUID = 1L;
						{
							this.add(new Column("ID",0));
							this.add(new Column("PARENT",1));
							this.add(new Column("NAME",2));
							this.add(new Column("TYPE",3));
						}
				   }, 
				  1);
		logger.info("data filled, execute query ");

		// printTable(writer, "rata_province");
						   // "rata_region",
						   // "rata_city",
						   // "axioma" 
		// ------------------------------------------------		
		// First step - fill the province - add the AXA_KEY column to RATA_PROVINCE
		/* printQuery(writer, "select rata_province.*, axioma.* " +
						   "from rata_province " +
						   "left join axioma on axioma.NAME=rata_province.NAME and axioma.TYPE='REGION'" );*/
		// ------------------------------------------------
		// Second step - fill the region 
		/* printQuery(writer, "select rata_region.*, rata_province.axa_key, axioma.* " +
						   "from rata_region " +
						   "inner join rata_province on rata_province.id=rata_region.province_id  " +
						   "left join axioma on axioma.NAME=rata_region.NAME and axioma.TYPE='DISTRICT' and axioma.parent=rata_province.axa_key");*/
		// ------------------------------------------------
		// Third step - fill the city 
		printQueryToFile(writer, "select rata_city.*, rata_province.axa_key, rata_region.axa_key, axioma.* " +
						   "from rata_city " +
						   "inner join rata_province on rata_province.id=rata_city.province_id  " +
						   "inner join rata_region on rata_region.id=rata_city.region_id " +
						   "left join axioma on axioma.NAME=rata_city.NAME and axioma.TYPE='TOWN' and axioma.parent=rata_region.axa_key",
						   "/tmp/temp/city.csv");
		
		/* printQuery(writer, "select * from axioma where TYPE='REGION'"); */
		writer.shutdown();
		logger.info("end");
	}

	
	private static void printQuery(HsqlDbWriter writer, String query) throws Exception {
		Connection connection=writer.getConnection();
		ResultSet rs=connection.createStatement().executeQuery(query);
		int columnCount=rs.getMetaData().getColumnCount();
		while(rs.next()){
			StringBuilder record=new StringBuilder();
			for(int index=0;index<columnCount;index++){
				record.append(rs.getString(index+1));
				record.append("; ");
			}
			System.out.println(record.toString());
		}
		rs.getStatement().close();
	}
	
	private static void printQueryToFile(HsqlDbWriter writer, String query, String pathToFile) throws Exception {
		FileOutputStream fos=null;
		try{
			fos=new FileOutputStream(pathToFile);
			BufferedWriter fileWriter=new BufferedWriter(new OutputStreamWriter(fos));
			Connection connection=writer.getConnection();
			ResultSet rs=connection.createStatement().executeQuery(query);
			int columnCount=rs.getMetaData().getColumnCount();
			while(rs.next()){
				StringBuilder record=new StringBuilder();
				for(int index=0;index<columnCount;index++){
					record.append(rs.getString(index+1));
					record.append("; ");
				}
				fileWriter.append(record.toString());
				fileWriter.append("\n");
				fileWriter.flush();
			}
			rs.getStatement().close();
		}catch(Exception ex){
			System.err.println("printQueryToFile Exception:"+ex.getMessage());
		}finally{
			try{
				fos.close();
			}catch(Exception ex){};
		}
	}
	
	/** write from HsqlDb to Excel */
	private static void copyFromExcelToHsqlDb(HsqlDbWriter writer,
											  String tableName,
											  String pathToFile, 
											  String sheetName, 
											  List<Column> columns, 
											  int startIndex) throws Exception {
		ExcelReader reader=new ExcelReader(pathToFile, 
										   sheetName, 
										   columns);
		reader.setCurrentRow(startIndex);
		writer.createTable(tableName, columns);
		while(reader.hasNext()){
			writer.write(tableName, trimAll(reader.next()));
		}
		reader.close();
	}
	
	private static String[] trimAll(String[] values){
		for(int index=0;index<values.length;index++){
			values[index]=org.apache.commons.lang.StringUtils.trim(values[index]);
		}
		return values;
	}
}
