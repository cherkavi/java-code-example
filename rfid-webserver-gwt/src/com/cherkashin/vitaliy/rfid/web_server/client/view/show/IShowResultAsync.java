package com.cherkashin.vitaliy.rfid.web_server.client.view.show;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IShowResultAsync {

	void getResult(Date dateBegin, Date dateEnd,
			AsyncCallback<Result[]> callback);

}
