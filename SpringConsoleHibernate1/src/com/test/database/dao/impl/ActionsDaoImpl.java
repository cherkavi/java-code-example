package com.test.database.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.test.database.dao.ActionsDao;
import com.test.database.model.Actions;

// @Repository("actionDao")
@Service("actionsDao")
@Scope("prototype")
public class ActionsDaoImpl extends HibernateDaoSupport implements ActionsDao {

	@Autowired
    public void anyMethodName(SessionFactory sessionFactory)
    {
        setSessionFactory(sessionFactory);
    }

	@SuppressWarnings("unchecked")
	public List<Actions> getAllActions(){
		return this.getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(Actions.class));
	}
	
	public Actions getAction(Integer id){
		return this.getHibernateTemplate().load(Actions.class, id);
	}

}
