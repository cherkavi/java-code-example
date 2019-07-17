package jdbc_copy;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import jdbc_copy.connection.UserConnection;
import jdbc_copy.table.DifferentField;
import jdbc_copy.table.Field;
import jdbc_copy.table.Table;
import jdbc_copy.xml_settings.XmlSettings;


public class EnterPoint {
	static{
		BasicConfigurator.configure();
	}
	
	private final static long serialVersionUID=1L;
	private UserConnection connection1;
	private UserConnection connection2;
	
	
	public static void main(String[] args){
		String fileName="compare_data.txt";
		if(args.length>0){
			fileName=args[0];
		}
		try{
			new EnterPoint().run(fileName);
		}catch(Exception ex){
			System.err.println("Program execute Exception: "+ex.getMessage());
		}
		
	}
	
	private Logger logger=Logger.getLogger(this.getClass());
	
	private void run(String pathToOutputFile) throws Exception {
		logger.debug("try to connect");
		this.loadData();
		logger.debug("execute logic : begin : ");
		BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathToOutputFile)));
		List<Table> firstConnectionTables=Table.getTablesFromConnection(this.connection1.getConnection(), null, null);
		List<Table> secondConnectionTables=Table.getTablesFromConnection(this.connection2.getConnection(), null, null);
		
		logger.debug("find unique tables in first connection ");
		List<Table> uniqueTables1=Table.getUniqueTablesFromFirstList(firstConnectionTables, secondConnectionTables);
		if(!uniqueTables1.isEmpty()){
			// write to file 
			writer.write("----  unique tables from FIRST connection  -----------\n");
			for(int counter=0;counter<uniqueTables1.size();counter++){
				writer.write(counter+" : "+uniqueTables1.get(counter));
				writer.write("\n");
			}
			writer.write("------------------------------------------------------\n");
			writer.flush();
		}
		logger.debug("find unique tables in second connection ");
		List<Table> uniqueTables2=Table.getUniqueTablesFromFirstList(secondConnectionTables, firstConnectionTables);
		if(!uniqueTables2.isEmpty()){
			// write to file 
			writer.write("----  unique tables from SECOND connection  -----------\n");
			for(int counter=0;counter<uniqueTables2.size();counter++){
				writer.write(counter+" : "+uniqueTables2.get(counter));
				writer.write("\n");
			}
			writer.write("------------------------------------------------------\n");
			writer.flush();
		}
		
		
		List<String> shareTableNames=Table.getShareTableNames(firstConnectionTables, secondConnectionTables);
		for(String currentTableName:shareTableNames){
			List<DifferentField> differentList=Field.compareFields(Table.getTableByName(currentTableName, firstConnectionTables).getFields(), 
																   Table.getTableByName(currentTableName, secondConnectionTables).getFields());
			if((differentList!=null)&&(!differentList.isEmpty())){
				writer.write("-------- equals names of tables and not equals fields  -----------\n");
				writer.write("    Table with differences in fields: "+currentTableName+"\n");
				for(int counter=0;counter<differentList.size();counter++){
					writer.write("        "+counter+" : "+differentList.get(counter).toString()+"\n");
				}
				writer.write("------------------------------------------------------\n");
			}
		}
		writer.close();
		logger.debug("execute login : end :");
	}
	
	
	
	
	private void loadData(){
		System.out.println("Load Data");
		String filePath=this.getFullPathToXml();
		
		String url=null;
		String driverClass=null;
		String login=null;
		String password=null;
		// load data from XML 
		try{
			XmlSettings xmlSettings=new XmlSettings(filePath);
			url=xmlSettings.getValue("//jdbc_first/url");
			driverClass=xmlSettings.getValue("//jdbc_first/driver_class");
			login=xmlSettings.getValue("//jdbc_first/login");
			password=xmlSettings.getValue("//jdbc_first/password");
			
			this.connection1=new UserConnection();
			if(url!=null)this.connection1.setUrl(url);
			if(driverClass!=null)this.connection1.setDriverClassName(driverClass);
			if(login!=null)this.connection1.setLogin(login);
			if(password!=null)this.connection1.setPassword(password);
			this.connection1.connect();

			url=xmlSettings.getValue("//jdbc_second/url");
			driverClass=xmlSettings.getValue("//jdbc_second/driver_class");
			login=xmlSettings.getValue("//jdbc_second/login");
			password=xmlSettings.getValue("//jdbc_second/password");
			
			this.connection2=new UserConnection();
			if(url!=null)this.connection2.setUrl(url);
			if(driverClass!=null)this.connection2.setDriverClassName(driverClass);
			if(login!=null)this.connection2.setLogin(login);
			if(password!=null)this.connection2.setPassword(password);
			this.connection2.connect();
			
		}catch(Exception ex){
			System.err.println("������ ������ ����� XML "+ex.getMessage());
		}
	}
	

	private final static String fileXmlName="settings.xml";
	
	/** �������� ������ ���� � XML ����� */
	private String getFullPathToXml(){
		String currentDirectory=System.getProperty("user.dir");
		String fileSeparator=System.getProperty("file.separator");
		String path=null;
		if(currentDirectory.endsWith(fileSeparator)){
			path=currentDirectory+fileXmlName;
		}else{
			path=currentDirectory+fileSeparator+fileXmlName;
		}
		return path;
	}
	
	
}
