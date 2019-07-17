package com.cherkashyn.vitalii.computer_shop.opencart.service.declaration;

import com.cherkashyn.vitalii.computer_shop.opencart.domain.Language;
import com.cherkashyn.vitalii.computer_shop.opencart.domain.Product;
import com.cherkashyn.vitalii.computer_shop.opencart.service.exception.ServiceException;

public interface ProductService {
	
	/**
	 * find product by id
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	Product find(int id) throws ServiceException;

	/**
	 * create product  
	 * @param createProduct object product
	 * @param productName - name of product 
	 * @param language - (table {@link Language})
	 * @return
	 */
	Product create(int categoryId, Product createProduct, String productName,
			Language language) throws ServiceException;
	
}
