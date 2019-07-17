package database.watchdog;

import java.sql.*;
import java.util.Date;

/** класс, который организовывает поток-демон, который с указанным интервалом начинает записывать инкрементное значение в указанную таблицу<br> 
 * <b>Singleton</b> 
 * */
public class WatchDog implements Runnable{
	private static WatchDog field_this=null;
	/** поток-демон, который с указанным интервалом начинает записывать инкрементное значение в указанную таблицу через указанные интервалы времени 
	 * @param delay - задержка в милисекундах
	 * @param connection - уникальное соединение с базой данных 
	 * @param table_name - имя таблицы
	 * @param table_field_id - уникальный идентификатор для таблицы
	 * @param table_field_counter - имя поля, в которое нужно записывать уникальное значение
	 * @param table_field_date - дата, которую нужно записывать 
	 */
	public static WatchDog start(int delay,
									   Connection connection, 
									   String table_name,
									   String table_field_id,
									   String table_field_counter, 
									   String table_field_date){
		if(field_this==null){
			field_this=new WatchDog(delay, connection,table_name, table_field_id, table_field_counter,table_field_date);
		}
		return field_this;
	}

	private static void error(String information){
		System.out.print("WatchDog");
		System.out.print(" ERROR: ");
		System.out.println(information);
	}
	
	private Connection field_connection;
	private String field_table_name;
	private String field_table_field_id;
	private String field_table_field_counter;
	private String field_table_field_date;
	
	private Thread field_thread;
	private int field_current_id;
	private int field_current_counter;
	private Date field_current_date;
	private int  field_delay;
	/** поток-демон, который с указанным интервалом начинает записывать инкрементное значение в указанную таблицу через указанные интервалы времени
	 * <b>Singleton</b> 
	 * @param delay - задержка в милисекундах
	 * @param connection - уникальное соединение с базой данных 
	 * @param table_name - имя таблицы
	 * @param table_field_id - уникальный идентификатор для таблицы
	 * @param table_field_counter - имя поля, в которое нужно записывать уникальное значение
	 * @param table_field_date - дата, которую нужно записывать 
	 */
	private WatchDog(int delay,
					Connection connection, 
					String table_name,
					String table_field_id,
					String table_field_counter, 
					String table_field_date){
		this.field_delay=delay;
		this.field_connection=connection;
		this.field_table_name=table_name;
		this.field_table_field_id=table_field_id;
		this.field_table_field_counter=table_field_counter;
		this.field_table_field_date=table_field_date;
		// создать новую запись в таблице
		this.field_current_id=createNewRecord();
		// запустить поток
		this.field_thread=new Thread(this);
		this.field_thread.setPriority(Thread.MIN_PRIORITY);
		this.field_thread.setDaemon(true);
		this.field_thread.start();
	}

	/** создать новую запись в таблице и вернуть значение для ID */
	private int createNewRecord(){
		int return_value=0;
		try{
			field_connection.setAutoCommit(true);
			ResultSet rs=field_connection.createStatement().executeQuery("SELECT MAX("+this.field_table_field_id+") FROM "+this.field_table_name);
			if(rs.next()){
				return_value=rs.getInt(1);
			};
			return_value++;
			rs.close();
			field_connection.createStatement().executeUpdate("INSERT INTO "+this.field_table_name+" ("+this.field_table_field_id+") VALUES ("+return_value+")");
		}catch(SQLException ex){
			error(" new record is not created ");
		}
		return return_value;
	}
	
	public void run(){
		PreparedStatement update_query=null;
		try{
			update_query=this.field_connection.prepareStatement("UPDATE "+this.field_table_name+" SET "+this.field_table_field_counter+"=?, "+this.field_table_field_date+"=? where "+this.field_table_field_id+"=?");
		}catch(SQLException ex){
			error(" FAIL init Thread SQLException:"+ex.getMessage());
		}catch(Exception ex){
			error(" FAIL init Thread    Exception:"+ex.getMessage());
		}
		
		if(update_query!=null){
			while(true){
				try{
					update_query.setInt(1,this.field_current_counter);
					update_query.setTimestamp(2, new Timestamp((new Date()).getTime()));
					update_query.setInt(3, this.field_current_id);
					update_query.executeUpdate();
				}catch(SQLException ex){
					error("run: SQLException:"+ex.getMessage());
				}catch(Exception ex){
					error("run:    Exception:"+ex.getMessage());
				}
				this.field_current_counter++;
				try{
					Thread.sleep(this.field_delay);
				}catch(InterruptedException ex){
					
				}
				System.out.println("WatchDog update:"+this.field_current_counter);
			}
		}else{
			// exit from Thread
		}
	}
}









