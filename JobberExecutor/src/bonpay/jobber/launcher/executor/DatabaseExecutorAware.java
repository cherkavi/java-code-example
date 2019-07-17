package bonpay.jobber.launcher.executor;

import java.sql.CallableStatement;


import java.sql.Connection;
import java.sql.Types;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import database.IConnectionAware;


public class DatabaseExecutorAware implements IExecutorAware{
	private Logger logger=Logger.getLogger(this.getClass());
	private IConnectionAware connectionAware;
	
	public DatabaseExecutorAware(IConnectionAware connectionAware){
		this.connectionAware=connectionAware;
	}
	
	/*private void printResultSet(ResultSet rs){
		System.out.println("---------------------");
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		try{
			while(rs.next()){
				System.out.println("id_job:"+rs.getInt("id_ext_job")+"    next_start:"+sdf.format(new Date(rs.getTimestamp("next_start").getTime()))+"   now: "+sdf.format(new Date()));
			}
		}catch(Exception ex){
		}
	}*/
	
	
	@Override
	public IExecutor[] getExecutors() {
		ArrayList<IExecutor> returnValue=new ArrayList<IExecutor>();
		Connection connection=this.connectionAware.getConnection();
		// прочесть из базы данных те записи, которые нужно обработать
		try{
			ArrayList<ResultSetWrap> list=new ArrayList<ResultSetWrap>();
			ResultSet rs=connection.createStatement().executeQuery(this.getQuery());
			//printResultSet(rs);
			//rs.close();
			//rs=connection.createStatement().executeQuery(this.getQuery());
			
			while(rs.next()){
				logger.info("Executor exists, process");
				IExecutor executor=this.readExecutorFromResultSet(rs);
				if(executor!=null){
					returnValue.add(executor);
					int period=rs.getInt("period_of_start");
					Date nextStart=null;
					try{
						nextStart=new Date(rs.getTimestamp("NEXT_START").getTime());
					}catch(Exception ex){};
					if(nextStart==null){
						nextStart=new Date();
					}
					SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
					//System.out.println("next_start before: "+sdf.format(nextStart));
					Calendar calendar=Calendar.getInstance();
					calendar.setTime(nextStart);
					calendar.add(Calendar.SECOND, period);
					nextStart=calendar.getTime();
					if(nextStart.before(new Date())){
						calendar.setTime(new Date());
						calendar.add(Calendar.SECOND, period);
						nextStart.setTime(calendar.getTime().getTime());
					}
					logger.info("next_start after: "+sdf.format(nextStart));
					list.add(new ResultSetWrap(rs.getInt("id_ext_job"), period, nextStart));
				}
			}
			rs.getStatement().close();
			// установить для каждой запущенной записи новое время 
			for(int counter=0;counter<list.size();counter++){
				if(this.setAsExecuted(connection, list.get(counter).getId(), list.get(counter).getPeriod(), list.get(counter).getTimeWrite())){
					logger.debug("setAsExecuted OK");
				}else{
					logger.error("setAsExecuted Error");
				};
			}
			if(list.size()==0){
				logger.debug("executor's does not exists ");
			}
		}catch(Exception ex){
			logger.error("getExecutors Exception: "+ex.getMessage());
		}finally{
			try{
				connection.close();
			}catch(Exception ex){};
		}
		return returnValue.toArray(new IExecutor[]{});
	}
	
	/** прочесть executor из ResultSet */
	private IExecutor readExecutorFromResultSet(ResultSet rs){
		IExecutor returnValue=null;
		// получить из ResultSet Executor
		try{
			String className=rs.getString("IEXECUTOR_CLASS_FULL");
			Object object=Class.forName(className).getConstructor().newInstance();
			returnValue=(IExecutor)object;
		}catch(Exception ex){
			logger.error(" readExecutorFromResultSet Exception:"+ex.getMessage());
		};
		return returnValue;
	}
	
