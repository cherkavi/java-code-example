package database.firebird;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/** класс, который осуществляет подключение к базе данных, используя Hibernate*/
public class HibernateFirebirdConnect {
	private SessionFactory field_session_factory;

	/** получить Properties для конфигурирования AnnotationConfiguration 
	 * @param path_to_gdb путь к файлу, используя разделитель "/"
	 * @param login логин
	 * @param password пароль
	 * @param pool_count размер пула соединений 
	 * */
	private Properties getPropertiesConfiguration(String path_to_gdb,
												  String login, 
												  String password, 
												  Integer pool_count){
        Properties properties=new Properties();
        properties.setProperty("hibernate.connection.driver_class", "org.firebirdsql.jdbc.FBDriver");
        String path_to_database="jdbc:firebirdsql://localhost:3050/"+path_to_gdb+"?sql_dialect=3";
        properties.setProperty("hibernate.connection.url",path_to_database);
        properties.setProperty("hibernate.connection.username", login);
        properties.setProperty("hibernate.connection.password", password);
        properties.setProperty("hibernate.connection.pool_size", pool_count.toString());
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.FirebirdDialect");
        properties.setProperty("hibernate.show_sql", "true");
        
        // hibernate.c3p0.minPoolSize=5
        // hibernate.c3p0.maxPoolSize=20
        // hibernate.c3p0.timeout=1800
        // hibernate.c3p0.max_statement=50
        properties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        //properties.setProperty("hbm2ddl.auto", "update");
        //properties.setProperty("current_session_context_class", "thread");
        return properties;
	}
	
	/** объект, который получает сессии
	 * @param login логин
	 * @param password пароль
	 * @param pool_size размер пула
	 * */
	@SuppressWarnings("unchecked")
	public HibernateFirebirdConnect(String path_to_gdb,
									String login, 
									String password, 
									int pool_size,
									Class ... annotatedClass
									){
        try{
            AnnotationConfiguration aconf = new AnnotationConfiguration();
            // можно создать Properties
            Integer poolSize;
            if(pool_size<0){
            	poolSize=new Integer(0);
            }else{
            	poolSize=new Integer(pool_size);
            }
            aconf.setProperties(getPropertiesConfiguration(path_to_gdb,
            											   login,
            											   password,
            											   poolSize));
            // добавить все POJO классов
            for(int counter=0;counter<annotatedClass.length;counter++){
            	aconf.addAnnotatedClass(annotatedClass[counter]);
            	// aconf.addPackage(arg0) - можно добавить пакет 
            }
           
            this.field_session_factory=aconf.buildSessionFactory();
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
