package write_users.logic;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ua.cetelem.db.ed.Externaldistributor;
import ua.cetelem.db.ed.Userexternaldistributorlink;
import ua.cetelem.db.insurance.Powerofattorney;
import ua.cetelem.db.um.User;
import ua.cetelem.db.um.Userrolelink;
import write_users.data_source.ExcelPoiReader;

public class UserWriterLogic {
	private Logger logger=Logger.getLogger(this.getClass());
	
	public void execute(ExcelPoiReader reader, Session session) throws Exception {
		int sheetIndex=0;
		/** max row index on sheet  */
		int rowMaxIndex=reader.getMaxRowIndex(sheetIndex);
		logger.debug("walk through all records ");
		session.beginTransaction();
		for(int rowIndex=2;rowIndex<=rowMaxIndex;rowIndex++){
			logger.debug("get nextRow:"+rowIndex+"/"+rowMaxIndex);
			String[] values=reader.getRowAsArrayOfString(sheetIndex, rowIndex);
			parseValues(values, session);
		}
		// session.getTransaction().commit();
		session.getTransaction().rollback();
	}
	
	private void parseValues(String[] values, Session session) throws Exception{
		logger.trace("table: USERS");
		User user=this.createUser(session, values);
		session.save(user);

		logger.trace("table: USERROLELINK");
		if(isProductCash(values)){
			Userrolelink userRoleLink=this.createUserRoleLink(user, session);
			session.save(userRoleLink);
		}
		
		logger.trace("table: USEREXTERNALDISTRIBUTORLINK");
		Userexternaldistributorlink userEdLink=this.createUserExternalDistributorLink(user, values[6], session);
		session.save(userEdLink);
		
		logger.trace("table: POWEROFATTORNEY ( first record )");
		Powerofattorney pao=createPowerOfAttorney1(user, values, session);
		session.save(pao);
		
		logger.trace("table: POWEROFATTORNEY ( second record )");
		if(isNumberOfInsuranceIsNotEmpty(values)){
			Powerofattorney pao2=createPowerOfAttorney2(user, values, session);
			session.save(pao2);
		}
	}
	
	
	private boolean isNumberOfInsuranceIsNotEmpty(String[] values) {
		return !isEmptyValue(values[10]);
	}

	private boolean isEmptyValue(String value) {
		if(value==null){
			return true;
		}
		return value.trim().length()==0;
	}

	private boolean isProductCash(String[] values) {
		return isStringValueEquals(values[14],"true");	
	}
	
	private boolean isStringValueEquals(String first, String second){
		if((first==null)&&(second==null)){
			return true;
		}
		if((first==null)||(second!=null)){
			return false;
		}
		return first.trim().equalsIgnoreCase(second.trim());
	}

	private Powerofattorney createPowerOfAttorney1(User user, String[] values, Session session) throws Exception {
		return this.createPowerOfAttorney(user, null, values, session);
	}

	private Powerofattorney createPowerOfAttorney2(User user, String[] values, Session session) throws Exception {
		return this.createPowerOfAttorney(user, this.getExternalDistributorByBpId(values[13], session), values, session);
	}
	
	private Powerofattorney createPowerOfAttorney(User user, Long id_externaldistributor, String[] arrayOfString, Session session) throws Exception{
		Powerofattorney returnValue=new Powerofattorney();
		returnValue.setId(this.getGeneratedValue(session));
		returnValue.setAttorney_date_finish(null);
		returnValue.setDictionary_type_of_attorney("2");
		returnValue.setId_externaldistributor(id_externaldistributor);
		returnValue.setAttorney_date_start(this.parseDateFromString(arrayOfString[8]));
		returnValue.setAttorney_number(arrayOfString[7]);
		returnValue.setId_user(user.getId());
		if(id_externaldistributor==null){
			returnValue.setDictionary_type_purpose("1");
		}else{
			returnValue.setDictionary_type_purpose("2");
		}
		
		return returnValue;
	}
	
