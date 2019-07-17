package com.cherkashyn.vitalii.indirector.workers.service.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import com.cherkashyn.vitalii.indirector.workers.service.exception.RepeatInsertException;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.Repository;

public abstract class RepositoryJdbc<T> implements Repository<T>{
	@Autowired
	protected SessionFactory sessionFactory;

	
	@Override
	public T create(T detached) throws ServiceException{
		Session session=null;
		try{
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.save(detached);
			session.getTransaction().commit();
			return detached;
		}catch ( ConstraintViolationException ex){
			throw new RepeatInsertException(ex);
		}catch ( HibernateException ex){
			throw new ServiceException(ex);
		}finally{
			if(session!=null){
				try{
					session.close();
				}catch(HibernateException ex){};
			}
		}
	}

	@Override
	public T create(Session session, T detached) throws ServiceException{
		try{
			session.save(detached);
			return detached;
		}catch ( ConstraintViolationException ex){
			throw new RepeatInsertException(ex);
		}catch ( HibernateException ex){
			throw new ServiceException(ex);
		}
	}
	
	

	@Override
	public T update(T value) throws ServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.update(value);
			session.getTransaction().commit();
			return value;
		}catch ( ConstraintViolationException ex){
			throw new RepeatInsertException(ex);
		}catch ( HibernateException ex){
			throw new ServiceException(ex);
		}finally{
			if(session!=null){
				try{
					session.close();
				}catch(HibernateException ex){};
			}
		}
	}
	
	@Override
	public T update(Session session, T value) throws ServiceException {
		try{
			session.update(value);
			return value;
		}catch ( ConstraintViolationException ex){
			throw new RepeatInsertException(ex);
		}catch ( HibernateException ex){
			throw new ServiceException(ex);
		}finally{
		}
	}
	
	
	
	@Override
	public void delete(T forRemove) throws ServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.delete(forRemove);
			session.getTransaction().commit();
		}catch ( ConstraintViolationException ex){
			throw new RepeatInsertException(ex);
		}catch ( HibernateException ex){
			throw new ServiceException(ex);
		}finally{
			if(session!=null){
				try{
					session.close();
				}catch(HibernateException ex){};
			}
		}
	}

	@Override
	public void delete(Session session, T forRemove) throws ServiceException {
		try{
			session.delete(forRemove);
		}catch ( ConstraintViolationException ex){
			throw new RepeatInsertException(ex);
		}catch ( HibernateException ex){
			throw new ServiceException(ex);
		}
	}
	
	
}
