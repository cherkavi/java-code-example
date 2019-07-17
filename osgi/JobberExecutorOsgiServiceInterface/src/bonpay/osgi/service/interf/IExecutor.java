package bonpay.osgi.service.interf;

public interface IExecutor {
	/** запустить задачу */
	public void execute();
	/** остановить задачу */
	public void stop();

}
