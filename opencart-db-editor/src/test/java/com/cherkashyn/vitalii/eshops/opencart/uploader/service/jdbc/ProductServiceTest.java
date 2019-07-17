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

import com.cherkashyn.vitalii.eshops.opencart.uploader.domain.Product;
import com.cherkashyn.vitalii.eshops.opencart.uploader.service.ProductService;
import com.cherkashyn.vitalii.eshops.opencart.utils.Tables;

public class ProductServiceTest {

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
	public void createAndRemoveProduct() throws SQLException{
		// given
		Product product=new Product("model", "name", "description", "metadescription", "metakeyworkd", "tag", 11.2f, "", 5);
		ProductService service=new ProductServiceJdbc(dataSource);
		int recordCountProduct=getRecordCount(dataSource, Tables.product());
		int recordCountProductDescription=getRecordCount(dataSource, Tables.productDescription());
		int recordCountProductImage=getRecordCount(dataSource, Tables.productImage());
		int recordCountProductReward=getRecordCount(dataSource, Tables.productReward());
		int recordCountProductStore=getRecordCount(dataSource, Tables.productToStore());
		
		// when
		Product createdProduct=service.createProduct(product);
		
		// then 
		Assert.assertNotNull(createdProduct.getId());
		Assert.assertTrue(createdProduct.getId()>0);
		
		Assert.assertEquals(recordCountProduct+1, getRecordCount(dataSource, Tables.product()));
		Assert.assertEquals(recordCountProductDescription+1, getRecordCount(dataSource, Tables.productDescription()));
		Assert.assertEquals(recordCountProductImage+0, getRecordCount(dataSource, Tables.productImage()));
		Assert.assertEquals(recordCountProductReward+1, getRecordCount(dataSource, Tables.productReward()));
		Assert.assertEquals(recordCountProductStore+1, getRecordCount(dataSource, Tables.productToStore()));

		
		// when 
		service.deleteProduct(createdProduct.getId());
		
		// then 
		Assert.assertEquals(recordCountProduct, getRecordCount(dataSource, Tables.product()));
		Assert.assertEquals(recordCountProductDescription, getRecordCount(dataSource, Tables.productDescription()));
		Assert.assertEquals(recordCountProductImage, getRecordCount(dataSource, Tables.productImage()));
		Assert.assertEquals(recordCountProductReward, getRecordCount(dataSource, Tables.productReward()));
		Assert.assertEquals(recordCountProductStore, getRecordCount(dataSource, Tables.productToStore()));
	}

	// TODO test: attachToCadtegory(int productId, int categoryId) throws SQLException ;
	// TODO test: delete all products
	
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
