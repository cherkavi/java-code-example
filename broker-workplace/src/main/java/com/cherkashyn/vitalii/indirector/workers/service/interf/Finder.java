package com.cherkashyn.vitalii.indirector.workers.service.interf;

import java.util.List;

import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;

public interface Finder<T> {
	
	List<T> findAll() throws ServiceException;

	T findById(Integer id) throws ServiceException;

}
