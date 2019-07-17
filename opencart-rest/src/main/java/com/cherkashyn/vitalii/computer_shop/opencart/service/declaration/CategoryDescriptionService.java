package com.cherkashyn.vitalii.computer_shop.opencart.service.declaration;

import com.cherkashyn.vitalii.computer_shop.opencart.domain.CategoryDescription;
import com.cherkashyn.vitalii.computer_shop.opencart.service.exception.ServiceException;

public interface CategoryDescriptionService {

	/**
	 * find Category by id 
	 */
	CategoryDescription find(int id) throws ServiceException;

	/**
	 * find Category by name  
	 */
	CategoryDescription find(String name) throws ServiceException;
	 
	/**
	 * remove all categories
	 */
	void deleteAll();
}
