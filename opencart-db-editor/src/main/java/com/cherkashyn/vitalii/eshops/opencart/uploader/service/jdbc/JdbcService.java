package com.cherkashyn.vitalii.eshops.opencart.uploader.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

public abstract class JdbcService {
	private DataSource dataSource;
	
	protected JdbcService(DataSource dataSource){
		this.dataSource=dataSource;
	}
	
	protected int getGeneratedKey(PreparedStatement statement) throws SQLException{
		int returnValue=0;
		if(statement==null){
			return returnValue;
		}
		ResultSet rs=statement.getGeneratedKeys();
		if(rs==null){
			return returnValue;
		}
		try{
			if(rs.next()){
				returnValue=rs.getInt(1);
			}
			return returnValue;
		}finally{
			rs.close();
		}
	}
	
	protected DataSource getDataSource(){
		return this.dataSource;
	}
	
	protected void deleteFromTable(Connection connection, String tableName) throws SQLException {
		PreparedStatement psCategory=connection.prepareStatement(" delete from "+tableName);
		psCategory.executeUpdate();
		DbUtils.closeQuietly(psCategory);
	}
	
	protected void deleteFromTableByFieldId(Connection connection, String tableName, String fieldName, int fieldId) throws SQLException {
		PreparedStatement psCategory=connection.prepareStatement(" delete from "+tableName+" where "+fieldName+"=? ");
		psCategory.setInt(1, fieldId);
		psCategory.executeUpdate();
		DbUtils.closeQuietly(psCategory);
	}

	
}
