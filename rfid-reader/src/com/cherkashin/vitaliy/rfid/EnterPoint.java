package com.cherkashin.vitaliy.rfid;

import java.io.FileInputStream;


import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.comm.PortInUseException;
import javax.comm.UnsupportedCommOperationException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import reader.IOutputBlockListener;
import reader.ReaderAsync;
import com_port.listener.ISocketPortListener;
import com_port.listener.SocketHeartBeatProgramRunner;
import com_port.listener.SocketPortListener;
import database.Connector;

@SuppressWarnings("restriction")
public class EnterPoint implements IOutputBlockListener, ISocketPortListener{
	private Logger logger=Logger.getLogger(this.getClass());
	
	public static void main(String[] args){
		try{
			if(args.length==0){
				System.err.println(" <config.properties>");
				System.out.println("#порт, на который будут приходить данные ");
				System.out.println("tcp_port_data=20101");
				System.out.println("#порт, который прослушивает сигнал сердцебиения ");
				System.out.println("tcp_port_heart_beat=20102");
				System.out.println("#время ожидания сигнала сердцебиения (в милисекундах )");
				System.out.println("time_heart_beat=5000 ");
				System.out.println("#команда запуска программы чтения из порта <имя порта> <rate:2400,4800,9600,...115200> <databit: 5,6,7,8> <stop bit: 1,1.5, 2> <parity: none, even, odd, even, mark, none, odd, space> <server> <port of data> <port of heart beat> <delay of heart beat>");
				System.out.println("run_command=java -jar ComPortReader.jar COM5 9600 8 1 none 127.0.0.1 20101 20102 4000");
				System.out.println("# specified URL of jdbc");
				System.out.println("jdbc_url=");
				System.out.println("# Database User");
				System.out.println("jdbc_user=");
				System.out.println("# Database Password");
				System.out.println("jdbc_password=");
				System.out.println("#logger Level: DEBUG, INFO, WARN, ERROR ");
				System.out.println("logger_level=ERROR ");
				System.out.println("#logger file: path to file or null(console)");
				System.out.println("logger_file=null ");
				System.exit(1);
			}
			Properties properties=new Properties();
			properties.load(new FileInputStream(args[0]));
			
			Connector connector=new Connector(properties.getProperty("jdbc_url"),properties.getProperty("jdbc_user"),properties.getProperty("jdbc_password"));
			if(connector.getConnection()==null){
				throw new Exception("connection error ");
			}
			setRootLoggerLevel(properties.getProperty("logger_level"));
			setRootLoggerAppender(properties.getProperty("logger_file"));
			new EnterPoint(Integer.parseInt(properties.getProperty("tcp_port_data")),Integer.parseInt(properties.getProperty("tcp_port_heart_beat")),Integer.parseInt(properties.getProperty("time_heart_beat")),properties.getProperty("run_command"), connector);
		}catch(PortInUseException portInUse){
			System.err.println("Your COM port use another application");
			System.err.println("Exception ex:"+portInUse.getMessage());
		}catch(UnsupportedCommOperationException paramEx){
			System.err.println("Parameter's does not supported");
			System.err.println("Exception ex:"+paramEx.getMessage());
		}catch(Exception ex){
			System.err.println("Check your COM port ");
			System.err.println("Exception ex:"+ex.getMessage());
		}
	}
	
	private static void setRootLoggerAppender(String file){
		if((file!=null)&&(!file.trim().equalsIgnoreCase("null"))){
			try{
				Logger.getRootLogger().addAppender(new FileAppender(new PatternLayout("%d{ABSOLUTE} %5p %c{1}:%L - %m%n"),file));
			}catch(Exception ex){
				Logger.getRootLogger().addAppender(new ConsoleAppender(new PatternLayout("%d{ABSOLUTE} %5p %c{1}:%L - %m%n")));
			}
		}else{
			Logger.getRootLogger().addAppender(new ConsoleAppender(new PatternLayout("%d{ABSOLUTE} %5p %c{1}:%L - %m%n")));
		}
	}
	
