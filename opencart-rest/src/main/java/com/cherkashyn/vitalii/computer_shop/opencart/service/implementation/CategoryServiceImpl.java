package com.cherkashyn.vitalii.computer_shop.opencart.service.implementation;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.computer_shop.opencart.domain.Category;
import com.cherkashyn.vitalii.computer_shop.opencart.domain.CategoryDescription;
import com.cherkashyn.vitalii.computer_shop.opencart.domain.CategoryDescriptionId;
import com.cherkashyn.vitalii.computer_shop.opencart.domain.CategoryPath;
import com.cherkashyn.vitalii.computer_shop.opencart.domain.CategoryPathId;
import com.cherkashyn.vitalii.computer_shop.opencart.domain.CategoryToStore;
import com.cherkashyn.vitalii.computer_shop.opencart.domain.CategoryToStoreId;
import com.cherkashyn.vitalii.computer_shop.opencart.domain.Language;
import com.cherkashyn.vitalii.computer_shop.opencart.service.declaration.CategoryDescriptionService;
import com.cherkashyn.vitalii.computer_shop.opencart.service.declaration.CategoryService;
import com.cherkashyn.vitalii.computer_shop.opencart.service.exception.ServiceException;
import com.cherkashyn.vitalii.computer_shop.opencart.service.helper.SessionExecutor;

@Service("opencart.CategoryService")
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	SessionFactory factory;

	@Autowired
	CategoryDescriptionService categoryDescriptionService;
	
	public Category find(final int id) throws ServiceException{
		return new SessionExecutor<Category>(factory, "can't get Category by Id ") {
			@Override
			protected Category logic(Session session) throws HibernateException, ServiceException {
				Query query=session.createQuery("from "+Category.class.getSimpleName()+" where category_id=:id");
				query.setParameter("id", id);
				return this.returnUniqueResult(query);
			}
		}.execute();
	}
	
	@Override
	public Category find(final String name) throws ServiceException {
		CategoryDescription description=categoryDescriptionService.find(name);
		return find(description.getId().getCategoryId());
	}


	
	@Override
	public Category create(final Category category, final String categoryName, final Language language) throws ServiceException {
		
		return new SessionExecutor<Category>(factory, "can't create Category ") {
			@Override
			protected Category logic(Session session) throws HibernateException, ServiceException {
				session.beginTransaction();
				session.save(category);
				session.flush();
				session.save(createDescription(session, category.getCategoryId(), categoryName, language));
				session.save(createPath(category.getCategoryId()));
				session.save(createStoreLink(category.getCategoryId()));
				session.getTransaction().commit();
				return category;
			}

			private CategoryToStore createStoreLink(Integer categoryId) {
				CategoryToStore returnValue=new CategoryToStore();
				returnValue.setId(new CategoryToStoreId(categoryId, 0));
				return returnValue;
			}

			private CategoryPath createPath(Integer categoryId) {
				CategoryPath returnValue=new CategoryPath();
				returnValue.setId(new CategoryPathId(categoryId, categoryId));
				returnValue.setLevel(0);
				return returnValue;
			}
			
			private CategoryDescription createDescription(Session session, Integer categoryId, String categoryName, Language language) throws ServiceException{
				CategoryDescription categoryDescription=new CategoryDescription();
				categoryDescription.setName(categoryName);
				categoryDescription.setId(new CategoryDescriptionId(categoryId, language.getLanguageId()));
				categoryDescription.setDescription(StringUtils.EMPTY);
				categoryDescription.setMetaDescription(StringUtils.EMPTY);
				categoryDescription.setMetaKeyword(StringUtils.EMPTY);
				return categoryDescription;
			}
		}.execute();
	}






	@Override
	public void deleteAll() {
		// TODO add Native SQL query Delete All
	}


	
}