	/** create UserExternalDistributorLink
	 * @param user -
	 * @param sapBranchId ( Column G(6) )
	 * @param session
	 * @return
	 */
	private Userexternaldistributorlink createUserExternalDistributorLink(User user, 
																		  String sapBranchId, 
																		  Session session) throws Exception{
		Userexternaldistributorlink returnValue=new Userexternaldistributorlink();
		returnValue.setId(this.getGeneratedValue(session));
		returnValue.setId_externaldistributor(getExternalDistributorByBranchId(sapBranchId, session));
		return returnValue;
	}
	
	/**
	 * get ExternalDistributor by Sap_Bp_id
	 * @param string
	 * @param session
	 * @return
	 */
	private Long getExternalDistributorByBpId(String sapBpId, Session session) throws Exception{
		try{
			return ((Externaldistributor)session.createCriteria(Externaldistributor.class)
												.add(Restrictions.eq("sap_bp_id", sapBpId))
												.setMaxResults(1)
												.uniqueResult())
					.getId();
		}catch(Exception ex){
			logger.error("ExternalDistributor by Sap_branch_id does not found:"+ex.getMessage());
			throw ex;
		}
	}

	
	/**
	 * find ExternalDistributor by Branch ID 
	 * @param sapBranchId - sap_branch_id ( Column G(6)) 
	 * @param session
	 * @return
	 * @throws Exception - when ExternalDistributor does not found 
	 */
	private Long getExternalDistributorByBranchId(String sapBranchId, Session session) throws Exception {
		try{
			return ((Externaldistributor)session.createCriteria(Externaldistributor.class)
												.add(Restrictions.eq("sap_branch_id", sapBranchId))
												.setMaxResults(1)
												.uniqueResult())
					.getId();
		}catch(Exception ex){
			logger.error("ExternalDistributor by Sap_branch_id does not found:"+ex.getMessage());
			throw ex;
		}
	}

	/** create USERROLELINK */
	private Userrolelink createUserRoleLink(User user, Session session) throws Exception{
		Userrolelink returnValue=new Userrolelink();
		returnValue.setId(this.getGeneratedValue(session));
		returnValue.setId_user(user.getId());
		returnValue.setId_role(63l);
		return returnValue;
	}
	
	/** create USER */
	private User createUser(Session session, String[] arrayOfString) throws Exception{
		Date currentDate=new Date();
		
		User user=new User();
		user.setId(getGeneratedValue(session));
		user.setCode_tt(arrayOfString[6]);
		user.setDictionary_type_of_attorney("2");
		// default password "Qwerty+3"
		user.setPassport("5a6def92fefe24156a2ca743280e72fcb83bfd60d9d7f3c6c891db562f31904bb6c720f7d111a2202ba064e418782ef6");
		user.setBlocked_end(null);
		user.setStatus(0);
		user.setLogin(arrayOfString[1]);
		user.setDeleted_comment(null);
		user.setTable_number(arrayOfString[4]);
		user.setCreated(currentDate);
		user.setBlocked_comment(null);
		user.setAttorney_date_start(this.parseDateFromString(arrayOfString[8]));
		user.setPassword_changed(currentDate);
		user.setAttorney_number(arrayOfString[7]);
		user.setReason(0);
		user.setAttorney_date_finish(null);
		user.setName(arrayOfString[2]);
		user.setDeleted(null);
		user.setBlocked_temporary(null);
		user.setLogin_attempts(0);
		user.setPhone1(null);
		user.setPhone2(null);
		user.setLast_ip("127.0.0.1");
		user.setBlocked_start(null);
		user.setLast_login(null);
		user.setDescription(arrayOfString[3]);
		user.setActivated(null);
		user.setLast_shop_code(null);
		user.setBlocked(false);
		return user;
	}
	
	
	private SimpleDateFormat sdf=new SimpleDateFormat("ddMMyyyy");
	
	private Date parseDateFromString(String stringWithDate){
		try{
			return sdf.parse(stringWithDate);
		}catch(Exception ex){
			logger.error("Parse date from string Exception:"+ex.getMessage());
			return null;
		}
	}
	
	/** get next value from database */
	private Long getGeneratedValue(Session session) throws SQLException{
		return (Long)session.createSQLQuery("select hibernate_sequence.nextval FROM dual").setMaxResults(1).uniqueResult();
	}
	
}
