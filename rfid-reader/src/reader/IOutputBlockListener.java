package reader;

/** оповещение о найденных блоках данных */
public interface IOutputBlockListener {
	/** оповещение о найденных блоках данных 
	 * @param array - найденный блок 
	 *  */
	public void notifyBlock(byte[] array);
}
