package com.cherkashin.vitaliy.rfid.web_server.client.view.edit;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("people_edit_manager")
public interface IPeopleEditManager extends RemoteService{
	/** сохранить нового пользователя
	 * @param name - имя
	 * @param surname - фамилия
	 * @param fatherName - отчество
	 * @param cardNumber - уникальный номер карты 
	 * @return 
	 * <li> <b>null</b> - сохранение прошло успешно</li>
	 * <li> <b>text</b> - ошибка сохранения данных </li>
	public String savePeople(String name, String surname, String fatherName, String сardNumber);
	 */
	
	/** получить список всех работников */
	public People[] getAllPeople();
	
	/** удалить работника по коду
	 * @param kod - уникальный код работника 
	 * @return - 
	 * <li> <b>null</b> - удаление прошло успешно</li>
	 * <li> <b>text</b> - ошибка удаления данных </li>
	 */
	public String removePeople(int kod);

	/** сохранить People, либо же обновить его, если kod is null 
	 * @param kod if null then UPDATE else INSERT
	 * @param name
	 * @param surname
	 * @param fatherName
	 * @param cardNumber
	 * @return
	 */
	public String savePeople(Integer kod, String name, String surname, String fatherName, String cardNumber);
	
	/** получить последние карты, который не привязаны ни к одному пользователю */
	public Card[] getLastCardWithoutUser();

	/** получить People по уникальному номеру */
	public People getPeople(Integer kod);
}
