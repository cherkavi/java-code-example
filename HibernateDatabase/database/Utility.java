package pay.database;

import java.util.Random;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import pay.database.wrap.Clients;
import pay.database.wrap.Satellite;

public class Utility {

	protected static void debug(String value){
		System.out.print("Utility");
		System.out.print(" DEBUG: ");
		System.out.println(value);
	}
	protected static void error(String value){
		System.out.print("Utility");
		System.out.print(" ERROR: ");
		System.out.println(value);
	}
	
	/** получить следующий ключ доступа к ресурсу Service для интернет-магазина (Satellite)<br>
	 * сгенерировать новое значение SATELLITE.KEY_FOR_SERVICE
	 * @param satelliteName - уникальное имя Партнера ( Satellite )
	 * @throws Exception - если не найдена запись по данному партнеру 
	 * @return ключ, который должен быть получен при следующем соединении с данным Satellite
	 * */
	public static String getNextAccessKey(String satelliteName) throws Exception {
		Session session=null;
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
				Connector.closeSession(session);
			}
		}
		if(returnKey==false){
			throw new Exception("getNextAccessKey error in create Next Access Key");
		}else{
			return key;
		}
	}

	
	/** получить ключ доступа к ресурсу Service для интернет-магазина (Satellite)<br>
	 * SATELLITE.KEY_FOR_SERVICE
	 * @param satelliteName - уникальное имя Партнера ( Satellite )
	 * @throws Exception - если не найдена запись по данному партнеру 
	 * @return ключ, который должен быть получен при следующем соединении с данным Satellite
	 * */
	public static String getAccessKey(String satelliteName){
		Session session=null;
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
				Connector.closeSession(session);
			}
		}
		return key;
	}
	
	/** проверить валидность ключа (KEY_FOR_SERVICE) доступа, который прислал Satellite 
	 * @param satelliteName имя Satellite
	 * @param accessKey - ключ, который должен быть сохранен в базе как ключ доступа для данного Satellite
	 * */
	public static boolean checkAccesKeyBySatelliteId(String satelliteName, String accessKey){
		Session session=null;
		boolean return_value=false;
		try{
			session=Connector.getSession();
			// получить запись по указанному SatelliteId
			Satellite satellite=((Satellite)session.createCriteria(Satellite.class).add(Restrictions.like("name",satelliteName)).uniqueResult());
			return_value=satellite.getKey_for_service().equals(accessKey);
		}catch(Exception ex){
			error("checkAccessKeyBySatelliteId Exception:"+ex.getMessage());
		}finally{
			if(session!=null){
				Connector.closeSession(session);
			}
		}
		return return_value;
	}
	
	/** сгенерировать уникальный ключ (без сохранения в базе ) клиента для Satellite <br>
	 * c которым (ключем) данный клиент сможет самостоятельно "зайти" к нам на HTML ресурс */
	public static String generateUniqueKeyForSatellite(String satelliteName){
		try{
			return getUniqueChar(10)+Integer.toString(getSatelliteIdFromSatelliteName(satelliteName));
		}catch(Exception ex){
			return "";
		}
	}
	
	
	/** создать уникальный код для клиента - запись в таблице CLIENTS 
	 * @param satelliteName - уникальное имя партнера (Satellite)
	 * @param unique_key_satellite - уникальный ключ для данного клиента в масштабе Satellite 
	 * @param unique_key_service - уникальный ключ для данного клиента в масштабе Service 
	 * @param returnPath - путь, по которому нужно направить данного клиента для возврата его на ресурс Satellite
	 * @return true - если обновление данных прошло успешно
	 */
	public static boolean createClients(String satelliteName,
									    String unique_key_satellite,
									    String unique_key_service,
									    String returnPath
									    ){
		boolean return_value=false;
		Session session=null;
		try{
			session=Connector.getSession();
			Transaction transaction=session.beginTransaction();
			// create Clients 
			int satelliteId=getSatelliteIdFromSatelliteName(satelliteName);
			Clients newClients=new Clients();
			newClients.setId_client_state(0);
			newClients.setId_satellite(satelliteId);
			newClients.setReturn_url(returnPath);
			newClients.setUnique_key_satellite(unique_key_satellite);
			newClients.setUnique_key_service(unique_key_service);
			session.save(newClients);
			transaction.commit();
			return_value=true;
		}catch(Exception ex){
			error("createClients Exception:"+ex.getMessage());
		}finally{
			if(session!=null){
				Connector.closeSession(session);
			}
		}
		return return_value;	
	}
	
	/** получить уникальный код в масштабе базы данных(таблицы) Satellite */
	public static int getSatelliteIdFromSatelliteName(String satelliteName) throws Exception {
		int return_value=(-1);
		Session session=null;
		try{
			session=Connector.getSession();
			return_value=((Satellite)session.createCriteria(Satellite.class).add(Restrictions.like("name", satelliteName)).uniqueResult()).getId();
		}catch(Exception ex){
			error("getSatelliteIdFromSatelliteName is not finded");
		}finally{
			if(session!=null){
				Connector.closeSession(session);
			}
		}
		// return result
		if(return_value==(-1)){
			throw new Exception("Satellite Id is not finded: "+satelliteName);
		}else{
			return return_value;
		}
	}
	
	public final static String hexChars[] = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
	/** сгенерировать случайную последовательность из Hex чисел, указанной длинны 
	 * @param count - длинна случайной последовательности, которую необходимо получить 
	 * */
	public static String getUniqueChar(int count){
        StringBuffer return_value=new StringBuffer();
        Random random=new java.util.Random();
        int temp_value;
        for(int counter=0;counter<count;counter++){
            temp_value=random.nextInt(hexChars.length);
            return_value.append(hexChars[temp_value]);
        }
        return return_value.toString();
    }
	
}
