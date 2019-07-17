package com.cherkashyn.vitalii.indirector.workers.service.impl.phonedirty;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.classic.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.PhoneDirty;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.impl.FinderJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.phonedirty.PhoneDirtyFinder;

@Service
public class PhoneDirtyFinderJdbc extends FinderJdbc<PhoneDirty> implements PhoneDirtyFinder{

	@Override
	protected Class<PhoneDirty> getEntityClass() {
		return PhoneDirty.class;
	}

	private final static int MAX_RESULT=10;
	
	@Override
	public List<PhoneDirty> findLike(String phone) throws ServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			@SuppressWarnings("unchecked")
			List<PhoneDirty> values=session.createCriteria(getEntityClass()).add(Restrictions.like("phone", phone, MatchMode.END)).setMaxResults(MAX_RESULT).addOrder(Order.desc(FIELD_ID)).list();
			if(values==null || values.size()==0){
				return new ArrayList<PhoneDirty>();
			}
			return new ArrayList<PhoneDirty>(values);
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
