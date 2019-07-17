package com.test.dao_impl;

// import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.test.dao.IPlaceDAO;
import com.test.database.db_beans.Place;

// @Service("actionsDao")   import org.springframework.stereotype.Service;
// @Scope("prototype")   import org.springframework.context.annotation.Scope;
public class PlaceDAO implements IPlaceDAO  {

	private HibernateTemplate hibernateTemplate;
	
	/** method for insert Injection as SessionFactory
	 * 	@ Autowired - you can also use this annotation for autowiring ( by Type in this situation ) - insert SessionFactory injection
	 *  */
	public void setSessionFactory(SessionFactory factory){
		this.hibernateTemplate=new HibernateTemplate(factory);
	}
	
	@Override
	public Place createPlace(String name) {
		Place place=new Place();
		place.setCarNumber(name);
		// first way
		// return this.hibernateTemplate.save(place);
		
		// second way
		Session session=hibernateTemplate.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(place);
		session.getTransaction().commit();
		session.close();
		return place;
	}

	@Override
	public void removePlace(Place place) {
		this.hibernateTemplate.delete(place);
	}

	@Override
	public void renamePlace(Place place, String newPlaceName) {
		place.setCarNumber(newPlaceName);
		this.hibernateTemplate.saveOrUpdate(place);
	}

}
