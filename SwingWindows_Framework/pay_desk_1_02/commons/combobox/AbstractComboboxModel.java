package pay_desk.commons.combobox;

/** класс,который является моделью для Combobox*/
public abstract class AbstractComboboxModel {
	/** получить все элементы, которые находятся в данной модели */
	public abstract Object[] getAllElements();
	
	/** получить выделенный в данный момент элемент*/
	public abstract Object getSelectdElement();
	
	/** установить выделенный в данный момент элемент */
	public abstract void setSelectedElement(Object object);
	
	/** получить ключевой элемент, на основании выделенного элемента */
	public abstract Object getKeySelectedElement();
}
