package com.cherkashyn.vitalii.indirector.workers.service.impl.phone;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.classic.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.Phone;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.impl.FinderJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.phone.PhoneFinder;

@Service
public class PhoneFinderJdbc extends FinderJdbc<Phone> implements PhoneFinder{
	private final static int MAX_RESULT=10;
	
	@Override
	protected Class<Phone> getEntityClass() {
		return Phone.class;
	}

	@Override
	public List<Phone> findLike(String phone) throws ServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			@SuppressWarnings("unchecked")
			List<Phone> values=session.createCriteria(getEntityClass()).add(Restrictions.like("phone", phone, MatchMode.END)).setMaxResults(MAX_RESULT).addOrder(Order.desc(FIELD_ID)).list();
			if(values==null || values.size()==0){
				return new ArrayList<Phone>();
			}
			return new ArrayList<Phone>(values);
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
