package com.cherkashyn.vitalii.eshops.opencart.uploader.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang.StringUtils;

import com.cherkashyn.vitalii.eshops.opencart.uploader.domain.Product;
import com.cherkashyn.vitalii.eshops.opencart.uploader.service.ProductService;
import com.cherkashyn.vitalii.eshops.opencart.utils.Tables;

public class ProductServiceJdbc extends JdbcService implements ProductService{
	private final static Logger LOGGER = Logger.getLogger(ProductServiceJdbc.class.getName());
	
	protected ProductServiceJdbc(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Product createProduct(Product product) throws SQLException {
		LOGGER.log(Level.INFO, "create product "+product.getModel());
		
		Connection connection=this.getDataSource().getConnection();
		try{
			LOGGER.log(Level.INFO, "insert into "+Tables.product());
			//  INSERT INTO oc_product (product_id, model, sku, upc, ean, jan, isbn, mpn, location, quantity, stock_status_id,                      image, manufacturer_id, shipping,   price, points, tax_class_id,   date_available, weight, weight_class_id, length, width, height, length_class_id, subtract, minimum, sort_order, status,           date_added, date_modified, viewed) 
			//      VALUES               (50,'first model', '',  '',  '',  '',   '',  '',       '',        1,               5, 'data/demo/canon_logo.jpg',               0,        1, 20.0500,      0,            0,     '2014-04-23',  0.000,               1,    0.0,  0.00,    0.0,               1,        1,       1,          1,      1,'2014-04-24 05:39:53','0000-00-00 00:00:00',0);
			
			insertProduct(connection, product);
			
			// INSERT INTO oc_product_description 
			// (product_id, language_id, name, description, meta_description, meta_keyword, tag) 
			// VALUES 
			// (50,1,'test product','','','','');
			
			LOGGER.log(Level.INFO, "insert into "+Tables.productDescription());
			PreparedStatement psProductDescription=connection.prepareStatement("insert into "+Tables.productDescription()
					+" (product_id, language_id, name, description, meta_description, meta_keyword, tag) values "
					+" (         ?,           ?,    ?,           ?,                ?,            ?,  ?)");
			psProductDescription.setInt(1, product.getId());
			psProductDescription.setInt(2, product.getLanguageId());
			psProductDescription.setString(3, product.getName());
			psProductDescription.setString(4, product.getDescription());
			psProductDescription.setString(5, product.getMetaDescription());
			psProductDescription.setString(6, product.getMetaKeyword());
			psProductDescription.setString(7, product.getTag());
			psProductDescription.executeUpdate();
			LOGGER.log(Level.INFO, "done insert into "+Tables.productDescription());
			
			// INSERT INTO oc_product_image (product_id, image, sort_order) VALUES (2353,50,'data/demo/htc_logo.jpg',0);
			if(product.getImages()!=null && product.getImages().length>0){
				LOGGER.log(Level.INFO, "insert into "+Tables.productImage());
				PreparedStatement psProductImage=connection.prepareStatement(
						"INSERT INTO"
						+Tables.productImage()+" (product_id, image, sort_order) "+
				        "VALUES                  (         ?,      ?,         0)");
				for( String nextImage: product.getImages()){
					psProductImage.clearParameters();
					psProductImage.setInt(1, product.getId());
					psProductImage.setString(2, nextImage);
					psProductImage.executeUpdate();
				}
				DbUtils.closeQuietly(psProductImage);
				LOGGER.log(Level.INFO, "insert done into "+Tables.productImage());
			}
			
			
			// INSERT INTO oc_product_reward (product_reward_id, product_id, customer_group_id, points) VALUES (546,50,1,0);
			LOGGER.log(Level.INFO, "insert into "+Tables.productReward());
			PreparedStatement psProductReward=connection.prepareStatement(
					"INSERT INTO "
					+Tables.productReward()+" (product_id, customer_group_id, points) "+
			        " VALUES                    (         ?,                1,      0)");
			psProductReward.setInt(1, product.getId());
			psProductReward.executeUpdate();
			DbUtils.closeQuietly(psProductReward);
			LOGGER.log(Level.INFO, "done insert into "+Tables.productReward());

			
			// INSERT INTO oc_product_to_store (product_id, store_id) VALUES (50,0);
			LOGGER.log(Level.INFO, "insert into "+Tables.productToStore());
			PreparedStatement psProductToStore=connection.prepareStatement(
					"INSERT INTO "
					+Tables.productToStore()+" (product_id, store_id) "+
			        " VALUES                    (         ?,        0)");
			psProductToStore.setInt(1, product.getId());
			psProductToStore.executeUpdate();
			DbUtils.closeQuietly(psProductToStore);

			connection.commit();
			LOGGER.log(Level.INFO, "done insert into "+Tables.productToStore());
		}finally{
			DbUtils.closeQuietly(connection);
		}
		LOGGER.log(Level.INFO, "done create product "+product.getModel());
		return product;
	}

	private void insertProduct(Connection connection, Product product) throws SQLException{
		LOGGER.log(Level.INFO, "insert into "+Tables.product());
		java.sql.Date insertDate=new java.sql.Date(new java.util.Date().getTime());
		if(product.getId()>0){
			PreparedStatement psProduct=connection.prepareStatement("insert into "+Tables.product()
					+" (model, sku, upc, ean, jan, isbn, mpn, location, quantity, stock_status_id, image, manufacturer_id, shipping, price, points, tax_class_id, date_available, weight, weight_class_id, length, width, height, length_class_id, subtract, minimum, sort_order, status, date_added, date_modified, viewed, product_id) values "
					+" (     ?,   ?,   ?,   ?,   ?,    ?,   ?,        ?,        ?,               ?,     ?,               ?,        ?,     ?,      ?,            ?,              ?,      ?,               ?,      ?,     ?,      ?,               ?,        ?,       ?,          ?,      ?,          ?,             ?,     ?,          ?)");
			psProduct.setString(1, product.getModel());
			psProduct.setString(2, StringUtils.EMPTY);// sku
			psProduct.setString(3, StringUtils.EMPTY);// upc
			psProduct.setString(4, StringUtils.EMPTY);// ean
			psProduct.setString(5, StringUtils.EMPTY);// jan
			psProduct.setString(6, StringUtils.EMPTY);// isbn
			psProduct.setString(7, StringUtils.EMPTY);// mpn
			psProduct.setString(8, StringUtils.EMPTY);// location 
			psProduct.setInt(9, product.getQuantity());// quantity, 
			psProduct.setInt(10, 5);// stock_status_id, 
			psProduct.setString(11, product.getImage());// image, 
			psProduct.setInt(12, 0);// manufacturer_id, 
			psProduct.setInt(13, 1);// shipping, 
			psProduct.setFloat(14, product.getPrice());// price, 
			psProduct.setInt(15, 0); // points, 
			psProduct.setInt(16, 0); // tax_class_id, 
			psProduct.setDate(17, insertDate); // date_available, 
			psProduct.setFloat(18, 0.0f); // weight, 
			psProduct.setInt(19, 1); // weight_class_id, 
			psProduct.setFloat(20, 0.0f); // length, 
			psProduct.setFloat(21, 0.0f); // width, 
			psProduct.setFloat(22, 0.0f); // height, 
			psProduct.setInt(23, 1); // length_class_id, 
			psProduct.setInt(24, 1); // subtract, 
			psProduct.setInt(25, 1); // minimum, 
			psProduct.setInt(26, 1); // sort_order, 
			psProduct.setInt(27, 1); // status, 
			psProduct.setDate(28, insertDate); // date_added, 
			psProduct.setDate(29, insertDate); // date_modified, 
			psProduct.setInt(30, 0); // viewed
			psProduct.setInt(31, product.getId());
			psProduct.executeUpdate();
			DbUtils.closeQuietly(psProduct);
		}else{
			PreparedStatement psProduct=connection.prepareStatement("insert into "+Tables.product()
					+" (model, sku, upc, ean, jan, isbn, mpn, location, quantity, stock_status_id, image, manufacturer_id, shipping, price, points, tax_class_id, date_available, weight, weight_class_id, length, width, height, length_class_id, subtract, minimum, sort_order, status, date_added, date_modified, viewed) values "
					+" (     ?,   ?,   ?,   ?,   ?,    ?,   ?,        ?,        ?,               ?,     ?,               ?,        ?,     ?,      ?,            ?,              ?,      ?,               ?,      ?,     ?,      ?,               ?,        ?,       ?,          ?,      ?,          ?,             ?,      ?)", Statement.RETURN_GENERATED_KEYS);
			psProduct.setString(1, product.getModel());
			psProduct.setString(2, StringUtils.EMPTY);// sku
			psProduct.setString(3, StringUtils.EMPTY);// upc
			psProduct.setString(4, StringUtils.EMPTY);// ean
			psProduct.setString(5, StringUtils.EMPTY);// jan
			psProduct.setString(6, StringUtils.EMPTY);// isbn
			psProduct.setString(7, StringUtils.EMPTY);// mpn
			psProduct.setString(8, StringUtils.EMPTY);// location 
			psProduct.setInt(9, product.getQuantity());// quantity, 
			psProduct.setInt(10, 5);// stock_status_id, 
			psProduct.setString(11, product.getImage());// image, 
			psProduct.setInt(12, 0);// manufacturer_id, 
			psProduct.setInt(13, 1);// shipping, 
			psProduct.setFloat(14, product.getPrice());// price, 
			psProduct.setInt(15, 0); // points, 
			psProduct.setInt(16, 0); // tax_class_id, 
			psProduct.setDate(17, insertDate); // date_available, 
			psProduct.setFloat(18, 0.0f); // weight, 
			psProduct.setInt(19, 1); // weight_class_id, 
			psProduct.setFloat(20, 0.0f); // length, 
			psProduct.setFloat(21, 0.0f); // width, 
			psProduct.setFloat(22, 0.0f); // height, 
			psProduct.setInt(23, 1); // length_class_id, 
			psProduct.setInt(24, 1); // subtract, 
			psProduct.setInt(25, 1); // minimum, 
			psProduct.setInt(26, 1); // sort_order, 
			psProduct.setInt(27, 1); // status, 
			psProduct.setDate(28, insertDate); // date_added, 
			psProduct.setDate(29, insertDate); // date_modified, 
			psProduct.setInt(30, 0); // viewed
			psProduct.executeUpdate();
			product.setId(this.getGeneratedKey(psProduct));
			DbUtils.closeQuietly(psProduct);
		}
		LOGGER.log(Level.INFO, "done insert into "+Tables.product());
	}

	@Override
	public void deleteProduct(int productId) throws SQLException{
		Connection connection=this.getDataSource().getConnection();
		try{
			String fieldName="product_id";
			this.deleteFromTableByFieldId(connection, Tables.product(), fieldName, productId);
			this.deleteFromTableByFieldId(connection, Tables.productDescription(), fieldName, productId);
			this.deleteFromTableByFieldId(connection, Tables.productImage(), fieldName, productId);
			this.deleteFromTableByFieldId(connection, Tables.productReward(), fieldName, productId);
			this.deleteFromTableByFieldId(connection, Tables.productToStore(), fieldName, productId);
			this.deleteFromTableByFieldId(connection, Tables.productToCategory(), fieldName, productId);
			connection.commit();
		}finally{
			DbUtils.closeQuietly(connection);
		}
	}

	@Override
	public void attachToCategory(int productId, int categoryId)
			throws SQLException {
		// INSERT INTO `oc_product_to_category` (`product_id`, `category_id`) VALUES (50,61);
		Connection connection=this.getDataSource().getConnection();
		try{
			LOGGER.log(Level.INFO, "insert into "+Tables.productToCategory());
			PreparedStatement psProductToCategory=connection.prepareStatement(
					"INSERT INTO"
					+Tables.productToCategory()+" (product_id, category_id) "+
			        "VALUES                       (         ?,           ?)");
			psProductToCategory.setInt(1, productId);
			psProductToCategory.setInt(2, categoryId);
			psProductToCategory.executeUpdate();
			DbUtils.closeQuietly(psProductToCategory);

			connection.commit();
			LOGGER.log(Level.INFO, "done insert into "+Tables.productToCategory());
		}finally{
			DbUtils.closeQuietly(connection);
		}
	}

	@Override
	public void deleteProductAll() throws SQLException {
		Connection connection=this.getDataSource().getConnection();
		try{
			this.deleteFromTable(connection, Tables.product());
			this.deleteFromTable(connection, Tables.productDescription());
			this.deleteFromTable(connection, Tables.productImage());
			this.deleteFromTable(connection, Tables.productReward());
			this.deleteFromTable(connection, Tables.productToStore());
			this.deleteFromTable(connection, Tables.productToCategory());
			connection.commit();
		}finally{
			DbUtils.closeQuietly(connection);
		}
	}

}