	/** установить как запущенный/отработанный  */
	private boolean setAsExecuted(Connection connection, int id, int period, Date timeWrite){
		boolean returnValue=false;
		// Connection connection=this.connectionAware.getConnection();
		try{
			// записать как отработанный/запущенный 
			// updateConnection.createStatement().executeQuery(this.getUpdateQuery(rs));
			/* 
			 int id=rs.getInt("id_ext_job");
			 int period=rs.getInt("period_of_start");
			Date nextStart=null;
			try{
				nextStart=new Date(rs.getTimestamp("NEXT_START").getTime());
			}catch(Exception ex){};
			if(nextStart==null){
				nextStart=new Date();
			}
			//SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
			//System.out.println("next_start before: "+sdf.format(nextStart));
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(nextStart);
			calendar.add(Calendar.SECOND, period);
			nextStart=calendar.getTime();
			if(nextStart.before(new Date())){
				calendar.setTime(new Date());
				calendar.add(Calendar.SECOND, period);
				nextStart.setTime(calendar.getTime().getTime());
			}
			*/
			
			//System.out.println("next_start after: "+sdf.format(nextStart));
			// INFO пометить задачу как запущенную, и перевести время выполнения на указанный период секунд в будущее 
			/* PreparedStatement ps=updateConnection.prepareStatement("update jobber set next_start=? where id=?");
			ps.setTimestamp(1, new Timestamp(nextStart.getTime()));
			ps.setInt(2, id);
			ps.executeUpdate();
			updateConnection.commit();
			*/
			/* PACK_UI_EXT_JOB.update_ext_job(
				     p_id_ext_job IN NUMBER  -- ИД задачи
				    ,p_next_start IN DATE -- Дата следующего запуски
				    ,p_result_msg OUT VARCHAR2 -- сообщение об ошибке
				) RETURN VARCHAR2 -- 0 - ошибки нет, другое - ошибка
			*/
			CallableStatement statement = connection.prepareCall("{?= call BC_ADMIN.PACK_UI_EXT_JOB.update_ext_job(?,?,?)}");
			statement.registerOutParameter(1, Types.VARCHAR); // return value
			statement.setInt(2, id); // ИД задачи
			statement.setTimestamp(3, new java.sql.Timestamp(timeWrite.getTime()));
			// statement.setDate(3, new java.sql.Date(nextStart.getTime())); // Дата следующего старта
			statement.registerOutParameter(4, Types.VARCHAR); // сообщение об ошибке
            statement.executeUpdate();
			if(statement.getString(1).equals("0")){
				returnValue=true;
			}else{
				logger.error("setAsExecuted Exception: "+statement.getString(4));
				returnValue=false;
			}
            statement.close();
			connection.commit();
			returnValue=true;
		}catch(Exception ex){
			logger.error("setAsExecuted Exception:"+ex.getMessage());
		}finally{
			try{
				//connection.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	/** запрос к базе данных для получения записей */
	private String getQuery(){
		// получить все записи из базы, для того чтобы из них получить объекты для преобразования в {@link IExecutor}
		// return "select * from jobber where jobber.next_start<'now'";
		// INFO получить запрос к базе данных, на основании которого получить все Jobber-ы, которые нужно выполнить 
		// return "select * from bc_admin.vc_ext_jobs_all where next_start<SYSDATE";
		String query="select * from bc_admin.vc_ext_jobs_all where next_start<to_date('"+sdf.format(new Date())+"','dd.mm.rrrr hh24:mi:ss')";
		logger.debug(query);
		return query;
		
	}
}

class ResultSetWrap{
	private int id;
	private int period;
	private Date timeWrite;	
	
	public ResultSetWrap(int id, int period, Date timeWrite){
		this.id=id;
		this.period=period;
		this.timeWrite=timeWrite;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the period
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * @return the timeWrite
	 */
	public Date getTimeWrite() {
		return timeWrite;
	}

	/**
	 * @param timeWrite the timeWrite to set
	 */
	public void setTimeWrite(Date timeWrite) {
		this.timeWrite = timeWrite;
	}
	
}
