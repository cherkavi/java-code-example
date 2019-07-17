package db_scan;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import check_hibernate_mapping.db_table.Table;

import ua.cetelem.LOG;
import ua.cetelem.config.Configuration;
import ua.cetelem.helpers.HibernateStorage;

public class ScanDatabase {
	private Configuration configuration=null;
	private HibernateStorage hibernateStorage=null;
	
	protected void setUp() throws Exception {
        String configurationPath="/home/prod/workspace/ukraine/coliseum/conf/";
        configuration = new Configuration(configurationPath, "coliseum", "test");
        LOG.init(configuration.getLog4JConfigLocation());
/*
Logger.getRootLogger().addAppender(new FileAppender(new PatternLayout("%r %-5p %c %x - %m%n"), "/tmp/temp/temp_logger.txt", false));
Logger.getRootLogger().setAdditivity(true);
Logger.getRootLogger().setLevel(Level.WARN);
*/

        configuration.initWithoutXml();
        LOG.debug("", "create Hibernate storage");
        hibernateStorage = new HibernateStorage();
        hibernateStorage.init(configuration.getHibernateConfigLocation(), // path to hibernate configuration 
        					  "ua/cetelem/db/hibernate-mappings.xml" // path to hibernate mapping
        					   );
		LOG.debug("begin");
	}

	protected void teadDown(){
		hibernateStorage.shutdown();
	}
	
	
	public static void main(String[] args) throws Exception {
		String outputPath="/tmp/temp/temp/";
		ScanDatabase scaner=new ScanDatabase();
		scaner.setUp();
		Connection connection=scaner.hibernateStorage.openSession().connection();
		List<Table> tables=Table.getTablesFromConnection(connection, null, null);
		for(Table table:tables){
			System.out.println("table:"+table.getName());
			scaner.saveFieldsTo(outputPath+table.getName()+".csv", table.getName(), connection);
		}
		System.out.println("OK");
	}

	private void saveFieldsTo(String pathToFile, String tableName, Connection connection) throws Exception{
		ResultSet rs=connection.createStatement().executeQuery("select  all_tab_columns.table_name, all_tab_columns.column_name, data_type from all_tab_columns where upper(table_name) like upper('"+tableName+"')");
		// openfile
		BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathToFile)));
		writer.write(tableName);
		writer.write(";\n");
		while(rs.next()){
			writer.write(getStringFromResultSet(rs, 3,";"));
			writer.write("\n");
			writer.flush();
		}
		// closefile
		writer.close();
		rs.getStatement().close();
	}
	
	private String getStringFromResultSet(ResultSet rs, int maxCount, String delimiter) throws Exception {
		StringBuffer returnValue=new StringBuffer();
		for(int counter=1; counter<=maxCount;counter++){
			returnValue.append(rs.getObject(counter));
			returnValue.append(delimiter);
		}
		return returnValue.toString();
	}
}
