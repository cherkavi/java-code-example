package hibernate_replacer.listeners;

import java.io.Serializable;
import java.util.*;
import java.sql.*;

import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.criterion.*;
import org.hibernate.event.*;
	
public class UserReadOnly  {}

/*extends DefaultSaveOrUpdateEventListener{
	private final static long serialVersionUID=1L;
	
	Logger logger=Logger.getLogger(UserReadOnly.class);
	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) {
		// super.onSaveOrUpdate(event);
		logger.warn(">>> saveOrUpdate:"+event.getObject());
		// event.setObject(null);
	}
}
*/