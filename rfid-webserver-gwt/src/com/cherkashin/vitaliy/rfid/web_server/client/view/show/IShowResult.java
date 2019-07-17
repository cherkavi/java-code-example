package com.cherkashin.vitaliy.rfid.web_server.client.view.show;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/** интерфейс по предоставлению данных из базы о пребывании на рабочих местах сотрудников */
@RemoteServiceRelativePath(value="show_result")
public interface IShowResult extends RemoteService{
	public Result[] getResult(Date dateBegin, Date dateEnd);
}
