package com.cherkashyn.vitalii.accounting.service.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import com.cherkashyn.vitalii.accounting.exception.GeneralServiceException;
import com.cherkashyn.vitalii.accounting.service.Repository;

public abstract class RepositoryJdbc<T> implements Repository<T>{
	@Autowired
	protected SessionFactory sessionFactory;

	
	@Override
	public T create(T detached) throws GeneralServiceException{
		Session session=null;
		try{
			session=sessionFactory.openSession();
			session.beginTransaction();
			@SuppressWarnings("unchecked")
			T returnValue=(T)session.save(detached);
			session.getTransaction().commit();
			return returnValue;
		}catch ( ConstraintViolationException ex){
			// RepeatInsertException
			throw new GeneralServiceException(ex);
		}catch ( HibernateException ex){
			throw new GeneralServiceException(ex);
		}finally{
			if(session!=null){
				try{
					session.close();
				}catch(HibernateException ex){};
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T create(Session session, T detached) throws GeneralServiceException{
		try{
			return (T)session.save(detached);
		}catch ( ConstraintViolationException ex){
			// throw new RepeatInsertException(ex);
			throw new GeneralServiceException(ex);
		}catch ( HibernateException ex){
			throw new GeneralServiceException(ex);
		}
	}
	
	

	@Override
	public T update(T value) throws GeneralServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.update(value);
			session.getTransaction().commit();
			return value;
		}catch ( ConstraintViolationException ex){
			// throw new RepeatInsertException(ex);
			throw new GeneralServiceException(ex);
		}catch ( HibernateException ex){
			throw new GeneralServiceException(ex);
		}finally{
			if(session!=null){
				try{
					session.close();
				}catch(HibernateException ex){};
			}
		}
	}
	
	@Override
	public T update(Session session, T value) throws GeneralServiceException {
		try{
			session.update(value);
			return value;
		}catch ( ConstraintViolationException ex){
			// throw new RepeatInsertException(ex);
			throw new GeneralServiceException(ex);
		}catch ( HibernateException ex){
			throw new GeneralServiceException(ex);
		}finally{
		}
	}
	
	
	
	@Override
	public void delete(T forRemove) throws GeneralServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.delete(forRemove);
			session.getTransaction().commit();
		}catch ( ConstraintViolationException ex){
			// throw new RepeatInsertException(ex);
			throw new GeneralServiceException(ex);
		}catch ( HibernateException ex){
			throw new GeneralServiceException(ex);
		}finally{
			if(session!=null){
				try{
					session.close();
				}catch(HibernateException ex){};
			}
		}
	}

	@Override
	public void delete(Session session, T forRemove) throws GeneralServiceException {
		try{
			session.delete(forRemove);
		}catch ( ConstraintViolationException ex){
			// throw new RepeatInsertException(ex);
			throw new GeneralServiceException(ex);
		}catch ( HibernateException ex){
			throw new GeneralServiceException(ex);
		}
	}
	
	
}
