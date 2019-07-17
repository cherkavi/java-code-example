package com.cherkashyn.vitalii.computer_shop.opencart.service.helper;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherkashyn.vitalii.computer_shop.opencart.service.exception.ServiceException;

/**
 * template method: logic
 * avoid duplication of logic for open/close sessions
 * @param <T>
 */
public abstract class SessionExecutor<T> {
	private final static Logger LOGGER=LoggerFactory.getLogger(SessionExecutor.class);
	private SessionFactory factory;
	private String errorMessage;

	public SessionExecutor(SessionFactory factory, String errorMessage) {
		this.factory = factory;
		this.errorMessage = errorMessage;
	}

	
	protected T returnUniqueResult(Query query) throws ServiceException{
		try{
			@SuppressWarnings("unchecked")
			T returnValue=(T) query.uniqueResult();
			return returnValue;
		}catch(NonUniqueResultException re){
			return null;
		}
	}
	
	/**
	 * execute query and return result by expected type
	 * @return
	 * @throws ServiceException
	 */
	public T execute() throws ServiceException{
		Session session=null;
		try{
			session=factory.openSession();
			return logic(session);
		}catch(HibernateException ex){
			LOGGER.error(ex.getMessage());
			throw new ServiceException(errorMessage, ex);
		}finally{
			if(session!=null){
				try{
					session.close();
				}catch(HibernateException ex){};
			}
		}
	}

	protected abstract T logic(Session session) throws HibernateException, ServiceException;
	
}
