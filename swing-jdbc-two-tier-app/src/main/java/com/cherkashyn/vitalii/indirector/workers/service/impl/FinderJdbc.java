package com.cherkashyn.vitalii.indirector.workers.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.Finder;

@Service
public abstract class FinderJdbc<T> implements Finder<T>{

	protected final static String FIELD_ID="id";
	@Autowired
	protected SessionFactory sessionFactory;
	
	protected abstract Class<T> getEntityClass();
	
	@Override
	public List<T> findAll() throws ServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			@SuppressWarnings("unchecked")
			List<T> values=session.createCriteria(getEntityClass()).addOrder(Order.desc(FIELD_ID)).list();
			if(values==null || values.size()==0){
				return new ArrayList<T>();
			}
			return new ArrayList<T>(values);
		}catch(HibernateException ex){
			throw new ServiceException(ex);
		}finally{
			if(session!=null){
				try{
					session.close();
				}catch(HibernateException ex){
				}
			}
		}
	}

	@Override
	public T findById(Integer id) throws ServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			@SuppressWarnings("unchecked")
			T value=(T) session.createCriteria(getEntityClass()).add(Restrictions.eq(FIELD_ID, id)).uniqueResult();
			return value;
		}catch(HibernateException ex){
			throw new ServiceException(ex);
		}finally{
			if(session!=null){
				try{
					session.close();
				}catch(HibernateException ex){
				}
			}
		}
	}
	
	
	
}
