package ua.cetelem.helpers.sequence_generator;

import java.io.Serializable;
import java.sql.ResultSet;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;

import ua.cetelem.LOG;

/**
 * default generator for mapping, use example :
        <id name="id">
            <!--  generator class="native"/  -->
            <generator class="ua.cetelem.helpers.sequence_generator.DefaultSequenceGenerator"/>
        </id>

 */
public class DefaultSequenceGenerator  implements org.hibernate.id.IdentifierGenerator{

	/** native generator for generate default value */
	// private IdentifierGenerator nativeGenerator=null;
	/** name of table with Default native generator, IMPORTANT not use this generator into nativeGeneratorTable */
	// private static final Class<?> nativeGeneratorTable=NativeGeneratorDetector.class;
	private final static String hibernateSequenceName="hibernate_sequence";
	
	/*
	private IdentifierGenerator getNativeGenerator(SessionImplementor implementator){
		// implementator.getFactory().getDialect().getSelectSequenceNextValString();
		// check Dialect 
		// LOG.debug(this, "Dialect: "+implementator.getFactory().getDialect()); // org.hibernate.dialect.Oracle10gDialect - maybe create from Dialect ( but don't understand the name of a sequence from Database hibernate_sequence - may be ignored )
		// IdentifierGenerator nativeGenerator=(IdentifierGenerator)implementator.getFactory().getDialect().getNativeIdentifierGeneratorClass().newInstance(); - create empty realization
		// net.sf.hibernate.cfg.Configuration.iterateGenerators(Configuration.java:434) - future version 
		return implementator.getFactory().getIdentifierGenerator(nativeGeneratorTable.getName());
	}*/

	private String sqlForGetValueFromDb=null;
	
	private Serializable getFromDb(SessionImplementor implementator) throws Exception {
		// getSelectSequenceNextValString, getSequenceNextValString
		if(this.sqlForGetValueFromDb==null){
			LOG.debug("Sequence Generator: "+this.sqlForGetValueFromDb);
			this.sqlForGetValueFromDb=implementator.getFactory().getDialect().getSequenceNextValString(hibernateSequenceName);
		}
		ResultSet rs=null;
		try{
			rs=implementator.getJDBCContext().getConnectionManager().getConnection().createStatement().executeQuery(this.sqlForGetValueFromDb);
			rs.next();
			return rs.getLong(1);
		}finally{
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
		}
	}
	
	public Serializable generate(SessionImplementor implementator, Object value) throws HibernateException {
		// check for instance Interface 
		if(value instanceof IUniqueId){
			IUniqueId persistObject=(IUniqueId)value;
			if(persistObject.getId()!=0){
				// LOG.info(this, "program ");
				return persistObject.getId();
			}
		}
		// try to return NATIVE Generator value 
		try{
			/*
			if(this.nativeGenerator==null){
				this.nativeGenerator=getNativeGenerator(implementator);
			}
			return nativeGenerator.generate(implementator, value);
			*/
			return getFromDb(implementator);
		}catch(Exception ex){
			LOG.error(this, ex, "generate value Error");
			throw new HibernateException("can't generate next value by NATIVE, check mapping for Class: ");
		}
	}

}
