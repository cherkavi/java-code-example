package com.cherkashyn.vitalii.computer_shop.opencart.service.implementation;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.computer_shop.opencart.domain.CategoryDescription;
import com.cherkashyn.vitalii.computer_shop.opencart.service.declaration.CategoryDescriptionService;
import com.cherkashyn.vitalii.computer_shop.opencart.service.exception.ServiceException;
import com.cherkashyn.vitalii.computer_shop.opencart.service.helper.SessionExecutor;

@Service("opencart.CategoryDescriptionService")
public class CategoryDescriptionServiceImpl implements CategoryDescriptionService{
	
	@Autowired
	SessionFactory factory;
	
	public CategoryDescription find(final int id) throws ServiceException{
		return new SessionExecutor<CategoryDescription>(factory, "can't get Category by Id ") {
			@Override
			protected CategoryDescription logic(Session session) throws HibernateException, ServiceException {
				Query query=session.createQuery("from "+CategoryDescription.class.getSimpleName()+" where category_id=:id");
				query.setParameter("id", id);
				return this.returnUniqueResult(query);
			}
		}.execute();
	}
	
	@Override
	public CategoryDescription find(final String name) throws ServiceException {
		return new SessionExecutor<CategoryDescription>(factory, "can't get Category by Name:"+name) {
			@Override
			protected CategoryDescription logic(Session session) throws HibernateException, ServiceException {
				Query query=session.createQuery("from "+CategoryDescription.class.getSimpleName()+" where lower(name) like :name ");
				query.setParameter("name", "%"+StringUtils.trimToNull(StringUtils.lowerCase(name))+"%");
				return this.returnUniqueResult(query);
			}
		}.execute();
	}

	@Override
	public void deleteAll() {
		// TODO add delete all Native SQL Query
		
	}
	
}
