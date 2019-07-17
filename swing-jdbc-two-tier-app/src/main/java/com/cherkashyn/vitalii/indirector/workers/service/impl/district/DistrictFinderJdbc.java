package com.cherkashyn.vitalii.indirector.workers.service.impl.district;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.District;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.impl.FinderJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.district.DistrictFinder;

@Service
public class DistrictFinderJdbc extends FinderJdbc<District> implements DistrictFinder{

	@Override
	protected Class<District> getEntityClass() {
		return District.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<District> findAll(District parent) throws ServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			List<District> values=null;
			if(parent==null){
				values=session.createCriteria(getEntityClass()).add(Restrictions.isNull("parent")).addOrder(Order.asc("name")).list();
			}else{
				values=session.createCriteria(getEntityClass()).add(Restrictions.eq("parent", parent)).addOrder(Order.asc("name")).list();
			}
			session.close();
			if(values==null || values.size()==0){
				return new ArrayList<District>();
			}
			return new ArrayList<District>(values);
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
	public List<District> findAllLeaf() throws ServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			@SuppressWarnings("unchecked")
			List<District> values=session.createSQLQuery("select * from district d where d.parent_id is not null and not exists (select * from district where parent_id=d.id )").addEntity(getEntityClass()).list();
			session.close();
			if(values==null || values.size()==0){
				return new ArrayList<District>();
			}
			return new ArrayList<District>(values);
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
