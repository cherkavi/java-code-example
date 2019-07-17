package com.cherkashin.vitaliy.rfid.web_server.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.cherkashin.vitaliy.rfid.web_server.client.view.show.IShowResult;
import com.cherkashin.vitaliy.rfid.web_server.client.view.show.Result;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import database.ConnectWrap;
import database.StaticConnector;

public class ShowResultImpl extends RemoteServiceServlet implements IShowResult{
	private final static long serialVersionUID=1L;
	
	private Date getDateClearHour(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	private Date getDateNextClearHour(Date date){
		Date returnValue=this.getDateClearHour(date);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(returnValue);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return calendar.getTime();
	}
	
	@Override
	public Result[] getResult(Date dateBegin, Date dateEnd) {
		Result[] returnValue=new Result[]{};
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			StringBuffer query=new StringBuffer();
			query.append("select Cast(rfid.time_write as Date) date_for_scan, \n");
			query.append("	people.name,\n");
			query.append("	people.surname, \n");
			query.append("	(select first 1 rfid_inner.time_write from rfid rfid_inner where cast(rfid.time_write as date)=cast(rfid_inner.time_write as date)) date_in, \n");
			query.append("	(select first 1 rfid_inner.time_write from rfid rfid_inner where cast(rfid.time_write as date)=cast(rfid_inner.time_write as date) order by id desc) date_out \n");
			query.append("	from rfid \n");
			query.append("	inner join people on people.id=rfid.id_user \n");
			query.append("	where rfid.time_write between ? and ? \n");
			query.append("	group by id_user, people.name, people.surname, Cast(rfid.time_write as Date) \n");
			PreparedStatement ps=connector.getConnection().prepareStatement(query.toString());
			ps.setDate(1, new java.sql.Date(this.getDateClearHour(dateBegin).getTime()));
			ps.setDate(2, new java.sql.Date(this.getDateNextClearHour(dateEnd).getTime()));
			ResultSet rs=ps.executeQuery();
			ArrayList<Result> list=new ArrayList<Result>();
			while(rs.next()){
				Result result=new Result();
				try{
					result.setDate( new Date(rs.getDate(1).getTime()));
				}catch(Exception ex){};
				result.setUserName( ((rs.getString(3)==null)?"":rs.getString(3)) +" "+((rs.getString(2)==null)?"":rs.getString(2)));
				try{
					result.setDateIn( new Date(rs.getDate(4).getTime()));
				}catch(Exception ex){};
				try{
					result.setDateOut( new Date(rs.getDate(5).getTime()));
				}catch(Exception ex){};
				try{
					result.setMinutes(0);
					result.setMinutes(  (int) ((result.getDateOut().getTime()-result.getDateIn().getTime())/60000 ));
				}catch(Exception ex){
				}
				list.add(result);
			}
			returnValue=list.toArray(returnValue);
		}catch(Exception ex){
			System.err.println("ShowResult#getResult: "+ex.getMessage());
			returnValue=null;
		}finally{
			connector.close();
		}
		return returnValue;
	}
	

}
