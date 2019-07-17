package com.cherkashyn.vitalii.indirector.workers.service.impl.worker;

import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.indirector.workers.domain.Phone;
import com.cherkashyn.vitalii.indirector.workers.domain.Worker;
import com.cherkashyn.vitalii.indirector.workers.domain.Worker2hour;
import com.cherkashyn.vitalii.indirector.workers.service.exception.RepeatInsertException;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.impl.RepositoryJdbc;
import com.cherkashyn.vitalii.indirector.workers.service.interf.Repository;
import com.cherkashyn.vitalii.indirector.workers.service.interf.hour.HourRepository;
import com.cherkashyn.vitalii.indirector.workers.service.interf.phone.PhoneRepository;
import com.cherkashyn.vitalii.indirector.workers.service.interf.worker.WorkerRepository;

@Service
public class WorkerRepositoryJdbc extends RepositoryJdbc<Worker> implements WorkerRepository{
	private static Logger LOGGER=LoggerFactory.getLogger(WorkerRepositoryJdbc.class);
	
	
	@Autowired
	private HourRepository repositoryHours;

	@Autowired
	private PhoneRepository repositoryPhones;
	
	
	@Override
	public Worker create(Worker detached) throws ServiceException{
		Session session=null;
		try{
			session=sessionFactory.openSession();
			session.beginTransaction();
			for(Worker2hour eachHour:detached.getHours()){
				session.saveOrUpdate(eachHour);
			}
			for(Phone eachPhone: detached.getPhones()){
				session.saveOrUpdate(eachPhone);
			}
			session.save(detached);
			session.getTransaction().commit();
			return detached;
		}catch ( ConstraintViolationException ex){
			LOGGER.error(ex.getMessage());
			throw new RepeatInsertException(ex);
		}catch ( HibernateException ex){
			LOGGER.error(ex.getMessage());
			throw new ServiceException(ex);
		}finally{
			if(session!=null){
				try{
					session.close();
				}catch(HibernateException ex){};
			}
		}
	}

	@Override
	public Worker update(Worker valueForUpdate) throws ServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			session.beginTransaction();
			Worker savedValue=(Worker) session.get(Worker.class, valueForUpdate.getId());
			
			savedValue.setName(valueForUpdate.getName());
			savedValue.setSurname(valueForUpdate.getSurname());
			savedValue.setFathername(valueForUpdate.getFathername());
			savedValue.setDescription(valueForUpdate.getDescription());

			savedValue.setHours( merge(session, valueForUpdate.getHours(), savedValue.getHours(), repositoryHours) );
			savedValue.setPhones( merge(session, valueForUpdate.getPhones(), savedValue.getPhones(), repositoryPhones) );

			savedValue.setAssets( valueForUpdate.getAssets() );
			savedValue.setSkills( valueForUpdate.getSkills());
			savedValue.setDistricts( valueForUpdate.getDistricts());
			savedValue.setAge(valueForUpdate.getAge());
			savedValue.setLiveDistrict(valueForUpdate.getLiveDistrict());
			
			session.update(savedValue);
			session.getTransaction().commit();
			return savedValue;
		}catch ( ConstraintViolationException ex){
			LOGGER.error(ex.getMessage());
			throw new RepeatInsertException(ex);
		}catch ( HibernateException ex){
			LOGGER.error(ex.getMessage());
			throw new ServiceException(ex);
		}finally{
			if(session!=null){
				try{
					session.close();
				}catch(HibernateException ex){};
			}
		}
	}
	
	
	private <T> Set<T> merge(Session session, Set<T> forUpdate, Set<T> saved, Repository<T> repository) throws ServiceException{
		// remove not existing in new value
		for(T eachAsset:saved){
			if( !forUpdate.contains(eachAsset) ){
				repository.delete(session, eachAsset);
			}
		}

		// create not existing in DB 
		for(T eachAsset:forUpdate){
			if(!saved.contains(eachAsset)){
				saved.add(repository.create(session, eachAsset));
			}
		}
		return saved;
	}
	
	
	
	@Override
	public void delete(Worker forRemove) throws ServiceException {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.refresh(forRemove);
			
			for(Phone eachPhones:forRemove.getPhones()){
				session.delete(eachPhones);
			}
			forRemove.getPhones().clear();
			for(Worker2hour eachHour:forRemove.getHours()){
				session.delete(eachHour);
			}
			forRemove.getHours().clear();
			session.delete(forRemove);
			session.getTransaction().commit();
		}catch ( ConstraintViolationException ex){
			throw new RepeatInsertException(ex);
		}catch ( HibernateException ex){
			throw new ServiceException(ex);
		}finally{
			if(session!=null){
				try{
					session.close();
				}catch(HibernateException ex){};
			}
		}
	}
	
}
