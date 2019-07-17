package com.cherkashin.vitaliy.rfid.web_server.client.view.edit;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IPeopleEditManagerAsync {

	void getAllPeople(AsyncCallback<People[]> callback);

	void removePeople(int kod, AsyncCallback<String> callback);

	void savePeople(Integer kod, String name, String surname, String fatherName, String cardNumber, AsyncCallback<String> callback);

	void getLastCardWithoutUser(AsyncCallback<Card[]> callback);

	void getPeople(Integer kod, AsyncCallback<People> callback);

}
