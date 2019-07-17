package com.cherkashyn.vitalii.eshops.opencart.uploader.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.cherkashyn.vitalii.eshops.opencart.uploader.domain.Category;
import com.cherkashyn.vitalii.eshops.opencart.uploader.service.CategoryService;
import com.cherkashyn.vitalii.eshops.opencart.utils.Tables;

public class CategoryServiceJdbc extends JdbcService implements CategoryService{
	private final static Logger LOGGER = Logger.getLogger(CategoryServiceJdbc.class.getName());
	
	public CategoryServiceJdbc(DataSource dataSource){
		super(dataSource);
	}
	
	private void insertCategory(Connection connection, Category category) throws SQLException{
		if(category.getId()>0){
			// "INSERT INTO `oc_category` (`category_id`, `image`, `parent_id`, `top`, `column`, `sort_order`, `status`, `date_added`, `date_modified`) VALUES (62,'',0,0,1,0,1,'2014-04-24 05:17:06','2014-04-24 05:17:06')"
			PreparedStatement psCategory=connection.prepareStatement(
					"INSERT INTO "
					+Tables.category()+" (image, parent_id, top, `column`, sort_order, status, date_added, date_modified, category_id) "+
			        "VALUES              (    ?,         ?,   ?,      ?,          ?,       ?,          ?,              ?,           ?)");
			psCategory.setString(1, category.getImage());
			psCategory.setInt(2, category.getParentId());
			psCategory.setInt(3, category.getTop());
			psCategory.setInt(4, category.getColumn());
			psCategory.setInt(5, category.getSortOrder());
			psCategory.setInt(6, category.getStatus());
			psCategory.setDate(7, category.getDateAdded());
			psCategory.setDate(8, category.getDateModified());
			psCategory.setInt(9, category.getId());
			psCategory.executeUpdate();
			DbUtils.closeQuietly(psCategory);
		}else{
			// "INSERT INTO `oc_category` (`category_id`, `image`, `parent_id`, `top`, `column`, `sort_order`, `status`, `date_added`, `date_modified`) VALUES (62,'',0,0,1,0,1,'2014-04-24 05:17:06','2014-04-24 05:17:06')"
			PreparedStatement psCategory=connection.prepareStatement(
					"INSERT INTO "
					+Tables.category()+" (image, parent_id, top, `column`, sort_order, status, date_added, date_modified) "+
			        "VALUES              (    ?,         ?,   ?,      ?,          ?,       ?,          ?,            ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			psCategory.setString(1, category.getImage());
			psCategory.setInt(2, category.getParentId());
			psCategory.setInt(3, category.getTop());
			psCategory.setInt(4, category.getColumn());
			psCategory.setInt(5, category.getSortOrder());
			psCategory.setInt(6, category.getStatus());
			psCategory.setDate(7, category.getDateAdded());
			psCategory.setDate(8, category.getDateModified());
			psCategory.executeUpdate();
			category.setId(this.getGeneratedKey(psCategory));
			DbUtils.closeQuietly(psCategory);
		}
		LOGGER.log(Level.INFO, "done insert into "+Tables.category());
		
	}
	
	@Override
	public Category createCategory(Category category) throws SQLException {
		LOGGER.log(Level.INFO, "create category "+category.getName());
		
		Connection connection=this.getDataSource().getConnection();
		try{
			LOGGER.log(Level.INFO, "insert into "+Tables.category());
			insertCategory(connection, category);
			LOGGER.log(Level.INFO, "insert into "+Tables.categoryDescription());
			PreparedStatement psCategoryDescription=connection.prepareStatement(
					"INSERT INTO "+Tables.categoryDescription()
					+" (category_id, language_id, name, description, meta_description, meta_keyword) "+
			        "VALUES  (    ?,           ?,    ?,            ?,               ?,            ?)");
			psCategoryDescription.setInt(1, category.getId());
			psCategoryDescription.setInt(2, category.getLanguageId());
			psCategoryDescription.setString(3, category.getName());
			psCategoryDescription.setString(4, category.getDescription());
			psCategoryDescription.setString(5, category.getMetaDescription());
			psCategoryDescription.setString(6, category.getMetaKeyword());
			psCategoryDescription.executeUpdate();
			DbUtils.closeQuietly(psCategoryDescription);
			LOGGER.log(Level.INFO, "done insert into "+Tables.categoryDescription());
			
			LOGGER.log(Level.INFO, "insert into "+Tables.categoryPath());
			PreparedStatement psCategoryPath=connection.prepareStatement(
					"INSERT INTO "+Tables.categoryPath()+" (category_id, path_id, level) VALUES (?,?,?) ");
			psCategoryPath.setInt(1, category.getId());
			psCategoryPath.setInt(2, category.getId());
			psCategoryPath.setInt(3, 0);
			psCategoryPath.executeUpdate();
			DbUtils.closeQuietly(psCategoryPath);
			LOGGER.log(Level.INFO, "done insert into "+Tables.categoryPath());
			
			LOGGER.log(Level.INFO, "insert into "+Tables.categoryToStore());
			PreparedStatement psCategoryToStore=connection.prepareStatement(
					"INSERT INTO "+Tables.categoryToStore()+"  (category_id, store_id) VALUES (?,?)");
			psCategoryToStore.setInt(1, category.getId());
			psCategoryToStore.setInt(2, 0);
			psCategoryToStore.executeUpdate();
			DbUtils.closeQuietly(psCategoryToStore);
			
			connection.commit();
			LOGGER.log(Level.INFO, "done insert into "+Tables.categoryToStore());
		}finally{
			DbUtils.closeQuietly(connection);
		}
		LOGGER.log(Level.INFO, "done create category "+category.getName());
		return category;
	}

	@Override
	public void removeCategory(int categoryId) throws SQLException{
		LOGGER.log(Level.INFO, "remove category "+categoryId);
		Connection connection=this.getDataSource().getConnection();
		try{
			LOGGER.log(Level.INFO, "remove "+Tables.category()+" Id: "+categoryId);
			removeFromTableByCategoryId(connection, Tables.category(), categoryId);
			
			LOGGER.log(Level.INFO, "remove "+Tables.categoryDescription()+" Id: "+categoryId);
			removeFromTableByCategoryId(connection, Tables.categoryDescription(), categoryId);
			
			LOGGER.log(Level.INFO, "remove "+Tables.categoryPath()+" Id: "+categoryId);
			removeFromTableByCategoryId(connection, Tables.categoryPath(), categoryId);
			
			LOGGER.log(Level.INFO, "remove "+Tables.categoryToStore()+" Id: "+categoryId);
			removeFromTableByCategoryId(connection, Tables.categoryToStore(), categoryId);
			
			connection.commit();
		}finally{
			DbUtils.closeQuietly(connection);
		}
		LOGGER.log(Level.INFO, "done remove category "+categoryId);
	}
	
	private void removeFromTableByCategoryId(Connection connection, String tableName, int categoryId) throws SQLException {
		deleteFromTableByFieldId(connection, tableName, "category_id", categoryId);
	}
	
	@Override
	public void removeCategoryAll() throws SQLException {
		LOGGER.log(Level.INFO, "remove categories ");
		Connection connection=this.getDataSource().getConnection();
		try{
			LOGGER.log(Level.INFO, "remove all "+Tables.category());
			deleteFromTable(connection, Tables.category());
			
			LOGGER.log(Level.INFO, "remove all "+Tables.categoryDescription());
			deleteFromTable(connection, Tables.categoryDescription());
			
			LOGGER.log(Level.INFO, "remove all "+Tables.categoryPath());
			deleteFromTable(connection, Tables.categoryPath());
			
			LOGGER.log(Level.INFO, "remove all "+Tables.categoryToStore());
			deleteFromTable(connection, Tables.categoryToStore());
			
			connection.commit();
		}finally{
			DbUtils.closeQuietly(connection);
		}
		LOGGER.log(Level.INFO, "done remove categories");
	}

	private final static String SELECT="select cat.category_id id, cat.parent_id parentId, cat.top, cat.`column`, cat.status, cd.name, cd.description, cat.date_added dateAdd, cat.date_modified dateModified, cd.meta_description metaDescription, cd.meta_keyword metaKeyword, cd.language_id languageId, cat.image, cat.sort_order sortOrder from "+Tables.category()+" cat inner join "+Tables.categoryDescription()+" cd on cd.category_id=cat.category_id where cd.name=?";
	
	@Override
	public Category findCategoryByName(String categoryName) throws SQLException {
		ResultSetHandler<Category> handler=new BeanHandler<Category>(Category.class);
		Connection connection=this.getDataSource().getConnection();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		try{
			statement=connection.prepareStatement(SELECT);
			statement.setString(1, categoryName);
			resultSet=statement.executeQuery();
			return handler.handle(resultSet);
		}finally{
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}

	
}
