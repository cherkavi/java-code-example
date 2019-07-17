package reader;

/** оповещатель о пришедших данных */
public interface IInputDataListener {
	public void inputData(byte[] data);
}
