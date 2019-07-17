package com.ubs.omnia.tools.uploader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Arrays;

import au.com.bytecode.opencsv.CSVReader;

public class LogParser {
	
    public static void main( String[] args ) throws ClassNotFoundException, SQLException, IOException{
    	InputParameter inputParameter=parseParameters(args);
    	System.out.println("--- begin ---");
    	String tableName="csv_log";
    	Connection connection=getConnection();
    	createTable(connection, tableName, DbColumns.values());
    	insertDataFromCsv(inputParameter.dataFile, connection, tableName, DbColumns.values());
    	System.out.println(executeSelect(connection, "select count(*) from "+tableName+" where conclusion is null;"));
    	System.out.println("---  end  ---");
    }
    
    static enum DbColumns{
    	EXTERNAL_ID("INT"){

			@Override
			public void setValueFromString(PreparedStatement preparedStatement, int index, String stringValue) throws SQLException {
				try{
					preparedStatement.setInt(index, Integer.parseInt(stringValue));
				}catch(RuntimeException re){
					setNull(preparedStatement, index);
				}
			}

			@Override
			public void setNull(PreparedStatement preparedStatement, int index) throws SQLException {
				preparedStatement.setNull(index, Types.INTEGER);
			}
    		
    	}, 
    	CASE_ID("INT"){
			@Override
			public void setValueFromString(PreparedStatement preparedStatement, int index, String stringValue) throws SQLException {
				try{
					preparedStatement.setInt(index, Integer.parseInt(stringValue));
				}catch(RuntimeException re){
					setNull(preparedStatement, index);
				}
			}

			@Override
			public void setNull(PreparedStatement preparedStatement, int index) throws SQLException {
				preparedStatement.setNull(index, Types.INTEGER);
			}
    		
    	}, 
    	MESSAGE("VARCHAR(255)"){
			@Override
			public void setValueFromString(PreparedStatement preparedStatement, int index, String stringValue) throws SQLException {
				preparedStatement.setString(index, stringValue);
			}

			@Override
			public void setNull(PreparedStatement preparedStatement, int index) throws SQLException {
				preparedStatement.setNull(index, Types.VARCHAR);
			}
    	}, 
    	CONCLUSION("VARCHAR(50)"){
			@Override
			public void setValueFromString(PreparedStatement preparedStatement, int index, String stringValue) throws SQLException {
				preparedStatement.setString(index, stringValue);
			}

			@Override
			public void setNull(PreparedStatement preparedStatement, int index) throws SQLException {
				preparedStatement.setNull(index, Types.VARCHAR);
			}
    		
    	};
    	
    	private String type;
    	
    	DbColumns(String sqlType){
    		this.type=sqlType;
    	}
    	public abstract void setValueFromString(PreparedStatement preparedStatement, int index, String stringValue) throws SQLException;

    	public abstract void setNull(PreparedStatement preparedStatement, int index) throws SQLException;

    	public String getSqlType(){
    		return this.type;
    	}
    	// EXTERNAL_ID INT, CASE_ID INT, MESSAGE VARCHAR(255), CONCLUSION VARCHAR(50)
    }
    
    
    private static String executeSelect(Connection connection, String sql) throws SQLException {
    	ResultSet rs=connection.createStatement().executeQuery(sql);
    	rs.next();
    	return rs.getString(1);
	}

	private static void insertDataFromCsv(String dataFile, Connection connection, String tableName, DbColumns[] dbColumns) throws IOException, SQLException {
    	CSVReader reader=null;
    	PreparedStatement preparedStatement=null;
		try{
			preparedStatement=createStatement(connection, tableName, dbColumns);
			reader=new CSVReader(new FileReader(new File(dataFile)));
			String[] line=null;
			while((line=reader.readNext())!=null){
				System.out.println("process line: "+Arrays.asList(line));
				preparedStatement.clearParameters();
				fillParameters(preparedStatement, dbColumns, line);
				preparedStatement.executeUpdate();
				connection.commit();
			}
		}finally{
			if(reader!=null){
				reader.close();
			}
			if(preparedStatement!=null){
				preparedStatement.close();
			}
		}
	}

	private static PreparedStatement createStatement(Connection connection, String tableName, DbColumns[] dbColumns) throws SQLException {
		StringBuilder sql=new StringBuilder().append("insert into "+tableName+" ");
		StringBuilder parameters=new StringBuilder();
		sql.append("(");
		for(int index=0;index<dbColumns.length;index++){
			if(index>0){
				sql.append(", ");
				parameters.append(", ");
			}
			sql.append(dbColumns[index].name());
			parameters.append("?");
		}
		sql.append(") VALUES (");
		sql.append(parameters);
		sql.append(");");
		return connection.prepareStatement(sql.toString());
	}

	private static void fillParameters(PreparedStatement preparedStatement, DbColumns[] dbColumns, String[] line) throws SQLException {
		for(int index=0;index<dbColumns.length;index++){
			if(line.length>=index){				
				dbColumns[index].setNull(preparedStatement, index+1);
			}else{
				dbColumns[index].setValueFromString(preparedStatement, index+1, line[index]);
			}
		}
		if(line.length>0){
			setInteger(preparedStatement, 1, line[0]);
		}else{
			preparedStatement.setNull(1, Types.INTEGER);
		}
		if(line.length>1){
			setInteger(preparedStatement, 2, line[1]);
		}else{
			preparedStatement.setNull(2, Types.INTEGER);
		}
		if(line.length>2){
			preparedStatement.setString(3, line[2].trim());
		}else{
			preparedStatement.setNull(3, Types.VARCHAR);
		}
		if(line.length>3){
			preparedStatement.setString(4, line[3].trim());
		}else{
			preparedStatement.setNull(4, Types.VARCHAR);
		}
	}

	private static void setInteger(PreparedStatement preparedStatement, int column, String value) throws SQLException {
		try{
			Integer intValue=Integer.parseInt(value);
			preparedStatement.setInt(column, intValue);
		}catch(RuntimeException re){
			preparedStatement.setNull(column, Types.INTEGER);
		}
	}

	private static void createTable(Connection connection, String tableName, DbColumns ... columns) throws SQLException {
    	StringBuilder sql=new StringBuilder().append("CREATE TABLE "+tableName+"(");
		for(int index=0;index<columns.length;index++){
			if(index>0){
				sql.append(", ");
			}
			sql.append(columns[index].name());
			sql.append(" ");
			sql.append(columns[index].getSqlType());
		}
		sql.append(");");
    	Statement statement=connection.createStatement();
    	statement.executeUpdate(sql.toString());
    	statement.close();
    }

	private static InputParameter parseParameters(String[] args) {
		if(args==null || args.length<1){
			System.err.println("Input parameters should be: <CSV file> ");
			System.exit(1);
		}
    	return new LogParser.InputParameter(args);
	}
	
	private final static String DB_NAME="log-parser";
	private final static String DB_USER="sa";
	private final static String DB_PASSWORD="";

	private static Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:mem:"+DB_NAME, DB_USER, DB_PASSWORD);
    }
    
	
	/**
	 * input parameters from command line
	 */
    static class InputParameter{
		String dataFile;

		public InputParameter(String[] args) {
			this.dataFile=args[0];
		}
    }
    
}

/*
CREATE TABLE TEST AS SELECT * FROM CSVREAD('test.csv');
CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255)) AS SELECT * FROM CSVREAD('test.csv');
*/