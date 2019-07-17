package com.cherkashyn.vitalii.accounting.service;

import java.util.List;

import com.cherkashyn.vitalii.accounting.exception.GeneralServiceException;

public interface Finder<T> {
	
	List<T> findAll() throws GeneralServiceException;

	T findById(Integer id) throws GeneralServiceException;

}