	private static void setRootLoggerLevel(String level){
		if(level!=null){
			if(level.trim().equalsIgnoreCase("DEBUG")){
				Logger.getRootLogger().setLevel(Level.DEBUG);
			}
			if(level.trim().equalsIgnoreCase("INFO")){
				Logger.getRootLogger().setLevel(Level.INFO);
			}
			if(level.trim().equalsIgnoreCase("WARN")){
				Logger.getRootLogger().setLevel(Level.WARN);
			}
			if(level.trim().equalsIgnoreCase("ERROR")){
				Logger.getRootLogger().setLevel(Level.ERROR);
			}
		}
	}
	
	private Connector connector;
	private ReaderAsync reader=null;
	
	/**
	 * @param portListener - порт, на который приходит информация о данных, прочитанных с порта
	 * @param portHeartBeat - порт, на который приходит информация сердцебиения с порта
	 * @param delayHeartBeat - задержка перед очередным считыванием данных с порта
	 * @param execProgram - строка для запуска программы 
	 * @param connector - соединитель с базой данных 
	 * @throws Exception
	 */
	public EnterPoint(int portListener, int portHeartBeat, int delayHeartBeat, String execProgram,  Connector connector) throws Exception{
		try{
			(new SocketPortListener(portListener)).addDataListener(this);
			new SocketHeartBeatProgramRunner(portHeartBeat, delayHeartBeat, execProgram);
			this.connector=connector;
			reader=new ReaderAsync(new byte[]{0x02}, new byte[]{0x0d, 0x0a, 0x03},50);
			reader.startReader();
			reader.addOutputBlockListener(this);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			this.logger.error("Start: "+sdf.format(new java.util.Date()));
		}catch(Exception ex){
			logger.error("Constructor Exception:"+ex.getMessage());
		}
	}

	
	@Override
	public void notifyBlock(byte[] array) {
		this.logger.debug("detect card number");
		Connection connection=this.connector.getConnection();
		// INFO сохранить в базу данных 
		String cardData=this.getCardData(array);
		Integer userId=this.getUserId(connection,cardData);
		this.logger.debug("save card ("+cardData+") to database by user ("+userId+"): ");
		if(this.saveToDatabase(connection, userId, cardData)==false){
			logger.error("saveToDatabase error");
		}
	}
	
	/** получить номер карты, отбросив служебные символы */
	private String getCardData(byte[] array){
		try{
			String returnValue=new String(this.subArray(array, 1, array.length-3));
			logger.debug("clear Data: "+returnValue);
			return returnValue;
		}catch(Exception ex){
			logger.error("get clear data from array from Port Exception: "+ex.getMessage());
			return null;
		}
	}
	
	private boolean saveToDatabase(Connection connection, Integer userId, String cardData){
		boolean returnValue=false;
		try{
			String query=null;
			if(userId==null){
				query="insert into rfid(card_number) values('"+cardData+"')";
			}else{
				query="insert into rfid(id_user,card_number) values("+userId+",'"+cardData+"')";
			}
			logger.debug(query);
			connection.createStatement().executeUpdate(query);
			connection.commit();
			returnValue=true;
		}catch(Exception ex){
			logger.error("saveToDatabase Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	
	/** получить уникальный идентификатор пользователя, по указанным данных */
	private Integer getUserId(Connection connection, String cardData){
		Integer returnValue=null;
		ResultSet rs=null;
		try{
			String query="select id from people where card_number='"+cardData+"'";
			logger.debug(query);
			rs=connection.createStatement().executeQuery(query);
			if(rs.next()){
				returnValue=rs.getInt(1);
				if(rs.wasNull()){
					returnValue=null;
				}
			}
			rs.getStatement().close();
		}catch(Exception ex){
			logger.error("getUserId Exception: "+ex.getMessage());
		}
		return returnValue;
	}

	/** */
	private byte[] subArray(byte[] array, int indexBegin, int indexEnd){
		byte[] returnValue=new byte[indexEnd-indexBegin];
		for(int counter=indexBegin;counter<indexEnd;counter++){
			returnValue[counter-indexBegin]=array[counter];
		}
		return returnValue;
	}

	@Override
	public void notifyDataFromPort(byte[] data) {
		reader.inputData(data);
	}
}
