package com.cherkashyn.vitalii.accounting.service;

import org.hibernate.Session;

import com.cherkashyn.vitalii.accounting.exception.GeneralServiceException;

public interface Repository<T> {
	
	T create(T detached) throws GeneralServiceException;
	T create(Session session, T detached) throws GeneralServiceException;
	
	T update(T value) throws GeneralServiceException;
	T update(Session session, T value) throws GeneralServiceException;
	
	void delete(Session session, T forRemove) throws GeneralServiceException;
	void delete(T forRemove) throws GeneralServiceException;
}
