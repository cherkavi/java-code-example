package com.cherkashyn.vitalii.eshops.opencart.uploader.service;

import java.sql.SQLException;

import com.cherkashyn.vitalii.eshops.opencart.uploader.domain.Product;

public interface ProductService {
	
	Product createProduct(Product product) throws SQLException ;
	
	void deleteProduct(int productId) throws SQLException ;
	
	void deleteProductAll() throws SQLException ;
	
	void attachToCategory(int productId, int categoryId) throws SQLException ;
	
}
