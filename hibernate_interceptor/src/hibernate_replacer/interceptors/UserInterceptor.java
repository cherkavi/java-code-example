package hibernate_replacer.interceptors;

import java.io.Serializable;
import java.util.Iterator;

import org.apache.log4j.Logger;
// import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class UserInterceptor{
	
}

/*
extends EmptyInterceptor{
	private final static long serialVersionUID=1L;
	private Logger logger=Logger.getLogger(UserInterceptor.class);
	
	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		logger.info(">>> onLoad:"+entity);
		return false; //super.onLoad(entity, id, state, propertyNames, types);
	}
	
	@Override
	public boolean onSave(Object entity, 
						  Serializable id, 
						  Object[] state,
						  String[] propertyNames, 
						  Type[] types) {
		logger.info(">>> onSave:"+entity);
		return false; // super.onSave(entity, id, state, propertyNames, types);
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		logger.debug(">>> onFlashDirty:"+entity);
		return false;
	}
	
	@Override
	public void preFlush(Iterator entities) {
		while(entities.hasNext()){
			Object nextEntity=entities.next();
			logger.info(""+nextEntity);
		}
	}
	
}
*/