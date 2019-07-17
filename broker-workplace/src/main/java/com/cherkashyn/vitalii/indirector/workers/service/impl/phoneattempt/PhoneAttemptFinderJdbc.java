package com.cherkashyn.vitalii.indirector.workers.service.impl.phoneattempt;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.PhoneAttempt;
import com.cherkashyn.vitalii.indirector.workers.domain.PhoneDirty;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.impl.FinderJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.phoneattempt.PhoneAttemptFinder;

@Service
public class PhoneAttemptFinderJdbc extends FinderJdbc<PhoneAttempt> implements PhoneAttemptFinder{

	@Override
	protected Class<PhoneAttempt> getEntityClass() {
		return PhoneAttempt.class;
	}

	@Override
	public List<PhoneAttempt> find(PhoneDirty phoneDirty) throws ServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			@SuppressWarnings("unchecked")
			List<PhoneAttempt> values=session.createCriteria(getEntityClass()).add(Restrictions.eq("phoneDirty", phoneDirty)) .addOrder(Order.desc(FIELD_ID)).list();
			if(values==null || values.size()==0){
				return new ArrayList<PhoneAttempt>();
			}
			return new ArrayList<PhoneAttempt>(values);
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
