package com.cherkashyn.vitalii.indirector.workers.service.impl.worker;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.Worker;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.impl.FinderJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.worker.WorkerFinder;

@Service
public class WorkerFinderJdbc extends FinderJdbc<Worker> implements WorkerFinder{

	@Override
	protected Class<Worker> getEntityClass() {
		return Worker.class;
	}

	@Override
	public List<Worker> findLast(int visibleWorkersCount) throws ServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			@SuppressWarnings("unchecked")
			List<Worker> values=session.createCriteria(getEntityClass()).addOrder(Order.desc(FIELD_ID)).setMaxResults(visibleWorkersCount).list();
			if(values==null || values.size()==0){
				return new ArrayList<Worker>();
			}
			return new ArrayList<Worker>(values);
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
	public void refresh(Worker worker) throws ServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			session.refresh(worker);
			// avoid Lazy Loading 
			worker.getAssets().size();
			worker.getDistricts().size();
			worker.getSkills().size();
			
			worker.getHours().size();
			worker.getPhones().size();
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
	public List<Worker> find(String searchValue, int visibleWorkersCount) throws ServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			String sqlSearchValue="'%"+searchValue.toUpperCase()+"%'";
			StringBuilder query=new StringBuilder();
			query.append("select * from worker where ");
		    query.append(" upper(surname) like ");
			query.append(sqlSearchValue);
			query.append(" or upper(name) like ");
			query.append(sqlSearchValue);
			query.append(" or upper(fathername) like ");
			query.append(sqlSearchValue);
			query.append(" or upper(description) like ");
			query.append(sqlSearchValue);
			query.append(" order by id desc");
			query.append(" limit ");
			query.append(visibleWorkersCount);

			
			@SuppressWarnings("unchecked")
			List<Worker> values=session.createSQLQuery(query.toString()).addEntity(getEntityClass()).list();
			
			if(values==null || values.size()==0){
				return new ArrayList<Worker>();
			}
			return new ArrayList<Worker>(values);
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
