package com.cherkashyn.vitalii.computer_shop.opencart.service.implementation;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.computer_shop.opencart.domain.Language;
import com.cherkashyn.vitalii.computer_shop.opencart.service.declaration.LanguageService;
import com.cherkashyn.vitalii.computer_shop.opencart.service.exception.ServiceException;
import com.cherkashyn.vitalii.computer_shop.opencart.service.helper.SessionExecutor;

@Service("opencart.LanguageService")
public class LanguageServiceImpl implements LanguageService{

	@Autowired
	SessionFactory factory;

	@Override
	public Language find(final int id) throws ServiceException {
		return new SessionExecutor<Language>(factory, MessageFormat.format("can't find language by id: {}", id)){
			@Override
			protected Language logic(Session session) throws HibernateException, ServiceException {
				Query query=session.createQuery("from "+Language.class.getSimpleName()+" where id=:id ");
				query.setParameter("id", id);
				try{
					return (Language)query.uniqueResult();
				}catch(NonUniqueResultException npe){
					throw new ServiceException(MessageFormat.format("can't find unique language by id: {}", id),npe);
				}catch(HibernateException he){
					throw new ServiceException("find language service error");
				}
			}
		}.execute();
	}

	@Override
	public Language find(final String languageName) throws ServiceException {
		return new SessionExecutor<Language>(factory, MessageFormat.format("can't find language by languageName: {}", languageName)){
			@Override
			protected Language logic(Session session) throws HibernateException, ServiceException {
				Query query=session.createQuery("from "+Language.class.getSimpleName()+" where lower(name) like :lang ");
				query.setParameter("lang", "%"+StringUtils.lowerCase(StringUtils.trim(languageName))+"%" );
				try{
					return (Language)query.uniqueResult();
				}catch(NonUniqueResultException npe){
					throw new ServiceException(MessageFormat.format("can't find unique language by name: {}", languageName),npe);
				}catch(HibernateException he){
					throw new ServiceException("find language service error");
				}
			}
		}.execute();
	}

	@Override
	public Language findDefaultLanguage() throws ServiceException {
		return new SessionExecutor<Language>(factory, "can't find default language"){
			@Override
			protected Language logic(Session session) throws HibernateException, ServiceException {
				Query query=session.createQuery("from "+Language.class.getSimpleName());
				query.setMaxResults(1);
				try{
					return (Language)query.list().get(0);
				}catch(HibernateException he){
					throw new ServiceException("find language service error");
				}
			}
		}.execute();
	}

}
