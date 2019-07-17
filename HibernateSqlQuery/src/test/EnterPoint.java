package test;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.SessionImpl;
import org.hibernate.impl.StatelessSessionImpl;
import org.hibernate.loader.criteria.CriteriaQueryTranslator;

import test.database.Dictionary;
import test.database.TableTest;


/** 
 * цель проекта - разработка аналогов Inner join через SQLQuery <br />
 * ключевое свойство - добавление addEntity в createSQLQuery <br />
 * <hr />
 * вторая часть - преобразование Criterion в SQLQuery
 * 
 * @author Администратор
 *
 */
public class EnterPoint {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		Session session=SessionFactoryHolder.getSessionFactory().openSession();
		
		List<Dictionary> dictionaryList=session.createCriteria(Dictionary.class).list();
		List<Dictionary> testList=session.createCriteria(TableTest.class).list();
		dictionaryList=session.createSQLQuery("select dictionary.* from dictionary ").addEntity(Dictionary.class).list();
		dictionaryList=session.createSQLQuery("select {dictionary.*} from dictionary").addEntity("dictionary",Dictionary.class).list();
		// TableTest=>table_test    Dictionary=>dictionary
		testList=session.createSQLQuery("SELECT t.* FROM test.table_test t inner join dictionary on dictionary.key_value=t.name").addEntity(TableTest.class).list();
		testList=session.createSQLQuery("SELECT {t.*} FROM test.table_test t inner join dictionary on dictionary.key_value=t.name").addEntity("t",TableTest.class).list();
		
		// testing extract two tables - Object[0]=TableTest    Object[1]=TableTest  
		List<Object[]> objectList=session.createSQLQuery("SELECT {t.*}, {d.*} FROM test.table_test t inner join dictionary d on d.key_value=t.name").addEntity("t",TableTest.class).addEntity("d",Dictionary.class).list();
		
		session.close();
		System.out.println("OK");
	}
}
