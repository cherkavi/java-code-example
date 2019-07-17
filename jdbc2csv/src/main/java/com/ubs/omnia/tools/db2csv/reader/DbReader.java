package com.ubs.omnia.tools.db2csv.reader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ubs.omnia.tools.db2csv.exception.GenericConverterException;
import com.ubs.omnia.tools.db2csv.exception.GenericReaderException;
import com.ubs.omnia.tools.db2csv.settings.Parameters;

public class DbReader implements CloseableHeaderIterator<List<String>>{
	private ResultSet rs;
	private int columnCount;
	
	public DbReader(Parameters parameters) throws GenericConverterException{
		// connect to DB 
		Connection connection=parameters.getJdbcParameters().connect();
		// execute SQL Query
		try {
			rs=connection.createStatement().executeQuery(parameters.getQuery().getSql());
		} catch (SQLException e) {
			throw new GenericConverterException("can't execute sql query: "+parameters.getQuery().getSql());
		}
		try {
			columnCount=rs.getMetaData().getColumnCount();
		} catch (SQLException e) {
			throw new GenericConverterException("can't read column count: "+parameters.getQuery().getSql());
		}
	}

	@Override
	public List<String> getHeaders() throws GenericReaderException {
		try {
			ResultSetMetaData metadata=this.rs.getMetaData();
			List<String> returnValue=new ArrayList<String>(columnCount);
			// read meta information from sql query 
			for(int index=0;index<columnCount;index++){
				returnValue.add(metadata.getColumnName(index+1));
			}
			return returnValue;
		} catch (SQLException e) {
			throw new GenericReaderException("can't read metadata from ResultSet ",  e);
		}
	}

	@Override
	public List<String> next() throws GenericReaderException {
		try {
			if(rs.next()){
				List<String> returnValue=new ArrayList<String>(columnCount);
				for(int index=0;index<=columnCount;index++){
					returnValue.add(rs.getString(index+1));
				}
				return returnValue;
			}else{
				return null;
			}
		} catch (SQLException e) {
			throw new GenericReaderException("can't read data from ResultSet ");
		}
	}

	@Override
	public void close() throws GenericReaderException {
		if(rs!=null){
			try {
				if(rs.getStatement()!=null){
					rs.getStatement().close();
				}
			} catch (SQLException e) {
			}
			try{
				if(rs.getStatement().getConnection()!=null){
					rs.getStatement().getConnection().close();
				}
			}catch(SQLException ex){
				throw new GenericReaderException();
			}
		}
	}
	
}


