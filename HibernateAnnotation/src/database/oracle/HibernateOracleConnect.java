package database.oracle;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateOracleConnect {
	private SessionFactory field_session_factory;

	/** �������� Properties ��� ���������������� AnnotationConfiguration 
	 * @param path URL � Oracle 
	 * @param login �����
	 * @param password ������
	 * @param pool_count ������ ���� ���������� 
	 * */
	private Properties getPropertiesConfiguration(String path,
												  String login, 
												  String password, 
												  Integer pool_count){
        Properties properties=new Properties();
        properties.setProperty("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver");
        
        properties.setProperty("hibernate.connection.url",path);
        properties.setProperty("hibernate.connection.username", login);
        properties.setProperty("hibernate.connection.password", password);
        properties.setProperty("hibernate.connection.pool_size", pool_count.toString());
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        properties.setProperty("hbm2ddl.auto", "update");
        //properties.setProperty("current_session_context_class", "thread");
        return properties;
	}
	
	/** ������, ������� �������� ������
	 * @param path of connection (jdbc:oracle:thin:@192.168.15.254:1521:demo)
	 * @param login �����
	 * @param password ������
	 * @param pool_size ������ ����
	 * */
	public HibernateOracleConnect(String path,
									String login, 
									String password, 
									int pool_size){
        try{
            AnnotationConfiguration aconf = new AnnotationConfiguration();
            // ����� ������� Properties
            Integer poolSize;
            if(pool_size<0){
            	poolSize=new Integer(0);
            }else{
            	poolSize=new Integer(pool_size);
            }
            aconf.setProperties(getPropertiesConfiguration(path,
            											   login,
            											   password,
            											   poolSize));
            // �������� ��� POJO ������ 
            aconf.addAnnotatedClass(TablePurchasesInt.class);
           
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

	/** ������� ���������� � Hibernate*/
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
