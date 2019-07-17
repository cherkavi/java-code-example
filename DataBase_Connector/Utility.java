package pay.database;

import java.io.ByteArrayInputStream;


import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import nsmep.receipt.records.NsmepResponse;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import pay.database.connector.Connector;
import pay.database.wrap.Satellite;
import pay.database.wrap.SatelliteAlgorithmStep;
import pay.database.wrap.SatelliteClients;
import pay.database.wrap.SatelliteClientsArgus;
import pay.database.wrap.SatelliteClientsParameters;
import pay.database.wrap.SatellitePartnerOrder;
import pay.transport.xml.Parameter;
import pay.transport.xml.Security;

public class Utility {

	protected void debug(String value){
		System.out.print("BonPay.pay.database.Utility");
		System.out.print(" DEBUG: ");
		System.out.println(value);
	}
	protected void error(String value){
		System.out.print("BonPay.pay.database.Utility");
		System.out.print(" ERROR: ");
		System.out.println(value);
	}
	
	/** получить по номеру клиента номер заказа, к нему прикрепленный 
	 * */
	public Integer getOrderNumberByClientId(Integer idClient){
		Integer returnValue=null;
		Session session=null; Connection connection=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			Object value=session.createCriteria(SatellitePartnerOrder.class).add(Restrictions.eq("idClient", idClient)).uniqueResult();
			if((value!=null)&&(value instanceof SatellitePartnerOrder)){
				returnValue=((SatellitePartnerOrder)value).getId();
			}
		}catch(Exception ex){
			error("Utility#getOrderNumberByClientId Exception:"+ex.getMessage());
		}finally{
			try{
				Connector.closeSession(session,connection);
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	/** получить номер клиента по известному уникальному номеру заказа*/
	
	
	/** получить по указанному clientId ответ от сервера в виде объекта NsmepResponse, 
	 * для пересчета суммы платежа  */
	@SuppressWarnings("unchecked")
	public NsmepResponse getNsmepResponseForRecalculate(Integer clientId){
		NsmepResponse returnValue=null;
		Session session=null; Connection connection=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			SatellitePartnerOrder partnerOrder=((List<SatellitePartnerOrder>)
												session.createCriteria(SatellitePartnerOrder.class)
												.add(Restrictions.eq("idClient", clientId))
												.addOrder(Order.asc("dateWrite")).list()).get(0);
			if(partnerOrder.getPartnerOrderNumber()!=null){
				SatelliteClientsArgus clientArgus=null;
				try{
					clientArgus=((List<SatelliteClientsArgus>)
					   session.createCriteria(SatelliteClientsArgus.class)
					   .add(Restrictions.eq("idOrder", partnerOrder.getId()))
					   .addOrder(Order.asc("dateWrite"))
					   .list()).get(0);
				}catch(Exception ex){
					error("#getNsmepResponseForRecalculate get SatelliteClientsArgus: "+ex.getMessage());
				}
				returnValue=new NsmepResponse(clientArgus.getData(),clientArgus.getSpecTp());
			}else{
				throw new Exception("Order number is not finded ");
			}
			
		}catch(Exception ex){
			error("#getNsmepResponseForRecalculate Exception:"+ex.getMessage());
		}finally{
			try{
				Connector.closeSession(session,connection);
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	
	/** установить для указанного клиента новый номер сесси (BONPAY_SESSION_ID)
	 * @param clientId - уникальный номер клиента 
	 * @param newSessionId - новый код, который будет присвоен данному клиенту
	 * */
	public boolean setNewBonPaySessionIdForClient(Integer clientId, 
														String newSessionId){
		boolean returnValue=false;
		Session session=null; Connection connection=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			// получить запись по указанному SatelliteId
			session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<SatelliteClients> satelliteClients=((List<SatelliteClients>)session.createCriteria(
												SatelliteClients.class)
												.add(Restrictions.eq("id",clientId)).addOrder(Order.desc("id"))
												.list());
			debug("Utility#setNewBonPaySessionIdForClient: oldValue:"+satelliteClients.get(0).getBonpaySessionId()+"    newValue:"+newSessionId);
			satelliteClients.get(0).setBonpaySessionId(newSessionId);
			session.update(satelliteClients.get(0));
			session.getTransaction().commit();
			returnValue=true;
		}catch(Exception ex){
			error("getSatelliteClientsState Exception:"+ex.getMessage());
		}finally{
			if(session!=null){
				if(session.getTransaction().isActive()){
					session.getTransaction().rollback();
				}
				Connector.closeSession(session,connection);
			}
		}
		return returnValue;
	}
	
	
	/** получить по указанному клиенту имя атрибута, 
	 * который был указан партнером в качестве передачи параметра уникального идентификатора данного клиента
	 * в запросе GET или POST ( позитивный, отмена, ошибка )*/
	public String getParameterKeyByClient(Integer clientId){
		String returnValue=null;
		Session session=null; Connection connection=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			// получить запись по указанному SatelliteId
			SatelliteClients satelliteClients=(SatelliteClients)session.createCriteria(SatelliteClients.class).add(Restrictions.eq("id",clientId)).uniqueResult();
			Satellite satellite=(Satellite)session.createCriteria(Satellite.class)
							    .add(Restrictions.eq("id", satelliteClients.getSatelliteId()))
							    .uniqueResult();
			returnValue=satellite.getParameterKey();
		}catch(Exception ex){
			error("getParameterKeyByClient Exception:"+ex.getMessage());
		}finally{
			if(session!=null){
				if(session.getTransaction().isActive()){
					session.getTransaction().rollback();
				}
				Connector.closeSession(session,connection);
			}
		}
		return returnValue;
	}
	
	/** получить по указанному клиенту путь, на который следует перенаправить данного клиента 
	 * @param clientIdString - уникальный идентификатор клиента
	 * @return URL_OK 
	 * */
	public String getUrlOkByClient(String clientIdString){
		String returnValue=null;
		Session session=null; Connection connection=null;
		try{
			Integer clientId=Integer.parseInt(clientIdString);
			connection=Connector.getConnection();session=Connector.openSession(connection);
			// получить запись по указанному SatelliteId
			SatelliteClients satelliteClients=((SatelliteClients)session.createCriteria(
												SatelliteClients.class)
												.add(Restrictions.eq("id",clientId))
												.uniqueResult());
			Integer satelliteId=satelliteClients.getSatelliteId();
			Satellite satellite=(Satellite)session.createCriteria(Satellite.class)
							    .add(Restrictions.eq("id", satelliteId))
							    .uniqueResult();
			returnValue=satellite.getUrlOk();
		}catch(Exception ex){
			error("getUrlOkByClient Exception:"+ex.getMessage());
		}finally{
			if(session!=null){
				if(session.getTransaction().isActive()){
					session.getTransaction().rollback();
				}
				Connector.closeSession(session,connection);
			}
		}
		return returnValue;
	}
	/** получить по указанному клиенту путь, на который следует перенаправить данного клиента 
	 * @param clientIdString - уникальный идентификатор клиента
	 * @return URL_OK 
	 * */
	public String getUrlCancelByClient(String clientIdString){
		String returnValue=null;
		Session session=null; Connection connection=null;
		try{
			Integer clientId=Integer.parseInt(clientIdString);
			connection=Connector.getConnection();session=Connector.openSession(connection);
			// получить запись по указанному SatelliteId
			SatelliteClients satelliteClients=((SatelliteClients)session.createCriteria(
												SatelliteClients.class)
												.add(Restrictions.eq("id",clientId))
												.uniqueResult());
			Integer satelliteId=satelliteClients.getSatelliteId();
			Satellite satellite=(Satellite)session.createCriteria(Satellite.class)
							    .add(Restrictions.eq("id", satelliteId))
							    .uniqueResult();
			returnValue=satellite.getUrlCancel();
		}catch(Exception ex){
			error("getUrlCancelByClient Exception:"+ex.getMessage());
		}finally{
			if(session!=null){
				if(session.getTransaction().isActive()){
					session.getTransaction().rollback();
				}
				Connector.closeSession(session,connection);
			}
		}
		return returnValue;
	}
	/** получить по указанному клиенту путь, на который следует перенаправить данного клиента 
	 * @param clientIdString - уникальный идентификатор клиента
	 * @return URL_OK 
	 * */
	public String getUrlErrorByClient(String clientIdString){
		String returnValue=null;
		Session session=null; Connection connection=null;
		try{
			Integer clientId=Integer.parseInt(clientIdString);
			connection=Connector.getConnection();session=Connector.openSession(connection);
			// получить запись по указанному SatelliteId
			SatelliteClients satelliteClients=((SatelliteClients)session.createCriteria(
												SatelliteClients.class)
												.add(Restrictions.eq("id",clientId))
												.uniqueResult());
			Integer satelliteId=satelliteClients.getSatelliteId();
			Satellite satellite=(Satellite)session.createCriteria(Satellite.class)
							    .add(Restrictions.eq("id", satelliteId))
							    .uniqueResult();
			returnValue=satellite.getUrlError();
		}catch(Exception ex){
			error("getUrlErrorByClient Exception:"+ex.getMessage());
		}finally{
			if(session!=null){
				if(session.getTransaction().isActive()){
					session.getTransaction().rollback();
				}
				Connector.closeSession(session,connection);
			}
		}
		return returnValue;
	}
	
	/** установить для клиента (SatelliteClients) новое состояние (CLIENT_STATE)  
	 * @param clientIdString уникальный идентификатор клиента 
	 * @param state состояние, которое нужно ему назначить 
	 * */
	public boolean setSatelliteClientsState(String clientIdString, Integer state){
		boolean returnValue=false;
		Session session=null; Connection connection=null;
		try{
			Integer clientId=Integer.parseInt(clientIdString);
			connection=Connector.getConnection();session=Connector.openSession(connection);
			// получить запись по указанному SatelliteId
			session.beginTransaction();
			SatelliteClients satelliteClients=((SatelliteClients)session.createCriteria(
												SatelliteClients.class)
												.add(Restrictions.eq("id",clientId))
												.uniqueResult());
			satelliteClients.setClientState(state);
			session.update(satelliteClients);
			session.getTransaction().commit();
			returnValue=true;
		}catch(Exception ex){
			error("getSatelliteClientsState Exception:"+ex.getMessage());
		}finally{
			if(session!=null){
				if(session.getTransaction().isActive()){
					session.getTransaction().rollback();
				}
				Connector.closeSession(session,connection);
			}
		}
		return returnValue;
	}
	
	/** получить уникальный номер клиента, на основании SessionId (уникального идентификатора сесси на BonPay)
	 * @param bonPaySessionId - уникальный Id сессии (на BonPay) по перенаправленному с Satellite клиента
	 * @return null - если данный клиент не найден
	 * */
	public Integer getClientIdBySessionId(String bonPaySessionId){
		Session session=null; Connection connection=null;
		Integer returnValue=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			// получить SatelliteClients по уникальному номеру сессии BonPay  
			@SuppressWarnings("unchecked")
			List<SatelliteClients> satelliteClients=((List<SatelliteClients>)session.createCriteria(SatelliteClients.class).add(Restrictions.like("bonpaySessionId",bonPaySessionId)).add(Restrictions.eq("clientState", new Integer(0))).addOrder(Order.desc("id")).list());
			returnValue=satelliteClients.get(0).getId();
		}catch(Exception ex){
			error("getClientIdBySessionId Exception:"+ex.getMessage()+"\n   bonPaySessionId:"+bonPaySessionId);
		}finally{
			if(session!=null){
				Connector.closeSession(session,connection);
			}
		}
		return returnValue;
	}
	/**
	 * получить уникальный номер SatelliteId на основании его удаленного хоста
	 * @param remoteHostName 
	 * return null, если не удалось опознать Satellite на основании remoteHost
	 */
	public Integer getSatelliteIdByRemoteHost(String remoteHost){
		Session session=null; Connection connection=null;
		Integer returnValue=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			// получить запись по указанному SatelliteId
			Satellite satellite=((Satellite)session.createCriteria(Satellite.class).add(Restrictions.like("url",remoteHost)).uniqueResult());
			returnValue=satellite.getId();
		}catch(Exception ex){
			error("getSatelliteIdByRemoteHost Exception:"+ex.getMessage());
		}finally{
			if(session!=null){
				Connector.closeSession(session,connection);
			}
		}
		return returnValue;
	}
	
	
	
