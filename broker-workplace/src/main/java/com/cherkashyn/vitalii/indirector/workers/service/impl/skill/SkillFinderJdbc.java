package com.cherkashyn.vitalii.indirector.workers.service.impl.skill;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.Category;
import com.cherkashyn.vitalii.indirector.workers.domain.Skill;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.impl.FinderJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.skill.SkillFinder;

@Service
public class SkillFinderJdbc extends FinderJdbc<Skill> implements SkillFinder{

	@Override
	protected Class<Skill> getEntityClass() {
		return Skill.class;
	}

	@Override
	public List<Skill> findAll(Category category) throws ServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			@SuppressWarnings("unchecked")
			List<Skill> values=session.createCriteria(getEntityClass()).add(Restrictions.eq("category", category)).addOrder(Order.desc(FIELD_ID)).list();
			session.close();
			if(values==null || values.size()==0){
				return new ArrayList<Skill>();
			}
			return new ArrayList<Skill>(values);
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
