package bonpay.partner.database.connector;
import org.hibernate.Session;


import bonpay.partner.database.wrap.*;
import java.sql.Connection;

/** класс, который производит соединение с базой данных, посредстом Hibernate и Connection */
public class Connector {
	private HibernateConnection hibernateConnection=null;

	// INFO - место присоединения всех классов для активации отображения объектов базы данных на объекты программы
	private static Class<?>[] classOfDatabase=new Class[]{
		   PartnerWidgets.class, 
		   Widgets.class,
		   Satellite.class,
		   SatelliteClients.class,
		   SatelliteClientsParameters.class,
		   SatelliteClientState.class,
		   Partner.class,
		   PaySystem.class,
		   PaySystemSatellite.class,
		   LoyalityType.class,
		   SatelliteLoyality.class,
		   LoyalityItem.class,
		   LoyalityTool.class,
		   SatelliteMoneyAccount.class,
		   SatelliteMoneyBon.class,
		   SatelliteMoneyWeb.class
		   };
	
	/**  
	 * @param pathToDataBase - путь к базе данных ("D:\\eclipse_workspace\\BonPay\\DataBase\\bonpay.gdb") 
	 * @param userName - имя пользователя 
	 * @param password - пароль 
	 * @param poolSize - размер пула 
	 * @throws Exception - если не удалось создать Connector  
	 */
	public Connector(String pathToDataBase,
					 String userName, 
					 String password, 
					 int poolSize) throws Exception {
		IConnector connector=new FirebirdConnection(null,pathToDataBase,userName,password,poolSize);
		hibernateConnection=new HibernateConnection(connector,
												    "org.hibernate.dialect.FirebirdDialect",
												    classOfDatabase);
	}
	
	
	/** получить Hibernate Session */
	public Session openSession(Connection connection){
		return hibernateConnection.openSession(connection);
	}
	
	/** получить соединение с базой данных */
	public Connection getConnection(){
		return hibernateConnection.getConnection(); 
	}
	
	/** закрыть Hibernate сессию */
/*	public static void closeSession(Session session){
		try{
			session.disconnect();
			session.close();
		}catch(Exception ex){
			System.out.println("Connector#closeSession Exception: "+ex.getMessage());
		};
	}
*/	
	public void closeSession(Session session, Connection connection){
		try{
			session.disconnect();
		}catch(Exception ex){
			System.out.println("Connector#disconnectSession Exception: "+ex.getMessage());
		};
		try{
			session.close();
		}catch(Exception ex){
			System.out.println("Connector#closeSession Exception: "+ex.getMessage());
		};
		try{
			connection.close();
		}catch(Exception ex){
			System.out.println("Connector#closeConnection Exception: "+ex.getMessage());
		};
	}
	
}
