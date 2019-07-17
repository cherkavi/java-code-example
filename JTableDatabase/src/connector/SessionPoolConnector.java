package connector;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Set;

public class SessionPoolConnector {

	private void outError(Object information){
		System.out.print("SessionPoolConnector");
		System.out.print("  ERROR  ");
		System.out.println(information);
	}
	
	/** pool of Connection for user's [User Name, HibernateOracleConnect] (1:1) */
	private HashMap <String,PoolConnect> connectionUserPool=new HashMap<String,PoolConnect>();
	private UserSessionManager userSession=new UserSessionManager();
	private Class<? extends PoolConnect> connectorClass;
	private Integer poolSize=new Integer(10);
	
	/** объект, который создает Pool соединений по указанному клиенту, а возвращает Connection по SessionId
	 * @param передается класс, который имеет конструктор следующего содержания 
	 * (String UserName, String Password, Integer poolSize)
	 * 
	 * 
	 * */
	public SessionPoolConnector(Class<? extends PoolConnect> connectorClass, Integer poolSize){
		this.connectorClass=connectorClass;
		if(poolSize!=null){
			this.poolSize=poolSize;
		}
	}
	

	/** получить Connection на основании введенных данных 
	 * @param userName имя пользователя
	 * @param password пароль
	 * @param sessionId уникальный номер сессии 
	 * @return Connection 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * */
	public Connection getConnection(String userName, 
								    String password, 
									String sessionId) {
		Connection returnValue;
		// проверка наличия связки - "имя пользователя"-"POOL"
		if(connectionUserPool.containsKey(userName)==true){
			// Pool создан по данному пользователю 
			if(connectionUserPool.get(userName).isPasswordEquals(password)){
				returnValue=connectionUserPool.get(userName).getConnection();
				this.userSession.put(userName, sessionId);
			}else{
				// Password is incorrect
				returnValue=null;
			}
		}else{
			// Pool не создан по данному объекту
			try{
				// создать объект и положить его в хранилище
				Constructor<? extends PoolConnect> constructor=this.connectorClass.getConstructor(String.class,String.class,Integer.class);
				PoolConnect newConnection=constructor.newInstance(userName, password, this.poolSize);
				// получить Connection из указанного Pool
				returnValue=newConnection.getConnection();
				if(returnValue==null){
					throw new Exception();
				}
				this.connectionUserPool.put(userName, newConnection);
				this.userSession.put(userName, sessionId);
			}catch(Exception ex){
				returnValue=null;
				System.out.println("SessionPoolConnector Exception: "+ex.getMessage());
			}
		}
		return returnValue;
	}

	/** вернуть Connection в POOL */
	public void closeConnection(Connection connection){
		try{
			connection.close();
		}catch(Exception ex){
			outError("closeConnection dropped:"+ex.getMessage());
		}
	}

	/** Получить Connection из POOL по указанному сессионному идентификатору 
	 * @param sessionId - уникальный номер сессии, по которому нужно получить Connection
	 * */
	public Connection getConnection(String sessionId){
		String user=this.userSession.getUser(sessionId);
		
		if(user==null){
			return null;
		}else{
			return this.connectionUserPool.get(user).getConnection();
		}
	}

	public void removeSessionId(String sessionId){
		String user=this.userSession.getUser(sessionId);
		this.userSession.clear(sessionId);
		if(this.userSession.hasSessionsByUser(user)==false){
			try{
				this.connectionUserPool.get(user).reset();
			}catch(Exception ex){
				// user:Pool is not finded
			}
		}
	}
	
	/** удалить по указанному пользователю все соединения */
	public boolean removeSessionByUser(String userName){
		boolean returnValue=true;
		try{
			this.connectionUserPool.get(userName).reset();
		}catch(Exception ex){
			System.err.println("SessionPoolConnector#removeSessionByUser UserName:"+userName+"  close Exception:"+ex.getMessage());
			returnValue=false;
		}
		return returnValue;
	}
	
	/** получить кол-во пользователей, по которым создан PoolConnection */
	public int getUserPoolCount(){
		return this.connectionUserPool.size();
	}
	
	/** получить имена пользователей, по которым созданы пулы */
	public Set<String> getUsersIntoPool(){
		return this.connectionUserPool.keySet();
	}
	
	/** получить по указанному пользователю кол-во открытых соединений */
	public int getConnectionCountByUser(String user){
		return this.connectionUserPool.get(user).getConnectionCount();
	}
	
	public void printConnection(PrintStream out){
		if(out==null){
			out=System.out;
		}
		out.println("Connection:");
		Set<String> users=getUsersIntoPool();
		for(String user:users){
			out.println("User:"+user+"    ConnectionCount:"+getConnectionCountByUser(user));
		}
		out.println("-----------");
	}
}
