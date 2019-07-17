package com.cherkashyn.vitalii.indirector.workers.service.interf;

import org.hibernate.Session;

import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;

public interface Repository<T> {
	
	T create(T detached) throws ServiceException;
	T create(Session session, T detached) throws ServiceException;
	
	T update(T value) throws ServiceException;
	T update(Session session, T value) throws ServiceException;
	
	void delete(Session session, T forRemove) throws ServiceException;
	void delete(T forRemove) throws ServiceException;
}
