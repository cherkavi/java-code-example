package com.cherkashyn.vitalii.eshops.opencart.uploader.service;

import java.sql.SQLException;

import com.cherkashyn.vitalii.eshops.opencart.uploader.domain.Category;

public interface CategoryService {
	
	Category createCategory(Category category) throws SQLException;
	
	void removeCategory(int id)throws SQLException;
	
	void removeCategoryAll() throws SQLException;
	
	Category findCategoryByName(String categoryName) throws SQLException;
	
}
