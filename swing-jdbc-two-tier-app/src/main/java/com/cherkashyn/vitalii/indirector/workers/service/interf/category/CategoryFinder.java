package com.cherkashyn.vitalii.indirector.workers.service.interf.category;

import java.util.List;

import com.cherkashyn.vitalii.indirector.workers.domain.Category;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.Finder;

public interface CategoryFinder extends Finder<Category>{

	List<Category> findAll() throws ServiceException;

	Category findById(Integer id) throws ServiceException;
}
