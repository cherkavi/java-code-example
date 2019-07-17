package com.cherkashyn.vitalii.eshops.opencart.uploader.service.jdbc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cherkashyn.vitalii.eshops.opencart.uploader.domain.Category;
import com.cherkashyn.vitalii.eshops.opencart.utils.Tables;

public class CategoryServiceTest {
	private static DataSource dataSource;
	
	@BeforeClass
	public static void init() throws SqlToolError, SQLException, IOException, URISyntaxException{
		dataSource=InitData.createDataSource();
		InitData.init(dataSource.getConnection());
	}
	
	@Test
	public void testConnection() throws SQLException{
		// given
		Connection connection=dataSource.getConnection();
		// when
		// then
		Assert.assertNotNull(connection);
		
		connection.close();
	}
	
	@Test
	public void testInsertCategory() throws SQLException{
		// given
		Category category=new Category("name", "description", "","");
		CategoryServiceJdbc service=new CategoryServiceJdbc(dataSource);
		int recordCountCategory=getRecordCount(dataSource, Tables.category());
		int recordCountCategoryDescription=getRecordCount(dataSource, Tables.categoryDescription());
		int recordCountCategoryPath=getRecordCount(dataSource, Tables.categoryPath());
		int recordCountCategoryToStore=getRecordCount(dataSource, Tables.categoryToStore());
		
		// when
		service.createCategory(category); // first generated id will be 0
		
		// then
		Assert.assertNotNull(category.getId());
		Assert.assertEquals(recordCountCategory+1, getRecordCount(dataSource, Tables.category()));
		Assert.assertEquals(recordCountCategoryDescription+1,getRecordCount(dataSource, Tables.categoryDescription()));
		Assert.assertEquals(recordCountCategoryPath+1, getRecordCount(dataSource, Tables.categoryPath()));
		Assert.assertEquals(recordCountCategoryToStore+1, getRecordCount(dataSource, Tables.categoryToStore()));
	}

	@Test
	public void testDeleteCategory() throws SQLException{
		// given
		Category category=new Category("name", "description", "","");
		CategoryServiceJdbc service=new CategoryServiceJdbc(dataSource);
		int recordCountCategory=getRecordCount(dataSource, Tables.category());
		int recordCountCategoryDescription=getRecordCount(dataSource, Tables.categoryDescription());
		int recordCountCategoryPath=getRecordCount(dataSource, Tables.categoryPath());
		int recordCountCategoryToStore=getRecordCount(dataSource, Tables.categoryToStore());
		
		// when
		service.createCategory(category); 
		Assert.assertNotNull(category.getId());
		
		// then
		Assert.assertEquals(recordCountCategory+1, getRecordCount(dataSource, Tables.category()));
		Assert.assertEquals(recordCountCategoryDescription+1,getRecordCount(dataSource, Tables.categoryDescription()));
		Assert.assertEquals(recordCountCategoryPath+1, getRecordCount(dataSource, Tables.categoryPath()));
		Assert.assertEquals(recordCountCategoryToStore+1, getRecordCount(dataSource, Tables.categoryToStore()));
		
		// when 
		service.removeCategory(category.getId());
		// then
		Assert.assertEquals(recordCountCategory, getRecordCount(dataSource, Tables.category()));
		Assert.assertEquals(recordCountCategoryDescription,getRecordCount(dataSource, Tables.categoryDescription()));
		Assert.assertEquals(recordCountCategoryPath, getRecordCount(dataSource, Tables.categoryPath()));
		Assert.assertEquals(recordCountCategoryToStore, getRecordCount(dataSource, Tables.categoryToStore()));
	}
	
