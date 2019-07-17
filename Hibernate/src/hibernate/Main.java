/*
 * Main.java
 *
 * Created on 11 червня 2008, 15:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package hibernate;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import java.util.List;
// ------- end one variant
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.Ejb3Configuration;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Technik
 */
public class Main {

    /** Creates a new instance of Main */
    public Main() {
    }
    
    
    private static void insert_new(Session session){
            table_assortment assortment=new table_assortment();
            // set value for object 
            assortment.setBar_code("hello");
            assortment.setKod_class(new Long(11111));
            // set ID
            //assortment.setKod(new Long(3000));
            // get unique ID
            SQLQuery query=session.createSQLQuery("SELECT GEN_ID(GEN_ASSORTMENT_KOD,1) FROM  RDB$DATABASE");
            List result=query.list();
            if(result.get(0)!=null){
                assortment.setKod(new Long(((java.math.BigInteger)result.get(0)).intValue()));
                System.out.println("ID generated:"+((java.math.BigInteger)result.get(0)).intValue());
            }else{
                System.out.println("Is null");
            }
            // start of transaction
            session.getTransaction().begin();
            //session.merge(assortment);
            // insert object into database
            session.saveOrUpdate(assortment);
            // flush data
            session.flush();
            // transaction commit
            session.getTransaction().commit();
            // session closing
            session.close();
        
    }
    private static void read(Session session, Long key){
        table_assortment assortment=(table_assortment)session.get(table_assortment.class,key);
        if(assortment!=null){
            System.out.println("KOD:>"+assortment.getKod());
        }else{
            System.out.println("Assortment is null");
        }
    }
    
    private static void read_all(Session session){
        //Criteria query=session.createCriteria(table_assortment.class);
        // Restriction.name only small letter!!!
        Criteria query=session.createCriteria(table_assortment.class)
                                .add(Restrictions.ge("kod",new Long(1000)))
                                //.setFirstResult(5)
                                .add(Restrictions.like("bar_code","%4%"))
                                .addOrder(Order.asc("kod"));
        List resultset=query.list();
        System.out.println("size:"+resultset.size());
        for(int counter=0;counter<resultset.size();counter++){
            System.out.println("ResultSet KOD:"+((table_assortment)resultset.get(counter)).getKod());
        }
    }
    private static void variant_one(){
        File file=null;
        try{
            file=new File("hibernate.cfg.xml");
            if(file.exists()){
                System.out.println("file exists");
            }else{
                System.out.println("file not exists");
            }
        }catch(Exception ex){
            System.out.println("Exception:"+ex.getMessage());
        }

        try{
            
            AnnotationConfiguration aconf = new AnnotationConfiguration().addAnnotatedClass(table_assortment.class);
            // прямое имя не хочет воспринимать "hibernate.cfg.xml"
            Configuration conf = aconf.configure(file);
            Session session=conf.buildSessionFactory().openSession();
            //insert_new(session);
            read(session,new Long(3008));
            read_all(session);
        }catch(Exception ex){
            System.out.println("Except:"+ex.getMessage());
        }
        
    }

    
    /** not working, why? */
    private static void variant_two(){
        File file=null;
        try{
            file=new File("hibernate.cfg.xml");
            if(file.exists()){
                System.out.println("file exists");
            }else{
                System.out.println("file not exists");
            }
        }catch(Exception ex){
            System.out.println("Exception:"+ex.getMessage());
        }

        Log logger=LogFactory.getLog(Main.class.getClass());
        SessionFactory sessionFactory;
        Ejb3Configuration ejb3Configuration;
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new AnnotationConfiguration().configure(file).buildSessionFactory();
            ejb3Configuration = new Ejb3Configuration().configure(file.getAbsolutePath());
            
            EntityManager manager=ejb3Configuration.buildEntityManagerFactory().createEntityManager();
            EntityTransaction transaction=manager.getTransaction();
            transaction.begin();
            
            table_assortment assortment=new table_assortment();
            // set value for object 
            assortment.setBar_code("hello");
            assortment.setKod_class(new Long(11111));
            assortment.setKod(new Long(4000));
            manager.persist(assortment);
            
            transaction.commit();
        } catch (Throwable ex) {
            logger.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        variant_one();
        //variant_two();
    }
    
}
