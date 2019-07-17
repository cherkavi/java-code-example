package database;

import java.io.File;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import database.firebird.TableOne;




/** класс, который осуществляет подключение к базе данных, используя Hibernate*/
public class HibernateConnect {
	private SessionFactory field_session_factory;

	@SuppressWarnings("unused")
	private File getFileConfiguration(){
        File file=null;
        try{
            file=new File("D:\\eclipse_workspace\\HibernateAnnotation\\src\\hibernate.cfg.xml");
             
            if(file.exists()){
                System.out.println("file exists");
            }else{
                System.out.println("file not exists");
            }
        }catch(Exception ex){
            System.out.println("Exception:"+ex.getMessage());
        }
        return file;
	}
	
	/** получить Properties для конфигурирования AnnotationConfiguration 
	 * @param login логин
	 * @param password пароль
	 * @param pool_count размер пула соединений 
	 * */
	private Properties getPropertiesConfiguration(String login, 
												  String password, 
												  Integer pool_count){
        Properties properties=new Properties();
        properties.setProperty("hibernate.connection.driver_class", "org.firebirdsql.jdbc.FBDriver");
        properties.setProperty("hibernate.connection.url", "jdbc:firebirdsql://localhost:3050/d:/temp_hibernate.gdb?sql_dialect=3");
        properties.setProperty("hibernate.connection.username", login);
        properties.setProperty("hibernate.connection.password", password);
        properties.setProperty("hibernate.connection.pool_size", pool_count.toString());
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.FirebirdDialect");
        properties.setProperty("hibernate.show_sql", "false");
        //properties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        //properties.setProperty("hbm2ddl.auto", "update");
        //properties.setProperty("current_session_context_class", "thread");
        return properties;
	}
	
	/** объект, который получает сессии
	 * @param login логин
	 * @param password пароль
	 * @param pool_size размер пула
	 * */
	public HibernateConnect(String login, String password, int pool_size){
        try{
            AnnotationConfiguration aconf = new AnnotationConfiguration();
            // можно создать Properties
            Integer poolSize;
            if(pool_size<0){
            	poolSize=new Integer(0);
            }else{
            	poolSize=new Integer(pool_size);
            }
            aconf.setProperties(getPropertiesConfiguration("SYSDBA","masterkey",poolSize));
            // или же подтянуть Properties из файла
            //aconf.configure(file);

            // добавить все POJO классов 
            aconf.addAnnotatedClass(TableOne.class);
           
            this.field_session_factory=aconf.buildSessionFactory();
            //Configuration conf = aconf.configure(file);
            //Session session=conf.buildSessionFactory().openSession();
            
        }catch(Exception ex){
            System.out.println("Except:"+ex.getMessage());
        }
	}
	
	public Session getSession(){
		try{
			return this.field_session_factory.openSession();
		}catch(NullPointerException ex){
			return null;
		}
	}

	/* get data from DataBase            
    Criteria query=session.createCriteria(TableOne.class);
    List resultset=query.list();
    System.out.println("List count:"+resultset.size());
    
    TableOne table_one=(TableOne)session.get(database.TableOne.class, new Integer(2));
    System.out.println("table_one.getField_string()"+table_one.getField_string()+"   table_one.getField_date():"+table_one.getField_timestamp());
    */            

	/** закрыть соединение с Hibernate*/
	public void close(){
		if(this.field_session_factory!=null){
			this.field_session_factory.close();
			this.field_session_factory=null;
		}
	}
	
	public void finalize(){
		if(this.field_session_factory!=null){
			this.field_session_factory.close();
		}
	}
}
