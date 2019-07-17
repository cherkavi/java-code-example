package ua.cetelem;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PreInsertEvent;
import org.hibernate.event.PreInsertEventListener;

public class TestUserGenerator extends TestCase implements PreInsertEventListener, PostInsertEventListener{
	
	private final static long serialVersionUID=1L;
	private Logger logger=Logger.getLogger(this.getClass());
	
	private SessionFactory sessionFactory=null;
	/** id for execute test - may be different ( will be removed from the database after test execution ) */
	private long idTinLogger=302122603;

	private String getXml(){
		StringBuilder returnValue=new StringBuilder();
		returnValue.append("<?xml version=\"1.0\"?>")
		.append("<!DOCTYPE hibernate-mapping PUBLIC").append("\n")
		.append("\"-//Hibernate/Hibernate Mapping DTD 3.0//EN\"").append("\n")
		.append("\"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">").append("\n")		
		.append("<hibernate-mapping>").append("\n")		
		.append("	<class name=\"ua.cetelem.TestUserGeneratorObject\" table=\"TIN_LOGGER\">").append("\n")		
		.append("		<id name=\"id\">").append("\n")		
		.append("			<generator class=\"ua.cetelem.helpers.sequence_generator.DefaultSequenceGenerator\"/>").append("\n")		
		.append("		</id>").append("\n")		
		.append("		<property name=\"tin\" type=\"string\" not-null=\"true\" length=\"12\" column=\"tin\"/>").append("\n")
		.append("		<property name=\"point_id\" type=\"string\" not-null=\"true\" length=\"12\" column=\"point_id\"/>").append("\n")
		.append("		<property name=\"user_id\" type=\"string\" length=\"12\" column=\"user_id\"/>").append("\n")
		.append("		<property name=\"date_write\" type=\"timestamp\" not-null=\"true\" column=\"date_write\"/>").append("\n")
		.append("		<property name=\"result_in_black_list\" type=\"long\" not-null=\"true\" column=\"result_in_black_list\"/>").append("\n")
		.append("		<property name=\"fio\" type=\"string\" length=\"150\" column=\"fio\"/>").append("\n")
		.append("	</class>").append("\n")
		.append("</hibernate-mapping>").append("\n");
		return returnValue.toString();
	}
	
	public void setUp(){
		BasicConfigurator.configure();
		org.hibernate.cfg.Configuration configuration=new org.hibernate.cfg.Configuration();
		/*
        // <property name="connection.url">jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=OFF)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=10.91.57.225)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=10.91.57.227)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=rtn7uadg.world)(FAILOVER_MODE=(TYPE=SESSION)(METHOD=BASIC)(RETRIES=180)(DELAY=5))))</property>
		configuration.setProperty(org.hibernate.cfg.Environment.URL, "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=OFF)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=10.91.57.225)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=10.91.57.227)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=rtn7uadg.world)(FAILOVER_MODE=(TYPE=SESSION)(METHOD=BASIC)(RETRIES=180)(DELAY=5))))");
        // <property name="connection.username">rataqauser</property>
		configuration.setProperty(org.hibernate.cfg.Environment.USER, "rataqauser");
        // <property name="connection.password">uauser</property>
		configuration.setProperty(org.hibernate.cfg.Environment.PASS, "uauser");
        // <property name="connection.driver_class">oracle.jdbc.driver.OracleDriver</property>
		configuration.setProperty(org.hibernate.cfg.Environment.DRIVER, "oracle.jdbc.driver.OracleDriver");
        // <property name="connection.pool_size">1</property>
		configuration.setProperty(org.hibernate.cfg.Environment.POOL_SIZE, "1");
		// <property name="dialect">org.hibernate.dialect.Oracle10gDialect</property>
		configuration.setProperty(org.hibernate.cfg.Environment.DIALECT, "org.hibernate.dialect.Oracle10gDialect");
		// <property name="show_sql">true</property><!-- decrease performance -->
		configuration.setProperty(org.hibernate.cfg.Environment.SHOW_SQL, "false");
		*/
		// configuration.getEventListeners().setPreInsertEventListeners(new PreInsertEventListener[]{this});
		configuration.getEventListeners().setPostInsertEventListeners(new PostInsertEventListener[]{this});
		
		// add to configuration xml text with file 
		configuration.addXML(getXml());
		String pathToConfiguration="/home/prod/workspace/ukraine/coliseum/conf/coliseum-hibernate-config.xml";
		configuration.configure(new File(pathToConfiguration));		
		configuration.setProperty(org.hibernate.cfg.Environment.SHOW_SQL, "false");
		// configuration.configure(createEmptyTempFile("hibernate.cfg.xml"));
		sessionFactory=configuration.buildSessionFactory();
		logger.debug("-------------test begin------------------");
	}
	
	public void tearDown(){
		logger.debug("-------------test end------------------");
		sessionFactory.close();
	}
	
	public void testInsertWithPredefinedNumber(){
		Session session=sessionFactory.openSession();		

		logger.info("remove from table if this exists"); 
		removeIfExists(idTinLogger);
		
		logger.info("create object and insert it to the database via hibernate (core of this test - try )");
		insertValue(getTinLogger(this.idTinLogger, new SimpleDateFormat("HH:mm:ss").format(new Date())));
		
		logger.info("check value for inserted into database");
		assertTrue(removeIfExists(idTinLogger));
		session.close();
	} 
	
	private void insertValue(TestUserGeneratorObject loggerObject) {
		Session session=null;
		try{
			logger.debug("Insert value");
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.save(loggerObject);
			session.getTransaction().commit();
			logger.debug("save OK");
		}catch(Exception ex){
			logger.error("error insert value into Database: "+loggerObject);
			new IllegalStateException();
		}finally{
			try{
				session.close();
			}catch(Exception ex){}
		}
	}

	private boolean removeIfExists(long idForRemove) {
		Session session=null;
		try{
			session=sessionFactory.openSession();
			TestUserGeneratorObject findedValue=(TestUserGeneratorObject)session.get(TestUserGeneratorObject.class, idForRemove);
			if(findedValue!=null){
				logger.debug("try to delete");
				session.beginTransaction();
				session.delete(findedValue);
				session.getTransaction().commit();
				logger.debug("delete OK");
				return true;
			}else{
				return false;
			}
		}finally{
			try{
				session.close();
			}catch(Exception ex){}
		}
	}

	private static TestUserGeneratorObject getTinLogger(long id, String value){
		TestUserGeneratorObject returnValue=new TestUserGeneratorObject();
		returnValue.setTin(value);
		returnValue.setPoint_id(value);
		returnValue.setDate_write(new Date());
		returnValue.setResult_in_black_list(0);
		returnValue.setId(id);
		return returnValue;
	}

	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		logger.debug("(PreInsert)Need to be insert into Database: "+this.idTinLogger);
		assertEquals((Long)this.idTinLogger, (Long)event.getId());
		return false;
	}

	@Override
	public void onPostInsert(PostInsertEvent event) {
		logger.debug("(PostInsert)Need to inserted into Database ( check element number after walk through generator ): "+this.idTinLogger);
		assertEquals((Long)this.idTinLogger, (Long)event.getId());
	}

}