	/** проверить валидность ключа (KEY_FOR_SERVICE) доступа, который прислал Satellite 
	 * @param satelliteName имя Satellite
	 * @param accessKey - ключ, который должен быть сохранен в базе как ключ доступа для данного Satellite
	 * */
	public boolean checkAccesKeyBySatelliteId(String satelliteName, String accessKey){
		Session session=null; Connection connection=null;
		boolean return_value=false;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			// получить запись по указанному SatelliteId
			Satellite satellite=((Satellite)session.createCriteria(Satellite.class).add(Restrictions.like("name",satelliteName)).uniqueResult());
			return_value=satellite.getKey_for_service().equals(accessKey);
		}catch(Exception ex){
			error("checkAccessKeyBySatelliteId Exception:"+ex.getMessage());
		}finally{
			if(session!=null){
				Connector.closeSession(session,connection);
			}
		}
		return return_value;
	}
	
	/** сгенерировать уникальный ключ для клиента Satellite <br>
	 * c которым (ключем) данный клиент сможет самостоятельно "зайти" к нам на HTML ресурс 
	 * @param security - часть XML файла, которая отвечает за безопасность соединения
	 * */
	public String generateUniqueKeyForServerAccessAndSaveIt(Security security) {
		String returnValue=null;
		Session session=null; Connection connection=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			int satelliteId=getSatelliteIdFromSatelliteName(security.getSatelliteId());
			if(satelliteId>0){
				returnValue=Integer.toString(satelliteId)+getUniqueChar(10);
				Satellite satellite=(Satellite)session.createCriteria(Satellite.class).add(Restrictions.eq("id", new Integer(satelliteId))).uniqueResult();
				satellite.setKey_for_satellite(security.getResponseKey());
				satellite.setKey_for_service(returnValue);
				session.beginTransaction();
				session.save(satellite);
				session.getTransaction().commit();
			}else{
				throw new Exception("satellite id not recongnized:"+security.getSatelliteId());
			}
		}catch(Exception ex){
			returnValue=null;
			error("generateUniqueKeyForServerAccessAndSaveIt: Exception:"+ex.getMessage());
		}finally{
			try{
				Connector.closeSession(session,connection);
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	/** получить код Satellite на основании его имени SATELLITE.ID*/
	public int getSatelliteIdFromSatelliteName(String satelliteName) throws Exception {
		int return_value=(-1);
		Session session=null; Connection connection=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			return_value=((Satellite)session.createCriteria(Satellite.class).add(Restrictions.like("name", satelliteName)).uniqueResult()).getId();
		}catch(Exception ex){
			error("getSatelliteIdFromSatelliteName is not finded");
		}finally{
			if(session!=null){
				Connector.closeSession(session,connection);
			}
		}
		// return result
		if(return_value==(-1)){
			throw new Exception("Satellite Id is not finded: "+satelliteName);
		}else{
			return return_value;
		}
	}
	
	/** Вернуть код satelliteId на основании имени 
	 * @param satelliteName - имя партнера 
	 * */
	public Integer getSatelliteIdByName(String satelliteName){
		Integer returnValue=null;
		Session session=null; Connection connection=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			Object object=session.createSQLQuery("SELECT ID FROM SATELLITE WHERE NAME=:name").addScalar("ID",Hibernate.INTEGER).setString("name",satelliteName).uniqueResult();
			if(object instanceof Integer){
				returnValue=(Integer)object;
			}
		}catch(Exception ex){
			error("getSatelliteIdByName Exception:"+ex.getMessage());
		}finally{
			try{
				Connector.closeSession(session,connection);
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	/**
	 * получить код клиента из таблицы SATELLITE_CLIENTS 
	 * @param satelliteId - код Satellite
	 * @param key - ключ, который назначен данному клиенту для доступа к ресурсу
	 * @return null - если ключ не найден по указанному клиенту 
	 * или код клиента в таблице SATELLITE_CLIENTS 
	 */
	@SuppressWarnings("unchecked")
	public Integer getSatelliteClientId_NewClient(Integer satelliteId, String key){
		Session session=null; Connection connection=null;
		Integer returnValue=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			List<Object> list=session.createSQLQuery("SELECT * FROM SATELLITE_CLIENTS WHERE SATELLITE_ID=:satellite_id AND SESSION_ID=:key AND (CLIENT_STATE=0 or CLIENT_STATE IS NULL)")
							 .addScalar("ID",Hibernate.INTEGER)
							 .setInteger("satellite_id", satelliteId)
							 .setString("key",key)
							 .list();
			returnValue=(Integer)list.get(0);
		}catch(Exception ex){
			error("getSatelliteClientId:"+ex.getMessage());
		}finally{
			try{
				Connector.closeSession(session,connection);
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	/** может ли данный клиент получать доступ к информационному ресурсу BonPay
	 * @param - request - запрос к данному ресурсу от Satellite.Sender ( имеет в параметрах "satellite_session")
	 * @param  - если это запрос первый, то нужно изменить уникальный номер сессии Sender-а на уникальный номер сессии получателя  
	 * */
	public boolean isAccessPermissionToBonPay(HttpServletRequest request,boolean setNewSession){
		// вывести в System.out все параметры из запроса:
/*		Enumeration<?> enumeration=request.getParameterNames();
		String key=null;
		while(enumeration.hasMoreElements()){
			try{
				key=null;
				key=(String)enumeration.nextElement();
				debug("ParamName:"+key+"    ParamValue:"+request.getParameter(key));
			}catch(Exception ex){
				error("get parameter Exception:"+ex.getMessage());
			}
		}
*/
		debug("isAccessPermissionToBonPay: session of this request:"+request.getSession().getId());
		if((setNewSession==true)&&(this.getSatelliteClientId_NewClient(request.getSession().getId())==null)){
			debug(" замена уникального номера сессии: real:"+request.getSession().getId()+"    new:"+request.getParameter("satellite_session"));
			if(replaceSessionIdForClient(request.getParameter("satellite_session"),
										 request.getSession().getId())==true){
				debug("session replaced before:"+request.getParameter("satellite_session")+"after:"+request.getSession().getId());
			}
		}
		debug("isAccessPermissionToBonPay:"+request.getSession().getId());
		Integer clientId=this.getSatelliteClientId_NewClient(request.getSession().getId());
		debug("isAccessPermissionToBonPay clientId:"+clientId);
		return clientId!=null;
	}

	/**
	 * определить клиента по уникальному номеру сессии для Sender и заменить у него BON_PAY_SESSION_ID
	 * @param senderSession - сессия Sendera на BonPay 
	 * @param newClientSession - новая сессия клиента, которую нужно записать вместо BonPay
	 */
	public boolean replaceSessionIdForClient(String senderSession, String clientSession){
		boolean returnValue=false;
		Session session=null; Connection connection=null;
		try{
			Integer clientId=getSatelliteClientId_NewClient(senderSession);
			if(clientId!=null){
				connection=Connector.getConnection();session=Connector.openSession(connection);
				SatelliteClients record=(SatelliteClients)session.get(SatelliteClients.class, clientId);
				record.setBonpaySessionId(clientSession);
				session.beginTransaction();
				session.update(record);
				session.getTransaction().commit();
				returnValue=true;
			}
		}catch(Exception ex){
			error("replaceSessionIdForClient Exception:"+ex.getMessage());
		}finally{
			try{
				Connector.closeSession(session,connection);
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	/**
	 * получить код клиента из таблицы SATELLITE_CLIENTS 
	 * @param satelliteId - код Satellite
	 * @param key - ключ, который назначен данному клиенту для доступа к ресурсу
	 * @return null - если ключ не найден по указанному клиенту 
	 * или код клиента в таблице SATELLITE_CLIENTS 
	 */
	@SuppressWarnings("unchecked")
	public Integer getSatelliteClientId_NewClient(String bonpaySession){
		Session session=null; Connection connection=null;
		Integer returnValue=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			List<Object> list=session.createSQLQuery("SELECT * FROM SATELLITE_CLIENTS WHERE BONPAY_SESSION_ID=:session AND (CLIENT_STATE=0 or CLIENT_STATE IS NULL)")
							 .addScalar("ID",Hibernate.INTEGER)
							 .setString("session", bonpaySession)
							 .list();
			returnValue=(Integer)list.get(0);
		}catch(Exception ex){
			error("getSatelliteClientId_NewClient:"+ex.getMessage()+"\n sessionId:"+bonpaySession+"");
		}finally{
			try{
				Connector.closeSession(session,connection);
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	/** получить параметр (Parameter) по его имени (Parameter.getName()) на основании уникального номера клиента
	 * @param clientId - уникальный номер клиента, по которому нужно получить параметры
	 * @param parameterName - уникальное имя параметра (в контексте  
	 * @return null если параметр не найден или же вернуть значение данного параметра
	 * */
	@SuppressWarnings("unchecked")
	public String getSatelliteClientParameterByName(Integer clientId,String parameterName){
		String returnValue=null;
		Session session=null; Connection connection=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			if(clientId!=null){
				List<SatelliteClientsParameters> list=(List<SatelliteClientsParameters>) session.createCriteria(SatelliteClientsParameters.class).add(Restrictions.eq("idSatelliteClients", clientId)).list();
				for(int counter=0;counter<list.size();counter++){
					Parameter parameter=(Parameter)this.convertByteArrayToSerializable(list.get(counter).getParameter());
					if(parameter.getName().equals(parameterName)){
						returnValue=parameter.getValue();
						break;
					}
				}
			}else{
				throw new Exception("Utility#SatelliteClientParameterByName   Client is null:"+clientId);
			}
		}catch(Exception ex){
			error("getSatelliteClientParameterByName:"+ex.getMessage());
		}finally{
			try{
				Connector.closeSession(session,connection);
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	@SuppressWarnings("unchecked")
	public String getSatelliteClientParameterByName(Integer clientId,String parameterName, Session session){
		String returnValue=null;
		try{
			if(clientId!=null){
				List<SatelliteClientsParameters> list=(List<SatelliteClientsParameters>) session.createCriteria(SatelliteClientsParameters.class).add(Restrictions.eq("idSatelliteClients", clientId)).list();
				for(int counter=0;counter<list.size();counter++){
					Parameter parameter=(Parameter)this.convertByteArrayToSerializable(list.get(counter).getParameter());
					if(parameter.getName().equals(parameterName)){
						returnValue=parameter.getValue();
						break;
					}
				}
			}else{
				throw new Exception("Utility#SatelliteClientParameterByName   Client is null:"+clientId);
			}
		}catch(Exception ex){
			error("getSatelliteClientParameterByName:"+ex.getMessage());
		}
		return returnValue;
	}
	
	
	public static void main(String[] args){
		//System.out.println("SatelliteKod:"+getSatelliteClientId_NewClient(1,"92F82AC074F6066AD66E7603D5E3C95B"));
	}
	
	private final static String hexChars[] = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
	/** сгенерировать случайную последовательность из Hex чисел, указанной длинны 
	 * @param count - длинна случайной последовательности, которую необходимо получить 
	 * */
	private String getUniqueChar(int count){
        StringBuffer return_value=new StringBuffer();
        Random random=new java.util.Random();
        int temp_value;
        for(int counter=0;counter<count;counter++){
            temp_value=random.nextInt(hexChars.length);
            return_value.append(hexChars[temp_value]);
        }
        return return_value.toString();
    }
	
	/** записать по указанному уникальному номеру клиента (ID) из таблицы SATELLITE_CLIENTS */
	public boolean updateSatelliteClientsSetBonPaySessionId(Integer id, String bonpaySession){
		boolean returnValue=false;
		Session session=null; Connection connection=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			
			SatelliteClients record=(SatelliteClients)session.get(SatelliteClients.class, id);
			if(record!=null){
				session.beginTransaction();
				record.setBonpaySessionId(bonpaySession);
				session.save(record);
				session.getTransaction().commit();
				returnValue=true;
			}else{
				throw new Exception("Satellite Clients by kod is not finded:"+id);
			}
		}catch(Exception ex){
			error("updateSatelliteClientsSetBonPaySessionId:"+ex.getMessage());
		}finally{
			try{
				Connector.closeSession(session,connection);
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	/** получить на основании объекта массив байт для возможного сохранения 
	 * @param object
	 * @throws Exception - если не удалось валидно получить массив из байт 
	 * */
	public byte[] convertSerializableToByteArray(Serializable object) throws Exception {
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		ObjectOutputStream oos=new ObjectOutputStream(baos);
		oos.writeObject(object);
		oos.close();
		return baos.toByteArray();
	}
	
	/** получить на основании массива байт объект 
	 * @param array 
	 * @throws Exception - если не удалось валидно получить объект из массива 
	 * */
	public Object convertByteArrayToSerializable(byte[] array) throws Exception {
		ByteArrayInputStream bais=new ByteArrayInputStream(array);
		ObjectInputStream oos=new ObjectInputStream(bais);
		Object returnValue=oos.readObject();
		bais.close();
		oos.close();
		return returnValue;
	}
	
	/** получить состояние клиента по его уникальному номеру 
	 * @param clientId - уникальный номер клиента 
	 * */
	public Integer getSatelliteClientState(Integer clientId){
		Integer returnValue=new Integer(999);
		Session session=null; Connection connection=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			SatelliteClients satelliteClients=(SatelliteClients)session.createCriteria(SatelliteClients.class).add(Restrictions.eq("id", clientId)).uniqueResult();
			returnValue=satelliteClients.getClientState();
		}catch(Exception ex){
			error("getSatelliteClientState:"+ex.getMessage());
		}finally{
			try{
				Connector.closeSession(session,connection);
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	/** получить из данной HTTP сессии ClientId и проверить его состояние
	 * @param request - HttpRequest
	 * @return <li> true - клиент существует, состояние 0 
	 * */
	public boolean checkAccessToBonPay(HttpServletRequest request){
		boolean returnValue=false;
		Integer clientId=(Integer)request.getSession().getAttribute("client_id");
		System.out.println("checkAccessToBonPay: ClientId:"+clientId);
		if(clientId!=null){
			Integer clientState=getSatelliteClientState(clientId);
			System.out.println("checkAccessToBonPay:       ClientState:"+clientState);
			if((clientState==null)||(clientState==0)){
				// состояние только что подключившегося клиента
				returnValue=true;
			}else{
				// данный клиент завершил транзакции в нашей системе
				returnValue=false;
			}
		}else{
			// нет такого клиента в нашей системе 
			returnValue=false;
		}
		return returnValue;
	}

	/** получить из данной HTTP сессии ClientId и проверить его состояние
	 * @param request - HttpRequest
	 * @return <li> true - клиент существует, состояние 0 
	 * */
	public boolean checkAccessToBonPayForResult(HttpServletRequest request){
		boolean returnValue=false;
		Integer clientId=(Integer)request.getSession().getAttribute("client_id");
		System.out.println("checkAccessToBonPay: ClientId:"+clientId);
		if(clientId!=null){
			returnValue=true;
		}else{
			// нет такого клиента в нашей системе 
			returnValue=false;
		}
		return returnValue;
	}
	
	/** установить/создать уникальный номер клиента для SATELLITE_CLIENTS.ID 
	 * @param clientId - уникальный номер для клиента 
	 * */
	public void setHttpKeyByClientId(Integer clientId){
		Session session=null; Connection connection=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			SatelliteClients satelliteClients=(SatelliteClients)session.createCriteria(SatelliteClients.class).add(Restrictions.eq("id", clientId)).uniqueResult();
			// исправление Tomcat Apache Bug
			if(satelliteClients.getSatelliteClientHttpKey()==null){
				satelliteClients.setSatelliteClientHttpKey(getUniqueChar(15));
				session.beginTransaction();
				session.save(satelliteClients);
				session.getTransaction().commit();
			}
		}catch(Exception ex){
			error("setHttpKeyByClientId:"+ex.getMessage());
		}finally{
			try{
				Connector.closeSession(session,connection);
			}catch(Exception ex){};
		}
	}
	
	/** получить уникальный номер для клиента, который нужно установить на странице JSP и/или в сервлете, для скрытия реального SATELLITE_CLIENTS.ID
	 * @param clientId - уникальный номер клиента, для которого необходимо произвести установку значения
	 * */
	public String getHttpKeyByClientId(Integer clientId){
		String returnValue=null;
		Session session=null; Connection connection=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			SatelliteClients satelliteClients=(SatelliteClients)session.createCriteria(SatelliteClients.class).add(Restrictions.eq("id", clientId)).uniqueResult();
			returnValue=satelliteClients.getSatelliteClientHttpKey();
		}catch(Exception ex){
			error("getHttpKeyByClientId:"+ex.getMessage());
		}finally{
			try{
				Connector.closeSession(session,connection);
			}catch(Exception ex){};
		}
		return returnValue;
	}
	/** получить идентификатор клиента SATELLITE.ID на основании скрытого номера  
	 * @param clientId - уникальный номер клиента, для которого необходимо произвести установку значения
	 * */
	public Integer getClientIdByHttpKey(String httpKey){
		Integer returnValue=null;
		Session session=null; Connection connection=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			SatelliteClients satelliteClients=(SatelliteClients)session.createCriteria(SatelliteClients.class).add(Restrictions.eq("satelliteClientHttpKey", httpKey)).uniqueResult();
			returnValue=satelliteClients.getId();
		}catch(Exception ex){
			error("getHttpKeyByClientId:"+ex.getMessage());
		}finally{
			try{
				Connector.closeSession(session,connection);
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	/** получить по указанному алгоритму и шагу (-1 нулевой шаг) SpecTp, который должен быть следующим 
	 * @param algorithmId - уникальный идентификатор алгоритма
	 * @param stepId - уникальный идентификатор текущего SpecTp ( или -1 - в случае начала)
	 * */
	@SuppressWarnings("unchecked")
	public Integer getSpecTpNextForAlgorithm(Integer algorithmId, Integer stepId, Session session) {
		Integer returnValue=null;
		try{
			List<SatelliteAlgorithmStep> list=(List<SatelliteAlgorithmStep>)session.createCriteria(SatelliteAlgorithmStep.class).add(Restrictions.eq("idAlgorithm",algorithmId)).addOrder(Order.asc("id")).list();
			if((stepId==null)||(stepId<0)){
				// получить значение первого шага
				returnValue=list.get(0).getSpecTp();
			}else{
				// найти указанный шаг и вернуть его, если шаг не найден - вернуть текущий
				returnValue=stepId;
				for(int counter=0;counter<list.size();counter++){
					if(list.get(counter).getSpecTp()==stepId){
						if(counter<(list.size()-1)){
							// шаг не последний - передать следующий шаг
							returnValue=list.get(counter+1).getSpecTp();
						}else{
							// это был последний шаг - его и нужно передавать
							returnValue=stepId;
						}
					}
				}
			}
		}catch(Exception ex){
			error("#getSpecTpNextForAlgorithm Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	
	/** записать по данному клиенту сумму заказа ( заказ оплачен успешно ) 
	 * @param clientId - уникальный номер клиента
	 * @param amount - кол-во копеек для записи 
	 * */
	public void setAmountByClient(Integer clientId, Integer amount){
		Session session=null; Connection connection=null;
		try{
			connection=Connector.getConnection();session=Connector.openSession(connection);
			SatelliteClients satelliteClients=(SatelliteClients)session.createCriteria(SatelliteClients.class).add(Restrictions.eq("id", clientId)).uniqueResult();
			session.beginTransaction();
			satelliteClients.setAmountInCent(amount);
			satelliteClients.setClientState(1);// set client state leaves OK
			session.update(satelliteClients);
			session.getTransaction().commit();
		}catch(Exception ex){
			error("setHttpKeyByClientId:"+ex.getMessage());
		}finally{
			try{
				Connector.closeSession(session,connection);
			}catch(Exception ex){};
		}
	}

	/** записать по данному клиенту сумму заказа ( заказ оплачен успешно ) 
	 * @param clientId - уникальный номер клиента
	 * @param amount - кол-во копеек для записи 
	 * @param session - сессия, по которой приосходит работа с базой данных
	 * @param commit - нужно ли делать сессии Commit
	 * 	<li> true  - нужно делать сommit</li>
	 *  <li> false - не нужно делать commit</li>
	 * */
	public void setAmountByClient(Integer clientId, Integer amount, Session session,boolean commit){
		try{
			SatelliteClients satelliteClients=(SatelliteClients)session.createCriteria(SatelliteClients.class).add(Restrictions.eq("id", clientId)).uniqueResult();
			if(session.getTransaction().isActive()==false){
				session.beginTransaction();
			}
			satelliteClients.setAmountInCent(amount);
			satelliteClients.setClientState(1);// set client state leaves OK
			session.update(satelliteClients);
			if(commit==true){
				session.getTransaction().commit();
			}
		}catch(Exception ex){
			error("setHttpKeyByClientId:"+ex.getMessage());
		}
	}
	
	/**
	 * записать по данному клиенту сумму заказа ( заказ оплачен успешно ) 
	 * @param request - запрос, который осуществил клиент для входа на ресурс  
	 * */
	public void setAmountByClient(HttpServletRequest request){
	  	Integer clientId=(Integer)request.getSession().getAttribute("client_id");
	  	String xmlOrder=this.getSatelliteClientParameterByName(clientId,"order");
	  	pay.transport.xml.Order order=new pay.transport.xml.Order(xmlOrder);
	  	setAmountByClient(clientId,order.getAmountInCent());
	}
	
	
	/* создать уникальный код для клиента - запись в таблице CLIENTS 
	 * @param satelliteName - уникальное имя партнера (Satellite)
	 * @param unique_key_satellite - уникальный ключ для данного клиента в масштабе Satellite 
	 * @param unique_key_service - уникальный ключ для данного клиента в масштабе Service 
	 * @param returnPath - путь, по которому нужно направить данного клиента для возврата его на ресурс Satellite
	 * @return true - если обновление данных прошло успешно
	public static boolean createClients(Wrap wrap,String unique_key_service){
		boolean return_value=false;
		Session session=null; Connection connection=null;
		try{
			session=Connector.getSession();
			Transaction transaction=session.beginTransaction();
			// create Clients 
			int satelliteId=getSatelliteIdFromSatelliteName(wrap.getSecurity().getSatelliteId());
			Clients newClients=new Clients();
			newClients.setId_client_state(0);
			newClients.setId_satellite(satelliteId);
			newClients.setUnique_key_satellite(wrap.getSecurity().getResponseKey());
			newClients.setUnique_key_service(unique_key_service);
			session.save(newClients);
			transaction.commit();
			return_value=true;
		}catch(Exception ex){
			error("createClients Exception:"+ex.getMessage());
		}finally{
			if(session!=null){
				Connector.closeSession(session,connection);
			}
		}
		return return_value;	
	}
	 */


	/* получить следующий ключ доступа к ресурсу Service для интернет-магазина (Satellite)<br>
	 * сгенерировать новое значение SATELLITE.KEY_FOR_SERVICE
	 * @param satelliteName - уникальное имя Партнера ( Satellite )
	 * @throws Exception - если не найдена запись по данному партнеру 
	 * @return ключ, который должен быть получен при следующем соединении с данным Satellite
	public static String getNextAccessKey(String satelliteName) throws Exception {
		Session session=null; Connection connection=null;
		String key=null;
		boolean returnKey=true;
		try{
			session=Connector.getSession();
			// получить запись по указанному SatelliteId
			Satellite satellite=((Satellite)session.createCriteria(Satellite.class).add(Restrictions.like("name",satelliteName)).uniqueResult());
			// сгенерировать новый уникальный ключ
			key=getUniqueChar(10)+Integer.toString(satellite.getId());
			// обновить ключ в записи
			satellite.setKey_for_service(key);
			Transaction transaction=session.beginTransaction();
			session.save(satellite);
			transaction.commit();
			returnKey=true;
			// вернуть ключ 
		}catch(Exception ex){
			returnKey=false;
		}finally{
			if(session!=null){
				Connector.closeSession(session,connection);
			}
		}
		if(returnKey==false){
			throw new Exception("getNextAccessKey error in create Next Access Key");
		}else{
			return key;
		}
	}
*/
	
	/* получить ключ доступа к ресурсу Service для интернет-магазина (Satellite)<br>
	 * SATELLITE.KEY_FOR_SERVICE
	 * @param satelliteName - уникальное имя Партнера ( Satellite )
	 * @throws Exception - если не найдена запись по данному партнеру 
	 * @return ключ, который должен быть получен при следующем соединении с данным Satellite
 * 	public static String getAccessKey(String satelliteName){
		Session session=null; Connection connection=null;
		String key=null;
		try{
			session=Connector.getSession();
			// получить запись по указанному SatelliteId
			Satellite satellite=((Satellite)session.createCriteria(Satellite.class).add(Restrictions.like("name",satelliteName)).uniqueResult());
			// сгенерировать новый уникальный ключ
			key=satellite.getKey_for_service();
		}catch(Exception ex){
			error("getAccessKey:"+ex.getMessage());
		}finally{
			if(session!=null){
				Connector.closeSession(session,connection);
			}
		}
		return key;
	}
*/	
}
