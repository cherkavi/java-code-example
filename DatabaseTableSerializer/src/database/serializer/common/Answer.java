package database.serializer.common;

import java.io.Serializable;

/** класс, который служит для ответа на переданные данные */
public class Answer implements Serializable{
	private final static long serialVersionUID=1L;
	
	/** полученный пакет успешно обработан */
	public static final int OK=0;
	/** отмена обработки полученного пакета, возможно, не найдены интерцепторы полученный пакет не нашел ни одного интерцептора */
	public static final int CANCEL=1;
	/** ошибка обработки полученного пакета */
	public static final int ERROR=2;

}
