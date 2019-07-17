package connector;

/** интерефейс, который содержит методы для хранения и получения имени пользователя по сессии*/
public interface IUserSession {
	/** сохранить связку "User Name" - "Session Id"
	 * @return true - данные успешно сохранены 
	 * */
	public boolean put(String user, String sessionId);
	
	/** получить "User Name" по "Session Id" 
	 * @return вернуть User Name или null, если не найден такой номер сессии в связки "Пользователь"-"Имя сессии" 
	 * */
	public String getUser(String sessionId);
	
	/** удалить данную сессию из связки "User" - "SessionId"
	 * @return вернуть true если данный номер был найден и сохранен
	 * */
	public boolean clear(String sessionId);
	
	/** 
	 * проверить зарегестрированы ли по пользователю SessionId
	 * @return true - есть по данному пользователю базы данных HttpSession, <br> 
	 * false - нет по данному пользователю ни одной сессии
	 * 			 
	 */
	public boolean hasSessionsByUser(String user);
}
