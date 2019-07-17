package org.ukraine.kiev.narodna.service;

import java.util.List;

import org.ukraine.kiev.narodna.domain.ListItemModel;

public abstract class Storage {

	/**
	 * @return - all accessible elements from list 
	 */
	public abstract List<ListItemModel> getAll();
	
	/**
	 * save element to storage 
	 * @param model
	 */
	public abstract ListItemModel save(ListItemModel model) throws ServiceException;
	
}
