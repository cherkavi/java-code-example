package com.cherkashyn.vitalii.accounting.service.finder;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.accounting.domain.Currency;
import com.cherkashyn.vitalii.accounting.domain.RowStatus;
import com.cherkashyn.vitalii.accounting.exception.GeneralServiceException;

@Service
public class CurrencyFinder extends FinderJdbc<Currency>{
	private final static String ROW_STATUS="rowstatus";
	
	@Override
	protected Class<Currency> getEntityClass() {
		return Currency.class;
	}

	public Currency findActive() throws GeneralServiceException{
		Session session=null;
		try{
			session=sessionFactory.openSession();
			return (Currency) session.createCriteria(getEntityClass()).add(Restrictions.eq(ROW_STATUS, RowStatus.ACTIVE.getValue())).setMaxResults(1).uniqueResult();
		}catch(HibernateException ex){
			throw new GeneralServiceException(ex);
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
