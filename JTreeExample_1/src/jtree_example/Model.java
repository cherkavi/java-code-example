package jtree_example;

import java.sql.Connection;

public interface Model {
	/** первоначальная загрузка данных */
	public void load(Connection connection) throws Exception;
	/** сохранение данных */
	public void save(Connection connection) throws Exception;
	/** получить общее кол-во всех элементов */
	public int getSize();
	/** получить указанный элемент по индексу */
	public Element getElement(int index);
	/** передвинуть элемент в направлении к нижнему (младшему) индексу */
	public void moveUpElement(int index);
	/** передвинуть элемент в направлении к верхнему (старшему) индексу */
	public void moveDownElement(int index);
	/** найти индекс элемента по известным параметрам */
	public int getIndex(Element element);
	/** переместить секцию на одну секцию вверх*/
	public void moveUpSection(Element element);
	/** переместить секцию на одну секцию вниз  */
	public void moveDownSection(Element element);
}
