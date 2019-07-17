package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.w3c.dom.Document;

import com.test.mapping.Ofs_t_cases;
import com.test.mapping.Ofs_t_casesMapper;

public class MyBatis {
	
	public static void main(String[] args) throws IOException{
		System.out.println("begin");
		SqlSessionFactory sqlSessionFactory = getFactoryFromXml();
		// SqlSessionFactory sqlSessionFactory = getFactory();
		System.out.println("SQL factory: "+sqlSessionFactory);
		SqlSession session=null;
		try{
			session=sqlSessionFactory.openSession(false);
			int insertId=insert(session);
			System.out.println("  Case insert: "+insertId);
			Ofs_t_cases cases=selectCase(session, insertId);
			cases.setDomain("Domain update");
			update(session, cases);
			delete(session, insertId);
			insertId=insert(session);
			delete(session, new Ofs_t_cases(insertId));
			session.commit();
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
		}finally{
			try{
				session.close();
			}catch(Exception ex){};
		}
		sqlSessionFactory.openSession();
		System.out.println("-end-");
	}

	private static int update(SqlSession session, Ofs_t_cases cases){
		return session.update("com.test.mapping.Ofs_t_casesMapper.update", cases);
	}
	
	private static int insert(SqlSession session) {
		Ofs_t_cases value=new Ofs_t_cases();
		value.setDomain("domain#"+new Random().nextInt());
		value.setTitle("title#"+new Random().nextInt());
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			// root elements
			Document doc = docBuilder.newDocument();           
			doc.appendChild(doc.createElement("null"));
			value.setPayload(doc);
		}catch(Exception ex){
			System.err.println("can't set value to payload, Exception:"+ex.getMessage());
		}
		session.insert("com.test.mapping.Ofs_t_casesMapper.insert", value);
		return value.getId();
	}

//	private static int update(SqlSession session){}
	
	private static int delete(SqlSession session, int id){
		return session.delete("com.test.mapping.Ofs_t_casesMapper.delete", id);
	}
	
	private static int delete(SqlSession session, Ofs_t_cases cases){
		return session.delete("com.test.mapping.Ofs_t_casesMapper.deleteCase", cases);
	}

	/**
	 * @param session
	 * @param id - unique id of the record in database
	 * @return record by id {@link Ofs_t_cases}
	 */
	private static Ofs_t_cases selectCase(SqlSession session, int id){
		// return 	session.selectOne("com.test.mapping.Ofs_t_cases.selectById", id);
		Ofs_t_casesMapper mapper=session.getMapper(Ofs_t_casesMapper.class);
		return mapper.selectOneCase(id);
	}

	// TODO create Update statement

	private static SqlSessionFactory getFactory(){
		DataSource dataSource = getDataSource() ; // BlogDataSourceFactory.getBlogDataSource();
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment("development", transactionFactory, dataSource);
		Configuration configuration = new Configuration(environment);
		configuration.addMapper(Ofs_t_casesMapper.class);
		configuration.addMapper(Ofs_t_cases.class);
		return new SqlSessionFactoryBuilder().build(configuration);
	}
	
	private static DataSource getDataSource(){
		BasicDataSource dataSource=new BasicDataSource();
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUsername("SYSTEM");
		dataSource.setPassword("root");
		dataSource.setDefaultAutoCommit(false);
		return dataSource;
	}
	
	/**
	 * @return factory from XML file 
	 * @throws IOException
	 */
	private static SqlSessionFactory getFactoryFromXml() throws IOException{
		String resource = "com/test/mybatis_settings.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
}
