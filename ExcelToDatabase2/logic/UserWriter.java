package write_users.logic;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import write_users.data_source.ExcelPoiReader;
import write_users.data_source.SessionFactoryBuilder;

public class UserWriter {
	private static Logger logger=null;
	
	public static void main(String[] args){
		// configure Log4J
		BasicConfigurator.configure();

		String pathToHibernateConfig="write_users/data_source/hibernate.cfg.xml";
		String pathToExcel="/tmp/temp/Users Cash loan_script_final.xls";
		
		logger=Logger.getLogger(UserWriter.class);
		logger.setLevel(Level.DEBUG);
		logger.info("begin");
		
		SessionFactory sessionFactory=null;
		try{
			logger.info("try to connect to database ");
			sessionFactory=SessionFactoryBuilder.getSessionFactory(pathToHibernateConfig);
			Session session=sessionFactory.openSession();
			logger.info("try to open Excel ");
			ExcelPoiReader reader=new ExcelPoiReader();
			reader.openXls(pathToExcel);
			
			logger.info("execute logic ");
			executeLogic(reader, session);
			
		}catch(Exception ex){
			logger.error("-----------------");
			logger.error("Execute Exception:"+ex.getMessage());
			logger.error("-----------------");
		}finally{
			try{
				sessionFactory.close();
			}catch(Exception ex){};
		}
		logger.info("-end-");
	}
// ---------------------------------------------------------------------------------------------------
	
	/** main logic of this application  */
	private static void executeLogic(ExcelPoiReader reader, Session session) throws Exception {
		new UserWriterLogic().execute(reader, session);
	}
	
}
