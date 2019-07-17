package mdb_create_read;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;

import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

public class AccessWork {
	public static void main(String[] args) throws Exception{
		System.out.println("begin");
		
		String pathToFile="c:\\temp.mdb";
		String tableName="temp_table";
		System.out.println(">>> try to write data to:"+pathToFile);
		writeToMdb(pathToFile, tableName);
		System.out.println("<<< try to read data from:"+pathToFile);
		readMdbPrintOut(pathToFile, tableName);
		
		System.out.println("-end-");
	}
	
	private static void readMdbPrintOut(String pathToFile, String tableName) throws IOException{
		// open file
		// read table
		// print table
		// close file 
		System.out.println(Database.open(new File(pathToFile)).getTable(tableName).display());
	}
	
	private static void writeToMdb(String pathToFile, String tableName) throws IOException, SQLException{
		// open file
		// write data
		// close file
		Database db = Database.create(new File(pathToFile));
		Table newTable = new TableBuilder(tableName)
		  .addColumn(new ColumnBuilder("a")
		             .setSQLType(Types.INTEGER)
		             .toColumn())
		  .addColumn(new ColumnBuilder("b")
		             .setSQLType(Types.VARCHAR)
		             .toColumn())
		  .toTable(db);
		newTable.addRow(1, "первій");
		newTable.addRow(2, "второй");
		newTable.addRow(3, "третий");
		newTable.addRow(4, "четвертій");
		db.flush();
		db.close();
	}
}
