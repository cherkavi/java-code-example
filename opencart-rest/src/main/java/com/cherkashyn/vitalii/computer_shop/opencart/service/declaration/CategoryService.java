package com.cherkashyn.vitalii.computer_shop.opencart.service.declaration;

import com.cherkashyn.vitalii.computer_shop.opencart.domain.Category;
import com.cherkashyn.vitalii.computer_shop.opencart.domain.Language;
import com.cherkashyn.vitalii.computer_shop.opencart.service.exception.ServiceException;

public interface CategoryService {

	/**
	 * find Category by id 
	 */
	Category find(int id) throws ServiceException;

	/**
	 * find Category by name  
	 */
	Category find(String name) throws ServiceException;
	 
	/**
	 * create category with certain name   
	 * @param category
	 * @param categoryName
	 * @param language
	 * @return persist category 
	 * @throws ServiceException
	 */
	Category create(Category category, String categoryName, Language language) throws ServiceException;

	/**
	 * remove all categories
	 */
	void deleteAll();
}
