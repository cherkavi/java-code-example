package elements.base;

/** состояние элемента */
public abstract class ElementState {
	/** получить двоичное представление объекта для его отправки по сети */
	public abstract byte[] getByteData();
}