	@Test
	public void createCategoryRemoveItByName() throws SQLException{
		// given
		String categoryName="category test name";
		Category category=new Category(categoryName, "description", "","");
		CategoryServiceJdbc service=new CategoryServiceJdbc(dataSource);
		int recordCountCategory=getRecordCount(dataSource, Tables.category());
		int recordCountCategoryDescription=getRecordCount(dataSource, Tables.categoryDescription());
		int recordCountCategoryPath=getRecordCount(dataSource, Tables.categoryPath());
		int recordCountCategoryToStore=getRecordCount(dataSource, Tables.categoryToStore());
		
		// when
		service.createCategory(category); 
		Category categoryStored=service.findCategoryByName(categoryName);
		
		// then 
		Assert.assertNotNull(category.getId());
		Assert.assertNotNull(categoryStored);
		Assert.assertNotNull(categoryStored.getId());
		Assert.assertEquals(categoryStored.getName(), category.getName());
		Assert.assertEquals(recordCountCategory+1, getRecordCount(dataSource, Tables.category()));
		Assert.assertEquals(recordCountCategoryDescription+1,getRecordCount(dataSource, Tables.categoryDescription()));
		Assert.assertEquals(recordCountCategoryPath+1, getRecordCount(dataSource, Tables.categoryPath()));
		Assert.assertEquals(recordCountCategoryToStore+1, getRecordCount(dataSource, Tables.categoryToStore()));
		
		// when 
		service.removeCategory(categoryStored.getId());

		// then 
		Assert.assertEquals(recordCountCategory, getRecordCount(dataSource, Tables.category()));
		Assert.assertEquals(recordCountCategoryDescription,getRecordCount(dataSource, Tables.categoryDescription()));
		Assert.assertEquals(recordCountCategoryPath, getRecordCount(dataSource, Tables.categoryPath()));
		Assert.assertEquals(recordCountCategoryToStore, getRecordCount(dataSource, Tables.categoryToStore()));
		
	}
	
	
	@Test
	public void createCategoryRemoveAll() throws SQLException{
		// given
		String categoryName="category test name";
		Category category=new Category(categoryName, "description", "","");
		CategoryServiceJdbc service=new CategoryServiceJdbc(dataSource);
		int recordCountCategory=getRecordCount(dataSource, Tables.category());
		int recordCountCategoryDescription=getRecordCount(dataSource, Tables.categoryDescription());
		int recordCountCategoryPath=getRecordCount(dataSource, Tables.categoryPath());
		int recordCountCategoryToStore=getRecordCount(dataSource, Tables.categoryToStore());
		
		// when
		Category categoryStored=service.createCategory(category); 
		
		// then 
		Assert.assertNotNull(category.getId());
		Assert.assertNotNull(categoryStored);
		Assert.assertNotNull(categoryStored.getId());
		Assert.assertEquals(categoryStored.getName(), category.getName());
		Assert.assertEquals(recordCountCategory+1, getRecordCount(dataSource, Tables.category()));
		Assert.assertEquals(recordCountCategoryDescription+1,getRecordCount(dataSource, Tables.categoryDescription()));
		Assert.assertEquals(recordCountCategoryPath+1, getRecordCount(dataSource, Tables.categoryPath()));
		Assert.assertEquals(recordCountCategoryToStore+1, getRecordCount(dataSource, Tables.categoryToStore()));
		
		// when 
		service.removeCategoryAll();

		// then 
		Assert.assertEquals(0, getRecordCount(dataSource, Tables.category()));
		Assert.assertEquals(0,getRecordCount(dataSource, Tables.categoryDescription()));
		Assert.assertEquals(0, getRecordCount(dataSource, Tables.categoryPath()));
		Assert.assertEquals(0, getRecordCount(dataSource, Tables.categoryToStore()));
		
	}
	

	private int getRecordCount(DataSource ds, String tableName) throws SQLException{
		Connection connection=null;
		Statement statement=null;
		ResultSet rs=null;
		try{
			connection=ds.getConnection();
			statement=connection.createStatement();
			rs=statement.executeQuery("select count(*) from "+tableName);
			rs.next();
			return rs.getInt(1);
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}

}
