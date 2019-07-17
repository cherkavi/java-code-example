package hibernate_replacer;

import hibernate_replacer.mappings.UserDescription;
import hibernate_replacer.mappings.Users;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibConnector {
	
	private static Logger logger=null;
	
	static{
		BasicConfigurator.configure();
		logger=Logger.getLogger(HibConnector.class);
		Logger.getRootLogger().setLevel(Level.DEBUG);
	}
	
	public static void main(String[] args){
		HibConnector.class.toString();
		logger.info("begin");
		Session session=openSession();
		updateUser(session, 1);
		updateDescription(session,1);
		// saveNewUser(session, 6 );
		printAllUsers(session);
		logger.info("-end-");
	}
	
	private static void updateUser(Session session, int id){
		Users user=(Users)session.get(Users.class, id);
		Transaction transaction=session.beginTransaction();
		logger.info("was: "+user.getDateWrite());
		user.setDateWrite(new Date());
		session.update(user);
		transaction.commit();

		session.refresh(user);
		// user=(Users)session.get(Users.class, id);
		logger.info("new: "+user.getDateWrite());
	}

	private static void updateDescription(Session session, int id){
		UserDescription user=(UserDescription)session.get(UserDescription.class, id);
		Transaction transaction=session.beginTransaction();
		logger.info("was (description) : "+user.getDateWrite());
		user.setDateWrite(new Date());
		session.update(user);
		transaction.commit();

		user=(UserDescription)session.get(UserDescription.class, id);
		logger.info("new (description): "+user.getDateWrite());
	}
	
	private static void printAllUsers(Session session){
		@SuppressWarnings("unchecked")
		List<Users> list=(List<Users>)session.createCriteria(Users.class).list();
		for(Users each:list){
			logger.info(">>> "+each.toString());
		}
	}
	
	private static void saveNewUser(Session session, int id){
		Transaction transaction=session.beginTransaction();
		session.save(getUser(id));
		transaction.commit();
	}
	
	static Users getUser(int id){
		Users returnValue=new Users();
		returnValue.setName("test");
		returnValue.setDescription("");
		returnValue.setDateWrite(new Date());
		returnValue.setId(id);
		return returnValue;
	}
	
	private static SessionFactory sessionFactory=null;
	
	public static Session openSession(){
		try{
			if(sessionFactory==null){
				Configuration configuration=new Configuration();
				// configuration.setInterceptor(new UserInterceptor());
				configuration.configure(new File("hib_config.xml"));
				configuration.addFile(new File("users.hbm.xml"));
				configuration.addFile(new File("users_for_save.hbm.xml"));
				configuration.addFile(new File("user_description.hbm.xml"));
				
				// configuration.getEventListeners().setUpdateEventListeners(new SaveOrUpdateEventListener[]{new UserSaveOrUpdateListener()});
				// configuration.setListener("save-update", new UserReadOnly());
				
				// configuration.getEventListeners().setPreUpdateEventListeners(new PreUpdateEventListener[]{new UserSaveOrUpdateListener()});

				// configuration.getEventListeners().setSaveOrUpdateEventListeners(new SaveOrUpdateEventListener[]{new UserSaveOrUpdateListener()});
				sessionFactory=configuration.buildSessionFactory();
			}
			return sessionFactory.openSession();
		}catch(Exception ex){
			logger.error("!!! TestConnect !!! Exception: "+ex.getMessage());
			return null;
		}
	}
	
	private static void testConnect() throws ClassNotFoundException, SQLException{
		 Class.forName("com.mysql.jdbc.Driver");
		 Connection connection = null;
		 connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/test","root", "1");
		 connection.close();		
	}
}
