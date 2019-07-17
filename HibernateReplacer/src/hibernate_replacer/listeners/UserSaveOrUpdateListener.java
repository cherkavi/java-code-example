package hibernate_replacer.listeners;

import hibernate_replacer.HibConnector;
import hibernate_replacer.mappings.Users;
import hibernate_replacer.mappings.UsersForSave;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.event.PreUpdateEvent;
import org.hibernate.event.PreUpdateEventListener;

public class UserSaveOrUpdateListener implements PreUpdateEventListener{
	private final static long serialVersionUID=1L;
	
	private Logger logger=Logger.getLogger(UserSaveOrUpdateListener.class);

	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		logger.warn("save or Update: "+event.getEntity());
		logger.warn("id:"+event.getId());
		if(event.getEntity() instanceof Users){
			// return VETO=true
			Session session=HibConnector.openSession();
			session.beginTransaction();
			UsersForSave userForSave=(UsersForSave)event.getSession().get(UsersForSave.class, event.getId());
			copyFromSourceToDestination((Users)event.getEntity(), userForSave);
			session.merge(userForSave);
			session.getTransaction().commit();
			return true;
		}else{
			return false;
		}
	}

	private void copyFromSourceToDestination(Users user, UsersForSave usersForSave){
		usersForSave.setName(user.getName());
		usersForSave.setDescription(user.getDescription());
		usersForSave.setDateWrite(user.getDateWrite());
	}
}
